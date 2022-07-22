package me.comphack.playerlogger.utils;


import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

public class GetLogs {
    Utils database = new Utils();

    public String getIP(String player) throws SQLException {
        String SQL = "SELECT * FROM player_logs WHERE username = '" + player + "'";
        Statement statement = database.getDatabaseFile().getPluginDatabase().createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);
        String IPAddr = resultSet.getString("ip_address");
        return IPAddr;
    }

    public String lastLoginDate(String player) throws SQLException {
        String SQL = "SELECT * FROM player_logs WHERE username = '" + player + "'";
        Statement statement = database.getDatabaseFile().getPluginDatabase().createStatement();
        ResultSet resultSet = statement.executeQuery(SQL);
        return resultSet.getString("last_join_date");
    }

}
