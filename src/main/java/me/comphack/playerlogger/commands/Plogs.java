package me.comphack.playerlogger.commands;

import me.comphack.playerlogger.PlayerLogger;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class Plogs implements CommandExecutor {

    PlayerLogger playerlogs = JavaPlugin.getPlugin(PlayerLogger.class);

    public Plogs() {
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        String SQL = "SELECT * FROM player_logs WHERE username = '" + args[0] + "'";
        if(sender.hasPermission("playerlogs.command.plogs") || sender.hasPermission("playerlogs.command.*")) {
            if (args[0].length() == 0 || args[0].contains("-") || args[0].contains("'") || args[0].contains("\"")) {
                sender.sendMessage("Please specify a player.");

            } else {
                try {
                    Statement statement = playerlogs.getConnection().createStatement();
                    ResultSet resultSet = statement.executeQuery(SQL);
                    String IPAddr = resultSet.getString("ip_address");
                    String LastLoginDate = resultSet.getString("last_join_date");
                    sender.sendMessage(ChatColor.GREEN + "Stats for " + ChatColor.YELLOW + args[0]);
                    sender.sendMessage(ChatColor.GOLD + "IP Address: " + ChatColor.YELLOW + IPAddr);
                    sender.sendMessage(ChatColor.GOLD + "Last Login Date: " + ChatColor.YELLOW + LastLoginDate);
                } catch (SQLException e) {
                    e.printStackTrace();
                    sender.sendMessage(ChatColor.RED + "An error occured while executing this command!");
                }

            }
        } else {
            sender.sendMessage(ChatColor.RED + "No Permission");
        }
        return false;
    }
}
