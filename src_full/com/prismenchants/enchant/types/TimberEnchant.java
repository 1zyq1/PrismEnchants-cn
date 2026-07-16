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

public class TimberEnchant
extends CustomEnchant {
    public TimberEnchant() {
        super("timber", "\u4f10\u6728", Rarity.EPIC, 1, ItemCategory.AXE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u780d\u65ad\u4e00\u4e2a\u6811\u5757\u65f6", "&7\u5c06\u780d\u5012\u6574\u68f5\u6811\u3002");
    }

    @Override
    public void onMine(Player player, Block block, int n, BlockBreakEvent blockBreakEvent) {
        Material material = block.getType();
        if (!this.isLog(material)) {
            return;
        }
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        HashSet<Block> hashSet = new HashSet<Block>();
        ArrayDeque<Block> arrayDeque = new ArrayDeque<Block>();
        arrayDeque.add(block);
        hashSet.add(block);
        int n2 = 80;
        while (!arrayDeque.isEmpty() && hashSet.size() <= n2) {
            Block block2 = (Block)arrayDeque.poll();
            if (!block2.equals(block)) {
                block2.breakNaturally(itemStack);
            }
            for (int i = -1; i <= 1; ++i) {
                for (int j = 0; j <= 1; ++j) {
                    for (int k = -1; k <= 1; ++k) {
                        Block block3 = block2.getRelative(i, j, k);
                        if (block3.getType() != material || !hashSet.add(block3) || hashSet.size() > n2) continue;
                        arrayDeque.add(block3);
                    }
                }
            }
        }
    }

    private boolean isLog(Material material) {
        String string = material.name();
        return string.endsWith("_LOG") || string.endsWith("_WOOD") || string.endsWith("_STEM") || string.endsWith("_HYPHAE");
    }
}

