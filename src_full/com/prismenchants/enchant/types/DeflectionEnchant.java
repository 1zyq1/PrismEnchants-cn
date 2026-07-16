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
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DeflectionEnchant
extends CustomEnchant {
    public DeflectionEnchant() {
        super("deflection", "\u504f\u8f6c", Rarity.LEGENDARY, 8, ItemCategory.ARMOR);
    }

    private double reduction(int n) {
        return Math.min(0.6, (double)n * 0.08);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u51cf\u5c11 &f\u8fdc\u7a0b &7\u4f24\u5bb3", "&a" + (int)(this.reduction(n) * 100.0) + "%&7\u3002");
    }

    @Override
    public void onDefend(Player player, Entity entity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (entity instanceof Projectile) {
            entityDamageByEntityEvent.setDamage(entityDamageByEntityEvent.getDamage() * (1.0 - this.reduction(n)));
        }
    }
}

