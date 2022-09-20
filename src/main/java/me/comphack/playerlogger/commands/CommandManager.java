package me.comphack.playerlogger.commands;

import me.comphack.playerlogger.commands.subcommands.*;
import me.comphack.playerlogger.utils.Utils;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.*;

public class CommandManager implements CommandExecutor {

    private ArrayList<SubCommand> subcommands = new ArrayList<>();
    private Utils utils = new Utils();
    public CommandManager(){
        subcommands.add(new GetLogsCommand());
        subcommands.add(new GetChatLogsCommand());
        subcommands.add(new GetLogOutLocCommand());
        subcommands.add(new GetFirstJoinLocCommand());
        subcommands.add(new ReloadCommand());

    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if(sender instanceof Player) {
            Player p = (Player) sender;
            if (args.length > 0) {
                for (int i = 0; i < getSubcommands().size(); i++) {
                    if (args[0].equalsIgnoreCase(getSubcommands().get(i).getName())) {
                        try {
                            getSubcommands().get(i).perform(p, args);
                        } catch (SQLException e) {
                            e.printStackTrace();
                        }
                    }
                }
            } else if (args.length == 0) {
                p.sendMessage(utils.chatcolor("&6&lPlayerLogger &7developed by &6&lCOMPHACK"));
                p.sendMessage(utils.chatcolor("&6&lPlugin Version: &f" + utils.getPluginVersion()));
                p.sendMessage("");
                for (int i = 0; i < getSubcommands().size(); i++) {
                    p.sendMessage(utils.chatcolor(getSubcommands().get(i).getSyntax()) + " &f- " + utils.chatcolor(getSubcommands().get(i).getDescription()));
                }
                p.sendMessage("");
            }
        } else {
            sender.sendMessage(utils.chatcolor(utils.getPluginConfig().getConfig().getString("messages.playeronly-command")
                    .replace("{prefix}", utils.chatcolor(utils.getPluginConfig().getConfig().getString("messages.prefix"
                    )))));
        }

        return true;
    }

    public ArrayList<SubCommand> getSubcommands(){
        return subcommands;
    }

}
