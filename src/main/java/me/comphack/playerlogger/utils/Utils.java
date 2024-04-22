package me.comphack.playerlogger.utils;

import me.comphack.playerlogger.PlayerLogger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.plugin.java.JavaPlugin;

public class Utils {


    public static PlayerLogger getPluginConfig() {
        PlayerLogger playerLogger = JavaPlugin.getPlugin(PlayerLogger.class);
        return playerLogger;

    }

    public static String cc(String s) {
        return ChatColor.translateAlternateColorCodes('&', s)
                .replace("{prefix}", ChatColor.translateAlternateColorCodes('&', getPluginConfig().getConfig().getString("messages.prefix")));
    }

    public String getPluginVersion() {
        String ver = Bukkit.getServer().getPluginManager().getPlugin("PlayerLogger").getDescription().getVersion();
        return ver;
    }

    public static String formatLocation(Location location) {
        if(location == null) {
            return null;
        }
        return location.getWorld().getName() + ", " + location.getBlockX() + ", " + location.getBlockY() + ", " + location.getBlockZ();
    }


}
