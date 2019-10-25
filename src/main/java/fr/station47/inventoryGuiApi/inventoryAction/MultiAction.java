package fr.station47.inventoryGuiApi.inventoryAction;

import com.google.common.collect.HashMultiset;
import com.google.common.collect.Multiset;
import org.bukkit.event.inventory.InventoryClickEvent;

import java.util.Iterator;
import java.util.Set;

/**
 * Sorts actions to take when an item is clicked in an inventory
 */
public class MultiAction implements InventoryAction {

    private Multiset<PriorityAction> actions;

    public MultiAction(){
        actions = HashMultiset.create();
        setCancel(true);
    }

    public void addAction(int priority, InventoryAction action){
        actions.add(new PriorityAction(priority,action));
    }

    /**
     * Will set the event as cancelled
     * @param cancel Cancel the event after all priorityAction
     */
    public void setCancel(boolean cancel){
        if (cancel){
            actions.add(new PriorityAction(200, e->e.setCancelled(true)));
        } else {
            actions.remove(new PriorityAction(200, e->setCancel(true)));
        }
    }

    @Override
    public void doAction(InventoryClickEvent event) {
        Iterator<PriorityAction> iterator = actions.iterator();
        while(iterator.hasNext()){
            iterator.next().exec(event);
        }
    }
}
