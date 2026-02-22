package me.comphack.playerlogger.webhooks;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import me.comphack.playerlogger.PlayerLogger;
import me.comphack.playerlogger.data.PlayerLog;

import java.time.Instant;

public class WebHookSender {

    private WebhookClient client;
    private String url;
    private PlayerLogger plugin = PlayerLogger.getInstance();

    public WebHookSender(WebhookClient client) {
        this.client = client;
    }

    public void sendEmbed(PlayerLog log, String type) {

        if (!plugin.getConfig().getBoolean("webhooks.enabled")) return;
        if (!plugin.getConfig().getBoolean("webhooks." + type + ".enabled")) return;

        String path = "webhooks." + type + ".";

        WebhookEmbedBuilder builder = new WebhookEmbedBuilder()
                .setColor(Integer.decode(plugin.getConfig().getString(path + "color")))
                .setTitle(new WebhookEmbed.EmbedTitle(
                        replace(plugin.getConfig().getString(path + "title"), log, type), null))
                .setThumbnailUrl(
                        replace(plugin.getConfig().getString(path + "thumbnail"), log, type))
                .setFooter(new WebhookEmbed.EmbedFooter(
                        replace(plugin.getConfig().getString(path + "footer"), log, type), null))
                .setTimestamp(Instant.now());

        plugin.getConfig().getMapList(path + "fields").forEach(map -> {

            String name = replace((String) map.get("name"), log, type);
            String value = replace((String) map.get("value"), log, type);
            Object inlineObj = map.get("inline");
            boolean inline = inlineObj instanceof Boolean && (Boolean) inlineObj;
            builder.addField(new WebhookEmbed.EmbedField(inline, name, value));
        });

        client.send(builder.build());
    }

    private String replace(String text, PlayerLog log, String eventType) {
        if (text == null) return "";

        return text
                .replace("{username}", log.getUsername())
                .replace("{ip}", log.getIp())
                .replace("{uuid}", log.getUuid().toString())
                .replace("{event}", eventType);
    }

}
