package me.comphack.playerlogger.database;


import com.mysql.cj.jdbc.MysqlDataSource;
import me.comphack.playerlogger.utils.Utils;
import org.bukkit.Bukkit;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Initialize {
    MysqlDataSource sqlconn = new MysqlDataSource();
    private Connection sqliteconn;
    String SQL =  "CREATE TABLE IF NOT EXISTS player_logs (username VARCHAR(16) PRIMARY KEY, ip_address VARCHAR(32), last_join_date VARCHAR(32))";
    public void connectMySQL() throws SQLException {
        Utils playerLogger = new Utils();
        String username = playerLogger.getPluginConfig().getConfig().getString("database.username");
        String host = playerLogger.getPluginConfig().getConfig().getString("database.host");
        String database = playerLogger.getPluginConfig().getConfig().getString("database.database");
        String password = playerLogger.getPluginConfig().getConfig().getString("database.password");
        int port = playerLogger.getPluginConfig().getConfig().getInt("database.port");

        sqlconn.setServerName(host);
        sqlconn.setPortNumber(port);
        sqlconn.setDatabaseName(database);
        sqlconn.setUser(username);
        sqlconn.setPassword(password);

    }

    public void testSQLConnection() throws SQLException {
        try (Connection conn = sqlconn.getConnection()) {
            getPluginDatabase().createStatement().execute(SQL);
            if (!conn.isValid(1)) {
                throw new SQLException("Could not establish database connection.");
            }
        }
    }

    public void createSQLiteDatabase() throws SQLException {
        try {
            sqliteconn = DriverManager.getConnection("jdbc:sqlite:" + Bukkit.getServer().getPluginManager().getPlugin("PlayerLogger").getDataFolder() + "/PlayerLogger.db");
            Bukkit.getLogger().info("Created Database file successfully");
        } catch ( Exception e) {
            e.printStackTrace();
            Bukkit.getLogger().warning("Could not create database file.");
        }
        getPluginDatabase().createStatement().execute(SQL);
    }

    public Connection getPluginDatabase() {
        if(sqlconn == null) {
        } return sqliteconn;
    }


}

