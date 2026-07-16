/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.listener;

import com.prismenchants.PrismEnchants;
import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.EnchantManager;
import com.prismenchants.item.ItemFactory;
import com.prismenchants.util.Keys;
import com.prismenchants.util.Lang;
import java.util.Map;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.PrepareAnvilEvent;
import org.bukkit.inventory.AnvilInventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class AnvilListener
implements Listener {
    private final EnchantManager manager;

    public AnvilListener(EnchantManager enchantManager) {
        this.manager = enchantManager;
    }

    private Lang lang() {
        return PrismEnchants.get().lang();
    }

    @EventHandler
    public void onPrepare(PrepareAnvilEvent prepareAnvilEvent) {
        AnvilInventory anvilInventory = prepareAnvilEvent.getInventory();
        ItemStack itemStack = anvilInventory.getItem(0);
        ItemStack itemStack2 = anvilInventory.getItem(1);
        if (itemStack == null || itemStack2 == null) {
            return;
        }
        if (this.classify(itemStack, itemStack2) == Action.NONE) {
            return;
        }
        prepareAnvilEvent.setResult(itemStack.clone());
    }

    @EventHandler
    public void onClick(InventoryClickEvent inventoryClickEvent) {
        Object object = inventoryClickEvent.getInventory();
        if (!(object instanceof AnvilInventory)) {
            return;
        }
        AnvilInventory anvilInventory = (AnvilInventory)object;
        if (inventoryClickEvent.getRawSlot() != 2) {
            return;
        }
        Object object2 = inventoryClickEvent.getWhoClicked();
        if (!(object2 instanceof Player)) {
            return;
        }
        object = (Player)object2;
        object2 = anvilInventory.getItem(0);
        ItemStack itemStack = anvilInventory.getItem(1);
        if (object2 == null || itemStack == null) {
            return;
        }
        Action action = this.classify((ItemStack)object2, itemStack);
        if (action == Action.NONE) {
            return;
        }
        inventoryClickEvent.setCancelled(true);
        switch (action.ordinal()) {
            case 1: {
                this.applyBook((Player)object, anvilInventory, (ItemStack)object2, itemStack);
                break;
            }
            case 2: {
                this.applyWhite((Player)object, anvilInventory, (ItemStack)object2);
                break;
            }
            case 3: {
                this.applyBlack((Player)object, anvilInventory, (ItemStack)object2);
                break;
            }
        }
    }

    private void applyBook(Player player, AnvilInventory anvilInventory, ItemStack itemStack, ItemStack itemStack2) {
        PersistentDataContainer persistentDataContainer = itemStack2.getItemMeta().getPersistentDataContainer();
        String string = persistentDataContainer.get(Keys.BOOK_ID, PersistentDataType.STRING);
        Integer n = persistentDataContainer.get(Keys.BOOK_LEVEL, PersistentDataType.INTEGER);
        Integer n2 = persistentDataContainer.get(Keys.BOOK_SUCCESS, PersistentDataType.INTEGER);
        Integer n3 = persistentDataContainer.get(Keys.BOOK_DESTROY, PersistentDataType.INTEGER);
        CustomEnchant customEnchant = this.manager.get(string);
        if (customEnchant == null || n == null) {
            return;
        }
        ItemStack itemStack3 = itemStack.clone();
        EnchantManager.ApplyOutcome applyOutcome = this.manager.applyBookRoll(itemStack3, customEnchant, n, n2 == null ? 100 : n2, n3 == null ? 0 : n3);
        this.clearModifier(anvilInventory);
        switch (applyOutcome) {
            case SUCCESS: {
                anvilInventory.setItem(0, null);
                this.give(player, itemStack3);
                this.play(player, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.2f);
                player.sendMessage(this.lang().msg("messages.apply-success", "enchant", customEnchant.rarity().color() + customEnchant.displayName()));
                break;
            }
            case FAIL_PROTECTED: {
                anvilInventory.setItem(0, null);
                this.give(player, itemStack3);
                this.play(player, Sound.ITEM_TRIDENT_RETURN, 1.0f);
                player.sendMessage(this.lang().msg("messages.apply-fail-protected", new String[0]));
                break;
            }
            case DESTROYED: {
                anvilInventory.setItem(0, null);
                this.play(player, Sound.ENTITY_ITEM_BREAK, 0.8f);
                player.sendMessage(this.lang().msg("messages.apply-destroyed", new String[0]));
                break;
            }
            case FAIL_SAFE: {
                anvilInventory.setItem(0, null);
                this.give(player, itemStack.clone());
                this.play(player, Sound.BLOCK_ANVIL_LAND, 1.0f);
                player.sendMessage(this.lang().msg("messages.apply-fail-safe", new String[0]));
                break;
            }
        }
    }

    private void applyWhite(Player player, AnvilInventory anvilInventory, ItemStack itemStack) {
        if (this.manager.isProtected(itemStack)) {
            player.sendMessage(this.lang().msg("messages.already-protected", new String[0]));
            return;
        }
        ItemStack itemStack2 = itemStack.clone();
        this.manager.setProtected(itemStack2, true);
        this.clearModifier(anvilInventory);
        anvilInventory.setItem(0, null);
        this.give(player, itemStack2);
        this.play(player, Sound.BLOCK_BEACON_ACTIVATE, 1.5f);
        player.sendMessage(this.lang().msg("messages.white-applied", new String[0]));
    }

    private void applyBlack(Player player, AnvilInventory anvilInventory, ItemStack itemStack) {
        Map<CustomEnchant, Integer> map = this.manager.getEnchants(itemStack);
        if (map.isEmpty()) {
            player.sendMessage(this.lang().msg("messages.no-enchants", new String[0]));
            return;
        }
        Map.Entry<CustomEnchant, Integer> entry = map.entrySet().iterator().next();
        ItemStack itemStack2 = itemStack.clone();
        this.manager.remove(itemStack2, entry.getKey());
        this.clearModifier(anvilInventory);
        anvilInventory.setItem(0, null);
        this.give(player, itemStack2);
        this.give(player, ItemFactory.book(entry.getKey(), entry.getValue()));
        this.play(player, Sound.BLOCK_GRINDSTONE_USE, 1.0f);
        player.sendMessage(this.lang().msg("messages.stripped", "enchant", entry.getKey().rarity().color() + entry.getKey().displayName()));
    }

    private Action classify(ItemStack itemStack, ItemStack itemStack2) {
        if (!itemStack2.hasItemMeta()) {
            return Action.NONE;
        }
        PersistentDataContainer persistentDataContainer = itemStack2.getItemMeta().getPersistentDataContainer();
        String string = persistentDataContainer.get(Keys.BOOK_ID, PersistentDataType.STRING);
        if (string != null) {
            CustomEnchant customEnchant = this.manager.get(string);
            if (customEnchant != null && this.manager.isEnabled(customEnchant) && customEnchant.category().matches(itemStack.getType())) {
                if (this.manager.getLevel(itemStack, customEnchant.id()) > 0) {
                    return Action.NONE;
                }
                return Action.BOOK;
            }
            return Action.NONE;
        }
        String string2 = persistentDataContainer.get(Keys.SCROLL_TYPE, PersistentDataType.STRING);
        if ("WHITE".equals(string2)) {
            return Action.WHITE;
        }
        if ("BLACK".equals(string2)) {
            return Action.BLACK;
        }
        return Action.NONE;
    }

    private void clearModifier(AnvilInventory anvilInventory) {
        ItemStack itemStack = anvilInventory.getItem(1);
        if (itemStack == null) {
            return;
        }
        if (itemStack.getAmount() > 1) {
            itemStack.setAmount(itemStack.getAmount() - 1);
        } else {
            anvilInventory.setItem(1, null);
        }
    }

    private void give(Player player, ItemStack itemStack2) {
        if (player.getItemOnCursor() == null || player.getItemOnCursor().getType().isAir()) {
            player.setItemOnCursor(itemStack2);
        } else {
            player.getInventory().addItem(itemStack2).values().forEach(itemStack -> player.getWorld().dropItem(player.getLocation(), (ItemStack)itemStack));
        }
    }

    private void play(Player player, Sound sound, float f) {
        player.playSound(player.getLocation(), sound, 1.0f, f);
    }

    private static enum Action {
        NONE,
        BOOK,
        WHITE,
        BLACK;

    }
}

