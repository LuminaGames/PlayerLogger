package me.comphack.playerlogger.commands;

import io.github.vedantmulay.neptuneapi.bukkit.commands.subcommand.SubCommand;
import me.comphack.playerlogger.utils.Message;
import org.bukkit.command.CommandSender;

public class HelpCommand implements SubCommand {
    @Override
    public boolean isPlayerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "playerlogger.command.help";
    }

    @Override
    public String getSyntax() {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        Message.HELP_MESSAGE.send(sender);
    }
}
