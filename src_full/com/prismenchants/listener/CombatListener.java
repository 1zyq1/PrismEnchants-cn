/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.listener;

import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.EnchantManager;
import com.prismenchants.util.Keys;
import java.util.Map;
import org.bukkit.entity.Entity;
import org.bukkit.entity.LivingEntity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;
import org.bukkit.event.entity.EntityDeathEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.persistence.PersistentDataType;
import org.bukkit.projectiles.ProjectileSource;

public class CombatListener
implements Listener {
    private final EnchantManager manager;

    public CombatListener(EnchantManager enchantManager) {
        this.manager = enchantManager;
    }

    @EventHandler(priority=EventPriority.HIGH, ignoreCancelled=true)
    public void onDamage(EntityDamageByEntityEvent entityDamageByEntityEvent) {
        Entity entity = entityDamageByEntityEvent.getDamager();
        Player player = this.resolveAttacker(entity);
        if (player != null && entityDamageByEntityEvent.getEntity() instanceof LivingEntity) {
            LivingEntity livingEntity = (LivingEntity) entityDamageByEntityEvent.getEntity();
            ItemStack itemInHand = player.getInventory().getItemInMainHand();
            for (Map.Entry<CustomEnchant, Integer> entry : this.manager.getEnchants(itemInHand).entrySet()) {
                if (!this.manager.isEnabled(entry.getKey()) || !entry.getKey().category().matches(itemInHand.getType())) continue;
                entry.getKey().onAttack(player, livingEntity, entry.getValue(), entityDamageByEntityEvent);
            }
            if (entity instanceof Projectile) {
                Projectile projectile = (Projectile)entity;
                this.handleArrowHit(player, livingEntity, projectile, entityDamageByEntityEvent);
            }
        }
        if (entityDamageByEntityEvent.getEntity() instanceof Player) {
            Player defender = (Player) entityDamageByEntityEvent.getEntity();
            for (ItemStack itemStack : defender.getInventory().getArmorContents()) {
                if (itemStack == null) continue;
                for (Map.Entry<CustomEnchant, Integer> entry : this.manager.getEnchants(itemStack).entrySet()) {
                    if (!this.manager.isEnabled(entry.getKey()) || !entry.getKey().category().matches(itemStack.getType())) continue;
                    entry.getKey().onDefend(defender, entity, entry.getValue(), entityDamageByEntityEvent);
                }
            }
        }
    }

    @EventHandler(ignoreCancelled=true)
    public void onFall(EntityDamageEvent entityDamageEvent) {
        if (entityDamageEvent.getCause() != EntityDamageEvent.DamageCause.FALL) {
            return;
        }
        Object object = entityDamageEvent.getEntity();
        if (!(object instanceof Player)) {
            return;
        }
        Player player = (Player)object;
        object = player.getInventory().getBoots();
        if (object == null) {
            return;
        }
        for (Map.Entry<CustomEnchant, Integer> entry : this.manager.getEnchants((ItemStack)object).entrySet()) {
            if (!this.manager.isEnabled(entry.getKey()) || !entry.getKey().category().matches(((ItemStack)object).getType())) continue;
            entry.getKey().onFall(player, entry.getValue(), entityDamageEvent);
        }
    }

    @EventHandler
    public void onKill(EntityDeathEvent entityDeathEvent) {
        Player player = entityDeathEvent.getEntity().getKiller();
        if (player == null) {
            return;
        }
        ItemStack itemStack = player.getInventory().getItemInMainHand();
        for (Map.Entry<CustomEnchant, Integer> entry : this.manager.getEnchants(itemStack).entrySet()) {
            if (!this.manager.isEnabled(entry.getKey()) || !entry.getKey().category().matches(itemStack.getType())) continue;
            entry.getKey().onKill(player, entityDeathEvent.getEntity(), entry.getValue());
            entry.getKey().onKillEvent(player, entityDeathEvent, entry.getValue());
        }
    }

    private void handleArrowHit(Player player, LivingEntity livingEntity, Projectile projectile, EntityDamageByEntityEvent entityDamageByEntityEvent) {
        String string = projectile.getPersistentDataContainer().get(Keys.ARROW_ENCHANTS, PersistentDataType.STRING);
        if (string == null || string.isEmpty()) {
            return;
        }
        for (String string2 : string.split(",")) {
            int n;
            CustomEnchant customEnchant;
            String[] stringArray = string2.split(":");
            if (stringArray.length != 2 || (customEnchant = this.manager.get(stringArray[0])) == null || !this.manager.isEnabled(customEnchant)) continue;
            try {
                n = Integer.parseInt(stringArray[1]);
            }
            catch (NumberFormatException numberFormatException) {
                continue;
            }
            customEnchant.onArrowHit(player, livingEntity, n, entityDamageByEntityEvent);
        }
    }

    private Player resolveAttacker(Entity entity) {
        Projectile projectile;
        ProjectileSource projectileSource;
        if (entity instanceof Player) {
            Player player = (Player)entity;
            return player;
        }
        if (entity instanceof Projectile && (projectileSource = (projectile = (Projectile)entity).getShooter()) instanceof Player) {
            Player player = (Player)projectileSource;
            return player;
        }
        return null;
    }
}

