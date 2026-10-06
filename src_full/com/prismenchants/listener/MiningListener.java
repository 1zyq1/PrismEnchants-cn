/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.listener;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.EnchantManager;
import com.prismenchants.util.SafeBreak;
import java.util.Map;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.BlockBreakEvent;
import org.bukkit.inventory.ItemStack;

public class MiningListener
implements Listener {
    private final EnchantManager manager;

    public MiningListener(EnchantManager enchantManager) {
        this.manager = enchantManager;
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onBreak(BlockBreakEvent blockBreakEvent) {
        // 连锁/范围挖掘由 SafeBreak 触发的事件不再二次处理，避免递归与重复结算
        if (SafeBreak.isFiring()) {
            return;
        }
        Player player = blockBreakEvent.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        for (Map.Entry<CustomEnchant, Integer> entry : this.manager.getEnchants(itemStack).entrySet()) {
            if (!this.manager.isEnabled(entry.getKey()) || !entry.getKey().category().matches(itemStack.getType())) continue;
            entry.getKey().onMine(player, blockBreakEvent.getBlock(), entry.getValue(), blockBreakEvent);
        }
    }
}

