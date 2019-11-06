package fr.station47.inventoryGuiApi;

import fr.station47.inventoryGuiApi.inventoryAction.InventoryAction;
import fr.station47.inventoryGuiApi.inventoryAction.InventoryItem;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.HumanEntity;
import org.bukkit.plugin.Plugin;

/**
 * Confirm menu in inventory GUI
 */
public class ConfirmMenu {
    private InventoryBuilder builder;
    private InventoryBuilder back;
    static int instance = 0;

    /**
     * Creates a GUI to ask for confirmation before running the InventoryAction
     * @param confirmTitle Menu title
     * @param confirmText confirm item name
     * @param cancelText cancel item name
     * @param action Action to run
     * @param plugin the plugin using the confirmation menu
     */
    public ConfirmMenu(String confirmTitle, String confirmText, String cancelText, InventoryAction action, Plugin plugin){
        builder = new InventoryBuilder(9,confirmTitle, plugin)
                .setInventoryItem(new InventoryItem(3, ChatColor.GREEN+confirmText, Material.GREEN_GLAZED_TERRACOTTA)
                                        .setAction(e->{
                                            action.doAction(e);
                                            e.setCancelled(true);
                                            e.getWhoClicked().closeInventory();
                                        }))
                .setInventoryItem(new InventoryItem(5, ChatColor.RED+cancelText, Material.RED_GLAZED_TERRACOTTA)
                                        .setAction(e->e.getWhoClicked().closeInventory()))
                .listenTo(true).unregisterListenerOnInvclose(true);
    }


    /**
     * Opens the menu to the player P
     * @param p
     */
    public void open(HumanEntity p){
        p.openInventory(builder.build());
    }

}
