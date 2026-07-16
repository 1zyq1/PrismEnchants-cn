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

public class SpeedsterEnchant
extends CustomEnchant {
    public SpeedsterEnchant() {
        super("speedster", "\u8fc5\u6377", Rarity.UNCOMMON, 8, ItemCategory.BOOTS);
    }

    @Override
    public boolean passive() {
        return true;
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u63d0\u4f9b\u6c38\u4e45 &f\u8fc5\u6377 " + n + "&7.");
    }

    @Override
    public void onArmorTick(Player player, int n) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.SPEED, 100, n - 1, true, false, false));
    }
}

