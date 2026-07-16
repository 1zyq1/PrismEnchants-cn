/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import com.prismenchants.util.Compat;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;

public class ConfusionEnchant
extends CustomEnchant {
    public ConfusionEnchant() {
        super("confusion", "\u6df7\u4e71", Rarity.COMMON, 8, ItemCategory.MELEE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7" + (20 + n * 10) + "% \u51e0\u7387\u4f7f\u76ee\u6807 &5\u6df7\u4e71&7\u3002");
    }

    @Override
    public void onAttack(Player player, LivingEntity livingEntity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (ThreadLocalRandom.current().nextInt(100) < 20 + n * 10) {
            livingEntity.addPotionEffect(new PotionEffect(Compat.NAUSEA, 80 + n * 40, 0));
        }
    }
}

