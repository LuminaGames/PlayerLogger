package me.comphack.playerlogger.database;

import me.comphack.playerlogger.utils.Utils;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.*;
import java.time.LocalDate;
import java.util.UUID;

public class DatabaseManager {

    static Connection connection;
    private Utils utils = new Utils();
    private String Url;
    private String host, port, database, username, password;


    public void setupJDBC() {
        database = utils.getPluginConfig().getConfig().getString("database.database");
        username = utils.getPluginConfig().getConfig().getString("database.username");
        password = utils.getPluginConfig().getConfig().getString("database.password");
        host = utils.getPluginConfig().getConfig().getString("database.host");
        port = utils.getPluginConfig().getConfig().getString("database.port");
        if (utils.getPluginConfig().getConfig().getBoolean("database.enabled")) {
            Url = "jdbc:mysql://" + host + ":" + port + "/" + database;
            Bukkit.getLogger().info("Using Storage Method [MySQL]");
        } else {
            Bukkit.getLogger().info("Using Storage Method [SQLite]");
            File database = new File(Bukkit.getServer().getPluginManager().getPlugin("PlayerLogger").getDataFolder(), "PlayerLogger.db");
            if (!database.exists()) {
                try {
                    database.createNewFile();
                } catch (IOException ex) {
                    ex.printStackTrace();
                }
            }
            Url = "jdbc:sqlite:" + database;
        }
        try {
            connection = DriverManager.getConnection(Url, username, password);
        } catch (SQLException e) {
            e.printStackTrace();
        }


    }


    public void PluginDatabase() {
        String SQL = "CREATE TABLE IF NOT EXISTS player_logs " +
                "(username VARCHAR(16) PRIMARY KEY," +
                " uuid VARCHAR(32)," +
                " ip_address VARCHAR(32)," +
                " last_join_date VARCHAR(32)," +
                " logout_world VARCHAR(32)," +
                " logout_x VARCHAR(32)," +
                " logout_y VARCHAR(32)," +
                " logout_z VARCHAR(32)," +
                " firstjoin_world VARCHAR(32)," +
                " firstjoin_x VARCHAR(32)," +
                " firstjoin_y VARCHAR(32)," +
                " firstjoin_z VARCHAR(32));";
        String SQL2 = " CREATE TABLE IF NOT EXISTS chat_logs" +
                " (username VARCHAR(16), " +
                " message VARCHAR(256), " +
                " date_and_time VARCHAR(32));";
        String SQL3 = " CREATE TABLE IF NOT EXISTS command_logs" +
                " (username VARCHAR(16), " +
                " command VARCHAR(256), " +
                " date_and_time VARCHAR(32));";

        try {
            connection.createStatement().execute(SQL);
            connection.createStatement().execute(SQL2);
            connection.createStatement().execute(SQL3);
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public String getIP(String player) throws SQLException {
        String SQL = "SELECT * FROM player_logs WHERE username = '" + player + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);
        String IPAddr = null;
        while (resultSet.next()) {
            IPAddr = resultSet.getString("ip_address");
        }
        return IPAddr;
    }


    public String getLastJoinDate(String player) throws SQLException {
        String SQL = "SELECT * FROM player_logs WHERE username = '" + player + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);
        String JoinDate = null;
        while (resultSet.next()) {
            JoinDate = resultSet.getString("last_join_date");
        }
        return JoinDate;

    }

    public String getLogoutLocation(String player) throws SQLException {
        String SQL = "SELECT * FROM player_logs WHERE username = '" + player + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);
        String logout_x = "No Data";
        String logout_y = "No Data";
        String logout_z = "No Data";
        String logout_world = "No Data";
        while (resultSet.next()) {
            logout_world = resultSet.getString("logout_world");
            logout_x = resultSet.getString("logout_x");
            logout_y = resultSet.getString("logout_y");
            logout_z = resultSet.getString("logout_z");
        }
        return logout_x + ", " + logout_y + ", " + logout_z + ", " + logout_world;

    }

    public String getFirstJoinInfo(String player) throws SQLException {
        String SQL = "SELECT * FROM player_logs WHERE username = '" + player + "'";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);
        String firstjoin_x = "No Data";
        String firstjoin_y = "No Data";
        String firstjoin_z = "No Data";
        String firstjoin_world = "No Data";
        while (resultSet.next()) {
            firstjoin_x = resultSet.getString("firstjoin_x");
            firstjoin_y = resultSet.getString("firstjoin_y");
            firstjoin_z = resultSet.getString("firstjoin_z");
            firstjoin_world = resultSet.getString("firstjoin_world");
        }
        return firstjoin_x + ", " + firstjoin_y + ", " + firstjoin_z + ", " + firstjoin_world;
    }

    public void setJoinStats(String player, InetSocketAddress address, UUID uuid, LocalDate lastjoin) throws SQLException {
        String SQL = "INSERT OR IGNORE INTO player_logs" +
                "(username, uuid, ip_address, last_join_date)" +
                "VALUES ('" + player + "', '" + uuid + "', '" + address + "', '" + lastjoin + "');";
                //"ON DUPLICATE KEY UPDATE ip_address = " + address + "," + "last_join_date = " + lastjoin + ";";
                String UpdateSQL = "UPDATE player_logs SET ip_address = '" + address + "', " + "last_join_date = '" + lastjoin + "' WHERE username = '" + player + "';";
                connection.createStatement().execute(SQL);
                connection.createStatement().execute(UpdateSQL);

    }

    public void setFirstJoinInfo(String player, Double x, Double y, Double z, String world) throws SQLException {
        String SQL = "UPDATE player_logs SET firstjoin_world = '" + world + "', firstjoin_x = '" + x + "'," +
                " firstjoin_y = '" + y + "', firstjoin_z = '" + z + "' WHERE username = '" + player + "';";
        connection.createStatement().execute(SQL);

    }

    public void setLogOutInfo(String player ,Double x, Double y, Double z, String world) throws SQLException {
        String SQL = "UPDATE player_logs SET logout_world = '" + world + "', logout_x = '" + x + "'," +
                " logout_y = '" + y + "', logout_z = '" + z + "' WHERE username = '" + player + "';";
        connection.createStatement().execute(SQL);

    }

    public void addChatLogs(String player, String message ,String dateTime) throws SQLException {
        String SQL = "INSERT INTO chat_logs (username, message, date_and_time) VALUES ('" + player + "', '" + message + "', '" + dateTime + "');";
        connection.createStatement().execute(SQL);
    }

    public String getChatLogs(String player, int limit) throws SQLException {
        String SQL = "SELECT * FROM chat_logs WHERE username = '" + player + "' LIMIT " + limit + ";";
        Statement statement = connection.createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);
        String ChatData = "No Data!";
        while(resultSet.next()) {
            for(int i = 1; i < limit; i++)
                ChatData = resultSet.getString(i);

        }
        return ChatData;

    }

    public Connection getConnection() {
        return connection;
    }








}

