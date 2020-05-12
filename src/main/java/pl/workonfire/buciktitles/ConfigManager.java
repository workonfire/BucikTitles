package pl.workonfire.buciktitles;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    public static FileConfiguration config = Main.plugin.getConfig();

    public static void reloadConfiguration() {
        Main.plugin.reloadConfig();
        config = Main.plugin.getConfig();
    }
}
