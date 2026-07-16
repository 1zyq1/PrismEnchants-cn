/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.List;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class ArthropodBaneEnchant
extends CustomEnchant {
    public ArthropodBaneEnchant() {
        super("arthropod_bane", "\u8282\u80a2\u514b\u661f", Rarity.UNCOMMON, 8, ItemCategory.MELEE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7+&c" + n * 2 + " &7\u5bf9 &f\u8282\u80a2\u52a8\u7269 &7\u9020\u6210\u989d\u5916\u4f24\u5bb3\u3002");
    }

    @Override
    public void onAttack(Player player, LivingEntity livingEntity, int n, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        String string = livingEntity.getType().name();
        boolean bl = string.contains("SPIDER") || string.equals("SILVERFISH") || string.equals("ENDERMITE") || string.equals("BEE");
        boolean bl2 = bl;
        if (bl) {
            entityDamageByEntityEvent.setDamage(entityDamageByEntityEvent.getDamage() + (double)n * 2.0);
        }
    }
}

