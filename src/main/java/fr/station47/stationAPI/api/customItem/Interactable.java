package fr.station47.stationAPI.api.customItem;

import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

/**
 * Intergace to create interactable items
 */
public interface Interactable {

    boolean condition(ItemStack item, Player player);
    InteractAction getAction();
}
