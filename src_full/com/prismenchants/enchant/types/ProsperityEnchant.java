/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class ProsperityEnchant
extends CustomEnchant {
    public ProsperityEnchant() {
        super("prosperity", "\u7e41\u8363", Rarity.RARE, 8, ItemCategory.PICKAXE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7" + n * 5 + "% \u51e0\u7387 &a\u7ffb\u500d", "&7\u6316\u6398\u6389\u843d\u7269\u3002");
    }

    @Override
    public void onMine(Player player, Block block, int n, BlockBreakEvent blockBreakEvent) {
        if (block.hasMetadata("pe_placed")) {
            return;
        }
        if (ThreadLocalRandom.current().nextInt(100) >= n * 5) {
            return;
        }
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        for (ItemStack itemStack2 : block.getDrops(itemStack)) {
            block.getWorld().dropItemNaturally(block.getLocation(), itemStack2.clone());
        }
    }
}

