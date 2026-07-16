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

public class MoltenEnchant
extends CustomEnchant {
    public MoltenEnchant() {
        super("molten", "\u7194\u5ca9", Rarity.RARE, 8, ItemCategory.ARMOR);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u4f7f\u8fd1\u6218\u653b\u51fb\u8005\u7740 &6\u706b &7\u6301\u7eed " + n + "\u79d2\u3002");
    }

    @Override
    public void onDefend(Player player, Entity entity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        LivingEntity livingEntity;
        if (entity instanceof LivingEntity && !(livingEntity = (LivingEntity)entity).equals(player)) {
            livingEntity.setFireTicks(Math.max(livingEntity.getFireTicks(), n * 20));
        }
    }
}

