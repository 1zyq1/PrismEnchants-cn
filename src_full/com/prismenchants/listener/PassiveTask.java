/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.listener;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.EnchantManager;
import java.util.Map;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class PassiveTask
implements Runnable {
    private final EnchantManager manager;

    public PassiveTask(EnchantManager enchantManager) {
        this.manager = enchantManager;
    }

    @Override
    public void run() {
        for (Player player : Bukkit.getOnlinePlayers()) {
            for (ItemStack itemStack : player.getInventory().getArmorContents()) {
                if (itemStack == null) continue;
                for (Map.Entry<CustomEnchant, Integer> entry : this.manager.getEnchants(itemStack).entrySet()) {
                    if (!entry.getKey().passive() || !this.manager.isEnabled(entry.getKey()) || !entry.getKey().category().matches(itemStack.getType())) continue;
                    entry.getKey().onArmorTick(player, entry.getValue());
                }
            }
            ItemStack itemStack = player.getInventory().getItemInMainHand();
            for (Map.Entry<CustomEnchant, Integer> entry : this.manager.getEnchants(itemStack).entrySet()) {
                if (!entry.getKey().passive() || !this.manager.isEnabled(entry.getKey()) || !entry.getKey().category().matches(itemStack.getType())) continue;
                entry.getKey().onHoldTick(player, entry.getValue());
            }
        }
    }
}

