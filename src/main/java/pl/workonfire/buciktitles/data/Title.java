package pl.workonfire.buciktitles.data;

import org.bukkit.Material;
import pl.workonfire.buciktitles.managers.ConfigManager;

import java.util.List;

import static java.lang.String.format;

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
        title = ConfigManager.getTitlesConfig().getString(format("titles.pages.%d.%s.title", page, titleID));
        permission = ConfigManager.getTitlesConfig().getString(format("titles.pages.%d.%s.permission", page, titleID));
        nameInGUI = ConfigManager.getTitlesConfig().getString(format("titles.pages.%d.%s.gui-item.name", page, titleID));
        material = Material.getMaterial(ConfigManager.getTitlesConfig().getString(format("titles.pages.%d.%s.gui-item.material", page, titleID)));
        lore = ConfigManager.getTitlesConfig().getStringList(format("titles.pages.%d.%s.gui-item.lore", page, titleID));
        amount = ConfigManager.getTitlesConfig().getInt(format("titles.pages.%d.%s.gui-item.amount", page, titleID));
        texture = ConfigManager.getTitlesConfig().getString(format("titles.pages.%d.%s.gui-item.texture", page, titleID));
    }

    // Getters \\
    public String getID() {
        return titleID;
    }

    public String getValue() {
        return title;
    }

    public String getFormattedValue() {
        return Functions.formatColors(getValue());
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
