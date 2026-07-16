/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.List;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CleaveEnchant
extends CustomEnchant {
    public CleaveEnchant() {
        super("cleave", "\u5288\u65a9", Rarity.EPIC, 8, ItemCategory.AXE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u5bf9\u76ee\u6807\u5468\u56f4\u7684\u654c\u4eba\u9020\u6210", "&c" + n * 10 + "% &7\u7684\u6e85\u5c04\u4f24\u5bb3\u3002");
    }

    @Override
    public void onAttack(Player player, LivingEntity livingEntity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        double d = entityDamageByEntityEvent.getDamage() * (double)n * 0.1;
        if (d <= 0.0) {
            return;
        }
        double d2 = 2.5;
        for (Entity entity : livingEntity.getNearbyEntities(d2, d2, d2)) {
            LivingEntity livingEntity2;
            if (!(entity instanceof LivingEntity) || (livingEntity2 = (LivingEntity)entity).equals(player) || livingEntity2.equals(livingEntity)) continue;
            livingEntity2.damage(d, player);
        }
    }
}

