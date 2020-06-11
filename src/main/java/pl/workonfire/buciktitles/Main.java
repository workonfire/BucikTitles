package pl.workonfire.buciktitles;

import org.bukkit.plugin.java.JavaPlugin;
import pl.workonfire.buciktitles.commands.MainTitleCommand;
import pl.workonfire.buciktitles.data.Functions;
import pl.workonfire.buciktitles.data.Metrics;
import pl.workonfire.buciktitles.listeners.EventHandler;
import pl.workonfire.buciktitles.listeners.TabCompleter;
import pl.workonfire.buciktitles.managers.ConfigManager;

/**
 * A very simple plugin for showing titles on chat and above players heads.
 * Made with ♥
 *
 * @author  workonfire, aka Buty935
 * @version 1.1.6
 * @since   2020-05-13
 */

@SuppressWarnings("ConstantConditions")
public final class Main extends JavaPlugin {
    public static Main plugin;
    public static String pluginVersion;

    @Override
    public void onEnable() {
        plugin = this;
        pluginVersion = getPlugin().getDescription().getVersion();
        getPlugin().saveDefaultConfig();
        ConfigManager.initializeConfiguration();
        getCommand("titles").setExecutor(new MainTitleCommand());
        if (!Functions.isServerLegacy()) getCommand("titles").setTabCompleter(new TabCompleter());
        getServer().getPluginManager().registerEvents(new EventHandler(), getPlugin());
        System.out.println(ConfigManager.getPrefix() + " §fBucikTitles §6" + getPluginVersion() + " §fby Buty935. Discord: §9workonfire#8262");
        if (ConfigManager.getConfig().getBoolean("options.metrics")) {
            final int pluginId = 7576;
            new Metrics(getPlugin(), pluginId);
            System.out.println(ConfigManager.getPrefix() + " bStats service has been §2enabled§r! Set §6metrics §rto §cfalse §rin §f§nconfig.yml§r in order to disable metrics.");
        }
    }

    public static Main getPlugin() {
        return plugin;
    }

    public static String getPluginVersion() {
        return pluginVersion;
    }
}
