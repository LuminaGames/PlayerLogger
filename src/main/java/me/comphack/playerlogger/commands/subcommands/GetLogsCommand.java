package me.comphack.playerlogger.commands.subcommands;

import me.comphack.playerlogger.commands.SubCommand;
import me.comphack.playerlogger.database.DatabaseManager;
import me.comphack.playerlogger.utils.Utils;
import org.bukkit.command.CommandSender;


import java.sql.SQLException;
import java.util.List;

public class GetLogsCommand implements SubCommand {
    private Utils utils = new Utils();
    private DatabaseManager database = new DatabaseManager();

    @Override
    public void execute(CommandSender sender, String[] args) throws SQLException {
        if(args[1].contains("-") || args[1].contains("'") || args[1].contains("\"") || args.length < 1){
            sender.sendMessage(utils.chatcolor("&cPlease specify a player or a appropriate characters"));
        } else {
            sender.sendMessage(utils.chatcolor("&6--------------------------------------------------------"));
            sender.sendMessage(utils.chatcolor("&6Showing Stats for: &f" + args[1]));
            sender.sendMessage(utils.chatcolor("&6IP Address: &f" + database.getIP(args[1])));
            sender.sendMessage(utils.chatcolor("&6Last Join Date: &f" + database.getLastJoinDate(args[1])));
            sender.sendMessage(utils.chatcolor("&6Logout &7(X,Y,Z,World): &f" + database.getLogoutLocation(args[1])));
            sender.sendMessage(utils.chatcolor("&6First Join &7(X,Y,Z World); &f" + database.getFirstJoinInfo(args[1])));
            sender.sendMessage(utils.chatcolor("&6--------------------------------------------------------"));
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public String getLabel() {
        return "getlogs";
    }

    @Override
    public String getPermission() {
        return "playerlogger.command.getlogs";
    }

    @Override
    public boolean isPlayerOnly() {
        return false;
    }
}
