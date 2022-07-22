package me.comphack.playerlogger.commands;

import me.comphack.playerlogger.utils.GetLogs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

import java.sql.SQLException;

public class Plogs implements CommandExecutor {

    GetLogs logs = new GetLogs();
    Boolean isonline = true;
    Player target;

    public Plogs() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        target = Bukkit.getPlayerExact(args[0]);
        if (sender.hasPermission("playerlogs.command.plogs") || sender.hasPermission("playerlogs.command.*")) {
            if (args[0].length() == 0 || args[0].contains("-") || args[0].contains("'") || args[0].contains("\"")) {
                sender.sendMessage("Please specify a player or use a valid character.");
            } else {
                sender.sendMessage(ChatColor.YELLOW + "Showing logs for " + ChatColor.GREEN + args[0]);
                try {
                    String ipaddr = logs.getIP(args[0]);
                    sender.sendMessage(ChatColor.YELLOW + "IP Address: " + ChatColor.GREEN + ipaddr);
                } catch (SQLException e) {
                    e.printStackTrace();
                    sender.sendMessage(ChatColor.RED + "Error occured while executing this command.");
                }
                try {
                    String lastlogin = logs.lastLoginDate(args[0]);
                    sender.sendMessage(ChatColor.YELLOW + "Last Login Date: " + ChatColor.GREEN + lastlogin);
                } catch (SQLException e) {
                    sender.sendMessage(ChatColor.RED + "Error occured while executing this command.");
                    e.printStackTrace();
                }
            }
            if (target == null) {
                isonline = false;
                sender.sendMessage(ChatColor.YELLOW + "Is Online: " + ChatColor.RED + "No");
            } else {
                isonline = true;
                sender.sendMessage(ChatColor.YELLOW + "Is Online: " + ChatColor.GREEN + "Yes");
            }


        } else {
            sender.sendMessage(ChatColor.RED + "[PlayerLogger] No Permission");
        }

        return false;


    }
}
