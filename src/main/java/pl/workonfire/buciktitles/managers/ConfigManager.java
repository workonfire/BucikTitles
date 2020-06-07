package pl.workonfire.buciktitles.managers;

import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;
import pl.workonfire.buciktitles.Main;
import pl.workonfire.buciktitles.data.Functions;

import java.io.File;

public class ConfigManager {
    private static FileConfiguration config;
    private static FileConfiguration languageConfig;
    private static FileConfiguration titlesConfig;

    /**
     * @since 1.0.5
     * Initializes the configuration files.
     */
    public static void initializeConfiguration() {
        config = Main.plugin.getConfig();
        final String languageFileName = config.getString("options.locale");
        final File languageConfigFile = new File(Main.plugin.getDataFolder() + "/locales", languageFileName + ".yml");
        final File titlesConfigFile = new File(Main.plugin.getDataFolder(), "titles.yml");
        if (!languageConfigFile.exists()) {
            languageConfigFile.getParentFile().mkdirs();
            Main.plugin.saveResource("locales/pl.yml", false);
            Main.plugin.saveResource("locales/en.yml", false);
        }
        if (!titlesConfigFile.exists()) {
            titlesConfigFile.getParentFile().mkdirs();
            Main.plugin.saveResource("titles.yml", false);
        }
        languageConfig = YamlConfiguration.loadConfiguration(languageConfigFile);
        titlesConfig = YamlConfiguration.loadConfiguration(titlesConfigFile);
    }

    /**
     * @since 1.0.5
     * Reloads the configuration files.
     */
    public static void reloadConfiguration() {
        Main.plugin.reloadConfig();
        initializeConfiguration();
    }

    /**
     * @since 1.0.1
     * Gets a language variable value from the config.
     * @param variable Unparsed language variable, e.g. "no-permission"
     * @return Language string
     */
    public static String getLanguageVariable(String variable) {
        return Functions.formatColors(getLanguageConfig().getString("language." + variable));
    }

    /**
     * Gets a language variable value from the config including a prefix.
     * @since 1.0.7
     * @param variable Unparsed language variable, e.g. "no-permission"
     * @return Language string with prefix.
     */
    public static String getPrefixedLanguageVariable(String variable) {
        return getPrefix() + " " + Functions.formatColors(getLanguageConfig().getString("language." + variable));
    }

    /**
     * Gets a global plugin prefix.
     * @since 1.0.6
     * @return Plugin prefix
     */
    public static String getPrefix() {
        return getLanguageVariable("plugin-prefix");
    }

    public static FileConfiguration getConfig() {
        return config;
    }

    public static FileConfiguration getLanguageConfig() {
        return languageConfig;
    }

    public static FileConfiguration getTitlesConfig() {
        return titlesConfig;
    }
}
