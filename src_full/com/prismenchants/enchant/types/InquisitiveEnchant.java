/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.List;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;

public class InquisitiveEnchant
extends CustomEnchant {
    public InquisitiveEnchant() {
        super("inquisitive", "\u63a2\u7d22\u4e4b\u5fc3", Rarity.UNCOMMON, 8, ItemCategory.MELEE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u6bcf\u6b21\u51fb\u6740\u83b7\u5f97 &a+" + n * 2 + " &7\u7ecf\u9a8c\u503c\u3002");
    }

    @Override
    public void onKill(Player player, LivingEntity livingEntity, int n) {
        player.giveExp(n * 2);
    }
}

