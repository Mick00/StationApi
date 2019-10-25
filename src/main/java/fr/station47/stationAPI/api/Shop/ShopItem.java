package fr.station47.stationAPI.api.Shop;

import fr.station47.inventoryGuiApi.inventoryAction.InventoryAction;
import fr.station47.inventoryGuiApi.inventoryAction.InventoryItem;
import fr.station47.theme.Theme;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ShopItem {

    private String name;
    private List<String> desc = new ArrayList<>();
    private Material guiMat = Material.DIRT;
    private Buyable buyableItem = null;
    private Sellable sellableItem = null;
    private boolean quantitySelector = false;

    public ShopItem(ItemStack itemStack, double buyPrice, double sellPrice){
        buyableItem = new BuyableItem(itemStack, buyPrice);
        sellableItem = new SellableItem(itemStack,sellPrice);
        guiMat = itemStack.getType();
    }

    public ShopItem(Material material, double buyPrice, double sellPrice){
        buyableItem = new BuyableItem(new ItemStack(material), buyPrice);
        sellableItem = new SellableItem(new ItemStack(material),sellPrice);
        guiMat = material;
    }

    public ShopItem(String name, List<String> description, Buyable buyable){
        this.name = name;
        this.desc = description;
        this.buyableItem = buyable;
    }

    public ShopItem(Buyable buyable){
        this.buyableItem = buyable;
    }

    public ShopItem(Sellable sellable){
        this.sellableItem = sellable;
    }

    public boolean buy(Player player, int qt){
        return Objects.nonNull(buyableItem) && buyableItem.buy(player, qt);
    }

    public boolean sell(Player player,int qt){
       return Objects.nonNull(sellableItem) && sellableItem.sell(player, qt);

    }

    public boolean canSell(){ return Objects.nonNull(sellableItem) && sellableItem.canSell(); }

    public boolean canSell(Player player){
        return Objects.nonNull(sellableItem) && sellableItem.canSell(player);
    }

    public boolean canBuy(){
        return Objects.nonNull(buyableItem) && buyableItem.canBuy();
    }

    public boolean canBuy(Player player){
        return Objects.nonNull(buyableItem) && buyableItem.canBuy(player);
    }

    public Sellable getSellable() {
        return sellableItem;
    }

    public Buyable getBuyable() {
        return buyableItem;
    }

    public void setQuantitySelector(boolean value){
        this.quantitySelector = value;
    }

    public boolean needOpenSelector(){
        return quantitySelector;
    }

    public InventoryItem getInventoryItem(){
        return getInventoryItem(1,true);
    }

    public InventoryItem getInventoryItem(int quantity){
        return getInventoryItem(quantity, false);
    }

    public InventoryItem getInventoryItem(int quantity, boolean quantitySelector){
        InventoryItem invItem = new InventoryItem(0, name, guiMat);
        invItem.setAmount(quantity);
        desc.forEach(invItem::addLore);
        if (canSell()) {
            invItem.addLore("Vendre: " + sellableItem.getUnitPrice()*quantity);
            sellableItem.getReq().stream()
                    .filter(r->r.getLabel()!=null)
                    .map(Requirement::getLabel)
                    .filter(l->!l.equals("none"))
                    .forEach(invItem::addLore);
        }
        if (canBuy()){
            invItem.addLore("Acheter: " + buyableItem.getUnitPrice()*quantity);
            buyableItem.getReq().stream()
                    .filter(r->r.getLabel()!=null)
                    .map(Requirement::getLabel)
                    .filter(l->!l.equals("none"))
                    .forEach(invItem::addLore);
        }
        invItem.setConfirm(false);
        invItem.setAction(e -> {
            e.setCancelled(true);
            Player player = ((Player) e.getWhoClicked());
            if (this.quantitySelector && quantitySelector){
                new QuantitySelector(this,player,null);
            } else {
                if (canBuy() && e.isLeftClick()) {
                    buy(player, quantity);
                } else if (canSell() && e.isRightClick()){
                    sell(player,quantity);
                }
            }
        });
        return invItem;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDesc(List<String> desc) {
        this.desc = desc;
    }

    public void setGuiMat(Material guiMat) {
        this.guiMat = guiMat;
    }

}
