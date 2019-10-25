package fr.station47.inventoryGuiApi.inventoryAction;

import org.bukkit.event.inventory.InventoryClickEvent;

/**
 * Will execute inventory action and cancel the event
 */
public class ConsumedInventoryAction implements InventoryAction {
    private InventoryAction action;

    public ConsumedInventoryAction(InventoryAction action){
        this.action = action;
    }

    @Override
    public void doAction(InventoryClickEvent event) {
        action.doAction(event);
        event.setCancelled(true);
    }
}
