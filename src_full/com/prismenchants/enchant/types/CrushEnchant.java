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
import org.bukkit.potion.PotionEffectType;

public class CrushEnchant
extends CustomEnchant {
    public CrushEnchant() {
        super("crush", "\u7c89\u788e", Rarity.RARE, 8, ItemCategory.MELEE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7" + (20 + n * 10) + "% \u51e0\u7387\u65bd\u52a0", "&7\u865a\u5f31 + \u6316\u6398\u75b2\u52b3 " + n + "\u3002");
    }

    @Override
    public void onAttack(Player player, LivingEntity livingEntity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (ThreadLocalRandom.current().nextInt(100) < 20 + n * 10) {
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WEAKNESS, 60 + n * 20, n - 1));
            livingEntity.addPotionEffect(new PotionEffect(Compat.MINING_FATIGUE, 60 + n * 20, n - 1));
        }
    }
}

