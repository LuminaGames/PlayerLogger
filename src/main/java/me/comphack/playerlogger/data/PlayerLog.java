package me.comphack.playerlogger.data;

import org.bukkit.Location;

import java.sql.Timestamp;
import java.util.UUID;

public class PlayerLog {

    private final String username;
    private final UUID uuid;
    private final Location firstJoinLocation;
    private final Location lastJoinLocation;
    private final String lastJoinDate;
    private final String ip;

    public PlayerLog(String username, UUID uuid, Location firstJoinLocation, Location lastJoinLocation, String lastJoinDate, String ip) {
        this.username = username;
        this.uuid = uuid;
        this.firstJoinLocation = firstJoinLocation;
        this.lastJoinLocation = lastJoinLocation;
        this.lastJoinDate = lastJoinDate;
        this.ip = ip;
    }

    public String getLastJoinDate() {
        return lastJoinDate;
    }


    public String getUsername() {
        return username;
    }

    public UUID getUuid() {
        return uuid;
    }

    public Location getFirstJoinLocation() {
        return firstJoinLocation;
    }

    public Location getLastJoinLocation() {
        return lastJoinLocation;
    }

    public String getIp() {
        return ip;
    }
}
