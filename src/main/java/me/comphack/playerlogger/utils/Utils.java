package me.comphack.playerlogger.utils;

import me.comphack.playerlogger.PlayerLogger;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.Collections;
import java.util.List;

public class Utils {


    public PlayerLogger getPluginConfig() {
        PlayerLogger playerLogger = JavaPlugin.getPlugin(PlayerLogger.class);
        return playerLogger;

    }

    public String chatcolor(String s) {
        if (s == null) return "";
        return ChatColor.translateAlternateColorCodes('&', s);
    }




}
