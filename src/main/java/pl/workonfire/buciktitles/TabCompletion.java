package pl.workonfire.buciktitles;

import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.TabCompleter;
import org.bukkit.util.StringUtil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

public class TabCompletion implements TabCompleter {
    private static final List<String> userCommands = Collections.singletonList("info");
    private static final List<String> adminCommands = Arrays.asList("reload", "info");

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender.hasPermission("bucik.titles.reload"))
            return (args.length == 1) ? StringUtil.copyPartialMatches(args[0], adminCommands, new ArrayList<>()) : null;
        else return (args.length == 1) ? StringUtil.copyPartialMatches(args[0], userCommands, new ArrayList<>()) : null;
    }
}
