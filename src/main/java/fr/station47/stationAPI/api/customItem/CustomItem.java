package fr.station47.stationAPI.api.customItem;

import fr.station47.stationAPI.api.StationAPI;
import org.bukkit.inventory.ItemStack;

public abstract class CustomItem {
    private String name;
    protected ItemStack item;

    /**
     * Creates a custom item and registers it in the CustomItemHandler
     * @param name
     */
    public CustomItem(String name){
        this.name = name;
        if (StationAPI.isCustomItemHandlerActive()){
            StationAPI.customItemHandler.registerNewItem(this);
        }
    }


    /**
     *
     * @return the name of the item
     */
    public String getName() {
        return name;
    }

    /**
     * Returns the itemStack
     * @return the ItemStack
     */
    public ItemStack getItem() {
        return item;
    }
}
