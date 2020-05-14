package pl.workonfire.buciktitles;

import org.bukkit.configuration.file.FileConfiguration;

public class ConfigManager {
    public static FileConfiguration config = Main.plugin.getConfig();

    /**
     * Reloads the configuration.
     */
    public static void reloadConfiguration() {
        Main.plugin.reloadConfig();
        config = Main.plugin.getConfig();
    }
}
