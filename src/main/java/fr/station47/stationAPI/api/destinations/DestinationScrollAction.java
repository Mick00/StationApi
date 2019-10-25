package fr.station47.stationAPI.api.destinations;


import fr.station47.stationAPI.api.Warmup;
import fr.station47.stationAPI.api.customItem.InteractAction;
import fr.station47.theme.Theme;
import org.bukkit.event.player.PlayerInteractEvent;

public class DestinationScrollAction implements InteractAction {

    private Destination destination;

    public DestinationScrollAction(Destination destination){
        this.destination = destination;
    }
    @Override
    public boolean leftClickAir(PlayerInteractEvent e) {
        return false;
    }

    @Override
    public boolean leftClickBlock(PlayerInteractEvent e) {
        return false;
    }

    @Override
    public boolean rightClickAir(PlayerInteractEvent e) {
        if(destination.getPermission().equals("none") || e.getPlayer().hasPermission(destination.getPermission())) {
            Warmup warmup = new Warmup(e.getPlayer(), false, 4, () -> e.getPlayer().teleport(destination.getLocation()));
            warmup.start();
            return true;
        } else {
            e.getPlayer().sendMessage(Theme.getTheme().getChatPrefix()+"Vous n'avez pas la permission necessaire");
        }
        return false;
    }

    @Override
    public boolean rightClickBlock(PlayerInteractEvent e) {
        return false;
    }
}
