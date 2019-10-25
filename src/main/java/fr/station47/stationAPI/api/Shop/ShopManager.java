package fr.station47.stationAPI.api.Shop;

import fr.station47.stationAPI.api.StationAPI;
import fr.station47.stationAPI.api.gui.GUI;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.trait.TraitInfo;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import java.util.HashMap;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;

public class ShopManager implements CommandExecutor {
    private DefaultPrice defaultPrice;

    private final HashMap<String, List<Material>> itemByShops;
    private ConcurrentHashMap<String, GUI> shops;
    public static ShopManager instance;

    public ShopManager(){
        instance = this;
        defaultPrice = new DefaultPrice();
        itemByShops = new HashMap<>();
        shops = new ConcurrentHashMap<>();

        if (Bukkit.getServer().getPluginManager().getPlugin("Citizens")!=null) {
            TraitInfo traitInfo = TraitInfo.create(MerchantTrait.class);
            CitizensAPI.getTraitFactory().registerTrait(traitInfo);
            Bukkit.getServer().getPluginManager().registerEvents(new NpcClicListener(), StationAPI.instance);
        }
    }

  /*  public void recalculate(){
        for (Map.Entry<String, List<Material>> shop:itemByShops.entrySet()){
            List<ShopItem> items = new ArrayList<>();
            for (Material mat: shop.getValue()){
                items.add(getShopItem(mat));
            }
            shops.put(shop.getKey(),new Shop(shop.getKey(),items));
        }
    }

    public ShopItem getShopItem(Material material){
        double price = getPrice(material);
        if (price !=-1) {
            double buyPrice = price*1.25;
            double sellPrice = price*0.7;
            if (sellPrice < defaultPrice.getPrice(material)*0.25){
                sellPrice = -1;
            }
            return new ShopItem(material, buyPrice, sellPrice);
        } else {
            return new ShopItem(material,-1,-1);
        }
    }

    public double getPrice(Material material){
        double defPrice = defaultPrice.getPrice(material);
        if (defPrice == -1){
            return -1;
        }
        int demand = data.getDemand(material);
        int production = data.getProduction(material);
        long totalPlayTime = data.getTotalPlayTime();
        if (totalPlayTime < 100){
            totalPlayTime = 100;
        }
        return  ((demand-production)*defPrice/totalPlayTime)+defPrice;
    }*/

    public GUI getShop(String shopName){
        return shops.get(shopName.toLowerCase());
    }

    public boolean shopExists(String name){
        return shops.containsKey(name.toLowerCase());
    }

    public void registerShop(String name, GUI shop){
        shops.put(name.toLowerCase(),shop);
    }

    public void unregisterShop(String name){
        shops.remove(name.toLowerCase());
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {

        return true;
    }

    public static ShopManager getInstance(){
        return instance;
    }
}
