package fr.station47.stationAPI.api.Shop;

import fr.station47.inventoryGuiApi.inventoryAction.InventoryItem;
import fr.station47.stationAPI.api.StationAPI;
import fr.station47.stationAPI.api.Utils;
import fr.station47.stationAPI.api.gui.GUI;
import fr.station47.inventoryGuiApi.InventoryBuilder;
import org.bukkit.entity.Player;

import java.util.List;

public class Shop implements GUI {
    private InventoryBuilder inventoryBuilder;

    public Shop(String shopname, List<ShopItem> items, boolean forceSelectQuantity){
        inventoryBuilder = new InventoryBuilder(27, shopname, StationAPI.instance)
                .listenTo(true);
        for (int i = 0; i<items.size(); i++){
            ShopItem si = items.get(i);
            if (forceSelectQuantity){
                si.setQuantitySelector(true);
            }
            InventoryItem item = si.getInventoryItem().setSlot(i);
            inventoryBuilder.setInventoryItem(item);
        }
    }

    public void open(Player player){
        player.openInventory(inventoryBuilder.build());
    }
}
