package pl.workonfire.buciktitles.commands;

import me.clip.placeholderapi.PlaceholderAPI;
import me.neznamy.tab.api.TABAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import pl.workonfire.buciktitles.Main;
import pl.workonfire.buciktitles.data.Functions;
import pl.workonfire.buciktitles.managers.ConfigManager;
import pl.workonfire.buciktitles.managers.GUIManager;

import static pl.workonfire.buciktitles.managers.ConfigManager.getPrefixedLanguageVariable;

public class MainTitleCommand implements CommandExecutor {

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        try {
            if (args.length > 0) {
                    /* /titles reload */
                if (args[0].equalsIgnoreCase("reload")) {
                    if (sender.hasPermission("bucik.titles.reload")) {
                        ConfigManager.reloadConfiguration();
                        sender.sendMessage(getPrefixedLanguageVariable("plugin-reloaded"));
                    } else sender.sendMessage(getPrefixedLanguageVariable("no-permission"));

                    /* /titles info */
                } else if (args[0].equalsIgnoreCase("info")) {
                    String header;
                    if (!(sender instanceof Player)) header = "\n§c§m--------------\n"; // for console
                    else header = "§c§m--------------\n";
                    sender.sendMessage(header +
                            "§bBucikTitles §6" + Main.version + "\n" +
                            "§6by §c§lB§6§lu§e§lt§a§ly§b§l9§3§l3§9§l5\n" +
                            "§6§ohttps://github.com/workonfire\n" +
                            "§c§m--------------");

                    /* /titles get */
                } else if (args[0].equalsIgnoreCase("get")) {
                    if (sender instanceof Player) {
                        if (sender.hasPermission("bucik.titles.get")) {
                            String title = PlaceholderAPI.setPlaceholders((Player) sender, Functions.getCurrentUserTitle((Player) sender))
                                    .replaceAll("%", "%%");
                            if (!title.isEmpty()) {
                                sender.sendMessage(getPrefixedLanguageVariable("currently-selected-titles-get") + title);
                            } else sender.sendMessage(getPrefixedLanguageVariable("no-title-selected"));
                        } else sender.sendMessage(getPrefixedLanguageVariable("no-permission"));
                    } else
                        sender.sendMessage(getPrefixedLanguageVariable("cannot-open-from-console"));

                    /* /titles enableUnlimitedNameTagMode */
                } else if (args[0].equalsIgnoreCase("enableUnlimitedNameTagMode")) {
                    if (sender.hasPermission("bucik.titles.debug")) {
                        if (!TABAPI.isUnlimitedNameTagModeEnabled()) {
                            TABAPI.enableUnlimitedNameTagModePermanently();
                            sender.sendMessage(getPrefixedLanguageVariable("unlimited-name-tag-mode-has-been-enabled"));
                        } else
                            sender.sendMessage(getPrefixedLanguageVariable("unlimited-name-tag-mode-is-enabled"));
                    } else sender.sendMessage(getPrefixedLanguageVariable("no-permission"));

                    /* /titles clear */
                } else if (args[0].equalsIgnoreCase("clear")) {
                    if (sender instanceof Player) {
                        if (sender.hasPermission("bucik.titles.debug")) Functions.takeOff((Player) sender, false);
                        else sender.sendMessage(getPrefixedLanguageVariable("no-permission"));
                    } else
                        sender.sendMessage(getPrefixedLanguageVariable("cannot-open-from-console"));
                }
                else sender.sendMessage(getPrefixedLanguageVariable("subcommand-does-not-exist"));

                /* /titles */
            } else {
                if (sender.hasPermission("bucik.titles.open")) {
                    if (!TABAPI.isUnlimitedNameTagModeEnabled() && sender.hasPermission("bucik.titles.debug"))
                        sender.sendMessage(getPrefixedLanguageVariable("unlimited-name-tag-mode-not-enabled"));
                    if (sender instanceof Player) GUIManager.showGUI(Main.plugin, (Player) sender, 1);
                    else
                        sender.sendMessage(getPrefixedLanguageVariable("cannot-open-from-console"));
                } else sender.sendMessage(getPrefixedLanguageVariable("no-permission"));
            }
            return true;
        } catch (Exception exception) {
            Functions.handleErrors((Player) sender, exception);
        }
        return true;
    }
}
