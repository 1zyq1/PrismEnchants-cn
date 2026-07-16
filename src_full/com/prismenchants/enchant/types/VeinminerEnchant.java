/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.ArrayDeque;
import java.util.HashSet;
import java.util.List;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class VeinminerEnchant
extends CustomEnchant {
    public VeinminerEnchant() {
        super("veinminer", "\u8fde\u9501\u6316\u77ff", Rarity.EPIC, 8, ItemCategory.PICKAXE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u6f5b\u884c\u6316\u6398\u53ef\u780d\u65ad", "&b" + n * 16 + " &7\u4e2a\u8fde\u9501\u77ff\u77f3\u5757\u3002");
    }

    @Override
    public void onMine(Player player, Block block, int n, BlockBreakEvent blockBreakEvent) {
        if (!player.isSneaking()) {
            return;
        }
        Material material = block.getType();
        if (!material.name().endsWith("_ORE") && material != Material.ANCIENT_DEBRIS) {
            return;
        }
        int n2 = n * 16;
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        HashSet<Block> hashSet = new HashSet<Block>();
        ArrayDeque<Block> arrayDeque = new ArrayDeque<Block>();
        arrayDeque.add(block);
        hashSet.add(block);
        while (!arrayDeque.isEmpty() && hashSet.size() <= n2) {
            Block block2 = (Block)arrayDeque.poll();
            if (!block2.equals(block)) {
                if (block2.getType() != material) continue;
                block2.breakNaturally(itemStack);
            }
            for (int i = -1; i <= 1; ++i) {
                for (int j = -1; j <= 1; ++j) {
                    for (int k = -1; k <= 1; ++k) {
                        Block block3;
                        if (i == 0 && j == 0 && k == 0 || (block3 = block2.getRelative(i, j, k)).getType() != material || !hashSet.add(block3) || hashSet.size() > n2) continue;
                        arrayDeque.add(block3);
                    }
                }
            }
        }
    }
}

