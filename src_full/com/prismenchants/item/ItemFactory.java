/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.item;

import com.prismenchants.PrismEnchants;
import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.EnchantManager;
import com.prismenchants.util.Keys;
import com.prismenchants.util.Lang;
import com.prismenchants.util.Text;
import java.util.ArrayList;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataType;

public final class ItemFactory {
    private ItemFactory() {
    }

    private static Lang lang() {
        return PrismEnchants.get().lang();
    }

    public static ItemStack book(CustomEnchant customEnchant, int n) {
        EnchantManager enchantManager = PrismEnchants.get().enchants();
        int n2 = enchantManager.successOf(customEnchant);
        int n3 = enchantManager.destroyOf(customEnchant);
        n = Math.max(1, Math.min(n, enchantManager.maxLevel(customEnchant)));
        ItemStack itemStack = new ItemStack(Material.ENCHANTED_BOOK);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(Text.of(customEnchant.rarity().color() + "\u2726 " + customEnchant.displayName() + " " + EnchantManager.roman(n)));
        ArrayList<String> arrayList = new ArrayList<String>();
        arrayList.add(Text.of("&8" + customEnchant.rarity().colored() + " &8\u2022 &7" + customEnchant.category().display()));
        arrayList.add("");
        for (String string : customEnchant.description(n)) {
            arrayList.add(Text.of(string));
        }
        arrayList.add("");
        arrayList.add(ItemFactory.lang().msg("items.book-success", "percent", String.valueOf(n2)));
        arrayList.add(ItemFactory.lang().msg("items.book-destroy", "percent", String.valueOf(n3)));
        arrayList.add("");
        arrayList.add(ItemFactory.lang().msg("items.book-hint", new String[0]));
        itemMeta.setLore(arrayList);
        itemMeta.getPersistentDataContainer().set(Keys.BOOK_ID, PersistentDataType.STRING, customEnchant.id());
        itemMeta.getPersistentDataContainer().set(Keys.BOOK_LEVEL, PersistentDataType.INTEGER, n);
        itemMeta.getPersistentDataContainer().set(Keys.BOOK_SUCCESS, PersistentDataType.INTEGER, n2);
        itemMeta.getPersistentDataContainer().set(Keys.BOOK_DESTROY, PersistentDataType.INTEGER, n3);
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack whiteScroll() {
        ItemStack itemStack = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ItemFactory.lang().msg("items.white-name", new String[0]));
        itemMeta.setLore(ItemFactory.lang().list("items.white-lore"));
        itemMeta.getPersistentDataContainer().set(Keys.SCROLL_TYPE, PersistentDataType.STRING, "WHITE");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }

    public static ItemStack blackScroll() {
        ItemStack itemStack = new ItemStack(Material.PAPER);
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.setDisplayName(ItemFactory.lang().msg("items.black-name", new String[0]));
        itemMeta.setLore(ItemFactory.lang().list("items.black-lore"));
        itemMeta.getPersistentDataContainer().set(Keys.SCROLL_TYPE, PersistentDataType.STRING, "BLACK");
        itemStack.setItemMeta(itemMeta);
        return itemStack;
    }
}

