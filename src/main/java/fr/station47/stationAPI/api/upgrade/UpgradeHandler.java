package fr.station47.stationAPI.api.upgrade;


import fr.station47.stationAPI.api.StationAPI;
import fr.station47.stationAPI.api.Utils;
import fr.station47.stationAPI.api.config.ConfigObject;
import fr.station47.stationAPI.api.customItem.Interactable;
import fr.station47.stationAPI.api.customItem.InteractAction;
import fr.station47.theme.Theme;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class UpgradeHandler implements Listener, Interactable {

    private String slotPlaceholder = ChatColor.GRAY+"( vide )";
    private HashMap<String, Upgrade> upgrades;
    private static UpgradeHandler instance;

    public UpgradeHandler(){
        instance = this;
        upgrades = new HashMap<>();
        StationAPI.customItemHandler.registerInteractable(this);
        StationAPI.configs.loadOrDefault("upgrades",new ConfigObject(new HashMap<>()));
    }

    @EventHandler
    public void onMoveItem(InventoryClickEvent event){
        if (Objects.nonNull(event.getCurrentItem()) && !event.getCurrentItem().getType().equals(Material.AIR) && Objects.nonNull(event.getCursor())){
            ItemStack toModify = event.getCurrentItem();
            ItemStack modifier = event.getCursor();
            if (modifier.getType() == Material.EMERALD && modifier.getItemMeta().getDisplayName() != null){
                String displayName = modifier.getItemMeta().getDisplayName();
                String[] splitName = displayName.split(" ");
                if (splitName.length!=2){
                    return;
                }
                Upgrade upgrade = upgrades.getOrDefault(splitName[0],null);
                int level = Utils.romanToInt(splitName[1]);
                if (upgrade != null && level > 0){
                    if (toModify.getItemMeta().getLore() != null && toModify.getItemMeta().getLore().contains(slotPlaceholder)){
                        if (upgrade.applicableOn(toModify)) {
                            ItemStack upgraded = upgrade.upgrade(toModify, level);
                            event.getWhoClicked().getInventory().addItem(upgraded);
                            event.getInventory().remove(toModify);
                            modifier.setAmount(modifier.getAmount() - 1);
                        } else {
                            event.getWhoClicked().sendMessage(Theme.getTheme().getChatPrefix()+"Cette amélioration ne peut être ajoutée a cet objet");
                        }
                    } else {
                        event.getWhoClicked().sendMessage(ChatColor.RED+"Cet objet n'a aucune emplacement pour recevoir une amélioration");
                    }
                }
            }
        }
    }

    @EventHandler
    public void printDamage(EntityDamageByEntityEvent event){
        Bukkit.broadcastMessage("damage: "+event.getDamage());
    }

    public void addUpgrade(Upgrade upgrade){
        upgrades.put(upgrade.getDisplayName(),upgrade);
        StationAPI.configs.save("upgrades");
    }

    @Override
    public boolean condition(ItemStack item, Player player) {
        if (item.getItemMeta().getDisplayName() != null){
            String[] splitname = item.getItemMeta().getDisplayName().split(" ");
            if (splitname.length==2){
                Upgrade upgrade = upgrades.getOrDefault(splitname[0],null);
                if (upgrade != null){
                    List<String> desc = upgrade.getDescription();
                    player.sendMessage(Theme.getTheme().getChatPrefix()+"Description de l'amélioration:");
                    desc.forEach(player::sendMessage);
                }
            }
        }
        return false;
    }

    @Override
    public InteractAction getAction() {
        return new InteractAction() {
            @Override
            public boolean leftClickAir(PlayerInteractEvent e) {
                return false;
            }

            @Override
            public boolean leftClickBlock(PlayerInteractEvent e) {
                return false;
            }

            @Override
            public boolean rightClickAir(PlayerInteractEvent e) {
                return false;
            }

            @Override
            public boolean rightClickBlock(PlayerInteractEvent e) {
                return false;
            }
        };
    }

    public static YamlConfiguration getConfig(){
        return StationAPI.configs.getConfig("upgrades");
    }

    public static UpgradeHandler getUpgradeHandler(){
        return instance;
    }

    public HashMap<String, Upgrade> getRegisteredUpgrade(){
        return upgrades;
    }
}
