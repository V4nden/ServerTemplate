package vanden.server.events;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.server.ServerListPingEvent;

public class MotdEvents implements Listener {
    @EventHandler
    public void motd(ServerListPingEvent e) {
        e.setMotd(ChatColor.translateAlternateColorCodes('&', "                      &x&f&f&9&4&0&0P&x&f&f&9&c&0&0i&x&f&f&a&5&0&0x&x&f&f&a&d&0&0e&x&f&f&b&5&0&0l  &7-  1.19.3 \n             &fЛучший ванильный сервер!"));
    }
}
