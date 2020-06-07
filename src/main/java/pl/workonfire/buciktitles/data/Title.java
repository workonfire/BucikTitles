package pl.workonfire.buciktitles.data;

import me.neznamy.tab.api.EnumProperty;
import me.neznamy.tab.api.TABAPI;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import pl.workonfire.buciktitles.managers.ConfigManager;

import java.util.List;
import java.util.Set;

import static java.lang.String.format;
import static pl.workonfire.buciktitles.managers.ConfigManager.getTitlesConfig;

public class Title {
    private final String titleID;
    private final String title;
    private final String permission;
    private final String nameInGUI;
    private final Material material;
    private final List<String> lore;
    private final int amount;
    private final String texture;

    public Title(String titleID, int page) {
        this.titleID = titleID;
        title = getTitlesConfig().getString(getTitlePropertyName(page, titleID, "title"));
        permission = getTitlesConfig().getString(getTitlePropertyName(page, titleID, "permission"));
        nameInGUI = getTitlesConfig().getString(getTitleGUIPropertyName(page, titleID, "name"));
        material = Material.getMaterial(getTitlesConfig().getString(getTitleGUIPropertyName(page, titleID, "material")));
        lore = getTitlesConfig().getStringList(getTitleGUIPropertyName(page, titleID, "lore"));
        amount = getTitlesConfig().getInt(getTitleGUIPropertyName(page, titleID, "amount"));
        texture = getTitlesConfig().getString(getTitleGUIPropertyName(page, titleID, "texture"));
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

    public String getID() {
        return titleID;
    }

    public String getRawValue() {
        return title;
    }

    public String getFormattedValue() {
        return Functions.formatColors(getRawValue());
    }

    public Material getMaterial() {
        return material;
    }

    public String getGUIName() {
        return nameInGUI;
    }

    public String getPermission() {
        return permission;
    }

    public List<String> getLore() {
        return lore;
    }

    public int getAmount() {
        return amount;
    }

    public String getTexture() {
        return texture;
    }

}
