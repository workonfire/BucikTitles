package pl.workonfire.buciktitles.commands;

import me.neznamy.tab.api.TABAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.workonfire.buciktitles.Main;
import pl.workonfire.buciktitles.data.Functions;
import pl.workonfire.buciktitles.managers.ConfigManager;
import pl.workonfire.buciktitles.managers.GUIManager;

import static pl.workonfire.buciktitles.data.Functions.formatColors;
import static pl.workonfire.buciktitles.managers.ConfigManager.getLanguageVariable;

public class MainTitleCommand implements CommandExecutor {
    public static String prefix;

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        prefix = formatColors(getLanguageVariable("plugin-prefix"));
        if (args.length > 0) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("bucik.titles.reload")) {
                    ConfigManager.reloadConfiguration();
                    sender.sendMessage(prefix + formatColors(getLanguageVariable("plugin-reloaded")));
                }
                else sender.sendMessage(prefix + formatColors(getLanguageVariable("no-permission")));
            }
            else if (args[0].equalsIgnoreCase("info")) {
                sender.sendMessage("§c§m--------------\n" +
                        "§bBucikTitles §6Plugin\n" +
                        "§6Author: §c§lB§6§lu§e§lt§a§ly§b§l9§3§l3§9§l5\n" +
                        "§6Version: §b" + Main.version + "\n" +
                        "§c§m--------------");
            }
            else if (args[0].equalsIgnoreCase("clear")) Functions.takeOff((Player) sender, false);
            else sender.sendMessage(prefix + formatColors(getLanguageVariable("subcommand-does-not-exist")));
        }
        else {
            if (sender.hasPermission("bucik.titles.open")) {
                if (!TABAPI.isUnlimitedNameTagModeEnabled() && sender.hasPermission("bucik.titles.debug"))
                    sender.sendMessage(prefix + formatColors(getLanguageVariable("unlimited-name-tag-mode-not-enabled")));
                if (sender instanceof Player) GUIManager.showUserInterface(Main.plugin, (Player) sender, 1);
                else sender.sendMessage(prefix + formatColors(getLanguageVariable("cannot-open-from-console")));
            }
            else sender.sendMessage(prefix + formatColors(getLanguageVariable("no-permission")));
        }
        return true;
    }
}