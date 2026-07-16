/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.util;

import com.prismenchants.util.Text;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;

public final class Lang {
    public static final String[] SHIPPED = new String[]{"en", "de", "es", "fr", "id", "pl", "it", "nl", "ar", "sv", "tr", "ja", "zh"};
    private final Plugin plugin;
    private FileConfiguration selected;
    private FileConfiguration fallback;

    public Lang(Plugin plugin) {
        this.plugin = plugin;
    }

    public void load(String string) {
        for (String string2 : SHIPPED) {
            File file = new File(this.plugin.getDataFolder(), "lang/" + string2 + ".yml");
            if (file.exists()) continue;
            this.plugin.saveResource("lang/" + string2 + ".yml", false);
        }
        this.fallback = this.loadFile("en");
        FileConfiguration fileConfiguration = this.loadFile(string);
        this.selected = fileConfiguration != null ? fileConfiguration : this.fallback;
    }

    private FileConfiguration loadFile(String string) {
        File file = new File(this.plugin.getDataFolder(), "lang/" + string + ".yml");
        return file.exists() ? YamlConfiguration.loadConfiguration(file) : null;
    }

    private String raw(String string) {
        String string2 = this.selected.getString(string);
        if (string2 == null) {
            string2 = this.fallback.getString(string);
        }
        return string2 == null ? string : string2;
    }

    public String msg(String string, String ... stringArray) {
        String string2 = this.raw(string);
        int n = 0;
        while (n + 1 < stringArray.length) {
            string2 = string2.replace("{" + stringArray[n] + "}", stringArray[n + 1]);
            n += 2;
        }
        return Text.of(string2);
    }

    public List<String> list(String string) {
        List<String> list = this.selected.getStringList(string);
        if (list.isEmpty()) {
            list = this.fallback.getStringList(string);
        }
        ArrayList<String> arrayList = new ArrayList<String>(list.size());
        for (String string2 : list) {
            arrayList.add(Text.of(string2));
        }
        return arrayList;
    }
}

