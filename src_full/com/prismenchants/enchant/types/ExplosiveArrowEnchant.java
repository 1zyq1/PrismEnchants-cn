/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.List;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ExplosiveArrowEnchant
extends CustomEnchant {
    public ExplosiveArrowEnchant() {
        super("explosive", "\u7206\u70b8\u4e4b\u7bad", Rarity.EPIC, 8, ItemCategory.BOW);
    }

    @Override
    public boolean ranged() {
        return true;
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u7bad\u77e2\u547d\u4e2d\u65f6 &c\u7206\u70b8", "&7\uff08\u4e0d\u7834\u574f\u65b9\u5757\uff09\u3002");
    }

    @Override
    public void onArrowHit(Player player, LivingEntity livingEntity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        livingEntity.getWorld().createExplosion(livingEntity.getLocation(), 1.0f + (float)n * 0.6f, false, false);
    }
}

