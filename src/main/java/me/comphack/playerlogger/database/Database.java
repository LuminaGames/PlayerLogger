package me.comphack.playerlogger.database;

import me.comphack.playerlogger.data.PlayerChat;
import me.comphack.playerlogger.data.PlayerLog;
import org.bukkit.Location;
import org.bukkit.entity.Player;

import java.util.List;

public interface Database {

    void init();

    PlayerLog getLogs(String player);

    List<PlayerChat> getChatLogs(String player, int limit, String... sort);

    List<PlayerChat> getCommandLogs(String player, int limit, String... sort);

    void setJoinStats(Player player, Location location);

    void setFirstJoinStats(Player player, Location location);

    void setLogoutInfo(String player, Location location);

    void addCommandLogs(String player, String message ,String dateTime);

    void addChatLogs(String player, String message ,String dateTime);
}
