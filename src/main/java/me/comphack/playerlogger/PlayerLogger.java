package me.comphack.playerlogger;

import io.github.vedantmulay.neptuneapi.bukkit.commands.subcommand.SubCommandManager;
import me.comphack.playerlogger.commands.*;
import me.comphack.playerlogger.database.Database;

import me.comphack.playerlogger.database.MySQL;
import me.comphack.playerlogger.database.SQLite;
import me.comphack.playerlogger.events.ChatEvent;
import me.comphack.playerlogger.events.CommandSendEvent;
import me.comphack.playerlogger.events.JoinEvent;
import me.comphack.playerlogger.events.LeaveEvent;
import me.comphack.playerlogger.utils.Message;
import me.comphack.playerlogger.utils.Metrics;
import me.comphack.playerlogger.utils.UpdateChecker;
import org.bukkit.Bukkit;
import org.bukkit.event.Listener;
import org.bukkit.plugin.java.JavaPlugin;



public class PlayerLogger extends JavaPlugin implements Listener {
    private Database database;
    static boolean updateAvailable = false;
    private SubCommandManager commandManager;

    @Override
    public void onEnable() {
        // Load Plugin Configs
        commandManager = new SubCommandManager();
        getConfig().options().copyDefaults(true);
        saveConfig();
        getLogger().info("Loaded Configurations");
        Message.setConfiguration(getConfig());
        // Setup Database

        if(getConfig().getBoolean("database.enabled")) {
            database = new  MySQL(this);
        } else {
            database = new SQLite(this);
        }

        getLogger().info("Loaded Database!");
        //Register Events and Commands
        initializeEvents();
        getCommand("playerlogger").setExecutor(commandManager);
        initializeCommands();
        getLogger().info("Loaded Events & Commands");
        onEnableText();
        int pluginId = 16130;
        // Hook Into bStats
        if(getConfig().getBoolean("general.use-bstats")) {
            Metrics metrics = new Metrics(this, pluginId);
        }
        getLogger().info("Checking for Updates...");
        // Check for Updates
        if(getConfig().getBoolean("general.check-updates")) {
            UpdateChecker.init(this, 103033).requestUpdateCheck().whenComplete((result, exception) -> {
                if (result.requiresUpdate()) {
                    updateAvailable = true;
                    this.getLogger().info(String.format("An update is available! PlayerLogger %s may be downloaded on SpigotMC", result.getNewestVersion()));
                    return;
                }
                UpdateChecker.UpdateReason reason = result.getReason();
                if (reason == UpdateChecker.UpdateReason.UP_TO_DATE) {
                    this.getLogger().info(String.format("Your version of PlayerLogger (%s) is up to date!", result.getNewestVersion()));
                } else if (reason == UpdateChecker.UpdateReason.UNRELEASED_VERSION) {
                    this.getLogger().info(String.format("Your version of PlayerLogger (%s) is more recent than the one publicly available. Are you on a development build?", result.getNewestVersion()));
                } else {
                    this.getLogger().warning("Could not check for a new version of PlayerLogger. Reason: " + reason);
                }
            });
        } else {
            Bukkit.getLogger().info("Update Checking is disabled. You can enable it back through the config file.");
        }

        SubCommandManager.setPlayerOnlyCommandMessage(Message.PLAYER_ONLY_COMMAND.asString());
        SubCommandManager.setNoPermissionMessage(Message.NO_PERMISSION.asString());
        SubCommandManager.setUnknownCommandMessage(Message.UNKNOWN_COMMAND.asString());
    }

    public void onEnableText() {
        getLogger().info("--------------------------------------------------");
        getLogger().info("                                                  ");
        getLogger().info("          Enabled Player Logger                   ");
        getLogger().info("                 " + getDescription().getVersion());
        getLogger().info("                                                  ");
        getLogger().info("           Developed by COMPHACK                  ");
        getLogger().info("         Running on " + getServer().getVersion());
        getLogger().info("                                                  ");
        getLogger().info("--------------------------------------------------");


    }

    public void initializeCommands() {
        commandManager.registerCommand("getlogs", new PlayerLogsCommand(this));
        commandManager.registerCommand("getchatlogs", new GetChatLogsCommand(this));
        commandManager.registerCommand("getcommandlogs", new GetCommandLogs(this));
        commandManager.registerCommand("firstjoinlocation", new GetFirstJoinLocationCommand(this));
        commandManager.registerCommand("lastlogoutlocation", new GetLogoutLocationCommand(this));
        commandManager.registerCommand("reload", new ReloadCommand(this));
        commandManager.registerCommand("help", new HelpCommand());
    }
    public void initializeEvents() {
        Bukkit.getServer().getPluginManager().registerEvents(new JoinEvent(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new LeaveEvent(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new ChatEvent(this), this);
        Bukkit.getServer().getPluginManager().registerEvents(new CommandSendEvent(this), this);

    }

    @Override
    public void onDisable() {
        getLogger().info("PlayerLogger has successfully shut down");
        getLogger().info("Plugin Version:" + getServer().getPluginManager().getPlugin("PlayerLogger").getDescription().getVersion());
        getLogger().info("Thank You For using my plugin.");
    }

    public static boolean isUpdateAvailable() {
        return updateAvailable;
    }

    public Database getDatabase() {
        return database;
    }
}
