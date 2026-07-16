/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.List;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class LastStandEnchant
extends CustomEnchant {
    public LastStandEnchant() {
        super("laststand", "\u80cc\u6c34\u4e00\u6218", Rarity.EPIC, 8, ItemCategory.ARMOR);
    }

    private double reduction(int n) {
        return Math.min(0.8, (double)n * 0.1);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u751f\u547d\u503c\u4f4e\u4e8e25%\u65f6\uff0c\u51cf\u5c11", "&7\u53d7\u5230\u7684\u4f24\u5bb3 &a" + (int)(this.reduction(n) * 100.0) + "%&7\u3002");
    }

    @Override
    public void onDefend(Player player, Entity entity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        double d = player.getMaxHealth();
        if (player.getHealth() <= d * 0.25) {
            entityDamageByEntityEvent.setDamage(entityDamageByEntityEvent.getDamage() * (1.0 - this.reduction(n)));
        }
    }
}

