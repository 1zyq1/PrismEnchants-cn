/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.util;

import com.prismenchants.PrismEnchants;
import org.bukkit.NamespacedKey;

public final class Keys {
    public static NamespacedKey BOOK_ID;
    public static NamespacedKey BOOK_LEVEL;
    public static NamespacedKey BOOK_SUCCESS;
    public static NamespacedKey BOOK_DESTROY;
    public static NamespacedKey SCROLL_TYPE;
    public static NamespacedKey PROTECTED;
    public static NamespacedKey GUI_ACTION;
    public static NamespacedKey ARROW_ENCHANTS;

    private Keys() {
    }

    public static NamespacedKey enchant(String string) {
        return new NamespacedKey(PrismEnchants.get(), "ench_" + string);
    }

    public static void init(PrismEnchants prismEnchants) {
        BOOK_ID = new NamespacedKey(prismEnchants, "book_id");
        BOOK_LEVEL = new NamespacedKey(prismEnchants, "book_level");
        BOOK_SUCCESS = new NamespacedKey(prismEnchants, "book_success");
        BOOK_DESTROY = new NamespacedKey(prismEnchants, "book_destroy");
        SCROLL_TYPE = new NamespacedKey(prismEnchants, "scroll_type");
        PROTECTED = new NamespacedKey(prismEnchants, "protected");
        GUI_ACTION = new NamespacedKey(prismEnchants, "gui_action");
        ARROW_ENCHANTS = new NamespacedKey(prismEnchants, "arrow_enchants");
    }
}

