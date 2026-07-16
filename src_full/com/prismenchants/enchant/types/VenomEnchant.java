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

public class VenomEnchant
extends CustomEnchant {
    public VenomEnchant() {
        super("venom", "\u6bd2\u6db2", Rarity.UNCOMMON, 8, ItemCategory.MELEE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7" + (40 + n * 10) + "% \u51e0\u7387\u5bf9\u76ee\u6807\u9644\u52a0 &2\u4e2d\u6bd2 &7\u3002");
    }

    @Override
    public void onAttack(Player player, LivingEntity livingEntity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (ThreadLocalRandom.current().nextInt(100) < 40 + n * 10) {
            livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 40 + n * 20, n - 1));
        }
    }
}

