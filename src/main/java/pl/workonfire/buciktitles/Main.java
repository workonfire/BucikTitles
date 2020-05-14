package pl.workonfire.buciktitles;

import me.neznamy.tab.api.EnumProperty;
import me.neznamy.tab.api.TABAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {
    public static Main plugin;
    public static String prefix;

    /**
     * Replaces & to § in order to show colors properly.
     * @param text String to format
     * @return Formatted string
     */
    public static String formatColors(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    @Override
    public void onEnable() {
        plugin = this;
        plugin.saveDefaultConfig();
        prefix = formatColors(ConfigManager.config.getString("language.plugin-prefix"));
        System.out.println(prefix + "§fBucikTitles by Buty935. Discord: workonfire#8262");
        getCommand("titles").setTabCompleter(new TabCompletion());
    }

    /**
     * Sets the TAB above name for player.
     * @param player Player representation
     * @param title_id Title ID from config
     * @param page Page number where the title appears
     */
    public static void setTitle(Player player, String title_id, int page) {
        String title = ConfigManager.config.getString(String.format("titles.pages.%d.%s.title", page, title_id));
        TABAPI.setValuePermanently(player.getUniqueId(), EnumProperty.ABOVENAME, title);
        player.closeInventory();
        player.sendMessage(prefix + formatColors(ConfigManager.config.getString("language.title-set")));
        if (ConfigManager.config.getBoolean("misc.play-sound-after-setting"))
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 2.0F);
    }

    /**
     * Removes the TAB above name from player.
     * @param player Player representation
     */
    public static void takeOff(Player player) {
        Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tab player " + player.getName() + " abovename");
        player.closeInventory();
        player.sendMessage(prefix + formatColors(ConfigManager.config.getString("language.title-removed")));
        if (ConfigManager.config.getBoolean("misc.play-sound-after-clear"))
            player.playSound(player.getLocation(), Sound.BLOCK_NOTE_BLOCK_PLING, 1.0F, 0.5F);
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
