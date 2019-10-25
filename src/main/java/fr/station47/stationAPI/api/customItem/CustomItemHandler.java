package fr.station47.stationAPI.api.customItem;

import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

import org.bukkit.inventory.ItemStack;
import java.util.ArrayList;
import java.util.List;

/**
 * Singleton handler for item iteraction
 * <p>Item handler has to be active in the config to be enabled by default</p>
 */
public class CustomItemHandler implements Listener {

    private List<Interactable> items;
    private static CustomItemHandler instance;
    private List<CustomItem> customItems;

    /**
     * Creates instance of the handler if singleton is not yet instanciated
     */
    public CustomItemHandler(){
        if (instance == null){
            items = new ArrayList<Interactable>();
            customItems = new ArrayList<CustomItem>();
            instance = this;
        }
    }

    /**
     *
     * @param event event launched
     */
    @EventHandler(priority = EventPriority.LOWEST)
    public void handle(PlayerInteractEvent event){
        ItemStack item = event.getItem();
        if (item == null){
            return;
        }
        for (Interactable interactable:items){
            if (interactable.condition(item, event.getPlayer())){
                InteractAction action = interactable.getAction();
                if (event.getAction().equals(Action.RIGHT_CLICK_AIR)) {
                    if (action.rightClickAir(event))
                        item.setAmount(item.getAmount()-1);
                } else if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
                    if (action.rightClickBlock(event)) {
                        item.setAmount(item.getAmount()-1);
                    }
                } else if (event.getAction().equals(Action.LEFT_CLICK_AIR)) {
                    if (action.leftClickAir(event)) {
                        item.setAmount(item.getAmount()-1);
                    }
                } else if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
                    if (action.leftClickBlock(event)) {
                        item.setAmount(item.getAmount()-1);
                    }
                }
                event.setCancelled(true);
                break;
            }
        }
    }

    /**
     * Registers and interactable Items
     * @param interactable interactable item
     */
    public void registerInteractable(Interactable interactable){
        items.add(interactable);
    }

    public static CustomItemHandler getInstance(){
        return instance;
    }

    /**
     * Registers a custom item so it can be used in the /citem command
     * @param item
     */
    public void registerNewItem(CustomItem item){
        customItems.add(item);
    }

    /**
     * @return list of the registered custom items
     * @see CustomItemHandler#registerNewItem(CustomItem)
     */
    public List<CustomItem> getCustomItems(){
        return customItems;
    }
}
