package vanden.server.commands;

import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import vanden.server.Variables;

import java.util.ArrayList;
import java.util.Random;

public class RPCommands {
    public static class DiceCommand implements CommandExecutor {
        @Override
        public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
            Player p1 = (Player) sender;
            ArrayList<String> dices = new ArrayList<String>();
            dices.add("⚅");
            dices.add("⚄");
            dices.add("⚃");
            dices.add("⚂");
            dices.add("⚁");
            dices.add("⚀");
            Random random = new Random();
            int a = random.nextInt(0, 5);
            int b = random.nextInt(0, 5);
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (Math.sqrt(Math.pow((p.getLocation().getX() - p1.getLocation().getX()), 2) + Math.pow((p.getLocation().getZ() - p1.getLocation().getZ()), 2)) <= 100) {
                    p.sendMessage(Variables.maincolor + "[R] " + ChatColor.WHITE + p1.getName() + " бросил кости и выбил " + ChatColor.YELLOW + dices.get(a) + " " + ChatColor.YELLOW + dices.get(b));
                }
            }
            return false;
        }
    }
    public static class RandomCommand implements CommandExecutor {
        @Override
        public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
            Player p1 = (Player) sender;
            Random random = new Random();
            for (Player p : Bukkit.getOnlinePlayers()) {
                if (Math.sqrt(Math.pow((p.getLocation().getX() - p1.getLocation().getX()), 2) + Math.pow((p.getLocation().getZ() - p1.getLocation().getZ()), 2)) <= 100) {
                    p.sendMessage(Variables.maincolor + "[R] " + ChatColor.WHITE + p1.getName() + " крутит рулетку и выбивает " + ChatColor.YELLOW + random.nextInt(0, 100));
                }
            }
            return false;
        }
    }
    public static class MeCommand implements CommandExecutor {
        @Override
        public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
            if (args.length > 0) {
                Player p1 = (Player) sender;
                String completestring = "";
                for (String strin : args) {
                    completestring += strin + " ";
                }

                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (Math.sqrt(Math.pow((p.getLocation().getX() - p1.getLocation().getX()), 2) + Math.pow((p.getLocation().getZ() - p1.getLocation().getZ()), 2)) <= 100) {
                        p.sendMessage(Variables.maincolor + "[R] " + ChatColor.WHITE + p1.getName() + completestring);
                    }
                }
            } else {
                sender.sendMessage(Variables.maincolor + "[I] " + org.bukkit.ChatColor.WHITE + "Info: Введи действие!");
            }
            return false;
        }
    }
}
