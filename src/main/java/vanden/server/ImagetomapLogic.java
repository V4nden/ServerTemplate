package vanden.server;

import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.MapMeta;
import org.bukkit.map.MapCanvas;
import org.bukkit.map.MapPalette;
import org.bukkit.map.MapRenderer;
import org.bukkit.map.MapView;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.net.MalformedURLException;
import java.net.URL;

public class ImagetomapLogic extends MapRenderer{
    public static String urlstring;

    public ImagetomapLogic(String a) {
        urlstring = a;
    }

    @Override
    public void render(@NotNull MapView map, @NotNull MapCanvas canvas, @NotNull Player player) {
        try {
            URL url = new URL(urlstring);
            BufferedImage image = ImageIO.read(url);
            canvas.drawImage(0, 0, MapPalette.resizeImage(image));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

}
