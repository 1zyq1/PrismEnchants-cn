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

public class ExperiencedEnchant
extends CustomEnchant {
    public ExperiencedEnchant() {
        super("experienced", "\u7ecf\u9a8c\u4e30\u5bcc", Rarity.UNCOMMON, 8, ItemCategory.TOOL);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u6bcf\u6316\u6398\u4e00\u5757\u77ff\u77f3/\u77f3\u5934\u83b7\u5f97 &a+" + n + " &7\u7ecf\u9a8c\u503c\u3002");
    }

    @Override
    public void onMine(Player player, Block block, int n, BlockBreakEvent blockBreakEvent) {
        String string = block.getType().name();
        if (string.contains("STONE") || string.contains("DEEPSLATE") || string.endsWith("_ORE")) {
            player.giveExp(n);
        }
    }
}

