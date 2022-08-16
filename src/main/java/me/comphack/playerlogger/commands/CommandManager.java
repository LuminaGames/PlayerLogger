package me.comphack.playerlogger.commands;

import me.comphack.playerlogger.PlayerLogger;
import me.comphack.playerlogger.commands.subcommands.*;
import me.comphack.playerlogger.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.command.PluginCommand;
import org.bukkit.command.TabExecutor;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.*;
import java.util.stream.Collectors;

public class CommandManager implements TabExecutor {
    private Utils utils = new Utils();
    private final Set<SubCommand> subCommands = new HashSet<>();

    public CommandManager(PlayerLogger plugin) {
        subCommands.add(new GetLogsCommand());
        subCommands.add(new HelpCommand());
        subCommands.add(new TransferCommand());
        subCommands.add(new GetFirstJoinLocCommand());
        subCommands.add(new GetLogOutLocCommand());
        subCommands.add(new GetChatLogsCommand());
        PluginCommand command = plugin.getCommand("playerlogger");
        command.setExecutor(this);
        command.setTabCompleter(this);

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (args.length > 0) {
            SubCommand cmd = subCommands.stream().filter(sub -> sub.getLabel().equalsIgnoreCase(args[0])).findAny().orElse(null);
            if (cmd == null) {
                sender.sendMessage(utils.chatcolor(utils.getPluginConfig().getConfig().getString("messages.unknown-command").replace("{prefix}", utils.chatcolor(utils.getPluginConfig().getConfig().getString("messages.prefix")))));
                return true;
            }
            if (cmd.isPlayerOnly() && !(sender instanceof Player)) {
                sender.sendMessage(utils.chatcolor(utils.getPluginConfig().getConfig().getString("messages.playeronly-command").replace("{prefix}", utils.chatcolor(utils.getPluginConfig().getConfig().getString("messages.prefix")))));
                return true;
            }
            if (!sender.hasPermission(cmd.getPermission())) {
                sender.sendMessage(utils.chatcolor(utils.getPluginConfig().getConfig().getString("messages.no-permission").replace("{prefix}", utils.chatcolor(utils.getPluginConfig().getConfig().getString("messages.prefix")))));
                return true;
            }
            try {
                cmd.execute(sender, args);
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            for (String msgs : utils.getPluginConfig().getConfig().getStringList("messages.help-1")) {
                sender.sendMessage(utils.chatcolor(msgs).replace("{version}", "1.0"));
            }        }
        return true;
    }

    @Override
    public List<String> onTabComplete(CommandSender sender, Command command, String alias, String[] args) {
        if (args.length > 1) {
            SubCommand cmd = subCommands.stream().filter(sub -> sub.getLabel().equalsIgnoreCase(args[0])).findAny().orElse(null);
            if (cmd == null) {
                return Collections.emptyList();
            }
            if (sender.hasPermission(cmd.getPermission())) {
                return cmd.tabComplete(sender, args) != null ? cmd.tabComplete(sender, args) : Collections.emptyList();
            }
        } else if (args.length == 1) {
            if (args[0].isEmpty()) {
                return subCommands.stream().filter(cmd -> sender.hasPermission(cmd.getPermission())).map(SubCommand::getLabel).collect(Collectors.toList());
            } else {
                return subCommands.stream().filter(sub -> sender.hasPermission(sub.getPermission())).map(SubCommand::getLabel).filter(label -> label.startsWith(args[0].toLowerCase())).collect(Collectors.toList());
            }
        }
        return Collections.emptyList();
    }
}
