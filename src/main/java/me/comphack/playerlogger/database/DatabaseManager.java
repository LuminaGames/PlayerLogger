package me.comphack.playerlogger.database;

import me.comphack.playerlogger.utils.PlayerChat;
import me.comphack.playerlogger.utils.Utils;
import org.bukkit.Bukkit;

import java.io.File;
import java.io.IOException;
import java.net.InetSocketAddress;
import java.sql.*;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.Date;

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
            Url = "jdbc:mysql://" + host + ":" + port + "/" + database + "?autoReconnect=true";
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

    public boolean isDebugMode() {
        if(utils.getPluginConfig().getConfig().getBoolean("general.debug-mode")) {
            return true;
        }
        return false;
    }

    public void sendDebugLog(String s) {
        Bukkit.getServer().getPluginManager().getPlugin("PlayerLogger").getLogger().info(s);
    }

    public String getIP(String player) {
        String SQL = "SELECT * FROM player_logs WHERE username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, player);
            ResultSet resultSet = preparedStatement.executeQuery();

            String IPAddr = null;

            if (isDebugMode()) {
                sendDebugLog("Requested a getIP request to the database for " + player);
            }
            while (resultSet.next()) {
                IPAddr = resultSet.getString("ip_address");
            }
            return IPAddr;
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getLastJoinDate(String player) {
        String SQL = "SELECT * FROM player_logs WHERE username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, player);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (isDebugMode()) {
                sendDebugLog("Requested a getLastJoinDate request to the database for " + player);
            }

            String joinDate = null;

            while (resultSet.next()) {
                joinDate = resultSet.getString("last_join_date");
            }

            return joinDate;
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return null;
    }


    public String getLogoutLocation(String player) {
        String SQL = "SELECT * FROM player_logs WHERE username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, player);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (isDebugMode()) {
                sendDebugLog("Requested a getLogoutLocation request to the database for " + player);
            }

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
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }


    public String getFirstJoinInfo(String player) {
        String SQL = "SELECT * FROM player_logs WHERE username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, player);
            ResultSet resultSet = preparedStatement.executeQuery();

            if (isDebugMode()) {
                sendDebugLog("Requested a firstJoinInfo request to the database for " + player);
            }

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
        }  catch (SQLException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setJoinStats(String player, InetSocketAddress address, UUID uuid, LocalDate lastjoin) {
        String insertSQL = "INSERT INTO player_logs (username, uuid, ip_address, last_join_date) VALUES (?, ?, ?, ?)";
        String updateSQL = "UPDATE player_logs SET ip_address = ?, last_join_date = ? WHERE username = ?";

        try (PreparedStatement insertStatement = connection.prepareStatement(insertSQL);
             PreparedStatement updateStatement = connection.prepareStatement(updateSQL)) {

            insertStatement.setString(1, player);
            insertStatement.setString(2, uuid.toString());
            insertStatement.setString(3, address.toString());
            insertStatement.setDate(4, java.sql.Date.valueOf(lastjoin));

            updateStatement.setString(1, address.toString());
            updateStatement.setDate(2, java.sql.Date.valueOf(lastjoin));
            updateStatement.setString(3, player);

            if (isDebugMode()) {
                sendDebugLog("Set Join Stats for " + player);
            }

            // Try to insert. If it fails due to a duplicate key, then update.
            try {
                insertStatement.execute();
            } catch (SQLException e) {
                try {
                    updateStatement.execute();
                }  catch (SQLException ex) {
                    ex.printStackTrace();
                }

            }
        }   catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void setFirstJoinInfo(String player, Double x, Double y, Double z, String world) {
        String SQL = "UPDATE player_logs SET firstjoin_world = ?, firstjoin_x = ?, firstjoin_y = ?, firstjoin_z = ? WHERE username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, world);
            preparedStatement.setDouble(2, x);
            preparedStatement.setDouble(3, y);
            preparedStatement.setDouble(4, z);
            preparedStatement.setString(5, player);

            preparedStatement.executeUpdate();

            if (isDebugMode()) {
                sendDebugLog("Set First Join Information Stats for " + player);
            }
        }   catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public void setLogOutInfo(String player, Double x, Double y, Double z, String world) {
        String SQL = "UPDATE player_logs SET logout_world = ?, logout_x = ?, logout_y = ?, logout_z = ? WHERE username = ?";

        try (PreparedStatement preparedStatement = connection.prepareStatement(SQL)) {
            preparedStatement.setString(1, world);
            preparedStatement.setDouble(2, x);
            preparedStatement.setDouble(3, y);
            preparedStatement.setDouble(4, z);
            preparedStatement.setString(5, player);

            preparedStatement.executeUpdate();

            if (isDebugMode()) {
                sendDebugLog("Set Log out Location info for " + player);
            }

        }  catch (SQLException e) {
            e.printStackTrace();
        }
    }


    public void addChatLogs(String player, String message ,String dateTime) {
        String SQL = "INSERT INTO chat_logs (username, message, date_and_time) VALUES (?, ?, ?);";
        try(PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, player);
            ps.setString(2, message);
            ps.setString(3, dateTime);
            ps.execute();
        }   catch (SQLException e) {
            e.printStackTrace();
        }

    }

    public void addCommandLogs(String player, String command ,String dateTime) {
        String SQL = "INSERT INTO command_logs (username, command, date_and_time) VALUES (?, ?, ?);";
        try(PreparedStatement ps = connection.prepareStatement(SQL)) {
            ps.setString(1, player);
            ps.setString(2, command);
            ps.setString(3, dateTime);
            ps.execute();
        }   catch (SQLException e) {
            e.printStackTrace();
        }

    }


    public List<PlayerChat> getCommandLogs(String player, int limit, String... sort) {
        String SQL = null;
        List<PlayerChat> chatLogs = new ArrayList<>();
        if (sort[0] == null) {
            sort[0] = "NEW";
        }

        switch (sort[0]) {
            case "OLD":
                SQL = "SELECT * FROM command_logs WHERE username = ? ORDER BY date_and_time ASC LIMIT ?";
                break;
            case "NEW":
                SQL = "SELECT * FROM command_logs WHERE username = ? ORDER BY date_and_time DESC LIMIT ?";
                break;
        }

        try {
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, player);
            ps.setInt(2, limit);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String message = resultSet.getString("command");
                String dateTimeStr = resultSet.getString("date_and_time");

                // Convert the date string to a standardized format
                SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                try {
                    Date date = inputFormat.parse(dateTimeStr);
                    String standardizedDateTime = outputFormat.format(date);

                    String username = resultSet.getString("username");
                    PlayerChat chat = new PlayerChat(message, username, standardizedDateTime);
                    chatLogs.add(chat);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chatLogs;
    }

    public List<PlayerChat> getChatLogs(String player, int limit, String... sort) {
        String SQL = null;
        List<PlayerChat> chatLogs = new ArrayList<>();
        if (sort[0] == null) {
            sort[0] = "NEW";
        }

        switch (sort[0]) {
            case "OLD":
                SQL = "SELECT * FROM chat_logs WHERE username = ? ORDER BY date_and_time ASC LIMIT ?";
                break;
            case "NEW":
                SQL = "SELECT * FROM chat_logs WHERE username = ? ORDER BY date_and_time DESC LIMIT ?";
                break;
        }

        try {
            PreparedStatement ps = connection.prepareStatement(SQL);
            ps.setString(1, player);
            ps.setInt(2, limit);
            ResultSet resultSet = ps.executeQuery();

            while (resultSet.next()) {
                String message = resultSet.getString("message");
                String dateTimeStr = resultSet.getString("date_and_time");

                // Convert the date string to a standardized format
                SimpleDateFormat inputFormat = new SimpleDateFormat("MM/dd/yyyy hh:mm a");
                SimpleDateFormat outputFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");

                try {
                    Date date = inputFormat.parse(dateTimeStr);
                    String standardizedDateTime = outputFormat.format(date);

                    String username = resultSet.getString("username");
                    PlayerChat chat = new PlayerChat(message, username, standardizedDateTime);
                    chatLogs.add(chat);
                } catch (ParseException e) {
                    e.printStackTrace();
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return chatLogs;
    }

    public Connection getConnection() {
        return connection;
    }

    public void closeConnection() {
        try {
            connection.close();
            sendDebugLog("Closing Database Connections.");
        } catch (SQLException e) {
            sendDebugLog("There was a error in disabling Database Connection");
            e.printStackTrace();
        }
    }

}