package pl.workonfire.buciktitles;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import static pl.workonfire.buciktitles.Functions.formatColors;

public final class Main extends JavaPlugin implements Listener {
    public static Main plugin;
    public static String prefix;

    @Override
    public void onEnable() {
        plugin = this;
        plugin.saveDefaultConfig();
        getServer().getPluginManager().registerEvents(new EventHandler(), plugin);
        getCommand("titles").setTabCompleter(new TabCompletion());
        prefix = formatColors(ConfigManager.config.getString("language.plugin-prefix"));
        System.out.println(prefix + "§fBucikTitles by Buty935. Discord: workonfire#8262");
    }

    public boolean onCommand(CommandSender sender, Command cmd, String label, String[] args) {
        if (args.length == 1) {
            if (args[0].equalsIgnoreCase("reload")) {
                if (sender.hasPermission("bucik.titles.reload")) {
                    ConfigManager.reloadConfiguration();
                    sender.sendMessage(prefix + formatColors(ConfigManager.config.getString("language.plugin-reloaded")));
                }
                else sender.sendMessage(prefix + formatColors(ConfigManager.config.getString("language.no-permission")));
            }
            else if (args[0].equalsIgnoreCase("info")) {
                sender.sendMessage("§c§m--------------");
                sender.sendMessage("§bBucikTitles §6Plugin");
                sender.sendMessage("§6Author: §c§lB§6§lu§e§lt§a§ly§b§l9§3§l3§9§l5");
                sender.sendMessage("§c§m--------------");
            }
            else sender.sendMessage(prefix + formatColors(ConfigManager.config.getString("language.subcommand-does-not-exist")));
        }
        else {
            if (sender.hasPermission("bucik.titles.open")) {
                if (sender instanceof Player) GUIManager.showUserInterface(plugin, (Player) sender, 1);
                else sender.sendMessage(prefix + formatColors(ConfigManager.config.getString("language.cannot-open-from-console")));
            }
            else sender.sendMessage(prefix + formatColors(ConfigManager.config.getString("language.no-permission")));
        }
        return true;
    }

}
