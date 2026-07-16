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

public class ExecuteEnchant
extends CustomEnchant {
    public ExecuteEnchant() {
        super("execute", "\u5904\u51b3", Rarity.LEGENDARY, 8, ItemCategory.MELEE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u5f53\u76ee\u6807\u4f4e\u4e8e &c" + (15 + n * 5) + "% &7\u751f\u547d\u503c\u65f6\uff1a", "&7\u9020\u6210 &c+" + n * 50 + "% &7\u989d\u5916\u4f24\u5bb3\u3002");
    }

    @Override
    public void onAttack(Player player, LivingEntity livingEntity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        double d = livingEntity.getMaxHealth();
        double d2 = d * (0.15 + (double)n * 0.05);
        if (livingEntity.getHealth() <= d2) {
            entityDamageByEntityEvent.setDamage(entityDamageByEntityEvent.getDamage() * (1.0 + (double)n * 0.5));
        }
    }
}

