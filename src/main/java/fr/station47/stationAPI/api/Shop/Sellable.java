package fr.station47.stationAPI.api.Shop;

import fr.station47.stationAPI.api.StationAPI;
import org.bukkit.entity.Player;

public abstract class Sellable extends Required{
    protected double unitPrice;

    public Sellable(double unitPrice){
        this.unitPrice = unitPrice;
        addRequirement(new Requirement(this::canSell,"none","Vous ne pouvez vendre ici"));
    }

    public boolean sell(Player player, int qt){
        if (canSell(player)){
            if (sale(player,qt)){
                pay(player,qt);
                return true;
            }
        }
        return false;
    }

    public boolean sell(Player player){
        return sell(player,1);
    }

    public boolean canSell(){
        return unitPrice > 0;
    }

    public boolean canSell(Player p) { return passRequ(p);}

    public void pay(Player player, int qt){
        StationAPI.econ.depositPlayer(player,qt*unitPrice);
    }

    public void pay(Player player){
        pay(player,1);
    }

    public double getPrice(int qt){
        return unitPrice*qt;
    }

    public double getUnitPrice(){
        return unitPrice;
    }

    protected abstract boolean sale(Player p, int quantity);
}
