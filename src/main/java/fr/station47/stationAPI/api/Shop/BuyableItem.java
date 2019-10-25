package fr.station47.stationAPI.api.Shop;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class BuyableItem extends Buyable{

    private ItemStack item;

    public BuyableItem(ItemStack item, double price){
        super(price);
        this.item = item;
    }


    public ItemStack getItem() {
        return item;
    }

    @Override
    protected boolean purchase(Player player, int quantity) {
        ItemStack is = getItem().clone();
        is.setAmount(quantity);
        player.getInventory().addItem(is);
        return true;
    }
}
