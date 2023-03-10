package vanden.server.events;

import net.dv8tion.jda.api.EmbedBuilder;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;
import vanden.server.Server;

import java.awt.*;
import java.io.IOException;

public class DiscordChatEvents implements Listener{
    @EventHandler
    public static void onMessage(AsyncPlayerChatEvent e) throws IOException {
        if (e.getMessage().startsWith("!")) {
            EmbedBuilder message = new EmbedBuilder();
            message.addField(e.getPlayer().getName(), e.getMessage().replaceFirst("!", ""), false);
            message.setThumbnail("https://www.mc-heads.net/avatar/" + e.getPlayer().getName() + "/32");
            message.setColor(Color.ORANGE);
            Server.bot.getGuildById("1055112549844123718").getTextChannelById("1055496064045682688").sendMessageEmbeds(message.build()).queue();
        }
    }
    @EventHandler
    public static void onJoin(PlayerJoinEvent e) {
        EmbedBuilder message = new EmbedBuilder();
        message.addField(e.getPlayer().getName(), "Присоединился к игре", false);
        message.setThumbnail("https://www.mc-heads.net/avatar/" + e.getPlayer().getName() + "/32");
        message.setColor(Color.GREEN);
        Server.bot.getGuildById("1055112549844123718").getTextChannelById("1055496064045682688").sendMessageEmbeds(message.build()).queue();
    }

    @EventHandler
    public static void onQuit(PlayerQuitEvent e) {
        EmbedBuilder message = new EmbedBuilder();
        message.addField(e.getPlayer().getName(), "Вышел из игры", false);
        message.setThumbnail("https://www.mc-heads.net/avatar/" + e.getPlayer().getName() + "/32");
        message.setColor(Color.RED);
        Server.bot.getGuildById("1055112549844123718").getTextChannelById("1055496064045682688").sendMessageEmbeds(message.build()).queue();
    }
}
