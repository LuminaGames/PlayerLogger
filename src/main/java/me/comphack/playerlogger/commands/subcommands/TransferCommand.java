package me.comphack.playerlogger.commands.subcommands;

import me.comphack.playerlogger.commands.SubCommand;
import me.comphack.playerlogger.database.DatabaseManager;
import me.comphack.playerlogger.utils.Utils;
import org.bukkit.command.CommandSender;

import java.sql.SQLException;
import java.util.List;

public class TransferCommand implements SubCommand {
    private DatabaseManager database = new DatabaseManager();
    private Utils utils = new Utils();
    @Override
    public void execute(CommandSender sender, String[] args) throws SQLException {
        sender.sendMessage(utils.chatcolor("&cThis features is still in development."));
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public String getLabel() {
        return "transfer";
    }

    @Override
    public String getPermission() {
        return "playerlogger.command.transfer";
    }

    @Override
    public boolean isPlayerOnly() {
        return false;
    }
}
