package fr.station47.inventoryGuiApi;

import fr.station47.inventoryGuiApi.inventoryAction.CloseEventAction;
import fr.station47.inventoryGuiApi.inventoryAction.DoNothing;
import fr.station47.inventoryGuiApi.inventoryAction.InventoryAction;
import org.bukkit.event.EventHandler;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;

public class InventoryListener implements Listener {

    private InventoryAction[] actions;
    private CloseEventAction closeEventAction;
    private String invName;
    private boolean destroyOnLeave;


    public InventoryListener(int size, String invName){
        this.actions = new InventoryAction[size];
        for (int i = 0; i<actions.length;i++){
            setAction(i, new DoNothing());
        }
        this.invName = invName;
        closeEventAction = null;
        destroyOnLeave = false;
    }

    @EventHandler
    public void onInventoryClick(InventoryClickEvent event){
        InventoryBuilder.pdebug("Click in "+event.getView().getTitle());
        if (event.getView().getTitle().equals(invName)
                && event.getRawSlot() >= 0
                && event.getRawSlot() < actions.length) {
            this.actions[event.getSlot()].doAction(event);
        }
    }

    @EventHandler
    public void onClose(InventoryCloseEvent event){
        if (event.getView().getTitle().equals(invName)) {
            if (closeEventAction != null) {
                closeEventAction.doAction(event);
            }
            if (destroyOnLeave) {
                HandlerList.unregisterAll(this);
            }
        }
    }


    public void setDestroyOnLeave(boolean b){
        destroyOnLeave = b;
    }

    public void setcloseAction(CloseEventAction action){
        this.closeEventAction = action;
    }

    public void setAction(int slot, InventoryAction action){
        actions[slot] = action;
    }

    public void setName(String name){
        this.invName = name;
    }

    public void destroy(){
        HandlerList.unregisterAll(this);
    }
}
