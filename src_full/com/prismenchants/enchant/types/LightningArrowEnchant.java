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

public class LightningArrowEnchant
extends CustomEnchant {
    public LightningArrowEnchant() {
        super("lightning_arrow", "\u96f7\u66b4\u4e4b\u7bad", Rarity.RARE, 1, ItemCategory.BOW);
    }

    @Override
    public boolean ranged() {
        return true;
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u7bad\u77e2\u547d\u4e2d\u65f6\u53ec\u5524 &e\u95ea\u7535&7\u3002");
    }

    @Override
    public void onArrowHit(Player player, LivingEntity livingEntity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        livingEntity.getWorld().strikeLightning(livingEntity.getLocation());
    }
}

