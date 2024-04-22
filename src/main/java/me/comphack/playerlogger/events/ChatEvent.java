package me.comphack.playerlogger.events;

import me.comphack.playerlogger.PlayerLogger;
import me.comphack.playerlogger.database.Database;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ChatEvent implements Listener {

    private PlayerLogger plugin;

    public ChatEvent(PlayerLogger plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void playerChatEvent(AsyncPlayerChatEvent e) {
        Database db = plugin.getDatabase();
        String player = e.getPlayer().getName();
        String message = e.getMessage();
        LocalDateTime date = LocalDateTime.now();
        String now = date.format(DateTimeFormatter.ofPattern("MM/dd/yyyy hh:mm a"));
        db.addChatLogs(player, message, now);

    }
}
