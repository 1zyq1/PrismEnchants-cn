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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class WitherArrowEnchant
extends CustomEnchant {
    public WitherArrowEnchant() {
        super("wither_arrow", "\u51cb\u96f6\u4e4b\u7bad", Rarity.RARE, 8, ItemCategory.BOW);
    }

    @Override
    public boolean ranged() {
        return true;
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u7bad\u77e2\u9644\u52a0 &8\u51c0\u5316 " + n + " &7\u751f\u547d\u3002");
    }

    @Override
    public void onArrowHit(Player player, LivingEntity livingEntity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.WITHER, 60 + n * 20, n - 1));
    }
}

