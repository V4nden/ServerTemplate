package vanden.server;

import me.clip.placeholderapi.PlaceholderAPI;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.md_5.bungee.api.ChatColor;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;
import vanden.server.commands.GetMapCommand;
import vanden.server.commands.RPCommands;
import vanden.server.commands.StatusCommand;
import vanden.server.events.ChatEvents;
import vanden.server.events.DiscordChatEvents;
import vanden.server.commands.DiscordCommands;
import vanden.server.events.DiscordEvents;
import vanden.server.events.MotdEvents;

import java.util.HashMap;
import java.util.Map;

public final class Server extends JavaPlugin {
    public static JavaPlugin instance;
    public static JDA bot;
    public static HashMap<String, JSONObject> playerdata = new HashMap<String, JSONObject>();
    @Override
    public void onEnable() {
        instance = this;

        this.saveDefaultConfig();

        if (this.getConfig().contains("data")) {
            this.restoreData();
        }

        this.getCommand("l").setExecutor(new LoginLogic.LoginCommand());
        this.getCommand("status").setExecutor(new StatusCommand());
        this.getCommand("getmap").setExecutor(new GetMapCommand());
        this.getCommand("dice").setExecutor(new RPCommands.DiceCommand());
        this.getCommand("random").setExecutor(new RPCommands.RandomCommand());
        this.getCommand("me").setExecutor(new RPCommands.MeCommand());

        this.getServer().getPluginManager().registerEvents(new ChatEvents(), this);
        this.getServer().getPluginManager().registerEvents(new DiscordChatEvents(), this);
        this.getServer().getPluginManager().registerEvents(new MotdEvents(), this);
        this.getServer().getPluginManager().registerEvents(new LoginLogic(), this);

        bot = JDABuilder.createLight("CODE")
                .addEventListeners(new DiscordEvents())
                .addEventListeners(new TicketLogic())
                .addEventListeners(new DiscordCommands())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT, GatewayIntent.GUILD_MEMBERS, GatewayIntent.DIRECT_MESSAGES, GatewayIntent.MESSAGE_CONTENT)
                .build();

        Bukkit.getServer().getScheduler().scheduleSyncRepeatingTask(this, new Runnable() {
            @Override
            public void run() {
                for (Player p : Bukkit.getOnlinePlayers()) {
                    if (LoginLogic.unloggedPlayers.contains(p)) {
                        p.teleport(p.getLocation());
                    }
                    p.setPlayerListHeader(" \n\n\n\n\n     \uE002     " + "\n" +
                    Variables.maincolor + "TPS: " + ChatColor.WHITE + PlaceholderAPI.setPlaceholders(p,"%server_tps%") +
                    Variables.maincolor + " Онлайн: " + ChatColor.WHITE + Bukkit.getOnlinePlayers().size());
                }
                FeautersLogic.feauters();
            }
        }, 0L, 1L);

        FeautersLogic.initIstruments();
    }

    @Override
    public void onDisable() {
        bot.shutdownNow();
        bot = null;
        if (!playerdata.isEmpty()) {
            this.saveData();
        }
    }

    public void saveData() {
        for (Map.Entry<String, JSONObject> entry : playerdata.entrySet()) {
            this.getConfig().set("data." + entry.getKey(), entry.getValue().toJSONString());
        }
        this.saveConfig();
    }

    public void restoreData() {
        JSONParser parser = new JSONParser();
        this.getConfig().getConfigurationSection("data").getKeys(false).forEach(key -> {
            JSONObject data = null;
            try {
                data = (JSONObject) parser.parse((String) this.getConfig().get("data." + key));
                System.out.println(data);
            } catch (ParseException e) {
                throw new RuntimeException(e);
            }
            playerdata.put(key, data);
            System.out.println(playerdata);
        });
    }
}
