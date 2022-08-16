package me.comphack.playerlogger.events;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

public class CommandSendEvent implements Listener {

    @EventHandler
    public void playerCommandSend(PlayerCommandPreprocessEvent e) {
        e.getMessage();
    }
}
