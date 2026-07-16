/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.util.Keys;
import com.prismenchants.util.Text;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public final class EnchantManager {
    private final Map<String, CustomEnchant> byId = new LinkedHashMap<String, CustomEnchant>();
    private final Map<String, Settings> settings = new HashMap<String, Settings>();
    private final Random random = new Random();

    public void register(CustomEnchant customEnchant) {
        this.byId.put(customEnchant.id(), customEnchant);
    }

    public void loadSettings(FileConfiguration fileConfiguration) {
        this.settings.clear();
        for (CustomEnchant customEnchant : this.byId.values()) {
            String string = "enchants." + customEnchant.id() + ".";
            boolean bl = fileConfiguration.getBoolean(string + "enabled", true);
            int n = fileConfiguration.getInt(string + "success", customEnchant.rarity().defaultSuccess());
            int n2 = fileConfiguration.getInt(string + "destroy", customEnchant.rarity().defaultDestroy());
            int n3 = Math.max(1, fileConfiguration.getInt(string + "max-level", customEnchant.maxLevel()));
            this.settings.put(customEnchant.id(), new Settings(bl, n, n2, n3));
        }
    }

    public Settings settings(CustomEnchant customEnchant) {
        Settings settings = this.settings.get(customEnchant.id());
        return settings != null ? settings : new Settings(true, customEnchant.rarity().defaultSuccess(), customEnchant.rarity().defaultDestroy(), customEnchant.maxLevel());
    }

    public boolean isEnabled(CustomEnchant customEnchant) {
        return this.settings((CustomEnchant)customEnchant).enabled;
    }

    public int maxLevel(CustomEnchant customEnchant) {
        return this.settings((CustomEnchant)customEnchant).maxLevel;
    }

    public int successOf(CustomEnchant customEnchant) {
        return this.settings((CustomEnchant)customEnchant).success;
    }

    public int destroyOf(CustomEnchant customEnchant) {
        return this.settings((CustomEnchant)customEnchant).destroy;
    }

    public CustomEnchant get(String string) {
        return string == null ? null : this.byId.get(string.toLowerCase(Locale.ROOT));
    }

    public Collection<CustomEnchant> all() {
        return this.byId.values();
    }

    public int count() {
        return this.byId.size();
    }

    public CustomEnchant randomWeighted() {
        int n = 0;
        for (CustomEnchant object : this.byId.values()) {
            n += object.rarity().weight();
        }
        int n2 = this.random.nextInt(n);
        for (CustomEnchant customEnchant : this.byId.values()) {
            if ((n2 -= customEnchant.rarity().weight()) >= 0) continue;
            return customEnchant;
        }
        return this.byId.values().iterator().next();
    }

    public boolean applies(CustomEnchant customEnchant, ItemStack itemStack) {
        return itemStack != null && customEnchant.category().matches(itemStack.getType());
    }

    public int getLevel(ItemStack itemStack, String string) {
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return 0;
        }
        PersistentDataContainer persistentDataContainer = itemStack.getItemMeta().getPersistentDataContainer();
        Integer n = persistentDataContainer.get(Keys.enchant(string), PersistentDataType.INTEGER);
        return n == null ? 0 : n;
    }

    public Map<CustomEnchant, Integer> getEnchants(ItemStack itemStack) {
        LinkedHashMap<CustomEnchant, Integer> linkedHashMap = new LinkedHashMap<CustomEnchant, Integer>();
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return linkedHashMap;
        }
        PersistentDataContainer persistentDataContainer = itemStack.getItemMeta().getPersistentDataContainer();
        for (CustomEnchant customEnchant : this.byId.values()) {
            Integer n = persistentDataContainer.get(Keys.enchant(customEnchant.id()), PersistentDataType.INTEGER);
            if (n == null || n <= 0) continue;
            linkedHashMap.put(customEnchant, n);
        }
        return linkedHashMap;
    }

    public void apply(ItemStack itemStack, CustomEnchant customEnchant, int n) {
        if (itemStack == null) {
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (itemMeta == null) {
            return;
        }
        int n2 = Math.max(1, Math.min(n, customEnchant.maxLevel()));
        itemMeta.getPersistentDataContainer().set(Keys.enchant(customEnchant.id()), PersistentDataType.INTEGER, n2);
        itemStack.setItemMeta(itemMeta);
        this.refreshLore(itemStack);
    }

    public ApplyOutcome applyBookRoll(ItemStack itemStack, CustomEnchant customEnchant, int n, int n2, int n3) {
        if (itemStack == null || customEnchant == null) {
            return ApplyOutcome.INVALID;
        }
        if (!this.isEnabled(customEnchant)) {
            return ApplyOutcome.INVALID;
        }
        if (!customEnchant.category().matches(itemStack.getType())) {
            return ApplyOutcome.INVALID;
        }
        if (this.getLevel(itemStack, customEnchant.id()) > 0) {
            return ApplyOutcome.INVALID;
        }
        if (this.random.nextInt(100) < n2) {
            this.apply(itemStack, customEnchant, n);
            return ApplyOutcome.SUCCESS;
        }
        if (this.random.nextInt(100) < n3) {
            if (this.isProtected(itemStack)) {
                this.setProtected(itemStack, false);
                return ApplyOutcome.FAIL_PROTECTED;
            }
            return ApplyOutcome.DESTROYED;
        }
        return ApplyOutcome.FAIL_SAFE;
    }

    public void remove(ItemStack itemStack, CustomEnchant customEnchant) {
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        itemMeta.getPersistentDataContainer().remove(Keys.enchant(customEnchant.id()));
        itemStack.setItemMeta(itemMeta);
        this.refreshLore(itemStack);
    }

    public boolean isProtected(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return false;
        }
        Byte by = itemStack.getItemMeta().getPersistentDataContainer().get(Keys.PROTECTED, PersistentDataType.BYTE);
        return by != null && by == 1;
    }

    public void setProtected(ItemStack itemStack, boolean bl) {
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (bl) {
            itemMeta.getPersistentDataContainer().set(Keys.PROTECTED, PersistentDataType.BYTE, (byte)1);
        } else {
            itemMeta.getPersistentDataContainer().remove(Keys.PROTECTED);
        }
        itemStack.setItemMeta(itemMeta);
        this.refreshLore(itemStack);
    }

    public void refreshLore(ItemStack itemStack) {
        if (itemStack == null || !itemStack.hasItemMeta()) {
            return;
        }
        ItemMeta itemMeta = itemStack.getItemMeta();
        List<String> existing = itemMeta.hasLore() ? itemMeta.getLore() : new ArrayList<>();
        List<String> keep = new ArrayList<>();
        if (existing != null) {
            for (String line : existing) {
                String plain = Text.plain(line);
                if (plain.startsWith("\u00bb ") || plain.startsWith("Protected")) continue;
                keep.add(line);
            }
        }
        List<String> newLore = new ArrayList<>();
        Map<CustomEnchant, Integer> enchants = this.getEnchants(itemStack);
        for (Map.Entry<CustomEnchant, Integer> entry : enchants.entrySet()) {
            CustomEnchant customEnchant = entry.getKey();
            String string = EnchantManager.roman(entry.getValue());
            newLore.add(Text.of("\u00bb " + customEnchant.rarity().color() + customEnchant.displayName() + (customEnchant.maxLevel() > 1 ? " " + string : "")));
        }
        if (this.isProtected(itemStack)) {
            newLore.add(Text.of("&bProtected &7(safe from destroy)"));
        }
        List<String> finalLore = new ArrayList<>(newLore);
        finalLore.addAll(keep);
        itemMeta.setLore(finalLore.isEmpty() ? null : finalLore);
        itemStack.setItemMeta(itemMeta);
    }

    public static String roman(int n) {
        return switch (n) {
            case 1 -> "I";
            case 2 -> "II";
            case 3 -> "III";
            case 4 -> "IV";
            case 5 -> "V";
            case 6 -> "VI";
            case 7 -> "VII";
            case 8 -> "VIII";
            case 9 -> "IX";
            case 10 -> "X";
            default -> String.valueOf(n);
        };
    }

    public static final class Settings {
        public final boolean enabled;
        public final int success;
        public final int destroy;
        public final int maxLevel;

        public Settings(boolean bl, int n, int n2, int n3) {
            this.enabled = bl;
            this.success = n;
            this.destroy = n2;
            this.maxLevel = n3;
        }
    }

    public static enum ApplyOutcome {
        SUCCESS,
        FAIL_SAFE,
        FAIL_PROTECTED,
        DESTROYED,
        INVALID;

    }
}

