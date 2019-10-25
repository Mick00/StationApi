package fr.station47.stationAPI.api.config;

import fr.station47.theme.Theme;
import org.bukkit.Bukkit;
import org.bukkit.configuration.ConfigurationSection;
import org.bukkit.configuration.file.YamlConfiguration;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

public class ConfigObject implements ConfigObjectInterface{

    private HashMap<String, Object> values;
    private String pathprefix = "";

    public ConfigObject(String prefix){
        values = new HashMap<>();
        pathprefix = prefix;
    }

    public ConfigObject(HashMap<String, Object> defaultValues){
        this.values = defaultValues;
    }

    public ConfigObject(){
        values = new HashMap<>();
    }

    public void put(String key, Object value){
        values.put(getFullPath(key),value);
    }

    /**
     * Applies the value to configuration. Regardless of already preset or not.
     * @param config
     */
    @Override
    public void applyTo(YamlConfiguration config) {
        for (Map.Entry<String, Object> value: values.entrySet()){
            config.set(value.getKey(),value.getValue());
        }
    }

    /**
     * Loads the configObject values from the config specified.
     * <p>It will automatically insert new key which have not been found into the configuration section</p>
     * @param config The Configuration section to load from
     * @return returns true if the config has been modified
     */
    @Override
    public boolean loadFrom(ConfigurationSection config) {
        boolean modified = false;
        for (String path: values.keySet()){
            if (config.isSet(path)){
                Object obj = config.get(path);
                if (obj instanceof String){
                    values.put(path, Theme.parse(String.valueOf(obj)));
                } else {
                    values.put(path, config.get(path));
                }
            } else {
                modified = true;
                Bukkit.getLogger().info("Adding: "+path+":"+values.get(path));
                config.set(path, values.get(path));
            }
        }
        return modified;
    }

    public HashMap<String, Object> getMap() {
        return values;
    }

    public Object get(String key){
        return values.get(getFullPath(key));
    }

    /**
     * Fetch and cast the key value
     * @param key       Key associated to the value
     * @param clazz     Class type of the value
     * @param <T>       Class
     * @return value of type clazz
     */
    public <T> T get(String key, Class<T> clazz){
        Object obj = values.get(getFullPath(key));
        return clazz.isInstance(obj)? clazz.cast(obj) : null;
    }

    public <T> List<T> getList(String key, Class<T> clazz){
        Object obj = values.get(getFullPath(key));
        if (obj instanceof List){
            List<?> list = (List<?>) obj;
            if (list.size() > 0){
                return list.stream().filter(clazz::isInstance).map(clazz::cast).collect(Collectors.toList());
            } else {
                return new ArrayList<T>();
            }
        }
        return null;

    }
    public int getInt(String key){
        return Integer.valueOf(values.get(getFullPath(key)).toString());
    }

    public double getDouble(String key) {return Double.valueOf(values.get(getFullPath(key)).toString());}

    public boolean getBoolean(String key) {
        return (boolean)values.get(getFullPath(key));
    }

    public List<?> getList(String key) {
        Object def = get(getFullPath(key));
        return def instanceof List ? (List<?>) def : null;
    }

    public List<String> getStringList(String key){
        List t = getList(key);
        List<String> res = new ArrayList<>();
        for (Object o: t){
            if (o instanceof String){
                res.add(Theme.parse(String.valueOf(o)));
            }
        }
        return res;
    }

    public String getString(String key){
        return String.valueOf(values.get(getFullPath(key)));
    }

    public void setPathPrefix(String prefix){
        this.pathprefix = prefix;
    }

    private String getFullPath(String path){
        return pathprefix+path;
    }
}
