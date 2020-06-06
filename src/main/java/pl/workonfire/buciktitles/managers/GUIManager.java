package pl.workonfire.buciktitles.managers;

import com.github.stefvanschie.inventoryframework.Gui;
import com.github.stefvanschie.inventoryframework.GuiItem;
import com.github.stefvanschie.inventoryframework.pane.OutlinePane;
import com.github.stefvanschie.inventoryframework.pane.StaticPane;
import org.bukkit.Material;
import org.bukkit.Sound;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.java.JavaPlugin;
import pl.workonfire.buciktitles.data.Functions;
import pl.workonfire.buciktitles.data.Title;

import java.util.ArrayList;
import java.util.List;

public class GUIManager {

    /**
     * Shows a title selection interface for a player.
     * @since 1.0
     * @param plugin Plugin instance
     * @param player Player instance
     * @param page Page number
     */
    public static void showGUI(JavaPlugin plugin, Player player, int page) {
        try {
            /* Playing the click sound */
            if (ConfigManager.getConfig().getBoolean("options.play-sounds"))
                player.playSound(player.getLocation(), Sound.UI_BUTTON_CLICK, 1.0F, 1.0F);

            /* Initializing the GUI */
            Gui gui = new Gui(plugin, 6, Functions.formatColors(ConfigManager.getConfig().getString("gui.title")));
            gui.setOnGlobalClick(event -> event.setCancelled(true));
            gui.setOnOutsideClick(event -> event.setCancelled(true));

            /* Creating border */
            String outlinePaneMaterialName = Functions.formatColors(ConfigManager.getConfig().getString("gui.outline-pane.item"));
            ItemStack outlinePaneItem = new ItemStack(Material.getMaterial(outlinePaneMaterialName));
            ItemMeta outlinePaneItemMeta = outlinePaneItem.getItemMeta();
            if (ConfigManager.getConfig().getString("gui.outline-pane.name") != null)
                outlinePaneItemMeta.setDisplayName(Functions.formatColors(ConfigManager.getConfig().getString("gui.outline-pane.name")));
            if (!ConfigManager.getConfig().getStringList("gui.outline-pane.lore").isEmpty()) {
                List<String> borderLore = new ArrayList<>();
                for (String loreLine : ConfigManager.getConfig().getStringList("gui.outline-pane.lore"))
                    borderLore.add(Functions.formatColors(loreLine));
                outlinePaneItemMeta.setLore(borderLore);
            }
            outlinePaneItem.setItemMeta(outlinePaneItemMeta);

            StaticPane leftPane;
            if (page != 1) {
                leftPane = new StaticPane(0, 0, 1, 5);
                OutlinePane backButton = new OutlinePane(0, 5, 1, 1);
                ItemStack backButtonItem = new ItemStack(Material.ARROW);
                ItemMeta backButtonItemItemMeta = backButtonItem.getItemMeta();
                if (ConfigManager.getConfig().getString("gui.previous-page-button.name") != null)
                    backButtonItemItemMeta.setDisplayName(Functions.formatColors(ConfigManager.getConfig().getString("gui.previous-page-button.name")));
                backButtonItem.setItemMeta(backButtonItemItemMeta);
                backButton.addItem(new GuiItem(backButtonItem, event -> showGUI(plugin, player, page - 1)));
                gui.addPane(backButton);
            }
            else leftPane = new StaticPane(0, 0, 1, 6);
            leftPane.fillWith(outlinePaneItem);
            gui.addPane(leftPane);

            StaticPane rightPane;
            int nextPage = page + 1;
            if (ConfigManager.getTitlesConfig().getConfigurationSection("titles.pages").getKeys(false).contains(String.valueOf(nextPage))) {
                rightPane = new StaticPane(8, 0, 1, 5);
                OutlinePane nextButton = new OutlinePane(8, 5, 1, 1);
                ItemStack nextButtonItem = new ItemStack(Material.ARROW);
                ItemMeta nextButtonItemMeta = nextButtonItem.getItemMeta();
                if (ConfigManager.getConfig().getString("gui.next-page-button.name") != null)
                    nextButtonItemMeta.setDisplayName(Functions.formatColors(ConfigManager.getConfig().getString("gui.next-page-button.name")));
                nextButtonItem.setItemMeta(nextButtonItemMeta);
                nextButton.addItem(new GuiItem(nextButtonItem, event -> showGUI(plugin, player, nextPage)));
                gui.addPane(nextButton);
            }
            else rightPane = new StaticPane(8, 0, 1, 6);
            rightPane.fillWith(outlinePaneItem);
            gui.addPane(rightPane);

            StaticPane topPane = new StaticPane(1, 0, 7, 1);
            topPane.fillWith(outlinePaneItem);
            gui.addPane(topPane);

            StaticPane bottomPaneLeft = new StaticPane(1, 5, 3, 1);
            bottomPaneLeft.fillWith(outlinePaneItem);
            gui.addPane(bottomPaneLeft);

            StaticPane bottomPaneRight = new StaticPane(5, 5, 3, 1);
            bottomPaneRight.fillWith(outlinePaneItem);
            gui.addPane(bottomPaneRight);

            OutlinePane takeOffButton = new OutlinePane(4, 5, 1, 1);
            ItemStack takeOffButtonItem = new ItemStack(Material.BARRIER);
            ItemMeta takeOffButtonMeta = takeOffButtonItem.getItemMeta();
            if (ConfigManager.getConfig().getString("gui.clear-titles-button.name") != null)
                takeOffButtonMeta.setDisplayName(Functions.formatColors(ConfigManager.getConfig().getString("gui.clear-titles-button.name")));
            takeOffButtonItem.setItemMeta(takeOffButtonMeta);
            takeOffButton.addItem(new GuiItem(takeOffButtonItem, event -> Functions.takeOff(player, false)));
            gui.addPane(takeOffButton);

            /* Showing the titles */
            OutlinePane titlesPane = new OutlinePane(1, 1, 7, 4);
            for (String titleID : Functions.getTitlesFromPage(page)) {
                Title title = new Title(titleID, page);
                if (player.hasPermission(title.getPermission())) {
                    ItemStack titleItem = new ItemStack(title.getMaterial());
                    ItemMeta titleItemMeta = titleItem.getItemMeta();
                    if (title.getGUIName() != null) titleItemMeta.setDisplayName(Functions.formatColors(title.getGUIName()));
                    else titleItemMeta.setDisplayName(title.getFormattedValue());
                    List<String> titleItemLore = new ArrayList<>();
                    if (!title.getLore().isEmpty()) {
                        for (String loreLine : title.getLore())
                            titleItemLore.add(Functions.formatColors(loreLine));
                    }
                    if (title.getRawValue().equals(Functions.getCurrentUserTitle(player))) {
                        titleItemLore.add(ConfigManager.getLanguageVariable("currently-selected"));
                        titleItemMeta.setLore(titleItemLore);
                    }
                    titleItemMeta.setLore(titleItemLore);
                    titleItem.setItemMeta(titleItemMeta);
                    if (title.getAmount() != 0) titleItem.setAmount(title.getAmount());
                    if (title.getTexture() != null) Functions.setHeadTexture(titleItem, title.getTexture());
                    titlesPane.addItem(new GuiItem(titleItem, event -> Functions.setTitle(player, title.getID(), page)));
                }
            }

            /* Showing the GUI */
            gui.addPane(titlesPane);
            gui.show(player);

        } catch (Exception exception) {
            Functions.handleErrors(player, exception);
        }
    }
}
