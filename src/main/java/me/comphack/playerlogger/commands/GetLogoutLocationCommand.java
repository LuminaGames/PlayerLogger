package me.comphack.playerlogger.commands;

import me.comphack.playerlogger.data.PlayerLog;
import me.comphack.playerlogger.database.Database;
import me.comphack.playerlogger.utils.Message;
import me.comphack.playerlogger.utils.Utils;
import io.github.vedantmulay.neptuneapi.bukkit.commands.subcommand.SubCommand;
import me.comphack.playerlogger.PlayerLogger;
import org.bukkit.Location;
import org.bukkit.command.CommandSender;

public class GetLogoutLocationCommand implements SubCommand {

    private PlayerLogger plugin;

    public GetLogoutLocationCommand(PlayerLogger plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean isPlayerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "playerlogger.command.lastlogoutlocation";
    }

    @Override
    public String getSyntax() {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Database database = plugin.getDatabase();
        PlayerLog log = database.getLogs(args[1]);
        Location loc = log.getLastJoinLocation();
        Message.GET_LAST_JOIN_LOCATION.send(sender, "{player}", log.getUsername(), "{location}",Utils.formatLocation(loc));

    }
}
