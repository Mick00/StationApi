package fr.station47.inventoryGuiApi.inventoryAction;

import org.bukkit.event.inventory.InventoryClickEvent;

public class DoNothing implements InventoryAction {
    @Override
    public void doAction(InventoryClickEvent event) {
        event.setCancelled(true);
    }
}
