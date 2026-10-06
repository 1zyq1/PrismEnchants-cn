/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.util;

import com.prismenchants.util.Text;
import java.io.File;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
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
    private FileConfiguration selectedBundled;
    private FileConfiguration bundledFallback;

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
        this.selected = this.loadFile(string);
        // jar 内置资源：数据目录里的语言文件缺少新键时用它补齐（无需删除旧文件）
        this.bundledFallback = this.loadBundled("en");
        this.selectedBundled = this.loadBundled(string);
        if (this.selected == null) {
            this.selected = this.selectedBundled != null ? this.selectedBundled : this.fallback;
        }
    }

    private FileConfiguration loadFile(String string) {
        File file = new File(this.plugin.getDataFolder(), "lang/" + string + ".yml");
        return file.exists() ? YamlConfiguration.loadConfiguration(file) : null;
    }

    private FileConfiguration loadBundled(String string) {
        InputStream inputStream = this.plugin.getResource("lang/" + string + ".yml");
        if (inputStream == null) {
            return null;
        }
        return YamlConfiguration.loadConfiguration(new InputStreamReader(inputStream, StandardCharsets.UTF_8));
    }

    private String raw(String string) {
        String string2 = this.selected == null ? null : this.selected.getString(string);
        if (string2 == null && this.selectedBundled != null) {
            string2 = this.selectedBundled.getString(string);
        }
        if (string2 == null && this.fallback != null) {
            string2 = this.fallback.getString(string);
        }
        if (string2 == null && this.bundledFallback != null) {
            string2 = this.bundledFallback.getString(string);
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
        List<String> list = this.selected == null ? new ArrayList<String>() : this.selected.getStringList(string);
        if (list.isEmpty() && this.selectedBundled != null) {
            list = this.selectedBundled.getStringList(string);
        }
        if (list.isEmpty() && this.fallback != null) {
            list = this.fallback.getStringList(string);
        }
        if (list.isEmpty() && this.bundledFallback != null) {
            list = this.bundledFallback.getStringList(string);
        }
        ArrayList<String> arrayList = new ArrayList<String>(list.size());
        for (String string2 : list) {
            arrayList.add(Text.of(string2));
        }
        return arrayList;
    }
}
