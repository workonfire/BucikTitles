package pl.workonfire.buciktitles;

import org.bukkit.plugin.java.JavaPlugin;
import pl.workonfire.buciktitles.commands.MainTitleCommand;
import pl.workonfire.buciktitles.data.Functions;
import pl.workonfire.buciktitles.listeners.EventHandler;
import pl.workonfire.buciktitles.listeners.TabCompleter;
import pl.workonfire.buciktitles.managers.ConfigManager;

public final class Main extends JavaPlugin {
    public static Main plugin;
    public static String prefix;
    public static final String version = "1.0.4";

    @Override
    public void onEnable() {
        plugin = this;
        plugin.saveDefaultConfig();
        getCommand("titles").setExecutor(new MainTitleCommand());
        getCommand("titles").setTabCompleter(new TabCompleter());
        getServer().getPluginManager().registerEvents(new EventHandler(), plugin);
        prefix = Functions.formatColors(ConfigManager.getLanguageVariable("plugin-prefix"));
        System.out.println(prefix + "§fBucikTitles §6" + version + " §fby Buty935. Discord: workonfire#8262");
    }
}
