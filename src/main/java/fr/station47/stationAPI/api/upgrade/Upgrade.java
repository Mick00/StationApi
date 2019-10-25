package fr.station47.stationAPI.api.upgrade;


import fr.station47.stationAPI.api.Utils;
import fr.station47.stationAPI.api.config.ConfigObject;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import java.util.List;

public abstract class Upgrade {

    private String slotPlaceholder = ChatColor.GRAY+"( vide )";
    protected final String name;
    protected String displayName;
    protected List<String> description;
    private ConfigObject configObject = null;

    public Upgrade(String name, String displayName, List<String> description){
        this.name = name;
        configObject = new ConfigObject();
        configObject.put("upgrades."+this.name+".displayName",displayName);
        configObject.put("upgrades."+this.name+".description",description);
        loadOrDefault();
    }

    public ItemStack upgrade(ItemStack target, int level){
        ItemStack upgraded = modify(target,level);
        ItemMeta meta = upgraded.getItemMeta();
        List<String> lore = meta.getLore();
        for (int i = 0; i < lore.size(); i++){
            if (lore.get(i).equals(slotPlaceholder)){
                lore.set(i,displayName+" "+ Utils.intToRom(level));
                break;
            }
        }
        meta.setLore(lore);
        upgraded.setItemMeta(meta);
        return upgraded;
    }

    protected abstract ItemStack modify(ItemStack target, int level);

    public abstract boolean applicableOn(ItemStack target);

    public String getName(){
        return name;
    }

    public String getDisplayName(){
        return displayName;
    }

    public List<String> getDescription(){
        return description;
    }

    public void loadOrDefault(){
        configObject.loadFrom(UpgradeHandler.getConfig());
        this.displayName = ChatColor.translateAlternateColorCodes('&', (String)configObject.getMap().get("upgrades."+this.name+".displayName"));
        this.description = (List<String>)configObject.getMap().get("upgrades."+this.name+".description");
    }

    public ItemStack getItem(int level){
        ItemStack item = new ItemStack(Material.EMERALD);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(displayName+" "+Utils.intToRom(level));
        meta.addEnchant(Enchantment.DURABILITY,10,true);
        item.setItemMeta(meta);
        return item;
    }

}
