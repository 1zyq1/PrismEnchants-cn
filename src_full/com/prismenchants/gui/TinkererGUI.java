/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.gui;

import com.prismenchants.PrismEnchants;
import com.prismenchants.util.Keys;
import com.prismenchants.util.Lang;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public final class TinkererGUI {
    public static final int SIZE = 36;
    public static final int SELL_SLOT = 31;

    private TinkererGUI() {
    }

    public static boolean isInputSlot(int n) {
        return n >= 0 && n <= 26;
    }

    public static void open(Player player) {
        Lang lang = PrismEnchants.get().lang();
        Holder holder = new Holder();
        Inventory inventory = Bukkit.createInventory((InventoryHolder)holder, 36, lang.msg("gui.tinkerer-title", new String[0]));
        holder.setInventory(inventory);
        ItemStack itemStack = new ItemStack(Material.EMERALD);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(lang.msg("gui.sell-button", new String[0]));
        itemMeta.setLore(lang.list("gui.sell-desc"));
        itemMeta.getPersistentDataContainer().set(Keys.GUI_ACTION, PersistentDataType.STRING, "sell");
        itemStack.setItemMeta(itemMeta);
        inventory.setItem(31, itemStack);
        ItemStack itemStack2 = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta itemMeta2 = itemStack2.getItemMeta();
        itemMeta2.setDisplayName(" ");
        itemStack2.setItemMeta(itemMeta2);
        for (int i = 27; i < 36; ++i) {
            if (i == 31) continue;
            inventory.setItem(i, itemStack2);
        }
        player.openInventory(inventory);
    }

    public static final class Holder
    implements InventoryHolder {
        private Inventory inv;

        public void setInventory(Inventory inventory) {
            this.inv = inventory;
        }

        @Override
        public Inventory getInventory() {
            return this.inv;
        }
    }
}

