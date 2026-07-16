/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.List;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageEvent;

public class AngelicEnchant
extends CustomEnchant {
    public AngelicEnchant() {
        super("angelic", "\u5929\u4f7f\u4e4b\u4f51", Rarity.RARE, 2, ItemCategory.BOOTS);
    }

    @Override
    public List<String> description(int n) {
        return n >= 2 ? List.of("&7\u5b8c\u5168\u514d\u9664\u5760\u843d\u4f24\u5bb3\u3002") : List.of("&7\u51cf\u5c11 50% \u5760\u843d\u4f24\u5bb3\u3002");
    }

    @Override
    public void onFall(Player player, int n, EntityDamageEvent entityDamageEvent) {
        if (n >= 2) {
            entityDamageEvent.setCancelled(true);
        } else {
            entityDamageEvent.setDamage(entityDamageEvent.getDamage() * 0.5);
        }
    }
}

