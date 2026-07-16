/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.listener;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.EnchantManager;
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
        Player player = blockBreakEvent.getPlayer();
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        for (Map.Entry<CustomEnchant, Integer> entry : this.manager.getEnchants(itemStack).entrySet()) {
            if (!this.manager.isEnabled(entry.getKey()) || !entry.getKey().category().matches(itemStack.getType())) continue;
            entry.getKey().onMine(player, blockBreakEvent.getBlock(), entry.getValue(), blockBreakEvent);
        }
    }
}

