package pl.workonfire.buciktitles.listeners;

import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.workonfire.buciktitles.managers.ConfigManager;
import pl.workonfire.buciktitles.data.Functions;


public class EventHandler implements Listener {

    @org.bukkit.event.EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (ConfigManager.getConfig().getBoolean("options.clear-title-on-player-quit")) Functions.takeOff(event.getPlayer(), true);
    }

    @org.bukkit.event.EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        try {
            String selectedTitle = Functions.getCurrentUserTitle(event.getPlayer());
            if (!selectedTitle.isEmpty()
                    && ConfigManager.getConfig().getString("options.show-title").equalsIgnoreCase("above_head_chat"))
                event.setFormat(selectedTitle + "§r " + event.getFormat());
        } catch (Exception exception) {
            Functions.handleErrors(event.getPlayer(), exception);
        }
    }
}
