/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.Damageable;
import org.bukkit.inventory.meta.ItemMeta;

public class AutoRepairEnchant
extends CustomEnchant {
    public AutoRepairEnchant() {
        super("autorepair", "\u81ea\u52a8\u4fee\u590d", Rarity.RARE, 8, ItemCategory.TOOL);
    }

    @Override
    public boolean passive() {
        return true;
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u6bcf\u9694\u51e0\u79d2\u4fee\u590d &a" + n + " &7\u70b9\u8010\u4e45", "&7\u6301\u6709\u65f6\u6301\u7eed\u751f\u6548\u3002");
    }

    @Override
    public void onHoldTick(Player player, int n) {
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        ItemMeta itemMeta = itemStack.getItemMeta();
        if (!(itemMeta instanceof Damageable)) {
            return;
        }
        Damageable damageable = (Damageable)itemMeta;
        if (damageable.getDamage() <= 0) {
            return;
        }
        damageable.setDamage(Math.max(0, damageable.getDamage() - n));
        itemStack.setItemMeta(itemMeta);
    }
}

