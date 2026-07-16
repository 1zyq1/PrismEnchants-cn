/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.enchant.types;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.ItemCategory;
import com.prismenchants.enchant.Rarity;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;
import org.bukkit.Material;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;

public class BeheadingEnchant
extends CustomEnchant {
    public BeheadingEnchant() {
        super("beheading", "\u65a9\u9996", Rarity.RARE, 8, ItemCategory.MELEE);
    }

    @Override
    public List<String> description(int n) {
        return List.of("&7" + n * 8 + "% \u51e0\u7387\u6389\u843d", "&7\u53d7\u5bb3\u8005\u7684 &f\u5934\u90e8&7\u3002");
    }

    @Override
    public void onKill(Player player, LivingEntity livingEntity, int n) {
        if (ThreadLocalRandom.current().nextInt(100) >= n * 8) {
            return;
        }
        ItemStack itemStack = this.headFor(livingEntity);
        if (itemStack == null) {
            return;
        }
        livingEntity.getWorld().dropItemNaturally(livingEntity.getLocation(), itemStack);
    }

    private ItemStack headFor(LivingEntity livingEntity) {
        if (livingEntity instanceof Player) {
            Player player = (Player)livingEntity;
            ItemStack itemStack = new ItemStack(Material.PLAYER_HEAD);
            SkullMeta skullMeta = (SkullMeta)itemStack.getItemMeta();
            skullMeta.setOwningPlayer(player);
            itemStack.setItemMeta(skullMeta);
            return itemStack;
        }
        Material material = switch (livingEntity.getType()) {
            case ZOMBIE -> Material.ZOMBIE_HEAD;
            case SKELETON -> Material.SKELETON_SKULL;
            case WITHER_SKELETON -> Material.WITHER_SKELETON_SKULL;
            case CREEPER -> Material.CREEPER_HEAD;
            case PIGLIN -> Material.PIGLIN_HEAD;
            case ENDER_DRAGON -> Material.DRAGON_HEAD;
            default -> null;
        };
        return material == null ? null : new ItemStack(material);
    }
}

