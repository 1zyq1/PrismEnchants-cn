/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.util;

import org.bukkit.Bukkit;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

/**
 * 连锁/范围挖掘统一的“安全破坏”入口。
 * 直接调用 Block.breakNaturally() 不会触发 BlockBreakEvent，
 * Residence / WorldGuard 等保护插件因此无法拦截。
 * 这里先触发一个可取消的 BlockBreakEvent，仅在未被取消时才真正破坏。
 */
public final class SafeBreak {
    private static boolean firing = false;

    private SafeBreak() {
    }

    /** 是否正在由本工具触发合成事件，供 MiningListener 防重入。 */
    public static boolean isFiring() {
        return firing;
    }

    /**
     * 尝试破坏方块。已被保护插件拦截、或属于玩家放置方块时返回 false。
     */
    public static boolean breakBlock(Player player, Block block, ItemStack tool) {
        if (block == null || block.getType().isAir() || block.hasMetadata("pe_placed")) {
            return false;
        }
        boolean cancelled;
        firing = true;
        try {
            BlockBreakEvent event = new BlockBreakEvent(block, player);
            Bukkit.getPluginManager().callEvent(event);
            cancelled = event.isCancelled();
        }
        finally {
            firing = false;
        }
        if (cancelled) {
            return false;
        }
        block.breakNaturally(tool);
        return true;
    }
}
