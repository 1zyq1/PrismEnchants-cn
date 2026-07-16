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

public class AquaEnchant
extends CustomEnchant {
    public AquaEnchant() {
        super("aqua", "\u6c34\u884c", Rarity.RARE, 1, ItemCategory.HELMET);
    }

    @Override
    public boolean passive() {
        return true;
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u5728\u6c34\u4e0b\u81ea\u7531\u547c\u5438", "&7\u5e76\u5728\u9ed1\u6697\u4e2d\u89c6\u7ebf\u826f\u597d\u3002");
    }

    @Override
    public void onArmorTick(Player player, int n) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.WATER_BREATHING, 100, 0, true, false, false));
        player.addPotionEffect(new PotionEffect(PotionEffectType.NIGHT_VISION, 220, 0, true, false, false));
    }
}

