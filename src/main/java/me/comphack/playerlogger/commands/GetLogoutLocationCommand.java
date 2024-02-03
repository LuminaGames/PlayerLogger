package me.comphack.playerlogger.commands;

import me.comphack.playerlogger.utils.Utils;
import io.github.vedantmulay.neptuneapi.bukkit.commands.subcommand.SubCommand;
import me.comphack.playerlogger.PlayerLogger;
import me.comphack.playerlogger.database.DatabaseManager;
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
        DatabaseManager database = plugin.getDatabase();

        if(args[1].contains("-") || args[1].contains("'") || args[1].contains("\"")){
            sender.sendMessage(Utils.cc("&cPlease specify a player or a appropriate characters"));
        } else {
            String location = database.getLogoutLocation(args[1]);
            sender.sendMessage(Utils.cc("&6Logout Location for &f" + args[1]));
            sender.sendMessage(Utils.cc("&7(X, Y, Z, World) &f" + location));
        }
    }
}
