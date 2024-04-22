package me.comphack.playerlogger.commands;

import io.github.vedantmulay.neptuneapi.bukkit.commands.subcommand.SubCommand;
import me.comphack.playerlogger.PlayerLogger;
import me.comphack.playerlogger.data.PlayerLog;
import me.comphack.playerlogger.database.Database;
import me.comphack.playerlogger.utils.Message;
import me.comphack.playerlogger.utils.Utils;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class GetFirstJoinLocationCommand implements SubCommand {

    private PlayerLogger plugin;

    public GetFirstJoinLocationCommand(PlayerLogger plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean isPlayerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "playerlogger.command.firstjoinlocation";
    }

    @Override
    public String getSyntax() {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Database database = plugin.getDatabase();
        PlayerLog log = database.getLogs(args[1]);
        Location loc = log.getFirstJoinLocation();
        Message.GET_FIRST_JOIN_LOCATION.send(sender, "{player}", log.getUsername(), "{location}", Utils.formatLocation(log.getFirstJoinLocation()) == null ? Utils.formatLocation(log.getFirstJoinLocation()) : "Not Found");

    }
}
