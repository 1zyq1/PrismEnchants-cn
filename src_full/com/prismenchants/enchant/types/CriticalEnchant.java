/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Particle;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CriticalEnchant
extends CustomEnchant {
    public CriticalEnchant() {
        super("critical", "\u66b4\u51fb", Rarity.EPIC, 8, ItemCategory.MELEE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7" + n * 10 + "% \u51e0\u7387\u9020\u6210", "&c\u53cc\u500d\u4f24\u5bb3&7\u3002");
    }

    @Override
    public void onAttack(Player player, LivingEntity livingEntity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (ThreadLocalRandom.current().nextInt(100) < n * 10) {
            entityDamageByEntityEvent.setDamage(entityDamageByEntityEvent.getDamage() * 2.0);
            livingEntity.getWorld().spawnParticle(Particle.CRIT, livingEntity.getLocation().add(0.0, 1.0, 0.0), 20);
        }
    }
}

