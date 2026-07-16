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

public class IgniteEnchant
extends CustomEnchant {
    public IgniteEnchant() {
        super("ignite", "\u70b9\u71c3", Rarity.UNCOMMON, 8, ItemCategory.MELEE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u4f7f\u76ee\u6807\u7740 &6\u706b &7\u6301\u7eed " + n * 2 + "\u79d2\u3002");
    }

    @Override
    public void onAttack(Player player, LivingEntity livingEntity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        livingEntity.setFireTicks(Math.max(livingEntity.getFireTicks(), n * 40));
    }
}

