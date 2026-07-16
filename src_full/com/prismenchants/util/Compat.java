/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.util;

import org.bukkit.potion.PotionEffectType;

public final class Compat {
    public static PotionEffectType SLOWNESS;
    public static PotionEffectType NAUSEA;
    public static PotionEffectType MINING_FATIGUE;
    public static PotionEffectType JUMP_BOOST;
    public static PotionEffectType HASTE;

    private Compat() {
    }

    public static void init() {
        SLOWNESS = Compat.resolve("SLOWNESS", "SLOW");
        NAUSEA = Compat.resolve("NAUSEA", "CONFUSION");
        MINING_FATIGUE = Compat.resolve("MINING_FATIGUE", "SLOW_DIGGING");
        JUMP_BOOST = Compat.resolve("JUMP_BOOST", "JUMP");
        HASTE = Compat.resolve("HASTE", "FAST_DIGGING");
    }

    private static PotionEffectType resolve(String ... stringArray) {
        for (String string : stringArray) {
            PotionEffectType potionEffectType = PotionEffectType.getByName(string);
            if (potionEffectType == null) continue;
            return potionEffectType;
        }
        return null;
    }
}

