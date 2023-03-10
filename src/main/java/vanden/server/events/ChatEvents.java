package vanden.server.events;

import me.clip.placeholderapi.PlaceholderAPI;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.kyori.adventure.text.format.TextColor;
import net.kyori.adventure.util.RGBLike;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.persistence.PersistentDataType;
import vanden.server.Server;
import vanden.server.Variables;

public class ChatEvents implements Listener {
    @EventHandler
    public static void chat(AsyncPlayerChatEvent e) {
        if (e.getMessage().startsWith("!")) {
            for (Player p : Bukkit.getOnlinePlayers()) {
                Component component = Component.text(Variables.maincolor + "[G] " + ChatColor.WHITE + e.getPlayer().getName() + ": " + e.getMessage().replaceFirst("!", ""))
                        .hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT,
                                Component.text(PlaceholderAPI.setPlaceholders(e.getPlayer(), e.getPlayer().getName() + "\n" +
                                                "⌚: %statistic_time_played:hours% h\n" +
                                                "✉: " + e.getPlayer().getPersistentDataContainer().get(new NamespacedKey(Server.instance, "discord"), PersistentDataType.STRING) + "\n" +
                                                "✎: " + e.getPlayer().getPersistentDataContainer().get(new NamespacedKey(Server.instance, "status"), PersistentDataType.STRING) + "\n" +
                                                "⛏: %statistic_mine_block%"))
                                        .color(NamedTextColor.WHITE)));
                p.sendMessage(component);
            }
        } else {
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (Math.sqrt(Math.pow((p.getLocation().getX() - e.getPlayer().getLocation().getX()), 2) + Math.pow((p.getLocation().getZ() - e.getPlayer().getLocation().getZ()), 2)) <= 100) {
                    p.sendMessage(Variables.maincolor + "[L] " + ChatColor.WHITE + e.getPlayer().getName() + ": " + e.getMessage());
                }
            }
        }
        e.setCancelled(true);
    }
}
