package me.comphack.playerlogger.commands.subcommands;

import me.comphack.playerlogger.commands.SubCommand;
import me.comphack.playerlogger.database.DatabaseManager;
import me.comphack.playerlogger.utils.Utils;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class GetFirstJoinLocCommand extends SubCommand {
    private Utils utils = new Utils();
    private DatabaseManager database = new DatabaseManager();


    @Override
    public String getName() {
        return "firstjoinlocation";
    }

    @Override
    public String getDescription() {
        return "&6Get the first joining location of the player in the server";
    }

    @Override
    public String getSyntax() {
        return "&6/playerlogger firstjoinlocation <Player>";
    }

    @Override
    public void perform(Player player, String[] args) throws SQLException {
        if(player.hasPermission("playerlogger.command.firstjoinlocation") || player.hasPermission("playerlogger.command.*")) {
            if(args[1].contains("-") || args[1].contains("'") || args[1].contains("\"")){
                player.sendMessage(utils.chatcolor("&cPlease specify a player or a appropriate characters"));
            } else {
                String location = database.getFirstJoinInfo(args[1]);
                player.sendMessage(utils.chatcolor("&6First Location for " + args[1]));
                player.sendMessage(utils.chatcolor("&6X, Y ,Z , World: &f" + location));
            }
        } else {
            player.sendMessage(utils.chatcolor(utils.getPluginConfig().getConfig().getString("messages.no-permission")
                    .replace("{prefix}", utils.chatcolor(utils.getPluginConfig().getConfig().getString("messages.prefix"
                    )))));
        }
    }
}
