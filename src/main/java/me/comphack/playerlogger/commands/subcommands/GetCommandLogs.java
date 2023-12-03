package me.comphack.playerlogger.commands.subcommands;

import me.comphack.playerlogger.commands.SubCommand;
import me.comphack.playerlogger.database.DatabaseManager;
import me.comphack.playerlogger.utils.PlayerChat;
import me.comphack.playerlogger.utils.Utils;
import org.bukkit.entity.Player;

import java.sql.SQLException;
import java.util.List;

public class GetCommandLogs extends SubCommand {

    Utils utils = new Utils();
    @Override
    public String getName() {
        return "getcommandlogs";
    }

    @Override
    public String getDescription() {
        return "Get the last n number command logs of a user from the database";
    }

    @Override
    public String getSyntax() {
        return "&6/plogger getcommandlogs <player> [newest|oldest] <limit>";
    }

    @Override
    public void perform(Player player, String[] args) throws SQLException {
        DatabaseManager db = new DatabaseManager();
        if(player.hasPermission("playerlogger.command.getcommandlogs") || player.hasPermission("playerlogger.command.*")){
            if(args.length <= 3) {
                player.sendMessage(utils.chatcolor("{prefix} &7Not enough arguments provided"));
                return;
            }
            int limit = Integer.parseInt(args[3]);
            String username = args[1];
            String sort = args[2];



            player.sendMessage(utils.chatcolor("{prefix} &fShowing command history for &6" + username));
            List<PlayerChat> chatLogs;
            if(sort.equalsIgnoreCase("newest")) {
                chatLogs = db.getCommandLogs(username, limit, "NEW");
                player.sendMessage(utils.chatcolor("{prefix} &fSorting &6newest to oldest"));
            } else {
                chatLogs = db.getCommandLogs(username, limit, "OLD");
                player.sendMessage(utils.chatcolor("{prefix} &fSorting &6oldest to newest"));
            }
            if(chatLogs.isEmpty()) {
                player.sendMessage(utils.chatcolor("{prefix} &cNo player with that name could be found."));
                return;
            }
            player.sendMessage("");
            for(PlayerChat chatLog : chatLogs) {
                String username_ = chatLog.getUsername();
                String dateTime = chatLog.getDateTime();
                String message = chatLog.getMessage();
                player.sendMessage(utils.chatcolor("{prefix} &6" + username_ + "&f: &e" + message + " &7[&f"+ dateTime + "&7]"));
            }
        } else {
            player.sendMessage(utils.chatcolor(utils.getPluginConfig().getConfig().getString("messages.no-permission")));
        }
    }
}
