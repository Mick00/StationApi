package fr.station47.stationAPI.api.destinations;

import fr.station47.stationAPI.api.Shop.Shop;
import fr.station47.stationAPI.api.Shop.ShopItem;
import fr.station47.stationAPI.api.StationAPI;
import fr.station47.theme.Theme;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.plugin.Plugin;
import java.io.*;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class DestinationsManager {

    private HashMap<String, Destination> destinations;
    private HashMap<String, Merchants> merchants;
    private YamlConfiguration config;
    private File save;
    private static DestinationsManager instance;

    public DestinationsManager(Plugin pl){

        instance = this;
        save = new File(pl.getDataFolder(), "destinations.yml");
        try {
            if (!save.exists()) {
                save.createNewFile();
                destinations = new HashMap<>();
            } else {
                loadDestinations();
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        createShop();
        merchants = new HashMap<>();

    }

    public NPC findNearestMerchant(Location location, String shopName){
        Merchants merchGuild = merchants.getOrDefault(shopName.toLowerCase(), null);
        return merchGuild==null?null:merchGuild.findNearest(location);
    }

    public boolean npcShopExists(String name){
        return merchants.containsKey(name.toLowerCase());
    }

    public void registerNpcShop(String name, NPC npc){
        Merchants merchant = merchants.getOrDefault(name.toLowerCase(),null);
        if (merchant != null){
            merchant.addNpc(npc);
        } else {
            merchant = new Merchants();
            merchant.addNpc(npc);
            merchants.put(name.toLowerCase(),merchant);
        }
    }

    public void unregisterNpcShop(String name, NPC npc){
        Merchants merchant = merchants.getOrDefault(name.toLowerCase(),null);
        if (merchant!=null){
            merchant.removeNpc(npc);
        }
    }

    private void loadDestinations() throws IOException{
        config = YamlConfiguration.loadConfiguration(save);
        destinations = new HashMap<>();
        List<String> des = config.getStringList("destinations");
        for (String s : des) {
            add(Destination.fromString(s));
        }
    }

    public void saveDestinations(){
        List<String> des = new ArrayList<>();
        for (Destination d: destinations.values()){
            des.add(d.toString());
        }
        config.set("destinations",des);
        try {
            config.save(save);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public boolean teleportPlayer(String destination, Player player){
        if (destinations.containsKey(destination.toLowerCase())) {
            Destination d = destinations.get(destination.toLowerCase());
            player.teleport(d.getLocation());
            player.sendMessage(Theme.getTheme().getChatPrefix() + "Vous avez été téléporté vers " +d.getName());
            return true;
        }
        return false;
    }

    public void add(Destination d){
        destinations.put(d.getName().toLowerCase(), d);
    }

    public void remove(String name){
        destinations.remove(name);
    }

    public boolean destinationExists(String name){
        return destinations.containsKey(name.toLowerCase());
    }

    public Destination getDestination(String name){
        String lowercaseName = name.toLowerCase();
        if (destinationExists(lowercaseName)){
            return destinations.get(lowercaseName);
        }
        return null;
    }

    public HashMap<String, Destination> getDestinations(){
        return destinations;
    }

    public HashMap<String, Merchants> getMerchants() {
        return merchants;
    }

    public void createShop(){
        List<ShopItem> items = new ArrayList<>();
        for (Destination destination: destinations.values()){
            if (!destination.isHidden() && destination.getPrice() > 0) {
                InteractableTpScroll interactable = new InteractableTpScroll(destination);
                StationAPI.customItemHandler.registerInteractable(interactable);
                items.add(new ShopItem(interactable.getItem(), destination.getPrice(), -1));
            }
        }
        StationAPI.shopManager.registerShop("libraire", new Shop("Libraire", items, true));
    }

    public static DestinationsManager getInstance(){
        return instance;
    }
}
