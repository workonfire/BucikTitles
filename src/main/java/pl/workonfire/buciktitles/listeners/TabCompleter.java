package pl.workonfire.buciktitles.listeners;

import me.neznamy.tab.api.TABAPI;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class TabCompleter implements org.bukkit.command.TabCompleter {
    private static final List<String> commands = new ArrayList<>(Arrays.asList("info", "clear"));

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender.hasPermission("bucik.titles.reload")) commands.add("reload");
        if (sender.hasPermission("bucik.titles.get")) commands.add("get");
        if (sender.hasPermission("bucik.titles.debug") && !TABAPI.isUnlimitedNameTagModeEnabled())
            commands.add("enableUnlimitedNameTagMode");
        return (args.length == 1) ? StringUtil.copyPartialMatches(args[0], commands, new ArrayList<>()) : null;
    }
}
