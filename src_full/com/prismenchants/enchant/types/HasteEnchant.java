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

public class HasteEnchant
extends CustomEnchant {
    public HasteEnchant() {
        super("haste", "\u6025\u901f", Rarity.UNCOMMON, 8, ItemCategory.PICKAXE);
    }

    @Override
    public boolean passive() {
        return true;
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u624b\u6301\u65f6\u83b7\u5f97 &f\u6025\u901f " + n + " &7\u6548\u679c\u3002");
    }

    @Override
    public void onHoldTick(Player player, int n) {
        player.addPotionEffect(new PotionEffect(Compat.HASTE, 100, n - 1, true, false, false));
    }
}

