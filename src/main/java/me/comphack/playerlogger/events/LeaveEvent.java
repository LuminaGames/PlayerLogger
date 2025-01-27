package me.comphack.playerlogger.events;

import me.comphack.playerlogger.PlayerLogger;
import me.comphack.playerlogger.database.Database;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class LeaveEvent implements Listener {

    private final PlayerLogger plugin;

    public LeaveEvent(PlayerLogger plugin) {
        this.plugin = plugin;
    }


    @EventHandler
    public void onLeaveEvent(PlayerQuitEvent e) {
        Database db = plugin.getDatabase();
        db.setLogoutInfo(e.getPlayer().getName(), e.getPlayer().getLocation());

        if(PlayerLogger.WEBHOOK_LOGGING && plugin.getConfig().getBoolean("webhooks.settings.send-leave")) {
            plugin.getWebhookSender().sendLeaveEmbed(plugin.getDatabase().getLogs(e.getPlayer().getName()));
        }
    }
}
