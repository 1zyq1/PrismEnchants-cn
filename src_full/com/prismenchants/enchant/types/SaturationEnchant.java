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

public class SaturationEnchant
extends CustomEnchant {
    public SaturationEnchant() {
        super("saturation", "\u9971\u548c", Rarity.UNCOMMON, 1, ItemCategory.ARMOR);
    }

    @Override
    public boolean passive() {
        return true;
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u7a7f\u6234\u65f6\u4e0d\u4f1a\u9965\u997f\u3002");
    }

    @Override
    public void onArmorTick(Player player, int n) {
        if (player.getFoodLevel() < 20 || player.getSaturation() < 5.0f) {
            player.addPotionEffect(new PotionEffect(PotionEffectType.SATURATION, 60, 0, true, false, false));
        }
    }
}

