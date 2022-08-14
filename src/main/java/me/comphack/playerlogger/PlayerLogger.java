package me.comphack.playerlogger;


import me.comphack.playerlogger.commands.CommandManager;
import me.comphack.playerlogger.database.DatabaseManager;


import me.comphack.playerlogger.events.JoinEvent;
import me.comphack.playerlogger.events.LeaveEvent;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;



public class PlayerLogger extends JavaPlugin implements Listener {
    private CommandManager cmd;
    private DatabaseManager dbmanager = new DatabaseManager();
    @Override
    public void onEnable() {
        cmd = new CommandManager(this);
        getConfig().options().copyDefaults(true);
        saveConfig();
        dbmanager.setupJDBC();
        dbmanager.PluginDatabase();
        initializeEvents();
        onEnableText();
    }

    public void onEnableText() {
        getLogger().info("--------------------------------------------------");
        getLogger().info("                                                  ");
        getLogger().info("          Enabled Player Logger                   ");
        getLogger().info("                 v1.0.1                     ");
        getLogger().info("                                                  ");
        getLogger().info("           Developed by COMPHACK                  ");
        getLogger().info("                                                  ");
        getLogger().info("--------------------------------------------------");


    }

    public void initializeEvents() {
        Bukkit.getServer().getPluginManager().registerEvents(new JoinEvent(), this);
        Bukkit.getServer().getPluginManager().registerEvents(new LeaveEvent(), this);
    }




    @Override
    public void onDisable() {
        getLogger().info("PlayerLogger v1.0 has successfully shut down");
        getLogger().info("Thank You For using my plugin.");

    }
}
