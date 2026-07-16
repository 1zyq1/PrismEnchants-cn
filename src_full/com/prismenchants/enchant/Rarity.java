/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant;

public enum Rarity {
    COMMON("&f", "Common", 40, 100, 0),
    UNCOMMON("&a", "Uncommon", 25, 85, 5),
    RARE("&9", "Rare", 15, 70, 12),
    EPIC("&5", "Epic", 10, 55, 20),
    LEGENDARY("&6", "Legendary", 6, 40, 30),
    MYTHIC("&d", "Mythic", 2, 28, 45);

    private final String color;
    private final String display;
    private final int weight;
    private final int defaultSuccess;
    private final int defaultDestroy;

    private Rarity(String string2, String string3, int n2, int n3, int n4) {
        this.color = string2;
        this.display = string3;
        this.weight = n2;
        this.defaultSuccess = n3;
        this.defaultDestroy = n4;
    }

    public String color() {
        return this.color;
    }

    public String display() {
        return this.display;
    }

    public String colored() {
        return this.color + this.display;
    }

    public int weight() {
        return this.weight;
    }

    public int defaultSuccess() {
        return this.defaultSuccess;
    }

    public int defaultDestroy() {
        return this.defaultDestroy;
    }
}

