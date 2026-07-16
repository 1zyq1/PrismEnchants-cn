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

public class ToxicArrowEnchant
extends CustomEnchant {
    public ToxicArrowEnchant() {
        super("toxic_arrow", "\u6bd2\u7bad", Rarity.RARE, 8, ItemCategory.BOW);
    }

    @Override
    public boolean ranged() {
        return true;
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u7bad\u77e2\u9644\u52a0 &2\u4e2d\u6bd2 " + n + " &7\u751f\u547d\u3002");
    }

    @Override
    public void onArrowHit(Player player, LivingEntity livingEntity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        livingEntity.addPotionEffect(new PotionEffect(PotionEffectType.POISON, 60 + n * 10, n - 1));
    }
}

