package vanden.server;

import me.clip.placeholderapi.libs.kyori.adventure.text.TextComponent;
import net.dv8tion.jda.api.exceptions.ErrorResponseException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.ComponentBuilder;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.checker.units.qual.N;
import org.jetbrains.annotations.NotNull;
import vanden.server.Server;
import vanden.server.Variables;

import java.util.ArrayList;

public class LoginLogic implements Listener {
    public static ArrayList<Player> unloggedPlayers = new ArrayList<Player>();
    @EventHandler
    public void joinLogin(PlayerJoinEvent e) {
        try {
            e.getPlayer().getPersistentDataContainer().set(new NamespacedKey(Server.instance, "discord"), PersistentDataType.STRING, Server.bot.getGuildById("1055112549844123718").retrieveMemberById(Server.playerdata.get(e.getPlayer().getName()).get("discord").toString()).complete().getUser().getAsTag());
            e.getPlayer().sendMessage(Variables.maincolor + "[I] " + ChatColor.WHITE + "Info: Привет! Введи пароль для начала игры." );
            unloggedPlayers.add(e.getPlayer());
            if (!e.getPlayer().getPersistentDataContainer().has(new NamespacedKey(Server.instance, "status"))) {
                e.getPlayer().getPersistentDataContainer().set(new NamespacedKey(Server.instance, "status"), PersistentDataType.STRING, "-");
            }
            Bukkit.getServer().getScheduler().runTaskLater(Server.instance, new Runnable() {
                @Override
                public void run() {
                    if (unloggedPlayers.contains(e.getPlayer())) {
                        e.getPlayer().kick(Component.text("" + ChatColor.GOLD + ChatColor.BOLD + "Pixel" + ChatColor.WHITE + "\nВы были исключены т.к. не успели написать верный пароль!"));
                    }
                }
            }, 20L*15L);
        } catch (ErrorResponseException ex) {
            e.getPlayer().setWhitelisted(false);
            e.getPlayer().kick(Component.text("" + ChatColor.GOLD + ChatColor.BOLD + "Pixel" + ChatColor.YELLOW + "\nВы были исключены т.к. вышли с дискорд сервера!\nПожалуйста заполните заявку заного!\nhttps://discord.gg/d9eAkmH7zg"));
        }

    }

    public static class LoginCommand implements CommandExecutor {

        @Override
        public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
            if (sender instanceof Player) {
                if (args.length > 0) {
                    if (Server.playerdata.get(sender.getName()).get("password").equals(args[0])) {
                        unloggedPlayers.remove(sender);
                        sender.sendMessage(Variables.maincolor + "[I] " + ChatColor.WHITE + "Info: Успешный вход! Приятной игры!");
                    } else {
                        sender.sendMessage(Variables.maincolor + "[I] " + ChatColor.WHITE + "Info: Неверный пароль!");
                    }
                } else {
                    sender.sendMessage(Variables.maincolor + "[I] " + ChatColor.WHITE + "Info: Неправильное использование!. Синтаксис: /l <пароль>");
                }
            }
            return false;
        }
    }
}
