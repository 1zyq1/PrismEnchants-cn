/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.List;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class SpikesEnchant
extends CustomEnchant {
    public SpikesEnchant() {
        super("spikes", "\u5c16\u523a", Rarity.UNCOMMON, 8, ItemCategory.ARMOR);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u53cd\u5c04 &c" + n + " \u2764 &7\u4f24\u5bb3\u8fd1\u6218\u653b\u51fb\u8005\u3002");
    }

    @Override
    public void onDefend(Player player, Entity entity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        LivingEntity livingEntity;
        if (entity instanceof LivingEntity && !(livingEntity = (LivingEntity)entity).equals(player)) {
            livingEntity.damage((double)n * 2.0, player);
        }
    }
}

