package fr.station47.stationAPI.api.Shop;

import fr.station47.stationAPI.api.StationAPI;
import net.citizensnpcs.api.trait.Trait;
import net.citizensnpcs.api.trait.TraitName;
import org.bukkit.ChatColor;

@TraitName("marchand")
public class MerchantTrait extends Trait {

    public MerchantTrait() {
        super("marchand");
    }

    @Override
    public void onSpawn(){
        if (StationAPI.destinationsManager!=null) {
            StationAPI.destinationsManager.registerNpcShop(ChatColor.stripColor(getNPC().getName()), getNPC());
        }
    }

    @Override
    public void onRemove(){
        if (StationAPI.destinationsManager!=null) StationAPI.destinationsManager.unregisterNpcShop(getNPC().getName(),getNPC());
    }
}
