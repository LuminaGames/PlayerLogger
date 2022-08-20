package me.comphack.playerlogger.utils;

import me.comphack.playerlogger.PlayerLogger;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.plugin.java.JavaPlugin;

public class Utils {


    public PlayerLogger getPluginConfig() {
        PlayerLogger playerLogger = JavaPlugin.getPlugin(PlayerLogger.class);
        return playerLogger;

    }

    public String chatcolor(String s) {
        if (s == null) return "";
        return ChatColor.translateAlternateColorCodes('&', s);
    }

    public String DatabaseType() {
        String sql;
        if(getPluginConfig().getConfig().getBoolean("database.enabled")) {
            sql = "MySQL";
        } else {
            sql = "SQLite";
        }
        return sql;
    }

    public String getPluginVersion() {
        String ver = Bukkit.getServer().getPluginManager().getPlugin("PlayerLogger").getDescription().getVersion();
        return ver;
    }




}
