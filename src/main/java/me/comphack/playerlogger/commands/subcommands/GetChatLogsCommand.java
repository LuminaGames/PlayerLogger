package me.comphack.playerlogger.commands.subcommands;

import me.comphack.playerlogger.commands.SubCommand;
import me.comphack.playerlogger.utils.Utils;
import org.bukkit.entity.Player;


public class GetChatLogsCommand extends SubCommand {
    private Utils utils = new Utils();


    @Override
    public String getName() {
        return "getchatlogs";
    }

    @Override
    public String getDescription() {
        return "&6Get the last n number chat logs of a user from the database";
    }

    @Override
    public String getSyntax() {
        return "&6/plogger getchatlogs <Player> <Amount>&c**";
    }

    @Override
    public void perform(Player player, String[] args) {
        if(player.hasPermission("playerlogger.command.getchatlogs") || player.hasPermission("playerlogger.command.*")){
            player.sendMessage(utils.chatcolor("{prefix}&cThis command is in development.")
                    .replace("{prefix}", utils.chatcolor(utils.getPluginConfig().getConfig().getString("messages.prefix"
                    ))));
        } else {
            player.sendMessage(utils.chatcolor(utils.getPluginConfig().getConfig().getString("messages.no-permission")
                    .replace("{prefix}", utils.chatcolor(utils.getPluginConfig().getConfig().getString("messages.prefix"
                    )))));
        }
    }
}
