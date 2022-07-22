package me.comphack.playerlogger.events;


import me.comphack.playerlogger.utils.Utils;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

import java.sql.SQLException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class JoinEvent implements Listener {
    Utils database = new Utils();

    @EventHandler
    public void PlayerJoin(PlayerJoinEvent e) throws SQLException {
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd");
        LocalDate now = LocalDate.now();
        String UpdateSQL = "UPDATE player_logs SET ip_address = '" + e.getPlayer().getAddress() + "', " + "last_join_date = '" + now + "' WHERE username = '" + e.getPlayer().getName() + "';";
        String InsertSQL = "INSERT OR IGNORE INTO player_logs (username, ip_address, last_join_date) VALUES ('"+ e.getPlayer().getName() + "', '" + e.getPlayer().getAddress() + "', '" + now + "');";
        database.getDatabaseFile().getPluginDatabase().createStatement().execute(InsertSQL);
        database.getDatabaseFile().getPluginDatabase().createStatement().execute(UpdateSQL);
    }
}
