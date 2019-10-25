package fr.station47.stationAPI.api.Shop;

import fr.station47.stationAPI.api.StationAPI;
import org.bukkit.Material;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

public class DefaultPrice {
    private YamlConfiguration config;

    private HashMap<Material, Double> defaultPrices;

     public DefaultPrice()  {
        try {
            config = new YamlConfiguration();
            defaultPrices = new HashMap<>();
            File configFile = new File(StationAPI.instance.getDataFolder(),"defaultPrices.yml");
            if (!configFile.exists()){
                configFile.createNewFile();
                for (Material material: Material.values()){
                    config.set("prices."+material.toString(),-1);
                }
                config.save(configFile);

            }
            try {
                config.load(configFile);
                for (Material material: Material.values()){
                    defaultPrices.put(material, config.getDouble("prices."+material.toString()));
                }
            } catch (InvalidConfigurationException e) {
                e.printStackTrace();
            }

        } catch (IOException e){
            e.printStackTrace();
        }

    }

    public double getPrice(Material mat){
        return defaultPrices.getOrDefault(mat,Double.MAX_VALUE);
    }
}
