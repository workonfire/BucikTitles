package pl.workonfire.buciktitles.data;

import org.bukkit.Material;

import java.util.List;

import static pl.workonfire.buciktitles.data.Functions.getTitleGUIPropertyName;
import static pl.workonfire.buciktitles.managers.ConfigManager.getTitlesConfig;
import static pl.workonfire.buciktitles.data.Functions.getTitlePropertyName;

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
