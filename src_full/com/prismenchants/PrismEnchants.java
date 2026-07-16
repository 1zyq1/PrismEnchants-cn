/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants;

import com.prismenchants.command.PrismCommand;
import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.EnchantManager;
import com.prismenchants.enchant.types.AngelicEnchant;
import com.prismenchants.enchant.types.AquaEnchant;
import com.prismenchants.enchant.types.ArthropodBaneEnchant;
import com.prismenchants.enchant.types.AutoRepairEnchant;
import com.prismenchants.enchant.types.BeheadingEnchant;
import com.prismenchants.enchant.types.BerserkEnchant;
import com.prismenchants.enchant.types.BleedEnchant;
import com.prismenchants.enchant.types.BlindEnchant;
import com.prismenchants.enchant.types.CleaveEnchant;
import com.prismenchants.enchant.types.ConfusionEnchant;
import com.prismenchants.enchant.types.CounterEnchant;
import com.prismenchants.enchant.types.CriticalEnchant;
import com.prismenchants.enchant.types.CrushEnchant;
import com.prismenchants.enchant.types.DeflectionEnchant;
import com.prismenchants.enchant.types.ExcavatorEnchant;
import com.prismenchants.enchant.types.ExecuteEnchant;
import com.prismenchants.enchant.types.ExperiencedEnchant;
import com.prismenchants.enchant.types.ExplosiveArrowEnchant;
import com.prismenchants.enchant.types.FireproofEnchant;
import com.prismenchants.enchant.types.FrostArrowEnchant;
import com.prismenchants.enchant.types.FrostbiteEnchant;
import com.prismenchants.enchant.types.FrostguardEnchant;
import com.prismenchants.enchant.types.GreedEnchant;
import com.prismenchants.enchant.types.GuardianEnchant;
import com.prismenchants.enchant.types.HasteEnchant;
import com.prismenchants.enchant.types.HealthBoostEnchant;
import com.prismenchants.enchant.types.IgniteEnchant;
import com.prismenchants.enchant.types.InquisitiveEnchant;
import com.prismenchants.enchant.types.LastStandEnchant;
import com.prismenchants.enchant.types.LaunchEnchant;
import com.prismenchants.enchant.types.LeapingEnchant;
import com.prismenchants.enchant.types.LifestealEnchant;
import com.prismenchants.enchant.types.LightningArrowEnchant;
import com.prismenchants.enchant.types.LightningEnchant;
import com.prismenchants.enchant.types.MoltenEnchant;
import com.prismenchants.enchant.types.ProsperityEnchant;
import com.prismenchants.enchant.types.RegrowthEnchant;
import com.prismenchants.enchant.types.SaturationEnchant;
import com.prismenchants.enchant.types.SlayerEnchant;
import com.prismenchants.enchant.types.SmelterEnchant;
import com.prismenchants.enchant.types.SmiteEnchant;
import com.prismenchants.enchant.types.SniperEnchant;
import com.prismenchants.enchant.types.SpeedsterEnchant;
import com.prismenchants.enchant.types.SpikesEnchant;
import com.prismenchants.enchant.types.StormcallerEnchant;
import com.prismenchants.enchant.types.TankEnchant;
import com.prismenchants.enchant.types.TelekinesisEnchant;
import com.prismenchants.enchant.types.TimberEnchant;
import com.prismenchants.enchant.types.ToxicArrowEnchant;
import com.prismenchants.enchant.types.TreasureHunterEnchant;
import com.prismenchants.enchant.types.VampireEnchant;
import com.prismenchants.enchant.types.VeinminerEnchant;
import com.prismenchants.enchant.types.VenomEnchant;
import com.prismenchants.enchant.types.VolleyEnchant;
import com.prismenchants.enchant.types.WitherArrowEnchant;
import com.prismenchants.listener.AnvilListener;
import com.prismenchants.listener.BlockPlaceListener;
import com.prismenchants.listener.BookApplyListener;
import com.prismenchants.listener.CombatListener;
import com.prismenchants.listener.GuiListener;
import com.prismenchants.listener.MiningListener;
import com.prismenchants.listener.PassiveTask;
import com.prismenchants.listener.RangedListener;
import com.prismenchants.util.Compat;
import com.prismenchants.util.Keys;
import com.prismenchants.util.Lang;
import java.io.File;
import java.io.IOException;
import org.bukkit.command.PluginCommand;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

public final class PrismEnchants
extends JavaPlugin {
    private static PrismEnchants instance;
    private EnchantManager enchantManager;
    private Lang lang;

    public static PrismEnchants get() {
        return instance;
    }

    public EnchantManager enchants() {
        return this.enchantManager;
    }

    public Lang lang() {
        return this.lang;
    }

    @Override
    public void onEnable() {
        instance = this;
        this.saveDefaultConfig();
        Keys.init(this);
        Compat.init();
        this.lang = new Lang(this);
        this.lang.load(this.getConfig().getString("language", "en"));
        this.enchantManager = new EnchantManager();
        this.registerEnchants();
        this.setupEnchantConfig();
        this.getServer().getPluginManager().registerEvents(new CombatListener(this.enchantManager), this);
        this.getServer().getPluginManager().registerEvents(new MiningListener(this.enchantManager), this);
        this.getServer().getPluginManager().registerEvents(new AnvilListener(this.enchantManager), this);
        this.getServer().getPluginManager().registerEvents(new BookApplyListener(this.enchantManager), this);
        this.getServer().getPluginManager().registerEvents(new RangedListener(this.enchantManager), this);
        this.getServer().getPluginManager().registerEvents(new GuiListener(this.enchantManager), this);
        this.getServer().getPluginManager().registerEvents(new BlockPlaceListener(), this);
        this.getServer().getScheduler().runTaskTimer((Plugin)this, new PassiveTask(this.enchantManager), 40L, 40L);
        PrismCommand prismCommand = new PrismCommand(this.enchantManager);
        this.getCommand("prismenchants").setExecutor(prismCommand);
        this.getCommand("prismenchants").setTabCompleter(prismCommand);
        this.getLogger().info("PrismEnchants enabled with " + this.enchantManager.count() + " enchants (language: " + this.getConfig().getString("language", "en") + ").");
    }

    public void reloadAll() {
        this.reloadConfig();
        this.lang.load(this.getConfig().getString("language", "en"));
        this.setupEnchantConfig();
    }

    private void setupEnchantConfig() {
        File file = new File(this.getDataFolder(), "enchants.yml");
        YamlConfiguration yamlConfiguration = YamlConfiguration.loadConfiguration(file);
        ((FileConfiguration)yamlConfiguration).options().header("PrismEnchants \u2014 per-enchant settings.\nenabled: turn an enchant on/off.  success/destroy: book roll chances (%).\nmax-level: highest level this enchant can reach.");
        boolean bl = false;
        for (CustomEnchant customEnchant : this.enchantManager.all()) {
            String string = "enchants." + customEnchant.id() + ".";
            if (!yamlConfiguration.contains(string + "enabled")) {
                yamlConfiguration.set(string + "enabled", true);
                bl = true;
            }
            if (!yamlConfiguration.contains(string + "success")) {
                yamlConfiguration.set(string + "success", customEnchant.rarity().defaultSuccess());
                bl = true;
            }
            if (!yamlConfiguration.contains(string + "destroy")) {
                yamlConfiguration.set(string + "destroy", customEnchant.rarity().defaultDestroy());
                bl = true;
            }
            if (yamlConfiguration.contains(string + "max-level")) continue;
            yamlConfiguration.set(string + "max-level", customEnchant.maxLevel());
            bl = true;
        }
        if (bl) {
            try {
                yamlConfiguration.save(file);
            }
            catch (IOException iOException) {
                this.getLogger().warning("Could not save enchants.yml: " + iOException.getMessage());
            }
        }
        this.enchantManager.loadSettings(yamlConfiguration);
    }

    private void registerEnchants() {
        this.enchantManager.register(new LifestealEnchant());
        this.enchantManager.register(new VenomEnchant());
        this.enchantManager.register(new FrostbiteEnchant());
        this.enchantManager.register(new LightningEnchant());
        this.enchantManager.register(new ExecuteEnchant());
        this.enchantManager.register(new BleedEnchant());
        this.enchantManager.register(new BlindEnchant());
        this.enchantManager.register(new ConfusionEnchant());
        this.enchantManager.register(new IgniteEnchant());
        this.enchantManager.register(new CrushEnchant());
        this.enchantManager.register(new CriticalEnchant());
        this.enchantManager.register(new BerserkEnchant());
        this.enchantManager.register(new VampireEnchant());
        this.enchantManager.register(new BeheadingEnchant());
        this.enchantManager.register(new SmiteEnchant());
        this.enchantManager.register(new ArthropodBaneEnchant());
        this.enchantManager.register(new SlayerEnchant());
        this.enchantManager.register(new InquisitiveEnchant());
        this.enchantManager.register(new GreedEnchant());
        this.enchantManager.register(new LaunchEnchant());
        this.enchantManager.register(new CleaveEnchant());
        this.enchantManager.register(new GuardianEnchant());
        this.enchantManager.register(new SpikesEnchant());
        this.enchantManager.register(new RegrowthEnchant());
        this.enchantManager.register(new AngelicEnchant());
        this.enchantManager.register(new MoltenEnchant());
        this.enchantManager.register(new FrostguardEnchant());
        this.enchantManager.register(new LastStandEnchant());
        this.enchantManager.register(new HealthBoostEnchant());
        this.enchantManager.register(new SpeedsterEnchant());
        this.enchantManager.register(new AquaEnchant());
        this.enchantManager.register(new LeapingEnchant());
        this.enchantManager.register(new StormcallerEnchant());
        this.enchantManager.register(new FireproofEnchant());
        this.enchantManager.register(new TankEnchant());
        this.enchantManager.register(new SaturationEnchant());
        this.enchantManager.register(new DeflectionEnchant());
        this.enchantManager.register(new CounterEnchant());
        this.enchantManager.register(new SmelterEnchant());
        this.enchantManager.register(new VeinminerEnchant());
        this.enchantManager.register(new TreasureHunterEnchant());
        this.enchantManager.register(new HasteEnchant());
        this.enchantManager.register(new ExcavatorEnchant());
        this.enchantManager.register(new TimberEnchant());
        this.enchantManager.register(new ProsperityEnchant());
        this.enchantManager.register(new TelekinesisEnchant());
        this.enchantManager.register(new ExperiencedEnchant());
        this.enchantManager.register(new AutoRepairEnchant());
        this.enchantManager.register(new VolleyEnchant());
        this.enchantManager.register(new ExplosiveArrowEnchant());
        this.enchantManager.register(new LightningArrowEnchant());
        this.enchantManager.register(new WitherArrowEnchant());
        this.enchantManager.register(new SniperEnchant());
        this.enchantManager.register(new ToxicArrowEnchant());
        this.enchantManager.register(new FrostArrowEnchant());
    }
}

