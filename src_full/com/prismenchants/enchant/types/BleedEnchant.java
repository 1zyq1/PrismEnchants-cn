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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class BleedEnchant
extends CustomEnchant {
    public BleedEnchant() {
        super("bleed", "\u6d41\u8840", Rarity.UNCOMMON, 8, ItemCategory.MELEE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7" + (25 + n * 10) + "% \u51e0\u7387\u4f7f\u76ee\u6807", "&7&4\u6d41\u8840 &7\u6301\u7eed\u9020\u6210\u4f24\u5bb3\u3002");
    }

    @Override
    public void onAttack(Player player, LivingEntity livingEntity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (ThreadLocalRandom.current().nextInt(100) < 25 + n * 10) {
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60, n - 1));
        }
    }
}

