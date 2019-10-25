package fr.station47.stationAPI.api.destinations;



import fr.station47.stationAPI.api.customItem.InteractAction;
import fr.station47.stationAPI.api.customItem.Interactable;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

public class InteractableTpScroll implements Interactable {

    private ItemStack scroll;
    private InteractAction action;

    public InteractableTpScroll(Destination destination){
        scroll = new DestinationScroll(destination).getItem();
        action = new DestinationScrollAction(destination);
    }

    @Override
    public boolean condition(ItemStack item, Player player) {
        return item.isSimilar(getItem());
    }

    public ItemStack getItem() {
        return scroll;
    }

    @Override
    public InteractAction getAction() {
        return action;
    }
}
