/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.List;
import java.util.Map;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class SmelterEnchant
extends CustomEnchant {
    private static final Map<Material, Material> SMELTS = Map.ofEntries(Map.entry(Material.IRON_ORE, Material.IRON_INGOT), Map.entry(Material.DEEPSLATE_IRON_ORE, Material.IRON_INGOT), Map.entry(Material.GOLD_ORE, Material.GOLD_INGOT), Map.entry(Material.DEEPSLATE_GOLD_ORE, Material.GOLD_INGOT), Map.entry(Material.COPPER_ORE, Material.COPPER_INGOT), Map.entry(Material.DEEPSLATE_COPPER_ORE, Material.COPPER_INGOT), Map.entry(Material.ANCIENT_DEBRIS, Material.NETHERITE_SCRAP));

    public SmelterEnchant() {
        super("smelter", "\u51b6\u70bc", Rarity.RARE, 1, ItemCategory.PICKAXE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u77ff\u77f3\u81ea\u52a8\u201c\u201c\u51b6\u70bc\u3002");
    }

    @Override
    public void onMine(Player player, Block block, int n, BlockBreakEvent blockBreakEvent) {
        Material material = SMELTS.get(block.getType());
        if (material == null) {
            return;
        }
        blockBreakEvent.setDropItems(false);
        block.getWorld().dropItemNaturally(block.getLocation(), new ItemStack(material));
    }
}

