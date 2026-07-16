/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.List;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class TankEnchant
extends CustomEnchant {
    public TankEnchant() {
        super("tank", "\u5766\u514b", Rarity.LEGENDARY, 8, ItemCategory.ARMOR);
    }

    private double reduction(int n) {
        return Math.min(0.4, (double)n * 0.05);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u51cf\u5c11\u6240\u6709\u53d7\u5230\u7684\u4f24\u5bb3", "&a" + (int)(this.reduction(n) * 100.0) + "%&7\u3002");
    }

    @Override
    public void onDefend(Player player, Entity entity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        entityDamageByEntityEvent.setDamage(entityDamageByEntityEvent.getDamage() * (1.0 - this.reduction(n)));
    }
}

