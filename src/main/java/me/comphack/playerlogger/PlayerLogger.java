package me.comphack.playerlogger;

import me.comphack.playerlogger.commands.Plogs;
import me.comphack.playerlogger.events.JoinEvent;
import me.comphack.playerlogger.utils.Utils;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.SQLException;


public class PlayerLogger extends JavaPlugin implements Listener {


    private Utils database = new Utils();
    @Override
    public void onEnable() {
        if(getConfig().getBoolean("database.enabled")) {
            try {
                database.getDatabaseFile().connectMySQL();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        } else {
            try {
                database.getDatabaseFile().createSQLiteDatabase();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        getConfig().options().copyDefaults(true);
        saveConfig();
        initializeCommands();
        initializeEvents();
        onEnableText();
    }

    public void onEnableText() {
        getLogger().info("--------------------------------------------------");
        getLogger().info("                                                  ");
        getLogger().info("          Enabled Player Logger                   ");
        getLogger().info("                 v1.1-ALPHA                       ");
        getLogger().info("                                                  ");
        getLogger().info("           Developed by COMPHACK                  ");
        getLogger().info("                                                  ");
        getLogger().info("--------------------------------------------------");


    }

    public void initializeEvents() {
        this.getServer().getPluginManager().registerEvents(new JoinEvent(), this);
    }

    public void initializeCommands() {
        this.getCommand("playerlogs").setExecutor(new Plogs());
    }

    @Override
    public void onDisable() {
        getLogger().info("PlayerLogger v1.1-ALPHA has successfully shut down");
        getLogger().info("Thank You For using my plugin.");

    }
}
