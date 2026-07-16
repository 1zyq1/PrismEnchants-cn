/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.listener;

import com.prismenchants.PrismEnchants;
import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.EnchantManager;
import com.prismenchants.enchant.Rarity;
import com.prismenchants.gui.EnchanterGUI;
import com.prismenchants.gui.TinkererGUI;
import com.prismenchants.item.ItemFactory;
import com.prismenchants.util.Keys;
import com.prismenchants.util.Lang;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.Map;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Sound;
import org.bukkit.entity.HumanEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class GuiListener
implements Listener {
    private static final double REFUND_RATE = 0.85;
    private final EnchantManager manager;

    // Minecraft 1.21.4: XP points needed to go from level-1 to level
    private static int xpForLevel(int level) {
        if (level >= 30) {
            return 9 * level - 158;
        } else if (level >= 15) {
            return 5 * level - 38;
        } else {
            return 2 * level + 7;
        }
    }

    // Total XP points from level 0 to level N (fixed cost basis)
    private static int totalXpForLevels(int levels) {
        int xp = 0;
        for (int i = 0; i < levels; i++) {
            xp += xpForLevel(i);
        }
        return xp;
    }

    // Get player's total accumulated XP in points (level + progress)
    private static int getPlayerTotalXp(Player player) {
        int level = player.getLevel();
        int xp = 0;
        for (int i = 0; i < level; i++) {
            xp += xpForLevel(i);
        }
        xp += (int)(player.getExp() * player.getExpToLevel());
        return xp;
    }

    // Set player's level + progress from an XP total
    private static void setPlayerTotalXp(Player player, int total) {
        if (total <= 0) {
            player.setLevel(0);
            player.setExp(0.0f);
            return;
        }
        int level = 0;
        int remaining = total;
        int needed;
        while (remaining >= (needed = xpForLevel(level))) {
            remaining -= needed;
            level++;
        }
        player.setLevel(level);
        player.setExp(level == 0 ? 0.0f : (float)remaining / (float)needed);
    }

    public GuiListener(EnchantManager enchantManager) {
        this.manager = enchantManager;
    }

    private Lang lang() {
        return PrismEnchants.get().lang();
    }

    @EventHandler
    public void onClick(InventoryClickEvent inventoryClickEvent) {
        InventoryHolder inventoryHolder = inventoryClickEvent.getInventory().getHolder();
        if (inventoryHolder instanceof EnchanterGUI.Holder) {
            this.handleEnchanter(inventoryClickEvent);
        } else if (inventoryHolder instanceof TinkererGUI.Holder) {
            this.handleTinkerer(inventoryClickEvent);
        }
    }

    private void handleEnchanter(InventoryClickEvent inventoryClickEvent) {
        inventoryClickEvent.setCancelled(true);
        ItemStack itemStack = inventoryClickEvent.getCurrentItem();
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return;
        }
        String string = itemStack.getItemMeta().getPersistentDataContainer().get(Keys.GUI_ACTION, PersistentDataType.STRING);
        if (string == null) {
            return;
        }
        Object object = inventoryClickEvent.getWhoClicked();
        if (!(object instanceof Player)) {
            return;
        }
        Player player = (Player)object;
        switch (string) {
            case "apprentice": {
                this.rollTier(player, EnumSet.of(Rarity.COMMON, Rarity.UNCOMMON), EnchanterGUI.cost("apprentice"));
                break;
            }
            case "master": {
                this.rollTier(player, EnumSet.of(Rarity.RARE, Rarity.EPIC), EnchanterGUI.cost("master"));
                break;
            }
            case "legendary": {
                this.rollTier(player, EnumSet.of(Rarity.LEGENDARY, Rarity.MYTHIC), EnchanterGUI.cost("legendary"));
                break;
            }
            case "white": {
                this.buy(player, EnchanterGUI.cost("white"), ItemFactory.whiteScroll());
                break;
            }
            case "black": {
                this.buy(player, EnchanterGUI.cost("black"), ItemFactory.blackScroll());
                break;
            }
        }
    }

    private void rollTier(Player player, EnumSet<Rarity> enumSet, int n) {
        if (!this.charge(player, n)) {
            return;
        }
        // 10% failure chance — you pay but get nothing
        if (ThreadLocalRandom.current().nextDouble() < 0.10) {
            player.sendMessage(this.lang().msg("messages.shop-failed", new String[0]));
            this.play(player, Sound.ENTITY_VILLAGER_NO, 1.0f);
            return;
        }
        ArrayList<CustomEnchant> arrayList = new ArrayList<CustomEnchant>();
        for (CustomEnchant customEnchant : this.manager.all()) {
            if (!enumSet.contains((Object)customEnchant.rarity()) || !this.manager.isEnabled(customEnchant)) continue;
            arrayList.add(customEnchant);
        }
        if (arrayList.isEmpty()) {
            player.sendMessage(this.lang().msg("messages.shop-no-tier", new String[0]));
            int costXp = totalXpForLevels(n);
            int refundXp = (int)(costXp * REFUND_RATE);
            if (refundXp > 0) setPlayerTotalXp(player, getPlayerTotalXp(player) + refundXp);
            return;
        }
        CustomEnchant customEnchant = (CustomEnchant)arrayList.get(ThreadLocalRandom.current().nextInt(arrayList.size()));
        int n2 = 1 + ThreadLocalRandom.current().nextInt(this.manager.maxLevel(customEnchant));
        this.deliver(player, ItemFactory.book(customEnchant, n2));
        player.sendMessage(this.lang().msg("messages.shop-rolled", "enchant", customEnchant.rarity().color() + customEnchant.displayName() + " " + EnchantManager.roman(n2)));
        this.play(player, Sound.UI_TOAST_CHALLENGE_COMPLETE, 1.0f);
    }

    private void buy(Player player, int n, ItemStack itemStack) {
        if (!this.charge(player, n)) {
            return;
        }
        this.deliver(player, itemStack);
        this.play(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f);
    }

    private boolean charge(Player player, int levels) {
        int costXp = totalXpForLevels(levels);
        if (getPlayerTotalXp(player) < costXp) {
            player.sendMessage(this.lang().msg("messages.need-levels", "cost", String.valueOf(levels)));
            this.play(player, Sound.ENTITY_VILLAGER_NO, 1.0f);
            return false;
        }
        setPlayerTotalXp(player, getPlayerTotalXp(player) - costXp);
        return true;
    }

    private void handleTinkerer(InventoryClickEvent inventoryClickEvent) {
        boolean bl;
        int n = inventoryClickEvent.getRawSlot();
        boolean bl2 = bl = n < 36;
        if (bl && !TinkererGUI.isInputSlot(n)) {
            HumanEntity humanEntity;
            inventoryClickEvent.setCancelled(true);
            ItemStack itemStack = inventoryClickEvent.getCurrentItem();
            if (itemStack == null || !itemStack.hasItemMeta()) {
                return;
            }
            String string = itemStack.getItemMeta().getPersistentDataContainer().get(Keys.GUI_ACTION, PersistentDataType.STRING);
            if ("sell".equals(string) && (humanEntity = inventoryClickEvent.getWhoClicked()) instanceof Player) {
                Player player = (Player)humanEntity;
                this.sellAll(player, inventoryClickEvent.getInventory());
            }
        }
    }

    private void sellAll(Player player, Inventory inventory) {
        int n = 0;
        int n2 = 0;
        for (int i = 0; i <= 26; ++i) {
            int n3;
            ItemStack itemStack = inventory.getItem(i);
            if (itemStack == null || (n3 = this.valueOf(itemStack)) <= 0) continue;
            n += n3 * itemStack.getAmount();
            n2 += itemStack.getAmount();
            inventory.setItem(i, null);
        }
        if (n2 == 0) {
            player.sendMessage(this.lang().msg("messages.tinkerer-empty", new String[0]));
            this.play(player, Sound.ENTITY_VILLAGER_NO, 1.0f);
            return;
        }
        int xpValue = totalXpForLevels(n);
        int refundXp = (int)(xpValue * REFUND_RATE);
        if (refundXp > 0) setPlayerTotalXp(player, getPlayerTotalXp(player) + refundXp);
        player.sendMessage(this.lang().msg("messages.tinkerer-sold", "count", String.valueOf(n2), "xp", String.valueOf(refundXp)));
        this.play(player, Sound.ENTITY_EXPERIENCE_ORB_PICKUP, 1.0f);
    }

    private int valueOf(ItemStack itemStack) {
        Map<CustomEnchant, Integer> map = this.manager.getEnchants(itemStack);
        int n = 0;
        for (Map.Entry<CustomEnchant, Integer> entry : map.entrySet()) {
            n += this.rarityValue(entry.getKey().rarity()) * entry.getValue();
        }
        return n;
    }

    private int rarityValue(Rarity rarity) {
        return switch (rarity) {
            default -> throw new IncompatibleClassChangeError();
            case COMMON -> 1;
            case UNCOMMON -> 2;
            case RARE -> 4;
            case EPIC -> 7;
            case LEGENDARY -> 12;
            case MYTHIC -> 20;
        };
    }

    @EventHandler
    public void onClose(InventoryCloseEvent inventoryCloseEvent) {
        if (!(inventoryCloseEvent.getInventory().getHolder() instanceof TinkererGUI.Holder)) {
            return;
        }
        HumanEntity humanEntity = inventoryCloseEvent.getPlayer();
        if (!(humanEntity instanceof Player)) {
            return;
        }
        Player player = (Player)humanEntity;
        for (int i = 0; i <= 26; ++i) {
            ItemStack itemStack2 = inventoryCloseEvent.getInventory().getItem(i);
            if (itemStack2 == null) continue;
            player.getInventory().addItem(itemStack2).values().forEach(itemStack -> player.getWorld().dropItem(player.getLocation(), (ItemStack)itemStack));
            inventoryCloseEvent.getInventory().setItem(i, null);
        }
    }

    private void deliver(Player player, ItemStack itemStack2) {
        player.getInventory().addItem(itemStack2).values().forEach(itemStack -> player.getWorld().dropItem(player.getLocation(), (ItemStack)itemStack));
    }

    private void play(Player player, Sound sound, float f) {
        player.playSound(player.getLocation(), sound, 1.0f, f);
    }
}

