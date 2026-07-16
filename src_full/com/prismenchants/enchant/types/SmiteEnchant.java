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

public class SmiteEnchant
extends CustomEnchant {
    public SmiteEnchant() {
        super("smite", "\u5723\u51fb", Rarity.RARE, 8, ItemCategory.MELEE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7+&c" + n * 2 + " &7\u4f24\u5bb3 &f\u4e0a\u53e4\u751f\u7269 &7\u3002");
    }

    @Override
    public void onAttack(Player player, LivingEntity livingEntity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        String string = livingEntity.getType().name();
        boolean bl = string.contains("ZOMBIE") || string.contains("SKELETON") || string.contains("WITHER") || string.contains("DROWNED") || string.contains("HUSK") || string.contains("STRAY") || string.contains("PHANTOM") || string.contains("ZOGLIN") || string.contains("BOGGED") || string.equals("ZOMBIFIED_PIGLIN");
        boolean bl2 = bl;
        if (bl) {
            entityDamageByEntityEvent.setDamage(entityDamageByEntityEvent.getDamage() + (double)n * 2.0);
        }
    }
}

