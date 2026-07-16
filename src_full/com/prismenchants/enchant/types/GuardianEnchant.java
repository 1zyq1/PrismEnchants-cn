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

public class GuardianEnchant
extends CustomEnchant {
    public GuardianEnchant() {
        super("guardian", "\u5b88\u62a4\u8005", Rarity.RARE, 8, ItemCategory.ARMOR);
    }

    private int chance(int n) {
        return Math.min(90, n * 10);
    }

    private double reduction(int n) {
        return Math.min(0.75, (double)n * 0.1);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7" + this.chance(n) + "% \u51e0\u7387\u683c\u6321", "&7" + (int)(this.reduction(n) * 100.0) + "% \u7684\u53d7\u5230\u4f24\u5bb3\u3002");
    }

    @Override
    public void onDefend(Player player, Entity entity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        if (ThreadLocalRandom.current().nextInt(100) < this.chance(n)) {
            entityDamageByEntityEvent.setDamage(entityDamageByEntityEvent.getDamage() * (1.0 - this.reduction(n)));
        }
    }
}

