/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class HealthBoostEnchant
extends CustomEnchant {
    public HealthBoostEnchant() {
        super("healthboost", "\u751f\u547d\u63d0\u5347", Rarity.RARE, 8, ItemCategory.ARMOR);
    }

    @Override
    public boolean passive() {
        return true;
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u7a7f\u6234\u65f6\u83b7\u5f97 &e" + n + " &7\u9897\u5438\u6536\u4e4b\u5fc3", "&7\u3002");
    }

    @Override
    public void onArmorTick(Player player, int n) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 100, n - 1, true, false, false));
    }
}

