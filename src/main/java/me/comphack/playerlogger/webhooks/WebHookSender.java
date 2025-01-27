package me.comphack.playerlogger.webhooks;

import club.minnced.discord.webhook.WebhookClient;
import club.minnced.discord.webhook.send.WebhookEmbed;
import club.minnced.discord.webhook.send.WebhookEmbedBuilder;
import me.comphack.playerlogger.data.PlayerLog;

import java.time.Instant;

public class WebHookSender {

    private WebhookClient client;
    private String url;

    public WebHookSender(WebhookClient client) {
        this.client = client;
    }

    public void sendJoinEmbed(PlayerLog log) {
        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setColor(0x4CAF50)
                .setThumbnailUrl("https://api.mineatar.io/face/" + log.getUuid().toString())
                .setTitle(new WebhookEmbed.EmbedTitle("✅ Player Connected", null))
                .addField(new WebhookEmbed.EmbedField(false, "Username:", log.getUsername()))
                .addField(new WebhookEmbed.EmbedField(false, "IP Address:", "||" + log.getIp() + "||"))
                .setFooter(new WebhookEmbed.EmbedFooter("Joined the server", null))
                .setTimestamp(Instant.now())
                .build();
        client.send(embed);
    }

    public void sendLeaveEmbed(PlayerLog log) {
        WebhookEmbed embed = new WebhookEmbedBuilder()
                .setColor(0xF44336)
                .setThumbnailUrl("https://api.mineatar.io/face/" + log.getUuid().toString())
                .setTitle(new WebhookEmbed.EmbedTitle("❌ Player Disconnected", null))
                .addField(new WebhookEmbed.EmbedField(false, "Username:", log.getUsername()))
                .addField(new WebhookEmbed.EmbedField(false, "IP Address:", "||" + log.getIp() + "||"))
                .setFooter(new WebhookEmbed.EmbedFooter("Left the server", null))
                .setTimestamp(Instant.now())
                .build();
        client.send(embed);
    }

}
