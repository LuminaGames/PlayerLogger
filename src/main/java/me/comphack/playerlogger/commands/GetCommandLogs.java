package me.comphack.playerlogger.commands;

import io.github.vedantmulay.neptuneapi.bukkit.commands.subcommand.SubCommand;
import me.comphack.playerlogger.PlayerLogger;
import me.comphack.playerlogger.data.PlayerChat;
import me.comphack.playerlogger.database.Database;
import me.comphack.playerlogger.utils.Message;
import me.comphack.playerlogger.utils.Utils;
import org.bukkit.command.CommandSender;

import java.util.List;

public class GetCommandLogs implements SubCommand {

    private PlayerLogger plugin;

    public GetCommandLogs(PlayerLogger plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean isPlayerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "playerlogger.command.getcommandlogs";
    }

    @Override
    public String getSyntax() {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Database db = plugin.getDatabase();
        if(args.length <= 3) {
            sender.sendMessage(Utils.cc("{prefix} &7Not enough arguments provided"));
            return;
        }
        int limit = Integer.parseInt(args[3]);
        String username = args[1];
        String sort = args[2];

        List<PlayerChat> chatLogs;
        if(sort.equalsIgnoreCase("newest")) {
            chatLogs = db.getCommandLogs(username, limit, "NEW");
            sender.sendMessage(Utils.cc("{prefix} &fSorting &6newest to oldest"));
        } else {
            chatLogs = db.getCommandLogs(username, limit, "OLD");
            sender.sendMessage(Utils.cc("{prefix} &fSorting &6oldest to newest"));
        }
        if(chatLogs.isEmpty()) {
            Message.PLAYER_NOT_FOUND.send(sender);
            return;
        }
        sender.sendMessage("");
        for(PlayerChat chatLog : chatLogs) {
            String username_ = chatLog.getUsername();
            String dateTime = chatLog.getDateTime();
            String message = chatLog.getMessage();
            sender.sendMessage(Utils.cc("{prefix} &6" + "&f: &e" + message + " &7[&f"+ dateTime + "&7]"));
        }
    }
}
