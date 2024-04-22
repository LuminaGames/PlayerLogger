package me.comphack.playerlogger.events;

import me.comphack.playerlogger.PlayerLogger;
import me.comphack.playerlogger.database.Database;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

import java.sql.SQLException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class CommandSendEvent implements Listener {

    private PlayerLogger plugin;

    public CommandSendEvent(PlayerLogger plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerCommandSend(PlayerCommandPreprocessEvent e) throws SQLException {
        Database db = plugin.getDatabase();
        String command = e.getMessage();
        LocalDateTime date = LocalDateTime.now();
        String now = date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"));
        db.addCommandLogs(e.getPlayer().getName(), command, now);


    }
}
