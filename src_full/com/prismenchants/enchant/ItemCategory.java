/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant;

import org.bukkit.Material;

public enum ItemCategory {
    SWORD,
    AXE,
    MELEE,
    PICKAXE,
    TOOL,
    BOW,
    HELMET,
    CHESTPLATE,
    LEGGINGS,
    BOOTS,
    ARMOR,
    ALL;


    public boolean matches(Material material) {
        String string = material.name();
        return switch (this.ordinal()) {
            default -> throw new IncompatibleClassChangeError();
            case 11 -> true;
            case 0 -> string.endsWith("_SWORD");
            case 1 -> string.endsWith("_AXE");
            case 2 -> {
                if (string.endsWith("_SWORD") || string.endsWith("_AXE")) {
                    yield true;
                }
                yield false;
            }
            case 3 -> string.endsWith("_PICKAXE");
            case 4 -> {
                if (string.endsWith("_PICKAXE") || string.endsWith("_AXE") || string.endsWith("_SHOVEL") || string.endsWith("_HOE")) {
                    yield true;
                }
                yield false;
            }
            case 5 -> {
                if (material == Material.BOW || material == Material.CROSSBOW) {
                    yield true;
                }
                yield false;
            }
            case 6 -> {
                if (string.endsWith("_HELMET") || material == Material.TURTLE_HELMET) {
                    yield true;
                }
                yield false;
            }
            case 7 -> {
                if (string.endsWith("_CHESTPLATE") || material == Material.ELYTRA) {
                    yield true;
                }
                yield false;
            }
            case 8 -> string.endsWith("_LEGGINGS");
            case 9 -> string.endsWith("_BOOTS");
            case 10 -> string.endsWith("_HELMET") || string.endsWith("_CHESTPLATE") || string.endsWith("_LEGGINGS") || string.endsWith("_BOOTS") || material == Material.TURTLE_HELMET || material == Material.ELYTRA;
        };
    }

    public String display() {
        return switch (this.ordinal()) {
            case 2 -> "Swords & Axes";
            case 4 -> "Tools";
            case 10 -> "Armor";
            case 11 -> "Any item";
            default -> this.name().charAt(0) + this.name().substring(1).toLowerCase();
        };
    }
}

