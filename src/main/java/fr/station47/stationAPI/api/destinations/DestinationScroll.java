package fr.station47.stationAPI.api.destinations;


import fr.station47.stationAPI.api.Utils;
import fr.station47.stationAPI.api.customItem.CustomItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class DestinationScroll extends CustomItem {

    public DestinationScroll(Destination destination){
        super("scroll");
        item = new ItemStack(Material.PAPER);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(ChatColor.YELLOW+"Parchemin de téléportation");
        meta.setLore(Utils.whiteLore("Téléporation vers: "+ destination.getName()));
        item.setItemMeta(meta);
    }
}
