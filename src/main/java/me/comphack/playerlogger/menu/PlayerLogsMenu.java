package me.comphack.playerlogger.menu;

import fr.mrmicky.fastinv.FastInv;
import fr.mrmicky.fastinv.ItemBuilder;
import me.comphack.playerlogger.PlayerLogger;
import me.comphack.playerlogger.data.PlayerChat;
import me.comphack.playerlogger.data.PlayerLog;
import me.comphack.playerlogger.utils.Utils;
import org.bukkit.entity.Player;
import com.cryptomorin.xseries.XMaterial;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.time.Instant;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.List;

public class PlayerLogsMenu extends FastInv {

    private final PlayerLogger plugin = PlayerLogger.getInstance();

    public PlayerLogsMenu(String player, PlayerLog log) {
        super(27, Utils.cc("&6Showing Player Logs"));


        // Player head item at the center
        setItem(10,
                new ItemBuilder(XMaterial.PLAYER_HEAD.parseMaterial())
                        .name(Utils.cc("&6" + log.getUsername()))
                        .lore(Utils.cc("&6UUID: &f" + log.getUuid()))
                        .build()
        );

        setItem(11,
                new ItemBuilder(XMaterial.PAPER.parseMaterial())
                        .name(Utils.cc("&6IP Address"))
                        .lore(Utils.cc("&f" + log.getIp()))
                        .build()
        );

        setItem(12,
                new ItemBuilder(XMaterial.CLOCK.parseMaterial())
                        .name(Utils.cc("&6Last Join Date"))
                        .lore(Utils.cc("&f" + DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss").withZone(ZoneId.systemDefault()).format(Instant.ofEpochMilli(Long.parseLong(log.getLastJoinDate())))))
                        .build()
        );

        String firstJoin = Utils.formatLocation(log.getFirstJoinLocation());

        setItem(13,
                new ItemBuilder(XMaterial.MAP.parseMaterial())
                        .name(Utils.cc("&6First Join Location"))
                        .lore(firstJoin == null ? Utils.cc("&fNot Found") : Utils.cc("&f" + firstJoin))
                        .build()
        );

        String lastJoin = Utils.formatLocation(log.getLastJoinLocation());

        setItem(14,
                new ItemBuilder(XMaterial.MAP.parseMaterial())
                        .name(Utils.cc("&6Last Join Location"))
                        .lore(lastJoin == null ? Utils.cc("&fNot Found") : Utils.cc("&f" + lastJoin))
                        .build()
        );

        setItem(15,
                new ItemBuilder(XMaterial.PAPER.parseMaterial())
                        .name(Utils.cc("&6Show Recent Chat Logs"))
                        .lore(Utils.cc("&fShow the last 10 messages sent by &6" + log.getUsername()))
                        .build()
        );

        setItem(16,
                new ItemBuilder(XMaterial.PAPER.parseMaterial())
                        .name(Utils.cc("&6Show Recent Command Logs"))
                        .lore(Utils.cc("&fShow the last 10 commands sent by &6" + log.getUsername()))
                        .build()
        );
    }

    @Override
    public void onClick(InventoryClickEvent e) {
        Player player = (Player) e.getWhoClicked();

        if (e.getSlot() == 15) {
            List<PlayerChat> chatLogs = plugin.getDatabase().getChatLogs(player.getName(), 10, "NEW");

            for (PlayerChat chatLog : chatLogs) {
                String username_ = chatLog.getUsername();
                String dateTime = chatLog.getDateTime();
                String message = chatLog.getMessage();
                player.sendMessage(Utils.cc("{prefix} &6" + username_ + "&f: &e" + message + " &7[&f" + dateTime + "&7]"));
            }

            player.getOpenInventory().close();

        } else if (e.getSlot() == 16) {
            List<PlayerChat> commandLogs = plugin.getDatabase().getCommandLogs(player.getName(), 10, "NEW");

            for (PlayerChat commandLog : commandLogs) {
                String username_ = commandLog.getUsername();
                String dateTime = commandLog.getDateTime();
                String message = commandLog.getMessage();
                player.sendMessage(Utils.cc("{prefix} &6" + "&f: &e" + message + " &7[&f" + dateTime + "&7]"));
            }

            player.getOpenInventory().close();

        }
    }
}
