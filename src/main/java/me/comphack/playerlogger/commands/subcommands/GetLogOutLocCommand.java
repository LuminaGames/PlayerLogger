package me.comphack.playerlogger.commands.subcommands;

import me.comphack.playerlogger.commands.SubCommand;
import me.comphack.playerlogger.database.DatabaseManager;
import me.comphack.playerlogger.utils.Utils;
import org.bukkit.command.CommandSender;

import java.sql.SQLException;
import java.util.List;

public class GetLogOutLocCommand implements SubCommand {
    private DatabaseManager database = new DatabaseManager();
    private Utils utils = new Utils();

    @Override
    public void execute(CommandSender sender, String[] args) throws SQLException {
        if(args[1].contains("-") || args[1].contains("'") || args[1].contains("\"") || args.length < 1){
            sender.sendMessage(utils.chatcolor("&cPlease specify a player or a appropriate characters"));
        } else {
            String location = database.getLogoutLocation(args[1]);
            sender.sendMessage(utils.chatcolor("&6Logout Location for &f" + args[1]));
            sender.sendMessage(utils.chatcolor("&7(X, Y, Z, World) &f" + location));
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public String getLabel() {
        return "lastlogoutlocation";
    }

    @Override
    public String getPermission() {
        return "playerlogger.command.lastlogoutlocation";
    }

    @Override
    public boolean isPlayerOnly() {
        return false;
    }
}
