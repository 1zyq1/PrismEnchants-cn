/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant;

import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.event.entity.EntityShootBowEvent;

public abstract class CustomEnchant {
    private final String id;
    private final String displayName;
    private final Rarity rarity;
    private final int maxLevel;
    private final ItemCategory category;

    protected CustomEnchant(String string, String string2, Rarity rarity, int n, ItemCategory itemCategory) {
        this.id = string;
        this.displayName = string2;
        this.rarity = rarity;
        this.maxLevel = n;
        this.category = itemCategory;
    }

    public final String id() {
        return this.id;
    }

    public final String displayName() {
        return this.displayName;
    }

    public final Rarity rarity() {
        return this.rarity;
    }

    public final int maxLevel() {
        return this.maxLevel;
    }

    public final ItemCategory category() {
        return this.category;
    }

    public abstract List<String> description(int var1);

    public void onAttack(Player player, LivingEntity livingEntity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
    }

    public void onDefend(Player player, Entity entity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
    }

    public void onKill(Player player, LivingEntity livingEntity, int n) {
    }

    public void onKillEvent(Player player, EntityDeathEvent entityDeathEvent, int n) {
    }

    public void onFall(Player player, int n, EntityDamageEvent entityDamageEvent) {
    }

    public void onShootBow(Player player, EntityShootBowEvent entityShootBowEvent, int n) {
    }

    public void onArrowHit(Player player, LivingEntity livingEntity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
    }

    public boolean ranged() {
        return false;
    }

    public void onMine(Player player, Block block, int n, BlockBreakEvent blockBreakEvent) {
    }

    public void onArmorTick(Player player, int n) {
    }

    public void onHoldTick(Player player, int n) {
    }

    public boolean passive() {
        return false;
    }
}

