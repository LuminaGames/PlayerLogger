package me.comphack.playerlogger.events;

import me.comphack.playerlogger.database.DatabaseManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerQuitEvent;

import java.sql.SQLException;

public class LeaveEvent implements Listener {

    private DatabaseManager database = new DatabaseManager();


    @EventHandler
    public void onLeaveEvent(PlayerQuitEvent e) throws SQLException {
        String player = e.getPlayer().getName();
        Double x = e.getPlayer().getLocation().getX();
        Double y = e.getPlayer().getLocation().getY();
        Double z = e.getPlayer().getLocation().getZ();
        String world = e.getPlayer().getWorld().getName();
        database.setLogOutInfo(player, x, y, z, world);

    }
}
