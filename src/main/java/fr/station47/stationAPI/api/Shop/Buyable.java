package fr.station47.stationAPI.api.Shop;

import fr.station47.stationAPI.api.StationAPI;
import org.bukkit.entity.Player;

import java.util.List;

public abstract class Buyable extends Required {
    protected double unitPrice;

    public Buyable(double price){
        this.unitPrice = price;
        addRequirement(new Requirement(player -> canBuy(),"none", "Vous ne pouvez acheter ici."));
    }

    public boolean buy(Player player, int qt){
        if (canBuy(player) && hasEnoughMoney(player,qt)){
            if (purchase(player,qt)){
                charge(player,qt);
                return true;
            }
        }
        return false;
    }

    public boolean buy(Player player){
        return buy(player,1);
    }

    public boolean canBuy(){return unitPrice>0;}

    public boolean canBuy(Player p){return passRequ(p);}

    public boolean hasEnoughMoney(Player player, int qt){
        return StationAPI.econ.has(player,qt*unitPrice);
    }

    public boolean hasEnoughMoney(Player player){
        return hasEnoughMoney(player,1);
    }

    public void charge(Player player, int qt){
        StationAPI.econ.withdrawPlayer(player,qt*unitPrice);
    }

    public void charge(Player player){
        charge(player,1);
    }

    public double getPrice(int qt){
        return unitPrice*qt;
    }

    public double getUnitPrice(){
        return unitPrice;
    }

    protected abstract boolean purchase(Player player, int quantity);

}
