package me.comphack.playerlogger.commands.subcommands;

import me.comphack.playerlogger.commands.SubCommand;
import me.comphack.playerlogger.utils.Utils;
import org.bukkit.entity.Player;
import sun.nio.ch.Util;

import java.sql.SQLException;

public class ReloadCommand extends SubCommand {
    private Utils utils = new Utils();

    @Override
    public String getName() {
        return "reload";
    }

    @Override
    public String getDescription() {
        return "Reloads the plugin configuration";
    }

    @Override
    public String getSyntax() {
        return "&6/plogger reload";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(player.hasPermission("playerlogger.command.reload") || player.hasPermission("playerlogger.command.*")){
            utils.getPluginConfig().reloadConfig();
            utils.getPluginConfig().saveConfig();
            player.sendMessage(utils.chatcolor("&aConfiguration has bee successfully reloaded"));
        }
    }
}
