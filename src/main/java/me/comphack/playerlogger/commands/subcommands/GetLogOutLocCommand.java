package me.comphack.playerlogger.commands.subcommands;

import me.comphack.playerlogger.commands.SubCommand;
import me.comphack.playerlogger.database.DatabaseManager;
import me.comphack.playerlogger.utils.Utils;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class GetLogOutLocCommand extends SubCommand {

    private Utils utils = new Utils();
    private DatabaseManager database = new DatabaseManager();

    @Override
    public String getName() {
        return "lastlogoutlocation";
    }

    @Override
    public String getDescription() {
        return "&6Get last log out location of the player on the server";
    }

    @Override
    public String getSyntax() {
        return "&6/playerlogger lastlogoutlocation <Player>";
    }

    @Override
    public void perform(Player player, String[] args) throws SQLException {
        if(player.hasPermission("playerlogger.command.lastlogoutlocation" ) || player.hasPermission("playerlogger.command.*")) {
            if(args[1].contains("-") || args[1].contains("'") || args[1].contains("\"")){
                player.sendMessage(utils.chatcolor("&cPlease specify a player or a appropriate characters"));
            } else {
                String location = database.getLogoutLocation(args[1]);
                player.sendMessage(utils.chatcolor("&6Logout Location for &f" + args[1]));
                player.sendMessage(utils.chatcolor("&7(X, Y, Z, World) &f" + location));
            }
        }
    }
}
