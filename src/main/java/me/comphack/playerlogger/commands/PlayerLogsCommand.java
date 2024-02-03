package me.comphack.playerlogger.commands;

import me.comphack.playerlogger.utils.Utils;
import io.github.vedantmulay.neptuneapi.bukkit.commands.subcommand.SubCommand;
import me.comphack.playerlogger.PlayerLogger;
import me.comphack.playerlogger.database.DatabaseManager;
import me.comphack.playerlogger.utils.Message;
import org.bukkit.command.CommandSender;


public class PlayerLogsCommand implements SubCommand {

    private PlayerLogger plugin;

    public PlayerLogsCommand(PlayerLogger plugin) {
        this.plugin = plugin;
    }


    @Override
    public boolean isPlayerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "playerlogger.command.getlogs";
    }

    @Override
    public String getSyntax() {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        DatabaseManager db = plugin.getDatabase();
        if(args.length >= 1) {
            String target = args[1];
            String firstJoin = db.getFirstJoinInfo(target);
            String lastJoin = db.getLastJoinDate(target);
            String ipAddress = db.getIP(target);
            String logoutLocation = db.getLogoutLocation(target);
            Message.GET_LOGS_FORMAT.send(sender, "{player}", target, "{first_join}", firstJoin, "{last_join}", lastJoin, "{ip_address}", ipAddress, "{logout}", logoutLocation);
        } else {
            sender.sendMessage(Utils.cc("&cPlease provide a player"));
        }



    }
}
