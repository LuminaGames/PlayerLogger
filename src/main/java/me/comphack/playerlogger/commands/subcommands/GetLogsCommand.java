package me.comphack.playerlogger.commands.subcommands;

import me.comphack.playerlogger.commands.SubCommand;
import me.comphack.playerlogger.database.DatabaseManager;
import me.comphack.playerlogger.utils.Utils;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class GetLogsCommand extends SubCommand {

    private DatabaseManager database = new DatabaseManager();
    private Utils utils = new Utils();


    @Override
    public String getName() {
        return "getlogs";
    }

    @Override
    public String getDescription() {
        return "&6Gets logs from the database for a user";
    }

    @Override
    public String getSyntax() {
        return "&6/playerlogger getlogs <Player>";
    }

    @Override
    public void perform(Player player, String[] args) throws SQLException {
        if(player.hasPermission("playerlogger.command.getlogs") || player.hasPermission("playerlogger.command.*")){
            if (args.length > 1){
                if (args[1].contains("-") || args[1].contains("'") || args[1].contains("\"")) {
                    player.sendMessage(utils.chatcolor("&cPlease specify a player or a appropriate characters"));
                } else {
                    player.sendMessage(utils.chatcolor("&6-----------------------------------------------------"));
                    player.sendMessage(utils.chatcolor("&6Showing Stats for: &f" + args[1]));
                    player.sendMessage(utils.chatcolor("&6IP Address: &f" + database.getIP(args[1])));
                    player.sendMessage(utils.chatcolor("&6Last Join Date: &f" + database.getLastJoinDate(args[1])));
                    player.sendMessage(utils.chatcolor("&6Logout &7(X,Y,Z,World): &f" + database.getLogoutLocation(args[1])));
                    player.sendMessage(utils.chatcolor("&6First Join &7(X,Y,Z World); &f" + database.getFirstJoinInfo(args[1])));
                    player.sendMessage(utils.chatcolor("&6-----------------------------------------------------"));
                }
            } else {
                player.sendMessage(utils.chatcolor(utils.getPluginConfig().getConfig().getString("messages.no-permission")
                        .replace("{prefix}", utils.chatcolor(utils.getPluginConfig().getConfig().getString("messages.prefix"
                        )))));
            }

        }else if(args.length == 1){
            player.sendMessage(getSyntax());
        }


    }
}
