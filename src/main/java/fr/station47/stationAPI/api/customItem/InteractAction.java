package fr.station47.stationAPI.api.customItem;

import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Interface to create interactable items
 */
public interface InteractAction {

    boolean leftClickAir(PlayerInteractEvent e);
    boolean leftClickBlock(PlayerInteractEvent e);
    boolean rightClickAir(PlayerInteractEvent e);
    boolean rightClickBlock(PlayerInteractEvent e);

}
