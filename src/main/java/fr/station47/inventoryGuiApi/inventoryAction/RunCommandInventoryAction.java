package fr.station47.inventoryGuiApi.inventoryAction;

import org.bukkit.Bukkit;
import org.bukkit.event.inventory.InventoryClickEvent;

public class RunCommandInventoryAction implements InventoryAction {

    private String cmd;

    public RunCommandInventoryAction(String cmd){
        super();
        this.cmd = cmd;
    }


    @Override
    public void doAction(InventoryClickEvent event) {
        Bukkit.getConsoleSender();
        event.setCancelled(true);
    }
}
