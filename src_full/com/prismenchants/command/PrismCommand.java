/*
 * Decompiled with CFR 0.152.
 */
package com.prismenchants.command;

import com.prismenchants.PrismEnchants;
import com.prismenchants.enchant.CustomEnchant;
import com.prismenchants.enchant.EnchantManager;
import com.prismenchants.gui.EnchanterGUI;
import com.prismenchants.gui.TinkererGUI;
import com.prismenchants.item.ItemFactory;
import com.prismenchants.util.Lang;
import com.prismenchants.util.Text;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.entity.Player;

public class PrismCommand
implements CommandExecutor,
TabCompleter {
    private final EnchantManager manager;

    public PrismCommand(EnchantManager enchantManager) {
        this.manager = enchantManager;
    }

    private Lang lang() {
        return PrismEnchants.get().lang();
    }

    @Override
    public boolean onCommand(CommandSender commandSender, Command command, String string, String[] stringArray) {
        if (stringArray.length == 0) {
            this.help(commandSender);
            return true;
        }
        switch (stringArray[0].toLowerCase(Locale.ROOT)) {
            case "list": {
                this.listEnchants(commandSender);
                break;
            }
            case "enchanter": {
                this.openGui(commandSender, true);
                break;
            }
            case "tinkerer": {
                this.openGui(commandSender, false);
                break;
            }
            case "give": 
            case "book": {
                this.giveBook(commandSender, stringArray);
                break;
            }
            case "scroll": {
                this.giveScroll(commandSender, stringArray);
                break;
            }
            case "reload": {
                if (!commandSender.hasPermission("prismenchants.admin")) {
                    this.noPerm(commandSender);
                    return true;
                }
                PrismEnchants.get().reloadAll();
                commandSender.sendMessage(this.lang().msg("messages.reloaded", new String[0]));
                break;
            }
            default: {
                this.help(commandSender);
            }
        }
        return true;
    }

    private void openGui(CommandSender commandSender, boolean bl) {
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(this.lang().msg("messages.players-only", new String[0]));
            return;
        }
        Player player = (Player)commandSender;
        if (bl) {
            EnchanterGUI.open(player);
        } else {
            TinkererGUI.open(player);
        }
    }

    private void help(CommandSender commandSender) {
        commandSender.sendMessage(Text.of("&5\u2726 &dPrismEnchants"));
        commandSender.sendMessage(Text.of("&7/pe list"));
        commandSender.sendMessage(Text.of("&7/pe enchanter"));
        commandSender.sendMessage(Text.of("&7/pe tinkerer"));
        if (commandSender.hasPermission("prismenchants.admin")) {
            commandSender.sendMessage(Text.of("&7/pe give <enchant> [level]"));
            commandSender.sendMessage(Text.of("&7/pe scroll <white|black>"));
            commandSender.sendMessage(Text.of("&7/pe reload"));
        }
    }

    private void listEnchants(CommandSender commandSender) {
        commandSender.sendMessage(this.lang().msg("messages.list-header", "count", String.valueOf(this.manager.count())));
        for (CustomEnchant customEnchant : this.manager.all()) {
            String string = this.manager.isEnabled(customEnchant) ? "" : " &c(off)";
            commandSender.sendMessage(Text.of(" " + customEnchant.rarity().color() + customEnchant.displayName() + " &8[" + customEnchant.category().display() + ", max " + this.manager.maxLevel(customEnchant) + "]" + string));
        }
    }

    private void giveBook(CommandSender commandSender, String[] stringArray) {
        if (!commandSender.hasPermission("prismenchants.admin")) {
            this.noPerm(commandSender);
            return;
        }
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(this.lang().msg("messages.players-only", new String[0]));
            return;
        }
        Player player = (Player)commandSender;
        if (stringArray.length < 2) {
            commandSender.sendMessage(this.lang().msg("messages.usage-give", new String[0]));
            return;
        }
        CustomEnchant customEnchant = this.manager.get(stringArray[1]);
        if (customEnchant == null) {
            commandSender.sendMessage(this.lang().msg("messages.unknown-enchant", "input", stringArray[1]));
            return;
        }
        int n = 1;
        if (stringArray.length >= 3) {
            try {
                n = Math.max(1, Math.min(Integer.parseInt(stringArray[2]), this.manager.maxLevel(customEnchant)));
            }
            catch (NumberFormatException numberFormatException) {
                // empty catch block
            }
        }
        player.getInventory().addItem(ItemFactory.book(customEnchant, n));
        commandSender.sendMessage(this.lang().msg("messages.book-given", "enchant", customEnchant.rarity().color() + customEnchant.displayName() + " " + EnchantManager.roman(n)));
    }

    private void giveScroll(CommandSender commandSender, String[] stringArray) {
        if (!commandSender.hasPermission("prismenchants.admin")) {
            this.noPerm(commandSender);
            return;
        }
        if (!(commandSender instanceof Player)) {
            commandSender.sendMessage(this.lang().msg("messages.players-only", new String[0]));
            return;
        }
        Player player = (Player)commandSender;
        if (stringArray.length < 2) {
            commandSender.sendMessage(this.lang().msg("messages.usage-scroll", new String[0]));
            return;
        }
        if (stringArray[1].equalsIgnoreCase("white")) {
            player.getInventory().addItem(ItemFactory.whiteScroll());
        } else if (stringArray[1].equalsIgnoreCase("black")) {
            player.getInventory().addItem(ItemFactory.blackScroll());
        } else {
            commandSender.sendMessage(this.lang().msg("messages.usage-scroll", new String[0]));
            return;
        }
        commandSender.sendMessage(this.lang().msg("messages.scroll-given", new String[0]));
    }

    private void noPerm(CommandSender commandSender) {
        commandSender.sendMessage(this.lang().msg("messages.no-permission", new String[0]));
    }

    @Override
    public List<String> onTabComplete(CommandSender commandSender, Command command, String string, String[] stringArray) {
        ArrayList<String> arrayList = new ArrayList<String>();
        if (stringArray.length == 1) {
            for (String string2 : List.of("list", "enchanter", "tinkerer", "give", "scroll", "reload")) {
                if (!string2.startsWith(stringArray[0].toLowerCase(Locale.ROOT))) continue;
                arrayList.add(string2);
            }
        } else if (stringArray.length == 2 && (stringArray[0].equalsIgnoreCase("give") || stringArray[0].equalsIgnoreCase("book"))) {
            for (CustomEnchant customEnchant : this.manager.all()) {
                if (!customEnchant.id().startsWith(stringArray[1].toLowerCase(Locale.ROOT))) continue;
                arrayList.add(customEnchant.id());
            }
        } else if (stringArray.length == 2 && stringArray[0].equalsIgnoreCase("scroll")) {
            arrayList.add("white");
            arrayList.add("black");
        }
        return arrayList;
    }
}

