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
import org.bukkit.event.inventory.ClickType;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class BookApplyListener
implements Listener {
    private final EnchantManager manager;

    public BookApplyListener(EnchantManager enchantManager) {
        this.manager = enchantManager;
    }

    private Lang lang() {
        return PrismEnchants.get().lang();
    }

    @EventHandler
    public void onClick(InventoryClickEvent inventoryClickEvent) {
        if (inventoryClickEvent.getClick() != ClickType.LEFT && inventoryClickEvent.getClick() != ClickType.RIGHT) {
            return;
        }
        if (!(inventoryClickEvent.getClickedInventory() instanceof PlayerInventory)) {
            return;
        }
        Object object = inventoryClickEvent.getWhoClicked();
        if (!(object instanceof Player)) {
            return;
        }
        Player player = (Player)object;
        object = inventoryClickEvent.getCursor();
        ItemStack itemStack = inventoryClickEvent.getCurrentItem();
        if (object == null || ((ItemStack)object).getType().isAir()) {
            return;
        }
        if (itemStack == null || itemStack.getType().isAir()) {
            return;
        }
        if (!((ItemStack)object).hasItemMeta()) {
            return;
        }
        PersistentDataContainer persistentDataContainer = ((ItemStack)object).getItemMeta().getPersistentDataContainer();
        String string = persistentDataContainer.get(Keys.BOOK_ID, PersistentDataType.STRING);
        String string2 = persistentDataContainer.get(Keys.SCROLL_TYPE, PersistentDataType.STRING);
        if (string == null && string2 == null) {
            return;
        }
        if (string != null) {
            inventoryClickEvent.setCancelled(true);
            this.applyBook(player, inventoryClickEvent, (ItemStack)object, itemStack, string, persistentDataContainer);
        } else if ("WHITE".equals(string2)) {
            inventoryClickEvent.setCancelled(true);
            this.applyWhite(player, inventoryClickEvent, (ItemStack)object, itemStack);
        } else if ("BLACK".equals(string2)) {
            inventoryClickEvent.setCancelled(true);
            this.applyBlack(player, inventoryClickEvent, (ItemStack)object, itemStack);
        }
    }

    private void applyBook(Player player, InventoryClickEvent inventoryClickEvent, ItemStack itemStack, ItemStack itemStack2, String string, PersistentDataContainer persistentDataContainer) {
        CustomEnchant customEnchant = this.manager.get(string);
        if (customEnchant == null) {
            return;
        }
        if (!this.manager.isEnabled(customEnchant)) {
            player.sendMessage(this.lang().msg("messages.apply-disabled", new String[0]));
            this.play(player, Sound.ENTITY_VILLAGER_NO, 1.0f);
            return;
        }
        if (!customEnchant.category().matches(itemStack2.getType())) {
            player.sendMessage(this.lang().msg("messages.apply-wrong-item", "category", customEnchant.category().display()));
            this.play(player, Sound.ENTITY_VILLAGER_NO, 1.0f);
            return;
        }
        if (this.manager.getLevel(itemStack2, customEnchant.id()) > 0) {
            player.sendMessage(this.lang().msg("messages.apply-already-has", "enchant", customEnchant.displayName()));
            return;
        }
        Integer n = persistentDataContainer.get(Keys.BOOK_LEVEL, PersistentDataType.INTEGER);
        Integer n2 = persistentDataContainer.get(Keys.BOOK_SUCCESS, PersistentDataType.INTEGER);
        Integer n3 = persistentDataContainer.get(Keys.BOOK_DESTROY, PersistentDataType.INTEGER);
        EnchantManager.ApplyOutcome applyOutcome = this.manager.applyBookRoll(itemStack2, customEnchant, n == null ? 1 : n, n2 == null ? 100 : n2, n3 == null ? 0 : n3);
        this.consumeCursor(inventoryClickEvent, itemStack);
        switch (applyOutcome) {
            case SUCCESS: {
                this.play(player, Sound.BLOCK_ENCHANTMENT_TABLE_USE, 1.2f);
                player.sendMessage(this.lang().msg("messages.apply-success", "enchant", customEnchant.rarity().color() + customEnchant.displayName()));
                break;
            }
            case FAIL_PROTECTED: {
                this.play(player, Sound.ITEM_TRIDENT_RETURN, 1.0f);
                player.sendMessage(this.lang().msg("messages.apply-fail-protected", new String[0]));
                break;
            }
            case DESTROYED: {
                inventoryClickEvent.setCurrentItem(null);
                this.play(player, Sound.ENTITY_ITEM_BREAK, 0.8f);
                player.sendMessage(this.lang().msg("messages.apply-destroyed", new String[0]));
                break;
            }
            case FAIL_SAFE: {
                this.play(player, Sound.BLOCK_ANVIL_LAND, 1.0f);
                player.sendMessage(this.lang().msg("messages.apply-fail-safe", new String[0]));
                break;
            }
        }
    }

    private void applyWhite(Player player, InventoryClickEvent inventoryClickEvent, ItemStack itemStack, ItemStack itemStack2) {
        if (this.manager.isProtected(itemStack2)) {
            player.sendMessage(this.lang().msg("messages.already-protected", new String[0]));
            return;
        }
        this.manager.setProtected(itemStack2, true);
        this.consumeCursor(inventoryClickEvent, itemStack);
        this.play(player, Sound.BLOCK_BEACON_ACTIVATE, 1.5f);
        player.sendMessage(this.lang().msg("messages.white-applied", new String[0]));
    }

    private void applyBlack(Player player, InventoryClickEvent inventoryClickEvent, ItemStack itemStack, ItemStack itemStack2) {
        Map<CustomEnchant, Integer> map = this.manager.getEnchants(itemStack2);
        if (map.isEmpty()) {
            player.sendMessage(this.lang().msg("messages.no-enchants", new String[0]));
            return;
        }
        Map.Entry<CustomEnchant, Integer> entry = map.entrySet().iterator().next();
        this.manager.remove(itemStack2, entry.getKey());
        this.consumeCursor(inventoryClickEvent, itemStack);
        player.getInventory().addItem(ItemFactory.book(entry.getKey(), entry.getValue()));
        this.play(player, Sound.BLOCK_GRINDSTONE_USE, 1.0f);
        player.sendMessage(this.lang().msg("messages.stripped", "enchant", entry.getKey().rarity().color() + entry.getKey().displayName()));
    }

    private void consumeCursor(InventoryClickEvent inventoryClickEvent, ItemStack itemStack) {
        int n = itemStack.getAmount();
        if (n <= 1) {
            inventoryClickEvent.setCursor(null);
        } else {
            itemStack.setAmount(n - 1);
            inventoryClickEvent.setCursor(itemStack);
        }
    }

    private void play(Player player, Sound sound, float f) {
        player.playSound(player.getLocation(), sound, 1.0f, f);
    }
}

