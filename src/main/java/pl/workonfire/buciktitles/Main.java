package pl.workonfire.buciktitles;

import org.bukkit.plugin.java.JavaPlugin;
import pl.workonfire.buciktitles.commands.MainTitleCommand;
import pl.workonfire.buciktitles.listeners.EventHandler;
import pl.workonfire.buciktitles.listeners.TabCompleter;

import static pl.workonfire.buciktitles.data.Functions.formatColors;
import static pl.workonfire.buciktitles.managers.ConfigManager.getLanguageVariable;

public final class Main extends JavaPlugin {
    public static Main plugin;
    public static String prefix;
    public static final String version = "1.0.3";

    @Override
    public void onEnable() {
        plugin = this;
        plugin.saveDefaultConfig();
        plugin.getCommand("titles").setExecutor(new MainTitleCommand());
        getServer().getPluginManager().registerEvents(new EventHandler(), plugin);
        getCommand("titles").setTabCompleter(new TabCompleter());
        prefix = formatColors(getLanguageVariable("plugin-prefix"));
        System.out.println(prefix + "§fBucikTitles §6" + version + " §fby Buty935. Discord: workonfire#8262");
    }
}
