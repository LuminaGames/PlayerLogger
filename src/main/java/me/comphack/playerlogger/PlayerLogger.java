package me.comphack.playerlogger;

import me.comphack.playerlogger.commands.Plogs;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class PlayerLogger extends JavaPlugin implements Listener{


    private Connection connection;

    @Override
    public void onEnable() {
        getConfig().options().copyDefaults(true);
        saveConfig();
        if(getConfig().getBoolean("MySql.enable")) {
            getLogger().info("Using Storage Method [MySQL]");
            String host = getConfig().getString("MySql.host");
            String database = getConfig().getString("MySql.database");
            String password = getConfig().getString("MySql.password");
            String username = getConfig().getString("MySql.username");
            int port = getConfig().getInt("MySql.port");
            try {
                Connection mysqldb = DriverManager.getConnection("jdbc:mysql://" + host + ":" + port + "/" + database, username, password);
                getLogger().info(ChatColor.GREEN + "Connected to MySQL Successfully");
            } catch (SQLException e) {
                e.printStackTrace();
                getLogger().warning(ChatColor.DARK_RED + "Could not connect to mysql database please check your credentials");
            }
        } else {
            try {
             getLogger().info("Using Storage Method [SQLite]");
             readyDatabase();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }

        initializeCommands();
        initializeEvents();
        onEnableText();




    }
    public Connection getConnection() {
        if(connection != null) {
            return connection;
        }

        try {
            connection = DriverManager.getConnection("jdbc:sqlite:" + Bukkit.getServer().getPluginManager().getPlugin("PlayerLogger").getDataFolder() + "/PlayerLogger.db");
            Bukkit.getLogger().info("Created Database file successfully");
        } catch ( Exception e) {
            e.printStackTrace();
            Bukkit.getLogger().warning("Could not create database file.");
        }


        return this.connection;
    }

    public void readyDatabase() throws SQLException {
        String SQL = "CREATE TABLE IF NOT EXISTS player_logs (username VARCHAR(16) PRIMARY KEY, ip_address VARCHAR(32), last_join_date VARCHAR(32))";
        getConnection().createStatement().execute(SQL);
    }

    public Connection getDatabase() throws SQLException {
        return connection;
    }

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e) throws SQLException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate now = LocalDate.now();
        String UpdateSQL = "UPDATE player_logs SET ip_address = '" + e.getPlayer().getAddress() + "', " + "last_join_date = '" + now + "' WHERE username = '" + e.getPlayer().getName() + "';";
        String InsertSQL = "INSERT OR IGNORE INTO player_logs (username, ip_address, last_join_date) VALUES ('"+ e.getPlayer().getName() + "', '" + e.getPlayer().getAddress() + "', '" + now + "');";
        getConnection().createStatement().execute(InsertSQL);
        getConnection().createStatement().execute(UpdateSQL);
    }

    public void onEnableText() {
        Bukkit.getLogger().info("--------------------------------------------------");
        Bukkit.getLogger().info("                                                  ");
        Bukkit.getLogger().info("          Enabled Player Logger                   ");
        Bukkit.getLogger().info("                 v1.0-ALPHA                       ");
        Bukkit.getLogger().info("                                                  ");
        Bukkit.getLogger().info("           Developed by COMPHACK                  ");
        Bukkit.getLogger().info("                                                  ");
        Bukkit.getLogger().info("--------------------------------------------------");


    }
    public void initializeEvents() {
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    public void initializeCommands() {
        this.getCommand("playerlogs").setExecutor(new Plogs());
    }

    @Override
    public void onDisable() {

    }
}
