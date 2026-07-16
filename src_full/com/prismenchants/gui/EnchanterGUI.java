/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.gui;

import com.prismenchants.PrismEnchants;
import com.prismenchants.util.Keys;
import com.prismenchants.util.Lang;
import java.util.ArrayList;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public final class EnchanterGUI {
    private EnchanterGUI() {
    }

    public static int cost(String string) {
        FileConfiguration fileConfiguration = PrismEnchants.get().getConfig();
        return switch (string) {
            case "apprentice" -> fileConfiguration.getInt("enchanter.apprentice-cost", 15);
            case "master" -> fileConfiguration.getInt("enchanter.master-cost", 30);
            case "legendary" -> fileConfiguration.getInt("enchanter.legendary-cost", 45);
            case "white" -> fileConfiguration.getInt("enchanter.white-scroll-cost", 10);
            case "black" -> fileConfiguration.getInt("enchanter.black-scroll-cost", 8);
            default -> 0;
        };
    }

    public static void open(Player player) {
        Lang lang = PrismEnchants.get().lang();
        Holder holder = new Holder();
        Inventory inventory = Bukkit.createInventory((InventoryHolder)holder, 27, lang.msg("gui.enchanter-title", new String[0]));
        holder.setInventory(inventory);
        inventory.setItem(10, EnchanterGUI.button(Material.BOOK, lang.msg("gui.apprentice", new String[0]), EnchanterGUI.cost("apprentice"), lang.msg("gui.apprentice-desc", new String[0]), "apprentice"));
        inventory.setItem(12, EnchanterGUI.button(Material.ENCHANTED_BOOK, lang.msg("gui.master", new String[0]), EnchanterGUI.cost("master"), lang.msg("gui.master-desc", new String[0]), "master"));
        inventory.setItem(14, EnchanterGUI.button(Material.NETHER_STAR, lang.msg("gui.legendary", new String[0]), EnchanterGUI.cost("legendary"), lang.msg("gui.legendary-desc", new String[0]), "legendary"));
        inventory.setItem(16, EnchanterGUI.button(Material.PAPER, lang.msg("gui.white-button", new String[0]), EnchanterGUI.cost("white"), lang.msg("gui.white-desc", new String[0]), "white"));
        inventory.setItem(22, EnchanterGUI.button(Material.PAPER, lang.msg("gui.black-button", new String[0]), EnchanterGUI.cost("black"), lang.msg("gui.black-desc", new String[0]), "black"));
        ItemStack itemStack = new ItemStack(Material.GRAY_STAINED_GLASS_PANE);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(" ");
        itemStack.setItemMeta(itemMeta);
        for (int i = 0; i < inventory.getSize(); ++i) {
            if (inventory.getItem(i) != null) continue;
            inventory.setItem(i, itemStack);
        }
        player.openInventory(inventory);
    }

    private static ItemStack button(Material material, String string, int n, String string2, String string3) {
        Lang lang = PrismEnchants.get().lang();
        ItemStack itemStack = new ItemStack(material);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(string);
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(string2);
        arrayList.add("");
        arrayList.add(lang.msg("gui.cost", "cost", String.valueOf(n)));
        arrayList.add(lang.msg("gui.click-buy", new String[0]));
        itemMeta.setLore(arrayList);
        itemMeta.getPersistentDataContainer().set(Keys.GUI_ACTION, PersistentDataType.STRING, string3);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
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

