package pl.workonfire.buciktitles.listeners;

import me.neznamy.tab.api.EnumProperty;
import me.neznamy.tab.api.TABAPI;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import pl.workonfire.buciktitles.managers.ConfigManager;
import pl.workonfire.buciktitles.data.Functions;


public class EventHandler implements Listener {

    @org.bukkit.event.EventHandler
    public void onPlayerQuit(PlayerQuitEvent event) {
        if (ConfigManager.config.getBoolean("options.clear-title-on-player-quit")) Functions.takeOff(event.getPlayer(), true);
    }

    @org.bukkit.event.EventHandler
    public void onPlayerChat(AsyncPlayerChatEvent event) {
        try {
            String selectedTitle = TABAPI.getOriginalValue(event.getPlayer().getUniqueId(), EnumProperty.ABOVENAME);
            if (!selectedTitle.isEmpty()
                    && ConfigManager.config.getString("options.show-title").equalsIgnoreCase("above_head_chat"))
                event.setFormat(selectedTitle + "§r " + event.getFormat());
        } catch (Exception e) {
            Functions.handleErrors(event.getPlayer(), e);
        }
    }
}
