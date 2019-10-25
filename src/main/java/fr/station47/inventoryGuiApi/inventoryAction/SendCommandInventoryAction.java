package fr.station47.inventoryGuiApi.inventoryAction;

import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class SendCommandInventoryAction implements InventoryAction {

    private String cmd;
    private boolean asConsole;

    public SendCommandInventoryAction(String cmd, boolean asConsole) {
        this.cmd = cmd;
        this.asConsole = asConsole;
    }

    private void sendCommandAsPlayer(Player p){
        p.performCommand(this.cmd);
    }

    private void sendCommandAsConsole(){
        Bukkit.getServer().dispatchCommand(Bukkit.getConsoleSender(),cmd);
    }

    @Override
    public void doAction(InventoryClickEvent event) {
        if (asConsole){
            sendCommandAsConsole();
        } else if (event.getWhoClicked() instanceof Player){
            sendCommandAsPlayer((Player)event.getWhoClicked());
        }
        event.setCancelled(true);
    }
}
