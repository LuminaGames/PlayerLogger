package me.comphack.playerlogger.events;

import me.comphack.playerlogger.database.DatabaseManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerChatEvent;

import javax.xml.crypto.Data;
import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatEvent implements Listener {

    private DatabaseManager database = new DatabaseManager();

    @EventHandler
    public void playerChatEvent(AsyncPlayerChatEvent e) throws SQLException {
        String player = e.getPlayer().getName();
        String message = e.getMessage();
        LocalDateTime date = LocalDateTime.now();
        String now = date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"));
        database.addChatLogs(player, message, now);

    }
}
