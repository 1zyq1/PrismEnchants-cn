/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.listener;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.EnchantManager;
import com.prismenchants.util.Keys;
import java.util.Map;
import java.util.StringJoiner;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;

public class RangedListener
implements Listener {
    private final EnchantManager manager;

    public RangedListener(EnchantManager enchantManager) {
        this.manager = enchantManager;
    }

    @EventHandler(ignoreCancelled=true)
    public void onShoot(EntityShootBowEvent entityShootBowEvent) {
        Object object = entityShootBowEvent.getEntity();
        if (!(object instanceof Player)) {
            return;
        }
        Player player = (Player)object;
        object = entityShootBowEvent.getBow();
        if (object == null) {
            return;
        }
        Map<CustomEnchant, Integer> map = this.manager.getEnchants((ItemStack)object);
        if (map.isEmpty()) {
            return;
        }
        StringJoiner stringJoiner = new StringJoiner(",");
        for (Map.Entry<CustomEnchant, Integer> entry : map.entrySet()) {
            if (!this.manager.isEnabled(entry.getKey()) || !entry.getKey().category().matches(((ItemStack)object).getType())) continue;
            entry.getKey().onShootBow(player, entityShootBowEvent, entry.getValue());
            if (!entry.getKey().ranged()) continue;
            stringJoiner.add(entry.getKey().id() + ":" + String.valueOf(entry.getValue()));
        }
        if (stringJoiner.length() > 0 && entityShootBowEvent.getProjectile() != null) {
            entityShootBowEvent.getProjectile().getPersistentDataContainer().set(Keys.ARROW_ENCHANTS, PersistentDataType.STRING, stringJoiner.toString());
        }
    }
}

