package fr.station47.stationAPI.api.config;

import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

public interface ConfigObjectInterface {

    void applyTo(YamlConfiguration config);

    boolean loadFrom(ConfigurationSection config);
}
