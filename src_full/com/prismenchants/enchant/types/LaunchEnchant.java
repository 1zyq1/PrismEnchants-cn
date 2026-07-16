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

public class LaunchEnchant
extends CustomEnchant {
    public LaunchEnchant() {
        super("launch", "\u51fb\u98de", Rarity.UNCOMMON, 8, ItemCategory.MELEE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7" + n * 6 + "% \u51e0\u7387\u5c06\u76ee\u6807", "&f\u51fb\u98de&7\u3002");
    }

    @Override
    public void onAttack(Player player, LivingEntity livingEntity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (ThreadLocalRandom.current().nextInt(100) < n * 6) {
            livingEntity.setVelocity(livingEntity.getVelocity().setY(0.6 + (double)Math.min(n, 6) * 0.05));
        }
    }
}

