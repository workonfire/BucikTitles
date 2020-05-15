package pl.workonfire.buciktitles;

import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import me.neznamy.tab.api.EnumProperty;
import me.neznamy.tab.api.TABAPI;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Set;

import static java.lang.String.format;

public class Functions {

    /**
     * Replaces & to § in order to show colors properly.
     * @param text String to format
     * @return Formatted string
     */
    public static String formatColors(String text) {
        return ChatColor.translateAlternateColorCodes('&', text);
    }

    /**
     * Gets access to a list of titles from a specific page.
     * @param page GUI page
     * @return A set of titles
     */
    public static Set<String> getTitlesFromPage(int page) {
        return ConfigManager.config.getConfigurationSection(format("titles.pages.%s", page)).getKeys(false);
    }

    /**
     * Returns a textured player head.
     * @param head ItemStack representation of a player head
     * @param value Head texture as Base64 value
     * @param name Item name
     * @return ItemStack representation of a textured player head
     */
    public static ItemStack getTexturedHead(ItemStack head, String value, String name) {
        NBTItem nbti = new NBTItem(head);

        NBTCompound skull = nbti.addCompound("SkullOwner");
        skull.setString("Name", name);
        skull.setString("Id", "18762a48-d236-474e-becc-071a203abb8a");

        NBTListCompound texture = skull.addCompound("Properties").getCompoundList("textures").addCompound();
        texture.setString("Value", value);
        return nbti.getItem();
    }

    /**
     * Listens for errors and prints the details.
     * @param player Player object
     * @param exception Exception object
     */
    public static void handleErrors(Player player, Exception exception) {
        player.sendMessage(Main.prefix + Functions.formatColors(ConfigManager.config.getString("language.config-load-error")));
        if (ConfigManager.config.getBoolean("options.debug")) {
            player.sendMessage(Main.prefix + Functions.formatColors(ConfigManager.config.getString("language.config-load-error-debug-header")));
            StringWriter sw = new StringWriter();
            exception.printStackTrace(new PrintWriter(sw));
            exception.printStackTrace();
            String exceptionAsString = sw.toString();
            exceptionAsString = exceptionAsString.substring(0, Math.min(exceptionAsString.length(), 256));
            player.sendMessage("§c" + exceptionAsString
                    .replaceAll("\u0009", "    ")
                    .replaceAll("\r", "\n") + "...")
            ;
            player.sendMessage(Main.prefix + Functions.formatColors(ConfigManager.config.getString("language.debug-more-info-in-console")));
        }
    }

    /**
     * Sets the TAB above name for player.
     * @param player Player representation
     * @param title Title value from config
     * @param page Page number where the title appears
     */
    public static void setTitle(Player player, String title, int page) {
        try {
            Title titleObject = new Title(title, page);
            TABAPI.setValuePermanently(player.getUniqueId(), EnumProperty.ABOVENAME, titleObject.getValue());
            //Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tab player " + player.getName() + " abovename " + titleObject.getValue());
            player.closeInventory();
            player.sendMessage(Main.prefix + formatColors(ConfigManager.config.getString("language.title-set")));
            if (ConfigManager.config.getBoolean("options.play-sound-after-setting"))
                player.playSound(player.getLocation(), Sound.ENTITY_LLAMA_SWAG, 1.0F, 1.0F);
        } catch (Exception e) {
            handleErrors(player, e);
        }
    }

    /**
     * Removes the TAB above name from player.
     * @param player Player representation
     */
    public static void takeOff(Player player, boolean silent) {
        try {
            Bukkit.dispatchCommand(Bukkit.getConsoleSender(), "tab player " + player.getName() + " abovename");
            if (!silent) {
                player.closeInventory();
                player.sendMessage(Main.prefix + formatColors(ConfigManager.config.getString("language.title-removed")));
                if (ConfigManager.config.getBoolean("options.play-sound-after-clear"))
                    player.playSound(player.getLocation(), Sound.BLOCK_ANVIL_DESTROY, 1.0F, 1.0F);
            }
        } catch (Exception e) {
            handleErrors(player, e);
        }
    }
}
