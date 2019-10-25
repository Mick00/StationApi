package fr.station47.stationAPI.api.Shop;


import fr.station47.inventoryGuiApi.Button;
import fr.station47.inventoryGuiApi.InventoryBuilder;
import fr.station47.stationAPI.api.StationAPI;
import fr.station47.theme.Theme;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;

public class QuantitySelector {

    private InventoryBuilder builder;

    QuantitySelector(ShopItem item, Player showTo, Shop backShop){
        builder = new InventoryBuilder(9,"Sélectionner la quantité", StationAPI.instance);
        for (int i = 0; i < 8; i++) {
            final int qt = (int)Math.pow(2,i);
            builder.setInventoryItem(item.getInventoryItem(qt).setSlot(i));
        }
        if (backShop!=null){
            builder.setItemAndAction(
                    8
                    , Button.n(Material.REDSTONE_BLOCK,ChatColor.RED+"Retour",null)
                    , e->{backShop.open(showTo);e.setCancelled(true);}
            );
        }
        builder.unregisterListenerOnInvclose(true);
        StationAPI.instance.getServer().getPluginManager().registerEvents(builder.getListener(), StationAPI.instance);
        showTo.openInventory(builder.build());
    }



    private void handle(InventoryClickEvent event, ShopItem item, int qt){
        event.setCancelled(true);
        Player player = (Player)event.getWhoClicked();
        if (event.getClick().isLeftClick()){
            if (item.canBuy()){
                if (!item.buy(player, qt)) {
                    player.sendMessage(Theme.getTheme().getChatPrefix()+"Vous n'avez pas assez d'argent");
                }
            } else {
                player.sendMessage(Theme.getTheme().getChatPrefix()+"Vous ne pouvez acheter de ces objets ici");
            }
        } else if (event.getClick().isRightClick()){
            if (item.canSell()){
                if (!item.sell(player,qt)){
                    player.sendMessage(Theme.getTheme().getChatPrefix()+"Vous ne possédez pas ces objets");
                } else {
                    player.sendMessage(Theme.getTheme().getChatPrefix()+"Vos objets ont été vendus");
                }
            } else {
                player.sendMessage(Theme.getTheme().getChatPrefix()+"Vous ne pouvez vendre ces objets ici");
            }
        }
    }


}
