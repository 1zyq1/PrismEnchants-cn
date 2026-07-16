/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.List;
import org.bukkit.entity.AbstractArrow;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityShootBowEvent;
import org.bukkit.util.Vector;

public class VolleyEnchant
extends CustomEnchant {
    public VolleyEnchant() {
        super("volley", "\u9f50\u5c04", Rarity.EPIC, 8, ItemCategory.BOW);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7\u53d1\u5c04 &b" + n + " &7\u652f\u989d\u5916\u7bad\u77e2", "&7\u5f62\u6210\u6563\u5c04\u3002");
    }

    @Override
    public void onShootBow(Player player, EntityShootBowEvent entityShootBowEvent, int n) {
        Vector vector = entityShootBowEvent.getProjectile().getVelocity();
        float f = (float)vector.length();
        if (f <= 0.0f) {
            return;
        }
        Vector vector2 = vector.clone().normalize();
        for (int i = 0; i < n; ++i) {
            Arrow arrow = player.getWorld().spawnArrow(player.getEyeLocation().add(vector2.clone().multiply(0.5)), vector2, f, 12.0f);
            arrow.setShooter(player);
            arrow.setCritical(true);
            arrow.setPickupStatus(AbstractArrow.PickupStatus.DISALLOWED);
        }
    }
}

