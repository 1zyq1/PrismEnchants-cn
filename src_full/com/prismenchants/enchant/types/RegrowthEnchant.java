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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class RegrowthEnchant
extends CustomEnchant {
    public RegrowthEnchant() {
        super("regrowth", "\u518d\u751f", Rarity.LEGENDARY, 8, ItemCategory.ARMOR);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u751f\u547d\u4f4e\u4e8e 30% \u65f6\u83b7\u5f97", "&a\u518d\u751f " + n + " &7\u6548\u679c\u3002");
    }

    @Override
    public void onDefend(Player player, Entity entity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        double d = player.getMaxHealth();
        double d2 = player.getHealth() - entityDamageByEntityEvent.getFinalDamage();
        if (d2 > 0.0 && d2 <= d * 0.3 && !player.hasPotionEffect(PotionEffectType.REGENERATION)) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.REGENERATION, 80, n - 1));
        }
    }
}

