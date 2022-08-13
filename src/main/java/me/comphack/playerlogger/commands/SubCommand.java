package me.comphack.playerlogger.commands;

import org.bukkit.command.CommandSender;

import java.sql.SQLException;
import java.util.List;

public interface SubCommand {
    void execute(CommandSender sender, String[] args) throws SQLException;
    List<String> tabComplete(CommandSender sender, String[] args);
    String getLabel();
    String getPermission();
    boolean isPlayerOnly();
}
