package me.comphack.playerlogger.commands;

import me.comphack.playerlogger.data.PlayerLog;
import me.comphack.playerlogger.database.Database;
import me.comphack.playerlogger.menu.PlayerLogsMenu;
import me.comphack.playerlogger.utils.Utils;
import io.github.vedantmulay.neptuneapi.bukkit.commands.subcommand.SubCommand;
import me.comphack.playerlogger.PlayerLogger;
import me.comphack.playerlogger.utils.Message;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;


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
        Database db = plugin.getDatabase();
        Player player = (Player) sender;
        if(args.length >= 1) {
            String target = args[1];
            PlayerLog log = plugin.getDatabase().getLogs(target);

            if(log == null) {
                player.sendMessage(Utils.cc("&cThe player was not found in the database!"));
                return;
            }

            new PlayerLogsMenu(target, log).open(player);
        } else {
            sender.sendMessage(Utils.cc("&cPlease provide a player"));
        }



    }
}
