/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import com.prismenchants.util.Compat;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.potion.PotionEffect;

public class LeapingEnchant
extends CustomEnchant {
    public LeapingEnchant() {
        super("leaping", "\u5f39\u8df3", Rarity.COMMON, 8, ItemCategory.BOOTS);
    }

    @Override
    public boolean passive() {
        return true;
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u6c38\u4e45\u83b7\u5f97 &f\u8df3\u8dc3\u63d0\u5347 " + n + "&7\u3002");
    }

    @Override
    public void onArmorTick(Player player, int n) {
        player.addPotionEffect(new PotionEffect(Compat.JUMP_BOOST, 100, n - 1, true, false, false));
    }
}

