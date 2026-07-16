/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class StormcallerEnchant
extends CustomEnchant {
    public StormcallerEnchant() {
        super("stormcaller", "\u98ce\u66b4\u53ec\u5524", Rarity.MYTHIC, 8, ItemCategory.ARMOR);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7" + n * 7 + "% \u51e0\u7387\u5bf9\u8fd1\u6218", "&7\u653b\u51fb\u8005\u9644\u52a0 &e\u95ea\u7535 &7\u3002");
    }

    @Override
    public void onDefend(Player player, Entity entity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        LivingEntity livingEntity;
        if (entity instanceof LivingEntity && !(livingEntity = (LivingEntity)entity).equals(player) && ThreadLocalRandom.current().nextInt(100) < n * 7) {
            livingEntity.getWorld().strikeLightning(livingEntity.getLocation());
        }
    }
}

