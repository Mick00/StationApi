package fr.station47.stationAPI.api;

import com.sun.org.apache.xpath.internal.operations.Mod;
import fr.station47.stationAPI.api.Shop.ShopManager;
import fr.station47.stationAPI.api.config.ConfigHelper;
import fr.station47.stationAPI.api.customItem.CustomItemCommand;
import fr.station47.stationAPI.api.customItem.CustomItemHandler;
import fr.station47.stationAPI.api.destinations.DestinationsCommand;
import fr.station47.stationAPI.api.destinations.DestinationsManager;
import fr.station47.stationAPI.api.upgrade.UpgradeHandler;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class StationAPI extends JavaPlugin {

    private YamlConfiguration config;
    public static ConfigHelper configs;
    public static Economy econ;
    public static UpgradeHandler upgradeHandler = null;
    public static DestinationsManager destinationsManager = null;
    public static CustomItemHandler customItemHandler = null;
    public static ShopManager shopManager = null;
    public static StationAPI instance;
    public static List<Module> modules;

    public void onEnable(){
        instance = this;
        setUpConfig();
        setupEconomy();
        modules = new ArrayList<>();
        new SAPICommand();
        new CustomItemCommand("citem");
        configs = new ConfigHelper(this);
        if (config.getBoolean("modules.customItem")){
            customItemHandler = new CustomItemHandler();
            this.getServer().getPluginManager().registerEvents(customItemHandler,this);
        }

        if (config.getBoolean("modules.shop")){
            shopManager = new ShopManager();
            this.getCommand("shop").setExecutor(shopManager);
        }

        if (config.getBoolean("modules.upgrade")){
            upgradeHandler = new UpgradeHandler();
            this.getServer().getPluginManager().registerEvents(upgradeHandler,this);
        }
        if (config.getBoolean("modules.destination")){
            destinationsManager = new DestinationsManager(this);
            DestinationsCommand dcmd = new DestinationsCommand("destination",destinationsManager);
        }


    }

    public static void reload(){
        modules.forEach(Module::reload);
    }

    public static void addModule(Module module){
        modules.add(module);
    }

    private void setUpConfig(){
        File configFile = new File(getDataFolder(), "config.yml");
        if (!configFile.exists()) {
            saveResource("config.yml", false);
        }

        config = new YamlConfiguration();
        try {
            config.load(configFile);
        } catch (InvalidConfigurationException ex){
            Bukkit.getServer().getPluginManager().disablePlugin(this);
            Bukkit.getLogger().severe("Erreur dans la configuration du fichier de config!");
            ex.printStackTrace();

        } catch (IOException ex){
            Bukkit.getServer().getPluginManager().disablePlugin(this);
            Bukkit.getLogger().severe("Erreur IOException!");
            ex.printStackTrace();
        }
    }

    private boolean setupEconomy() {
        if (Bukkit.getPluginManager().getPlugin("Vault") == null) {
            return false;
        }

        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    public static boolean isUpgradeHandlerActive(){
        return upgradeHandler!=null;
    }

    public static boolean isDestinationManagerActive(){
        return destinationsManager!=null;
    }

    public static boolean isCustomItemHandlerActive(){
        return customItemHandler!=null;
    }

    public static boolean isShopManagerActive(){
        return shopManager!=null;
    }

}
