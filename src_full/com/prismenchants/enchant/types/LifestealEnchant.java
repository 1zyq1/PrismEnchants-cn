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

public class LifestealEnchant
extends CustomEnchant {
    public LifestealEnchant() {
        super("lifesteal", "\u751f\u547d\u5077\u53d6", Rarity.RARE, 8, ItemCategory.MELEE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u6cbb\u7597 &c" + n + " \u2764 &7\u751f\u547d\u3002");
    }

    @Override
    public void onAttack(Player player, LivingEntity livingEntity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        double d = player.getMaxHealth();
        player.setHealth(Math.min(d, player.getHealth() + (double)n));
    }
}

