package me.comphack.playerlogger.utils;

import me.clip.placeholderapi.PlaceholderAPI;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.entity.Player;
import io.github.vedantmulay.neptuneapi.bukkit.Utils;

import java.util.List;

public enum Message {

    PREFIX("messages.prefix"),
    UNKNOWN_COMMAND("messages.unknown-command"),
    NO_PERMISSION("messages.no-permission"),
    PLAYER_ONLY_COMMAND("messages.playeronly-command"),
    HELP_MESSAGE("messages.help-message"),
    GET_LOGS_FORMAT("messages.get-logs"),
    GET_FIRST_JOIN_LOCATION("messages.first-join-location"),
    GET_LAST_JOIN_LOCATION("messages.last-join-location"),
    GET_CHAT_LOGS_FORMAT("messages.chat-logs-format"),
    GET_COMMAND_LOGS_FORMAT("message.command-logs-format"),
    PLUGIN_RELOAD("message.reload"),
    LAST_JOIN_DATE("message.last-join-date"),
    PLAYER_NOT_FOUND("message.player-not-found");


    private final String path;
    private static FileConfiguration config;

    Message(String path) {
        this.path = path;
    }

    public static void setConfiguration(FileConfiguration c) {
        config = c;
    }

    public void send(CommandSender receiver, Object... replacements) {
        Object value = config.get(this.path);

        String message;
        if (value == null) {
            message = "Player Logger: message not found (" + this.path + ")";
        } else {
            message = value instanceof List ? PluginUtils.fromList((List<?>) value) : value.toString();
        }

        if (!message.isEmpty()) {
            receiver.sendMessage(Utils.cc(replace(message, replacements)));
        }
    }

    public void send(Player receiver, Object... replacements) {
        Object value = config.get(this.path);

        String message;
        if (value == null) {
            message = "Player Logger: message not found (" + this.path + ")";
        } else {
            message = value instanceof List ? PluginUtils.fromList((List<?>) value) : value.toString();
        }


        if (!message.isEmpty()) {
            try  {
                Class.forName("me.clip.placeholderapi.PlaceholderAPI");
                receiver.sendMessage(PlaceholderAPI.setPlaceholders(receiver, Utils.cc(replace(message, replacements))));
            } catch (ClassNotFoundException e) {
                receiver.sendMessage(Utils.cc(replace(message, replacements)));
            }
        }
    }

    private String replace(String message, Object... replacements) {
        for (int i = 0; i < replacements.length; i += 2) {
            if (i + 1 >= replacements.length) break;
            message = message.replace(String.valueOf(replacements[i]), String.valueOf(replacements[i + 1]));
        }

        String prefix = config.getString(PREFIX.getPath());
        return message
                .replace("{prefix}", prefix != null && !prefix.isEmpty() ? prefix : "");
    }

    public String getPath() {
        return this.path;
    }

    public String asString() {
        return Utils.cc(replace(config.getString(this.path)));
    }

}
