/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class CounterEnchant
extends CustomEnchant {
    public CounterEnchant() {
        super("counter", "\u53cd\u51fb", Rarity.EPIC, 8, ItemCategory.ARMOR);
    }

    private int chance(int n) {
        return Math.min(40, n * 4);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7" + this.chance(n) + "% \u51e0\u7387\u5b8c\u5168 &f\u95ea\u907f", "&7\u4e00\u6b21\u653b\u51fb\u3002");
    }

    @Override
    public void onDefend(Player player, Entity entity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (ThreadLocalRandom.current().nextInt(100) < this.chance(n)) {
            entityDamageByEntityEvent.setCancelled(true);
        }
    }
}

