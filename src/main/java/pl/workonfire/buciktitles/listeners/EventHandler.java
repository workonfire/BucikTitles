package pl.workonfire.buciktitles.listeners;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.workonfire.buciktitles.managers.ConfigManager;
import pl.workonfire.buciktitles.data.Util;

import static pl.workonfire.buciktitles.data.Title.getCurrentUserTitle;

@SuppressWarnings("ConstantConditions")
public class EventHandler implements Listener {

    @org.bukkit.event.EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (ConfigManager.getConfig().getBoolean("options.clear-title-on-player-quit")) Util.takeOff(event.getPlayer(), true);
    }

    @org.bukkit.event.EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        try {
            String selectedTitle = getCurrentUserTitle(event.getPlayer());
            if (!selectedTitle.isEmpty()
                    && ConfigManager.getConfig().getString("options.show-title").equalsIgnoreCase("above_head_chat")) {
                selectedTitle = PlaceholderAPI.setPlaceholders(event.getPlayer(), selectedTitle).replaceAll("%", "%%");
                event.setFormat(selectedTitle + "§r " + event.getFormat());
            }
        } catch (Exception exception) {
            Util.handleErrors(event.getPlayer(), exception);
        }
    }

    @org.bukkit.event.EventHandler
    public void onPlayerJoin(PlayerJoinEvent event) {
        if (!event.getPlayer().hasPlayedBefore()) {
            final String title = ConfigManager.getConfig().getString("options.default-title.title");
            if (getCurrentUserTitle(event.getPlayer()).isEmpty()
                    && ConfigManager.getConfig().getBoolean("options.default-title.enabled")
                    && title != null) Util.setRawTitle(event.getPlayer(), title);
        }
    }
}
