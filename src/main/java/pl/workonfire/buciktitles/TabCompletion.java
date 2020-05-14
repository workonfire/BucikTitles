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
    private static final List<String> user_commands = Collections.singletonList("info");
    private static final List<String> admin_commands = Arrays.asList("reload", "info");
    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (sender.hasPermission("bucik.titles.reload"))
            return (args.length == 1) ? StringUtil.copyPartialMatches(args[0], admin_commands, new ArrayList<>()) : null;
        else return (args.length == 1) ? StringUtil.copyPartialMatches(args[0], user_commands, new ArrayList<>()) : null;
    }
}
