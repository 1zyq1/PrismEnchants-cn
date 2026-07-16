/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class LightningEnchant
extends CustomEnchant {
    public LightningEnchant() {
        super("lightning", "\u95ea\u7535", Rarity.EPIC, 8, ItemCategory.MELEE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7" + n * 8 + "% \u51e0\u7387\u53ec\u5524 &e\u95ea\u7535 &7(+" + n * 2 + " \u4f24\u5bb3)\u3002");
    }

    @Override
    public void onAttack(Player player, LivingEntity livingEntity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (ThreadLocalRandom.current().nextInt(100) < n * 8) {
            livingEntity.getWorld().strikeLightningEffect(livingEntity.getLocation());
            entityDamageByEntityEvent.setDamage(entityDamageByEntityEvent.getDamage() + (double)n * 2.0);
        }
    }
}

