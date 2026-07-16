/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.List;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class TelekinesisEnchant
extends CustomEnchant {
    public TelekinesisEnchant() {
        super("telekinesis", "\u5ff5\u529b", Rarity.RARE, 1, ItemCategory.TOOL);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u6316\u6398\u6389\u843d\u7269\u76f4\u63a5", "&7\u4f20\u9001\u5230\u80cc\u5305\u3002");
    }

    @Override
    public void onMine(Player player, Block block, int n, BlockBreakEvent blockBreakEvent) {
        ItemStack itemStack2 = player.getInventory().getItemInMainHand();
        blockBreakEvent.setDropItems(false);
        for (ItemStack itemStack3 : block.getDrops(itemStack2)) {
            player.getInventory().addItem(itemStack3).values().forEach(itemStack -> block.getWorld().dropItemNaturally(block.getLocation(), (ItemStack)itemStack));
        }
    }
}

