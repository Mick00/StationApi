package fr.station47.stationAPI.api.Shop;

import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.math.BigDecimal;
import java.math.RoundingMode;

public class ShopUtils {


    public static ItemStack backButton(){
        ItemStack itemStack = new ItemStack(Material.REDSTONE_BLOCK);
        ItemMeta meta = itemStack.getItemMeta();
        meta.setDisplayName(ChatColor.RED+"Retour");
        itemStack.setItemMeta(meta);
        return itemStack;
    }

    public static double round(double value) {
        BigDecimal bd = new BigDecimal(value);
        bd = bd.setScale(2, RoundingMode.HALF_UP);
        return bd.doubleValue();
    }
}
