package vanden.server.commands;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import vanden.server.events.DiscordEvents;

import java.util.UUID;

public class GetMapCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            if (args.length > 0) {
                if (DiscordEvents.availableMaps.containsKey(UUID.fromString(args[0]))) {
                    ((Player) sender).getInventory().addItem(DiscordEvents.availableMaps.get(UUID.fromString(args[0])));
                }
            }
        }
        return false;
    }
}
