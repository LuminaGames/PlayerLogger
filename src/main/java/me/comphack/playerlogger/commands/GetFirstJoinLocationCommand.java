package me.comphack.playerlogger.commands;

import io.github.vedantmulay.neptuneapi.bukkit.Utils;
import io.github.vedantmulay.neptuneapi.bukkit.commands.subcommand.SubCommand;
import me.comphack.playerlogger.PlayerLogger;
import me.comphack.playerlogger.database.DatabaseManager;
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
        DatabaseManager database = plugin.getDatabase();

        if(args[1].contains("-") || args[1].contains("'") || args[1].contains("\"")) {
            sender.sendMessage(Utils.cc("&cPlease specify a player or a appropriate characters"));
        } else {
            String location = database.getFirstJoinInfo(args[1]);
            sender.sendMessage(Utils.cc("&6First Location for " + args[1]));
            sender.sendMessage(Utils.cc("&6X, Y ,Z , World: &f" + location));
        }
    }
}
