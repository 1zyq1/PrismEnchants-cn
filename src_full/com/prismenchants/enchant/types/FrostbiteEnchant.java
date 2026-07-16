/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import com.prismenchants.util.Compat;
import java.util.List;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.potion.PotionEffect;

public class FrostbiteEnchant
extends CustomEnchant {
    public FrostbiteEnchant() {
        super("frostbite", "\u51b0\u54ac", Rarity.UNCOMMON, 8, ItemCategory.MELEE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u5bf9\u76ee\u6807\u65bd\u52a0 &b\u7f13\u6162 " + n + " &7\u6548\u679c\u3002");
    }

    @Override
    public void onAttack(Player player, LivingEntity livingEntity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        livingEntity.addPotionEffect(new PotionEffect(Compat.SLOWNESS, 40 + n * 20, n - 1));
    }
}

