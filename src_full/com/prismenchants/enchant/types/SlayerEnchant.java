/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.List;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Monster;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SlayerEnchant
extends CustomEnchant {
    public SlayerEnchant() {
        super("slayer", "\u5c60\u6740\u8005", Rarity.RARE, 8, ItemCategory.MELEE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7+&c" + n + " &7\u4f24\u5bb3\u6240\u6709 &f\u602a\u7269 &7\u3002");
    }

    @Override
    public void onAttack(Player player, LivingEntity livingEntity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (livingEntity instanceof Monster) {
            entityDamageByEntityEvent.setDamage(entityDamageByEntityEvent.getDamage() + (double)n);
        }
    }
}

