package pl.workonfire.buciktitles.managers;

import org.bukkit.configuration.file.FileConfiguration;
import pl.workonfire.buciktitles.Main;

public class ConfigManager {
    public static FileConfiguration config = Main.plugin.getConfig();

    /**
     * Reloads the configuration.
     */
    public static void reloadConfiguration() {
        Main.plugin.reloadConfig();
        config = Main.plugin.getConfig();
    }

    /**
     * Gets a language variable value from the config.
     * @param variable Unparsed language variable, e.g. "no-permission"
     * @return Language string
     */
    public static String getLanguageVariable(String variable) {
        return config.getString("language." + variable);
    }
}
