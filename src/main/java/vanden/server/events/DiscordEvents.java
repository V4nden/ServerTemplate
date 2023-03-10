package vanden.server.events;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dv8tion.jda.api.events.guild.GuildReadyEvent;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.CommandData;
import net.dv8tion.jda.api.interactions.commands.build.Commands;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.event.ClickEvent;
import net.kyori.adventure.text.event.HoverEvent;
import net.kyori.adventure.text.format.NamedTextColor;
import net.md_5.bungee.api.ChatColor;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.NamespacedKey;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapView;
import org.bukkit.persistence.PersistentDataType;
import vanden.server.ImagetomapLogic;
import vanden.server.Server;
import vanden.server.Variables;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.UUID;

public class DiscordEvents extends ListenerAdapter {
    public static HashMap<UUID, ItemStack> availableMaps = new HashMap<UUID, ItemStack>();

    @Override
    public void onGuildReady(GuildReadyEvent e) {
        List<CommandData> commands = new ArrayList<CommandData>();
        commands.add(Commands.slash("news", "Создать новость"));
        commands.add(Commands.slash("emb", "Сообщение").addOption(OptionType.STRING, "type", "What kind of message do you like to send?"));
        e.getGuild().updateCommands().addCommands(commands).queue();
    }

    @Override
    public void onMessageReceived(MessageReceivedEvent e) {
        if (e.getGuild().getId().equals("1055112549844123718")) {
            if (e.getMessage().getChannel().getId().equals("1055496064045682688")) {
                if (!e.getAuthor().isBot()) {
                    if (e.getMessage().getAttachments().size() == 0) {
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendMessage(Variables.maincolor + "[D] " + ChatColor.WHITE + e.getAuthor().getName() + ": " + e.getMessage().getContentRaw());
                        }
                    } else {
                        ItemStack mapitem = new ItemStack(Material.FILLED_MAP, 1);
                        MapMeta mapMeta = (MapMeta) mapitem.getItemMeta();

                        MapView mapView = Bukkit.createMap(Bukkit.getWorld("world"));
                        mapView.addRenderer(new ImagetomapLogic(e.getMessage().getAttachments().get(0).getUrl()));

                        mapMeta.setMapView(mapView);
                        mapitem.setItemMeta(mapMeta);

                        UUID mapuuid = UUID.randomUUID();

                        availableMaps.put(mapuuid, mapitem);

                        Component component = Component.text(Variables.maincolor + "[D] " + ChatColor.YELLOW + "✂ " + ChatColor.WHITE + e.getAuthor().getName() + ": " + e.getMessage().getContentRaw())
                                .clickEvent(ClickEvent.clickEvent(ClickEvent.Action.RUN_COMMAND, "/getmap " + mapuuid))
                                .hoverEvent(HoverEvent.hoverEvent(HoverEvent.Action.SHOW_TEXT,
                                        Component.text("Нажми, чтобы посмотреть на вложение")
                                                .color(NamedTextColor.WHITE)));
                        for (Player p : Bukkit.getOnlinePlayers()) {
                            p.sendMessage(component);
                        }

                        Bukkit.getServer().getScheduler().runTaskLater(Server.instance, new Runnable() {
                            @Override
                            public void run() {
                                availableMaps.remove(mapuuid);
                            }
                        }, 20L*30L);
                    }
                }
            }
        }
    }
}
