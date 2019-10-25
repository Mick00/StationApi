package fr.station47.stationAPI.api.config;

import org.bukkit.Bukkit;
import org.bukkit.configuration.InvalidConfigurationException;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.plugin.Plugin;
import java.io.File;
import java.io.IOException;
import java.util.HashMap;

/**
 * Helps to manage config file
 * <p>Autoloads config file if not yet loaded</p>
 * @TODO: 10/23/2019 Create local utilities
 */
public class ConfigHelper {

    private File dataFolder;
    private HashMap<String, YamlConfiguration> configs;

    /**
     * Creates a config helper instance
     * @param plugin the plugin using the configs
     */
    public ConfigHelper(Plugin plugin){
        configs = new HashMap<>(5);
        this.dataFolder = plugin.getDataFolder();
        if (!dataFolder.exists()){
            dataFolder.mkdir();
        }
    }

    /**
     * Returns the YamlConfig
     * @param config
     * @return
     */
    public YamlConfiguration getConfig(String config){
        return configs.getOrDefault(config,null);
    }

    public boolean applyModification(String config, ConfigObjectInterface configchanges){
        YamlConfiguration yamlConfiguration = configs.getOrDefault(config, null);
        if (yamlConfiguration != null){
            configchanges.applyTo(yamlConfiguration);
            return true;
        } else {
            return false;
        }
    }

    /**
     * <p>
     *     Will load the configObject from the specified config file.
     * It will write the default value in the config if the key is not found.
     * Else it will replace it in the ConfigObject instance.
     * </p>
     * @param config Name of the config file
     * @param configObject The configObject containing the keys to load
     * @return A yaml
     */
    public YamlConfiguration loadOrDefault(String config, ConfigObject configObject){
        return loadOrDefault(config,configObject,false);
    }

    /**
     * <p>
     *     Will load the configObject from the specified config file.
     * It will write the default value in the config if the key is not found.
     * Else it will replace it in the ConfigObject instance.
     * </p>
     * @param config Name of the config file
     * @param configObject The configObject containing the keys to load
     * @param forceRead Force to read the file even if is already loaded
     * @return A yaml
     */
    public YamlConfiguration loadOrDefault(String config, ConfigObjectInterface configObject, boolean forceRead){
        if (forceRead || !configs.containsKey(config)) {
            File configFile = new File(dataFolder, config + ".yml");
            if (!configFile.exists()) {
                Bukkit.getLogger().info(config + ".yml does not exists, creating it...");
                YamlConfiguration virgin = new YamlConfiguration();
                configObject.applyTo(virgin);
                configs.put(config, virgin);
                save(config);
                return virgin;
            } else {
                YamlConfiguration yamlConfiguration = new YamlConfiguration();
                try {
                    yamlConfiguration.load(configFile);
                    if (configObject.loadFrom(yamlConfiguration)) {
                        yamlConfiguration.save(configFile);
                    }
                } catch (IOException | InvalidConfigurationException e) {
                    e.printStackTrace();
                }
                configs.put(config, yamlConfiguration);
                return yamlConfiguration;
            }
        } else {
            if(configObject.loadFrom(configs.get(config))){
                save(config);
            }
            return configs.get(config);
        }
    }

    /**
     * Saves the config file
     * @param config
     */
    public void save(String config){
        File saveFile = new File(dataFolder,config+".yml");
        try {
            configs.get(config).save(saveFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void saveAll(){
        configs.forEach((k,e)->save(k));
    }
}
