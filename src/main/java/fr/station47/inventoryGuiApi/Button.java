package fr.station47.inventoryGuiApi;

import org.bukkit.Material;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.List;

public class Button{

    public static ItemStack n(Material mat, String name, List<String> lore){
        ItemStack b = new ItemStack(mat,1);
        ItemMeta meta = b.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        b.setItemMeta(meta);
        return b;
    }


}
