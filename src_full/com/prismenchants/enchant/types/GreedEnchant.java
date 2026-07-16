/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;

public class GreedEnchant
extends CustomEnchant {
    public GreedEnchant() {
        super("greed", "\u8d2a\u5a6a", Rarity.RARE, 8, ItemCategory.MELEE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7" + n * 6 + "% \u51e0\u7387 &a\u7ffb\u500d", "&7\u602a\u7269\u7684\u6389\u843d\u7269\u3002");
    }

    @Override
    public void onKillEvent(Player player, EntityDeathEvent entityDeathEvent, int n) {
        if (ThreadLocalRandom.current().nextInt(100) >= n * 6) {
            return;
        }
        ArrayList<ItemStack> arrayList = new ArrayList<ItemStack>();
        for (ItemStack itemStack : entityDeathEvent.getDrops()) {
            arrayList.add(itemStack.clone());
        }
        entityDeathEvent.getDrops().addAll(arrayList);
        entityDeathEvent.setDroppedExp(entityDeathEvent.getDroppedExp() * 2);
    }
}

