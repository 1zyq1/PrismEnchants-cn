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

public class BerserkEnchant
extends CustomEnchant {
    public BerserkEnchant() {
        super("berserk", "\u72c2\u66b4", Rarity.EPIC, 8, ItemCategory.MELEE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u6700\u9ad8\u53ef\u9020\u6210 &c+" + n * 20 + "% &7\u4f24\u5bb3", "&7\u4f24\u5bb3\u968f\u7740\u751f\u547d\u51cf\u5c11\u800c\u589e\u52a0\u3002");
    }

    @Override
    public void onAttack(Player player, LivingEntity livingEntity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        double d = player.getMaxHealth();
        double d2 = 1.0 - player.getHealth() / d;
        entityDamageByEntityEvent.setDamage(entityDamageByEntityEvent.getDamage() * (1.0 + d2 * (double)n * 0.2));
    }
}

