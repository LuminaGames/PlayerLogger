package me.comphack.playerlogger.utils;

import me.comphack.playerlogger.PlayerLogger;
import me.comphack.playerlogger.database.Initialize;
import org.bukkit.plugin.java.JavaPlugin;

public class Utils {

    public Initialize getDatabaseFile() {
        Initialize database = new Initialize();
        return database;
    }
    public PlayerLogger getPluginConfig() {
        PlayerLogger playerLogger = JavaPlugin.getPlugin(PlayerLogger.class);
        return playerLogger;
    }


}
