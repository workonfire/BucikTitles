package pl.workonfire.buciktitles.data;

import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import me.neznamy.tab.api.EnumProperty;
import me.neznamy.tab.api.TABAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.SkullMeta;
import pl.workonfire.buciktitles.managers.ConfigManager;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.lang.reflect.Field;
import java.util.*;

import static java.lang.String.format;
import static pl.workonfire.buciktitles.managers.ConfigManager.getPrefixedLanguageVariable;

public class Functions {

    /**
     * Replaces & to § in order to show colors properly.
     * @since 1.0
     * @param text String to format
     * @return Formatted string
     */
    public static String formatColors(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Gets access to a list of titles from a specific page.
     * @since 1.0
     * @param page GUI page
     * @return A set of titles
     */
    public static Set<String> getTitlesFromPage(int page) {
        return ConfigManager.getTitlesConfig().getConfigurationSection(format("titles.pages.%s", page)).getKeys(false);
    }

    /**
     * Gets a current user title from TAB API.
     * @since 1.0.7
     * @param player Player object
     * @return Current user title
     */
    public static String getCurrentUserTitle(Player player) {
        return TABAPI.getOriginalValue(player.getUniqueId(), getHeadTitlePosition());
    }

    /**
     * Sets a specific texture for a player head.
     * @since 1.0
     * @param head ItemStack representation of a player head
     * @param value Head texture as Base64 value
     */
    public static void setHeadTexture(ItemStack head, String value) {
        SkullMeta meta = (SkullMeta) head.getItemMeta();
        GameProfile profile = new GameProfile(UUID.randomUUID(), "");
        profile.getProperties().put("textures", new Property("textures", value));
        Field profileField;
        try {
            profileField = meta.getClass().getDeclaredField("profile");
            profileField.setAccessible(true);
            profileField.set(meta, profile);
        } catch (IllegalArgumentException | IllegalAccessException | NoSuchFieldException | SecurityException exception) {
            exception.printStackTrace();
        }
        head.setItemMeta(meta);
    }

    /**
     * Listens for errors and prints the details.
     * @since 1.0
     * @param player Player object
     * @param exception Exception object
     */
    public static void handleErrors(Player player, Exception exception) {
        if (ConfigManager.getConfig().getBoolean("options.play-sounds")) {
            Sound errorSound;
            if (!isServerLegacy())
                errorSound = Sound.ITEM_TRIDENT_THUNDER;
            else
                errorSound = Sound.ENTITY_BAT_DEATH;
            player.playSound(player.getLocation(), errorSound, 1.0F, 1.0F);
        }
        player.sendMessage(getPrefixedLanguageVariable("config-load-error"));
        if (ConfigManager.getConfig().getBoolean("options.debug") && player.hasPermission("bucik.titles.debug")) {
            player.sendMessage(getPrefixedLanguageVariable("config-load-error-debug-header"));
            StringWriter stringWriter = new StringWriter();
            exception.printStackTrace(new PrintWriter(stringWriter));
            exception.printStackTrace();
            String exceptionAsString = stringWriter.toString();
            exceptionAsString = exceptionAsString.substring(0, Math.min(exceptionAsString.length(), 256));
            player.sendMessage("§c" + exceptionAsString
                    .replaceAll("\u0009", "    ")
                    .replaceAll("\r", "\n") + "...")
            ;
            player.sendMessage(getPrefixedLanguageVariable("debug-more-info-in-console"));
        }
    }

    /**
     * Sets the TAB above name for player.
     * @since 1.0
     * @param player Player representation
     * @param titleID Title ID from config
     * @param page Page number where the title appears
     */
    public static void setTitle(Player player, String titleID, int page) {
        try {
            Title title = new Title(titleID, page);
            Sound sound;
            player.closeInventory();
            if (!getCurrentUserTitle(player).equals(title.getRawValue())) {
                TABAPI.setValuePermanently(player.getUniqueId(), getHeadTitlePosition(), title.getRawValue());
                player.sendMessage(getPrefixedLanguageVariable("title-set"));
                sound = Sound.ENTITY_LLAMA_SWAG;
            }
            else {
                player.sendMessage(getPrefixedLanguageVariable("title-already-set"));
                sound = Sound.ENTITY_VILLAGER_NO;
            }
            if (ConfigManager.getConfig().getBoolean("options.play-sounds"))
                player.playSound(player.getLocation(), sound, 1.0F, 1.0F);
        } catch (Exception exception) {
            handleErrors(player, exception);
        }
    }

    /**
     * Sets the raw value of a title for player.
     * @since 1.1.0
     * @param player Player representation
     * @param title Raw title value
     */
    public static void setRawTitle(Player player, String title) {
        TABAPI.setValuePermanently(player.getUniqueId(), getHeadTitlePosition(), title);
    }

    /**
     * Removes the TAB above name from player.
     * @since 1.0
     * @param player Player representation
     * @param silent Whether to show the info for player or not
     */
    public static void takeOff(Player player, boolean silent) {
        try {
            if (!TABAPI.getOriginalValue(player.getUniqueId(), getHeadTitlePosition()).isEmpty()) {
                Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tab player " + player.getName() + " " + getHeadTitlePositionAsString());
                if (!silent) {
                    player.closeInventory();
                    player.sendMessage(getPrefixedLanguageVariable("title-removed"));
                    if (ConfigManager.getConfig().getBoolean("options.play-sounds"))
                        player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1.0F, 1.0F);
                }
            }
            else {
                player.closeInventory();
                player.sendMessage(getPrefixedLanguageVariable("no-title-selected"));
                if (ConfigManager.getConfig().getBoolean("options.play-sounds")) {
                    Sound errorSound;
                    if (!isServerLegacy())
                        errorSound = Sound.ITEM_TRIDENT_THUNDER;
                    else
                        errorSound = Sound.ENTITY_BAT_DEATH;
                    player.playSound(player.getLocation(), errorSound, 0.5F, 1.8F);
                }
            }
        } catch (Exception exception) {
            handleErrors(player, exception);
        }
    }

    /**
     * Gets the position of head title (abovename, belowname)
     * @since 1.0.6
     * @return Head title position as EnumProperty
     */
    public static EnumProperty getHeadTitlePosition() {
        switch (getHeadTitlePositionAsString()) {
            case "abovename":
                return EnumProperty.ABOVENAME;
            case "belowname":
                return EnumProperty.BELOWNAME;
            default:
                return null;
        }
    }

    /**
     * Gets the position of head title (abovename, belowname)
     * @since 1.0.6
     * @return Head title position as String
     */
    public static String getHeadTitlePositionAsString() {
        return ConfigManager.getConfig().getString("options.title-position");
    }

    /**
     * Gets the title property name.
     * @since 1.1.2
     * @param page GUI page where the title appers
     * @param titleID Title ID
     * @param propertyName Property name
     * @return Full property name, e.g. titles.pages.1.wzium.permission
     */
    public static String getTitlePropertyName(int page, String titleID, String propertyName) {
        return format("titles.pages.%d.%s.%s", page, titleID, propertyName);
    }

    /**
     * Gets the title GUI property name.
     * @since 1.1.2
     * @param page GUI page where the title appers
     * @param titleID Title ID
     * @param propertyName Property name
     * @return Full property name, e.g. titles.pages.1.wzium.gui-item.amount
     */
    public static String getTitleGUIPropertyName(int page, String titleID, String propertyName) {
        return format("titles.pages.%d.%s.gui-item.%s", page, titleID, propertyName);
    }

    /**
     * Check if the server is legacy.
     * @since 1.1.5
     * @return true, if the server is running on 1.12 or an earlier version.
     */
    public static boolean isServerLegacy() {
        List<String> newVersions = new ArrayList<>(Arrays.asList("1.13", "1.14", "1.15"));
        for (String version : newVersions)
            if (Bukkit.getVersion().contains(version)) return false;
        return true;
    }

}
