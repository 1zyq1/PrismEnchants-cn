/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.util;

import java.util.ArrayList;
import java.util.List;
import org.bukkit.ChatColor;

public final class Text {
    private Text() {
    }

    public static String of(String string) {
        return ChatColor.translateAlternateColorCodes('&', string);
    }

    public static List<String> lore(List<String> list) {
        ArrayList<String> arrayList = new ArrayList<String>(list.size());
        for (String string : list) {
            arrayList.add(Text.of(string));
        }
        return arrayList;
    }

    public static String plain(String string) {
        return ChatColor.stripColor(string);
    }
}

