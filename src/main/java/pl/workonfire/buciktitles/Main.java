package pl.workonfire.buciktitles;

import org.bukkit.plugin.java.JavaPlugin;
import pl.workonfire.buciktitles.commands.MainTitleCommand;
import pl.workonfire.buciktitles.listeners.EventHandler;
import pl.workonfire.buciktitles.listeners.TabCompleter;
import pl.workonfire.buciktitles.managers.ConfigManager;

/**
 * A very simple plugin for showing titles on chat above players heads that depends on TAB.
 * @author workonfire, aka Buty935
 * @version 1.0.7
 */

public final class Main extends JavaPlugin {
    public static Main plugin;
    public static final String version = "1.0.7";

    @Override
    public void onEnable() {
        plugin = this;
        plugin.saveDefaultConfig();
        ConfigManager.initializeConfiguration();
        getCommand("titles").setExecutor(new MainTitleCommand());
        getCommand("titles").setTabCompleter(new TabCompleter());
        getServer().getPluginManager().registerEvents(new EventHandler(), plugin);
        System.out.println(ConfigManager.getPrefix() + "§fBucikTitles §6" + version + " §fby Buty935. Discord: workonfire#8262");
    }
}
