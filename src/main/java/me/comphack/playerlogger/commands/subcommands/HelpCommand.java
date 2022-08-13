package me.comphack.playerlogger.commands.subcommands;

import me.comphack.playerlogger.commands.SubCommand;
import me.comphack.playerlogger.utils.Utils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;

import java.util.List;

public class HelpCommand implements SubCommand {

    private Utils utils = new Utils();
    @Override
    public void execute(CommandSender sender, String[] args) {
        for (String msgs : utils.getPluginConfig().getConfig().getStringList("messages.help-1")) {
            sender.sendMessage(utils.chatcolor(msgs).replace("{version}", "1.0"));
        }
    }

    @Override
    public List<String> tabComplete(CommandSender sender, String[] args) {
        return null;
    }

    @Override
    public String getLabel() {
        return "help";
    }

    @Override
    public String getPermission() {
        return "playerlogger.command.help";
    }

    @Override
    public boolean isPlayerOnly() {
        return false;
    }

}
