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

public class FireproofEnchant
extends CustomEnchant {
    public FireproofEnchant() {
        super("fireproof", "\u9632\u706b", Rarity.RARE, 1, ItemCategory.ARMOR);
    }

    @Override
    public boolean passive() {
        return true;
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u7a7f\u6234\u65f6\u514d\u75ab &6\u706b\u7130\u548c\u5ca9\u6d46&7\u3002");
    }

    @Override
    public void onArmorTick(Player player, int n) {
        player.addPotionEffect(new PotionEffect(PotionEffectType.FIRE_RESISTANCE, 100, 0, true, false, false));
    }
}

