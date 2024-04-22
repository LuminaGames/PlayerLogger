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
        String player = e.getPlayer().getName();
        Double x = e.getPlayer().getLocation().getX();
        Double y = e.getPlayer().getLocation().getY();
        Double z = e.getPlayer().getLocation().getZ();
        String world = e.getPlayer().getWorld().getName();
        db.setLogoutInfo(e.getPlayer().getName(), e.getPlayer().getLocation());

    }
}
