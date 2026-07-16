/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import com.prismenchants.util.Compat;
import java.util.List;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;

public class FrostguardEnchant
extends CustomEnchant {
    public FrostguardEnchant() {
        super("frostguard", "\u51b0\u971c\u5b88\u62a4", Rarity.UNCOMMON, 8, ItemCategory.ARMOR);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u4ee5 &b\u7f13\u6162 " + n + " &7\u51cf\u7f13", "&7\u8fd1\u6218\u653b\u51fb\u8005\u3002");
    }

    @Override
    public void onDefend(Player player, Entity entity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        LivingEntity livingEntity;
        if (entity instanceof LivingEntity && !(livingEntity = (LivingEntity)entity).equals(player)) {
            livingEntity.addPotionEffect(new PotionEffect(Compat.SLOWNESS, 40 + n * 20, n - 1));
        }
    }
}

