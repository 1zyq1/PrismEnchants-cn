/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class ExcavatorEnchant
extends CustomEnchant {
    public ExcavatorEnchant() {
        super("excavator", "\u6316\u6398\u8005", Rarity.EPIC, 1, ItemCategory.PICKAXE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u6f5c\u884c\u6316\u6398\u65f6\u4e00\u6b21\u7834\u574f", "&b3x3 &7\u533a\u57df\u3002");
    }

    @Override
    public void onMine(Player player, Block block, int n, BlockBreakEvent blockBreakEvent) {
        if (!player.isSneaking()) {
            return;
        }
        if (!ExcavatorEnchant.mineable(block.getType())) {
            return;
        }
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        for (int i = -1; i <= 1; ++i) {
            for (int j = -1; j <= 1; ++j) {
                for (int k = -1; k <= 1; ++k) {
                    Block block2;
                    if (i == 0 && j == 0 && k == 0 || !ExcavatorEnchant.mineable((block2 = block.getRelative(i, j, k)).getType())) continue;
                    block2.breakNaturally(itemStack);
                }
            }
        }
    }

    static boolean mineable(Material material) {
        String string = material.name();
        return string.contains("STONE") || string.contains("DEEPSLATE") || string.contains("NETHERRACK") || string.contains("ANDESITE") || string.contains("DIORITE") || string.contains("GRANITE") || string.contains("TUFF") || string.contains("BASALT") || string.contains("BLACKSTONE") || string.endsWith("_ORE") || string.equals("DIRT") || string.equals("GRAVEL");
    }
}

