package vanden.server;

import me.clip.placeholderapi.PlaceholderAPI;
import net.md_5.bungee.api.ChatMessageType;
import net.md_5.bungee.api.chat.TextComponent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Block;
import org.bukkit.block.Campfire;
import org.bukkit.entity.Player;
import org.bukkit.inventory.meta.Damageable;

import java.util.ArrayList;
import java.util.Set;

public class FeautersLogic {
    static Set<Material> Nullset = null;
    public static ArrayList<Material> instruments = new ArrayList<Material>();

    public static void feauters() {
        String completestring = "";
        for (Player p : Bukkit.getOnlinePlayers()) {
            if (p.getInventory().getItemInMainHand().getType() == Material.CLOCK) {
                completestring = PlaceholderAPI.setPlaceholders(p, Variables.secondcolor + "⌚ " + Variables.maincolor + "| " + Variables.secondcolor + "%world_timein12_world%");
            } else
            if (p.getInventory().getItemInMainHand().getType() == Material.SPYGLASS) {
                Block Target = p.getTargetBlock(Nullset, 200);
                if (Target.getType() != Material.FURNACE) {
                    int x = p.getLocation().getBlockX();
                    int y2 = p.getLocation().getBlockY();
                    int z = p.getLocation().getBlockZ();
                    int x1 = Target.getX();
                    int y1 = Target.getY();
                    int z1 = Target.getZ();

                    double distance = Math.round(Math.sqrt(Math.pow(x - x1, 2) + Math.pow(y2 - y1, 2) + Math.pow(z - z1, 2)));
                    completestring = "~" + String.valueOf(distance) + "m";
                }
            } else
            if (p.getInventory().getItemInMainHand().getType() == Material.COMPASS) {
                completestring = Variables.secondcolor + "➢" + Variables.maincolor + " | " + Variables.secondcolor + p.getLocation().getBlockX() + " " + p.getLocation().getBlockY() + " " + p.getLocation().getBlockZ();
            } else
            if (p.getTargetBlock(Nullset, 5).getType().equals(Material.CAMPFIRE)) {
                Campfire campfire = (Campfire) p.getTargetBlock(Nullset, 5).getState();
                String lost1= String.valueOf(30 - Math.round(campfire.getCookTime(0) / 20f));
                String lost2= String.valueOf(30 - Math.round(campfire.getCookTime(1) / 20f));
                String lost3= String.valueOf(30 - Math.round(campfire.getCookTime(2) / 20f));
                String lost4= String.valueOf(30 - Math.round(campfire.getCookTime(3) / 20f));
                completestring = Variables.secondcolor + "\uD83D\uDD25" + Variables.maincolor + " | " + Variables.secondcolor + lost1 + " / " + lost2 + " / " + lost3 + " / " + lost4;
            } else
            if (instruments.contains(p.getInventory().getItemInMainHand().getType())) {
                Damageable item = (Damageable) p.getInventory().getItemInMainHand().getItemMeta();
                completestring = Variables.secondcolor + "⌘" + Variables.maincolor + " | " + Variables.secondcolor + String.valueOf(p.getInventory().getItemInMainHand().getType().getMaxDurability() - item.getDamage()) + Variables.secondcolor +" / " + Variables.secondcolor + String.valueOf(p.getInventory().getItemInMainHand().getType().getMaxDurability());
            }
            if (instruments.contains(p.getInventory().getItemInMainHand().getType()) && instruments.contains(p.getInventory().getItemInOffHand().getType())){
                Damageable item = (Damageable) p.getInventory().getItemInMainHand().getItemMeta();
                Damageable item2 = (Damageable) p.getInventory().getItemInOffHand().getItemMeta();
                completestring = Variables.secondcolor + String.valueOf(p.getInventory().getItemInOffHand().getType().getMaxDurability() - item.getDamage()) + Variables.secondcolor +" / " + Variables.secondcolor + String.valueOf(p.getInventory().getItemInOffHand().getType().getMaxDurability()) + Variables.maincolor + " | " + Variables.secondcolor + "⌘" + Variables.maincolor + " | " + Variables.secondcolor + String.valueOf(p.getInventory().getItemInMainHand().getType().getMaxDurability() - item.getDamage()) + Variables.secondcolor +" / " + Variables.secondcolor + String.valueOf(p.getInventory().getItemInMainHand().getType().getMaxDurability());
            }
            p.sendActionBar(TextComponent.fromLegacyText(completestring));
        }
    }

    public static void initIstruments() {
        instruments.add(Material.DIAMOND_SHOVEL);
        instruments.add(Material.GOLDEN_SHOVEL);
        instruments.add(Material.STONE_SHOVEL);
        instruments.add(Material.IRON_SHOVEL);
        instruments.add(Material.NETHERITE_SHOVEL);
        instruments.add(Material.WOODEN_SHOVEL);
        instruments.add(Material.DIAMOND_PICKAXE);
        instruments.add(Material.GOLDEN_PICKAXE);
        instruments.add(Material.IRON_PICKAXE);
        instruments.add(Material.NETHERITE_PICKAXE);
        instruments.add(Material.STONE_PICKAXE);
        instruments.add(Material.WOODEN_PICKAXE);
        instruments.add(Material.DIAMOND_AXE);
        instruments.add(Material.GOLDEN_AXE);
        instruments.add(Material.IRON_AXE);
        instruments.add(Material.NETHERITE_AXE);
        instruments.add(Material.STONE_AXE);
        instruments.add(Material.WOODEN_PICKAXE);
        instruments.add(Material.DIAMOND_HOE);
        instruments.add(Material.GOLDEN_HOE);
        instruments.add(Material.IRON_HOE);
        instruments.add(Material.NETHERITE_HOE);
        instruments.add(Material.STONE_HOE);
        instruments.add(Material.WOODEN_HOE);
        instruments.add(Material.SHEARS);
        instruments.add(Material.FLINT_AND_STEEL);
        instruments.add(Material.FISHING_ROD);
        instruments.add(Material.DIAMOND_SWORD);
        instruments.add(Material.GOLDEN_SWORD);
        instruments.add(Material.STONE_SWORD);
        instruments.add(Material.IRON_SWORD);
        instruments.add(Material.NETHERITE_SWORD);
        instruments.add(Material.WOODEN_SWORD);
        instruments.add(Material.BOW);
        instruments.add(Material.TRIDENT);
        instruments.add(Material.CROSSBOW);
    }
}
