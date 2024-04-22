package me.comphack.playerlogger.events;

import me.comphack.playerlogger.PlayerLogger;
import me.comphack.playerlogger.data.PlayerLog;
import me.comphack.playerlogger.database.Database;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.net.InetSocketAddress;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.UUID;

public class JoinEvent implements Listener {

    private PlayerLogger plugin;

    public JoinEvent(PlayerLogger plugin) {
        this.plugin = plugin;
    }

    @EventHandler
    public void onPlayerJoin(PlayerJoinEvent e) throws SQLException {
        Database database = plugin.getDatabase();
        InetSocketAddress address = e.getPlayer().getAddress();
        String username = e.getPlayer().getName();
        UUID uuid = e.getPlayer().getUniqueId();
        Double x = e.getPlayer().getLocation().getX();
        Double y = e.getPlayer().getLocation().getY();
        Double z = e.getPlayer().getLocation().getZ();
        World world = e.getPlayer().getWorld();

        //Sets the IP Address of the player in the database
        database.setJoinStats(e.getPlayer(), new Location(world, x, y, z));

        if(!e.getPlayer().hasPlayedBefore()) {
            database.setFirstJoinStats(e.getPlayer(), new Location(world, x, y, z));
        }

        if(e.getPlayer().isOp() && PlayerLogger.isUpdateAvailable()) {
            e.getPlayer().sendMessage("An update of player logger is available. You can download it from spigot mc");
        }
    }
}
