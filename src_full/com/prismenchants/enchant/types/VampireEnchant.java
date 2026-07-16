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
import org.bukkit.potion.PotionEffect;
import org.bukkit.potion.PotionEffectType;

public class VampireEnchant
extends CustomEnchant {
    public VampireEnchant() {
        super("vampire", "\u5438\u8840\u9b3c", Rarity.RARE, 8, ItemCategory.MELEE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u51fb\u6740\u65f6\u5b8c\u5168\u6062\u590d\u5e76\u83b7\u5f97", "&e" + n + " &7\u7075\u9b42\u5fc3\u3002");
    }

    @Override
    public void onKill(Player player, LivingEntity livingEntity, int n) {
        double d = player.getMaxHealth();
        player.setHealth(Math.min(d, player.getHealth() + 6.0));
        player.addPotionEffect(new PotionEffect(PotionEffectType.ABSORPTION, 200, n - 1));
    }
}

