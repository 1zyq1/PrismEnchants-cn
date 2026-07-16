/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class TreasureHunterEnchant
extends CustomEnchant {
    public TreasureHunterEnchant() {
        super("treasure", "\u5bfb\u5b9d\u730e\u4eba", Rarity.RARE, 8, ItemCategory.PICKAXE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7" + n * 3 + "% \u51e0\u7387\u53d1\u73b0", "&b\u5b9d\u85cf &7\u3002");
    }

    @Override
    public void onMine(Player player, Block block, int n, BlockBreakEvent blockBreakEvent) {
        if (block.hasMetadata("pe_placed")) {
            return;
        }
        String string = block.getType().name();
        boolean bl = string.contains("STONE") || string.contains("DEEPSLATE") || string.endsWith("_ORE");
        boolean bl2 = bl;
        if (!bl) {
            return;
        }
        if (ThreadLocalRandom.current().nextInt(100) >= n * 3) {
            return;
        }
        Material[] materialArray = new Material[]{Material.DIAMOND, Material.EMERALD, Material.GOLD_INGOT, Material.AMETHYST_SHARD, Material.LAPIS_LAZULI};
        Material material = materialArray[ThreadLocalRandom.current().nextInt(materialArray.length)];
        block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(material));
    }
}

