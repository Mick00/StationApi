package fr.station47.stationAPI.api.upgrade;

import fr.station47.stationAPI.api.config.ConfigObject;
import fr.station47.stationAPI.api.customItem.CustomItemHandler;
import fr.station47.stationAPI.api.customItem.InteractAction;
import fr.station47.stationAPI.api.customItem.Interactable;
import org.bukkit.entity.Player;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

public abstract class Spell extends Upgrade implements Interactable {

    private InteractAction action;
    private HashMap<UUID, Long> lastUse;
    private long cooldown;
    private ConfigObject configObject;

    public Spell(String name, String displayName, List<String> description, int coolDown) {
        super(name, displayName, description);
        configObject = new ConfigObject();
        configObject.put("upgrades."+this.name+".cooldown",coolDown);
        lastUse = new HashMap<>();
        action = new InteractAction() {
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
                return precast(e);
            }

            @Override
            public boolean rightClickBlock(PlayerInteractEvent e) {
                return precast(e);
            }
        };
        load();
        CustomItemHandler.getInstance().registerInteractable(this);
    }

    @Override
    public boolean condition(ItemStack item, Player player) {
        return (Objects.nonNull(item.getItemMeta().getLore()) && item.getItemMeta().getLore().contains(getDisplayName()+" I"));
    }

    @Override
    public InteractAction getAction() {
        return action;
    }

    public boolean precast(PlayerInteractEvent event){
        long remaining = lastUse.getOrDefault(event.getPlayer().getUniqueId(),0L) + cooldown - System.currentTimeMillis();
        if (remaining > 0) {
            event.getPlayer().sendMessage("Il reste "+remaining/1000+" secondes avant de pouvoir réutiliser ce sort");
            return false;
        }
        lastUse.put(event.getPlayer().getUniqueId(),System.currentTimeMillis());
        return cast(event);
    }

    public abstract boolean cast(PlayerInteractEvent event);

    @Override
    protected ItemStack modify(ItemStack target, int level) {
        return target;
    }

    private void load(){
        configObject.loadFrom(UpgradeHandler.getConfig());
        cooldown = (long)(int)configObject.getMap().get("upgrades."+this.name+".cooldown");
    }

}
