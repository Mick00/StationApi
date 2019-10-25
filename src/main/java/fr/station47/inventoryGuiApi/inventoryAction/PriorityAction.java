package fr.station47.inventoryGuiApi.inventoryAction;

import org.bukkit.event.inventory.InventoryClickEvent;

public class PriorityAction implements Comparable<PriorityAction> {

    private int priority;
    private InventoryAction action;

    public PriorityAction(int priority, InventoryAction action) {
        this.priority = priority;
        this.action = action;
    }

    public void exec(InventoryClickEvent event){
        action.doAction(event);
    }

    @Override
    public int compareTo(PriorityAction o) {
        return  priority - o.priority;
    }
}
