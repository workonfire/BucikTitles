package pl.workonfire.buciktitles;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import com.sun.org.apache.xerces.internal.impl.xs.models.XSCMBinOp;
import de.tr7zw.changeme.nbtapi.NBTCompound;
import de.tr7zw.changeme.nbtapi.NBTItem;
import de.tr7zw.changeme.nbtapi.NBTListCompound;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.List;

import static java.lang.String.format;

public class GUIManager {
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
     * Shows a title selection interface for a player.
     * @param plugin Plugin instance
     * @param player Player instance
     * @param page Page number
     */
    public static void showUserInterface(JavaPlugin plugin, Player player, int page) {
        /* Initializing the GUI */
        Gui gui = new Gui(plugin, 6, Main.formatColors(ConfigManager.config.getString("gui.title")));
        gui.setOnGlobalClick(event -> event.setCancelled(true));
        gui.setOnOutsideClick(event -> event.setCancelled(true));

        /* Creating border */
        String outline_pane_material_name = Main.formatColors(ConfigManager.config.getString("gui.outline-pane.item"));
        ItemStack outline_pane_item = new ItemStack(Material.getMaterial(outline_pane_material_name));
        ItemMeta outline_pane_item_meta = outline_pane_item.getItemMeta();
        if (ConfigManager.config.getString("gui.outline-pane.name") != null)
            outline_pane_item_meta.setDisplayName(Main.formatColors(ConfigManager.config.getString("gui.outline-pane.name")));
        if (!ConfigManager.config.getStringList("gui.outline-pane.lore").isEmpty()) {
            List<String> border_lore = new ArrayList<>();
            for (String lore_line : ConfigManager.config.getStringList("gui.outline-pane.lore"))
                border_lore.add(Main.formatColors(lore_line));
            outline_pane_item_meta.setLore(border_lore);
        }
        outline_pane_item.setItemMeta(outline_pane_item_meta);

        StaticPane left_pane;
        if (page != 1) {
            left_pane = new StaticPane(0, 0, 1, 5);
            OutlinePane back_button = new OutlinePane(0, 5, 1, 1);
            ItemStack back_button_item = new ItemStack(Material.ARROW);
            ItemMeta back_button_item_meta = back_button_item.getItemMeta();
            if (ConfigManager.config.getString("gui.previous-page-button.name") != null)
                back_button_item_meta.setDisplayName(Main.formatColors(ConfigManager.config.getString("gui.previous-page-button.name")));
            back_button_item.setItemMeta(back_button_item_meta);
            back_button.addItem(new GuiItem(back_button_item, event -> showUserInterface(plugin, player, page - 1)));
            gui.addPane(back_button);
        }
        else left_pane = new StaticPane(0, 0, 1, 6);
        left_pane.fillWith(outline_pane_item);
        gui.addPane(left_pane);

        StaticPane right_pane;
        int next_page = page + 1;
        if (ConfigManager.config.getConfigurationSection("titles.pages").getKeys(false).contains(String.valueOf(next_page))) {
            right_pane = new StaticPane(8, 0, 1, 5);
            OutlinePane next_button = new OutlinePane(8, 5, 1, 1);
            ItemStack next_button_item = new ItemStack(Material.ARROW);
            ItemMeta next_button_item_meta = next_button_item.getItemMeta();
            if (ConfigManager.config.getString("gui.next-page-button.name") != null)
                next_button_item_meta.setDisplayName(Main.formatColors(ConfigManager.config.getString("gui.next-page-button.name")));
            next_button_item.setItemMeta(next_button_item_meta);
            next_button.addItem(new GuiItem(next_button_item, event -> showUserInterface(plugin, player, page + 1)));
            gui.addPane(next_button);
        }
        else right_pane = new StaticPane(8, 0, 1, 6);
        right_pane.fillWith(outline_pane_item);
        gui.addPane(right_pane);

        StaticPane top_pane = new StaticPane(1, 0, 7, 1);
        top_pane.fillWith(outline_pane_item);
        gui.addPane(top_pane);

        StaticPane bottom_pane_left = new StaticPane(1, 5, 3, 1);
        bottom_pane_left.fillWith(outline_pane_item);
        gui.addPane(bottom_pane_left);

        StaticPane bottom_pane_right = new StaticPane(5, 5, 3, 1);
        bottom_pane_right.fillWith(outline_pane_item);
        gui.addPane(bottom_pane_right);

        OutlinePane take_off_button = new OutlinePane(4, 5, 1, 1);
        ItemStack take_off_button_item = new ItemStack(Material.BARRIER);
        ItemMeta take_off_button_meta = take_off_button_item.getItemMeta();
        if (ConfigManager.config.getString("gui.clear-titles-button.name") != null)
            take_off_button_meta.setDisplayName(Main.formatColors(ConfigManager.config.getString("gui.clear-titles-button.name")));
        take_off_button_item.setItemMeta(take_off_button_meta);
        take_off_button.addItem(new GuiItem(take_off_button_item, event -> Main.takeOff(player)));
        gui.addPane(take_off_button);

        /* Showing the titles */
        OutlinePane titles_pane = new OutlinePane(1, 1, 7, 4);
        for (String title : ConfigManager.config.getConfigurationSection(format("titles.pages.%s", page)).getKeys(false)) {
            if (player.hasPermission(ConfigManager.config.getString(format("titles.pages.%s.%s.permission", page, title)))) {
                String title_item_material = ConfigManager.config.getString(format("titles.pages.%s.%s.gui-item.material", page, title));
                ItemStack title_item = new ItemStack(Material.getMaterial(title_item_material));
                ItemMeta title_item_meta = title_item.getItemMeta();
                String title_item_name = Main.formatColors(ConfigManager.config.getString(format("titles.pages.%s.%s.gui-item.name", page, title)));
                if (title_item_name != null)
                    title_item_meta.setDisplayName(title_item_name);
                if (!ConfigManager.config.getStringList(format("titles.pages.%s.%s.gui-item.lore", page, title)).isEmpty()) {
                    List<String> title_item_lore = new ArrayList<>();
                    for (String lore_line : ConfigManager.config.getStringList(format("titles.pages.%s.%s.gui-item.lore", page, title)))
                        title_item_lore.add(Main.formatColors(lore_line));
                    title_item_meta.setLore(title_item_lore);
                }

                title_item.setItemMeta(title_item_meta);
                title_item.setAmount(ConfigManager.config.getInt(format("titles.pages.%s.%s.gui-item.amount", page, title)));
                if (ConfigManager.config.getString(format("titles.pages.%s.%s.gui-item.texture", page, title)) != null) {
                    String title_item_texture = ConfigManager.config.getString(format("titles.pages.%s.%s.gui-item.texture", page, title));
                    title_item = getTexturedHead(title_item, title_item_texture, title_item_name);
                }
                titles_pane.addItem(new GuiItem(title_item, event -> Main.setTitle(player, title, page)));
            }
        }

        /* Showing the GUI */
        gui.addPane(titles_pane);
        gui.show(player);
    }
}
