package me.comphack.playerlogger.events;

import me.comphack.playerlogger.PlayerLogger;
import me.comphack.playerlogger.data.PlayerLog;
import me.comphack.playerlogger.database.Database;
import me.comphack.playerlogger.utils.Utils;
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
        double x = e.getPlayer().getLocation().getX();
        double y = e.getPlayer().getLocation().getY();
        double z = e.getPlayer().getLocation().getZ();
        World world = e.getPlayer().getWorld();

        //Sets the IP Address of the player in the database
        database.setJoinStats(e.getPlayer(), new Location(world, x, y, z));

        if(!e.getPlayer().hasPlayedBefore()) {
            database.setFirstJoinStats(e.getPlayer(), new Location(world, x, y, z));
        }

        if(e.getPlayer().isOp() && PlayerLogger.isUpdateAvailable()) {
            e.getPlayer().sendMessage(Utils.cc("&6An update of Player Logger is available... \n&fDownload: &ahttps://modrinth.com/plugin/player-logger"));
        }

        if(PlayerLogger.WEBHOOK_LOGGING && plugin.getConfig().getBoolean("webhooks.settings.send-join")) {
            plugin.getWebhookSender().sendJoinEmbed(plugin.getDatabase().getLogs(e.getPlayer().getName()));
        }
    }
}
