package vanden.server.commands;

import org.bukkit.NamespacedKey;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.persistence.PersistentDataType;
import org.checkerframework.checker.units.qual.N;
import org.jetbrains.annotations.NotNull;
import vanden.server.Server;

public class StatusCommand implements CommandExecutor {
    @Override
    public boolean onCommand(@NotNull CommandSender sender, @NotNull Command command, @NotNull String label, @NotNull String[] args) {
        if (sender instanceof Player) {
            String completestring = "";
            for (String arg : args) {
                completestring += arg + " ";
            }
            ((Player) sender).getPersistentDataContainer().set(new NamespacedKey(Server.instance, "status"), PersistentDataType.STRING, completestring);
        }
        return false;
    }
}
