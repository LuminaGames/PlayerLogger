package me.comphack.playerlogger.commands;

import me.comphack.playerlogger.utils.Utils;
import io.github.vedantmulay.neptuneapi.bukkit.commands.subcommand.SubCommand;
import me.comphack.playerlogger.PlayerLogger;
import org.bukkit.command.CommandSender;

public class ReloadCommand implements SubCommand {

    private PlayerLogger plugin;

    public ReloadCommand(PlayerLogger plugin) {
        this.plugin = plugin;
    }

    @Override
    public boolean isPlayerOnly() {
        return false;
    }

    @Override
    public String getPermission() {
        return "playerlogger.command.reload";
    }

    @Override
    public String getSyntax() {
        return null;
    }

    @Override
    public void execute(CommandSender sender, String[] args) {
        plugin.reloadConfig();
        plugin.saveConfig();
        sender.sendMessage(Utils.cc("{prefix}&aConfiguration has been successfully reloaded"));
    }
}
