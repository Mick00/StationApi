package fr.station47.stationAPI.api.destinations;

import net.citizensnpcs.api.npc.NPC;
import org.bukkit.Location;
import java.util.ArrayList;
import java.util.List;

public class Merchants {
    public List<NPC> locations;

    public Merchants(){
        locations = new ArrayList<>();
    }

    public void addNpc(NPC npc){
        locations.add(npc);
    }

    public void removeNpc(NPC npc){
        locations.remove(npc);
    }

    public NPC findNearest(Location location){
        NPC nearest = null;
        double distance = Double.MAX_VALUE;
        for (NPC npc: locations){
            double temp;
            if (location.getWorld().equals(npc.getStoredLocation().getWorld()) && (temp = location.distance(npc.getStoredLocation())) < distance) {
                nearest = npc;
                distance = temp;
            }
        }
        return nearest;
    }
}
