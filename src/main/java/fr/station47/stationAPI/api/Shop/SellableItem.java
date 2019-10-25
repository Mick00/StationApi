package fr.station47.stationAPI.api.Shop;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class SellableItem extends Sellable {

    private ItemStack item;

    public SellableItem(ItemStack itemStack, double price){
        super(price);
        this.item = itemStack;
        this.item.setAmount(1);
    }

    @Override
    public boolean sell(Player player, int qt) {
        ItemStack sold = item.clone();
        sold.setAmount(qt);
        if (canSell() && player.getInventory().containsAtLeast(item,qt)){
            player.getInventory().removeItem(sold);
            pay(player,qt);
            return true;
        }
        return false;
    }

    @Override
    protected boolean sale(Player p, int quantity) {
        ItemStack sold = item.clone();
        sold.setAmount(quantity);
        if (p.getInventory().containsAtLeast(item,quantity)){
            p.getInventory().removeItem(sold);
            return true;
        }
        return false;
    }

    public ItemStack getItem() {
        return item;
    }
}
