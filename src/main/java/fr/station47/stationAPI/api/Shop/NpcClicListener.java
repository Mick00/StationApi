package fr.station47.stationAPI.api.Shop;

import fr.station47.stationAPI.api.StationAPI;
import net.citizensnpcs.api.CitizensAPI;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.ChatColor;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerInteractEntityEvent;

public class NpcClicListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void handle(PlayerInteractEntityEvent event){
        if (event.getRightClicked().hasMetadata("NPC")){
            NPC npc = CitizensAPI.getNPCRegistry().getNPC(event.getRightClicked());
            if (npc.hasTrait(MerchantTrait.class)) {
                event.setCancelled(true);
                String shopName = ChatColor.stripColor(npc.getName());
                if (StationAPI.shopManager.shopExists(shopName)) {
                    StationAPI.shopManager.getShop(shopName).open(event.getPlayer());
                } else {
                    event.getPlayer().sendMessage("Shop introuvable");
                }
            }
        }
    }

}
