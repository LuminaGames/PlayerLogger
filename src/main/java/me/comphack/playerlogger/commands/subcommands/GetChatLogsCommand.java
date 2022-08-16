package me.comphack.playerlogger.commands.subcommands;

import me.comphack.playerlogger.commands.SubCommand;
import me.comphack.playerlogger.database.DatabaseManager;
import org.bukkit.command.CommandSender;

import java.sql.SQLException;
import java.util.List;

public class GetChatLogsCommand implements SubCommand {
    private DatabaseManager database =  new DatabaseManager();


    @Override
    public void execute(CommandSender sender, String[] args) throws SQLException {
        sender.sendMessage(database.getChatLogs(args[1], Integer.parseInt(args[2])));
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public String getLabel() {
        return "getchatlogs";
    }

    @Override
    public String getPermission() {
        return "playerlogger.command.getchatlogs";
    }

    @Override
    public boolean isPlayerOnly() {
        return false;
    }
}
