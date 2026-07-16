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

public class SniperEnchant
extends CustomEnchant {
    public SniperEnchant() {
        super("sniper", "\u72d9\u51fb", Rarity.UNCOMMON, 8, ItemCategory.BOW);
    }

    @Override
    public boolean ranged() {
        return true;
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u7bad\u77e2\u9020\u6210 &c+" + n * 2 + " &7\u989d\u5916\u4f24\u5bb3\u3002");
    }

    @Override
    public void onArrowHit(Player player, LivingEntity livingEntity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        entityDamageByEntityEvent.setDamage(entityDamageByEntityEvent.getDamage() + (double)n * 2.0);
    }
}

