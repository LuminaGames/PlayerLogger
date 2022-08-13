package me.comphack.playerlogger.events;

import me.comphack.playerlogger.database.DatabaseManager;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.Date;
import java.util.UUID;

public class JoinEvent implements Listener {

    private DatabaseManager database = new DatabaseManager();

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) throws SQLException {
        InetSocketAddress address = e.getPlayer().getAddress();
        String username = e.getPlayer().getName();
        UUID uuid = e.getPlayer().getUniqueId();
        Double x = e.getPlayer().getLocation().getX();
        Double y = e.getPlayer().getLocation().getY();
        Double z = e.getPlayer().getLocation().getZ();
        String world = e.getPlayer().getWorld().getName();
        LocalDate lastjoin = LocalDate.now();
        //Sets the IP Address of the player in the database
        database.setJoinStats(username, address, uuid, lastjoin);

        if(e.getPlayer().hasPlayedBefore()) {
        } else {
            database.setFirstJoinInfo(username, x, y, z, world);
        }
    }
}
