package me.comphack.playerlogger.events;

import me.comphack.playerlogger.database.DatabaseManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommandSendEvent implements Listener {

    @EventHandler
    public void playerCommandSend(PlayerCommandPreprocessEvent e) throws SQLException {
        DatabaseManager db = new DatabaseManager();
        String command = e.getMessage();
        LocalDateTime date = LocalDateTime.now();
        String now = date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"));
        db.addCommandLogs(e.getPlayer().getName(), command, now);


    }
}
