package pl.workonfire.buciktitles;

import org.bukkit.plugin.java.JavaPlugin;
import pl.workonfire.buciktitles.commands.MainTitleCommand;
import pl.workonfire.buciktitles.data.Metrics;
import pl.workonfire.buciktitles.listeners.EventHandler;
import pl.workonfire.buciktitles.listeners.TabCompleter;
import pl.workonfire.buciktitles.managers.ConfigManager;

/**
 * A very simple plugin for showing titles on chat above players heads that depends on TAB.
 * @author workonfire, aka Buty935
 * @version 1.1.5
 */

public final class Main extends JavaPlugin {
    public static Main plugin;
    public static final String version = "1.1.5";

    @Override
    public void onEnable() {
        plugin = this;
        plugin.saveDefaultConfig();
        ConfigManager.initializeConfiguration();
        getCommand("titles").setExecutor(new MainTitleCommand());
        getCommand("titles").setTabCompleter(new TabCompleter());
        getServer().getPluginManager().registerEvents(new EventHandler(), plugin);
        System.out.println(ConfigManager.getPrefix() + " §fBucikTitles §6" + version + " §fby Buty935. Discord: §9workonfire#8262");
        if (ConfigManager.getConfig().getBoolean("options.metrics")) {
            int pluginId = 7576;
            new Metrics(plugin, pluginId);
            System.out.println(ConfigManager.getPrefix() + " bStats service has been §2enabled§r! Set §6metrics §rto §cfalse §rin §f§nconfig.yml§r in order to disable metrics.");
        }
    }
}
