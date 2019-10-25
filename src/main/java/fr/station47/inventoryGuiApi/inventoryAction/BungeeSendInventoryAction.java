package fr.station47.inventoryGuiApi.inventoryAction;

import com.google.common.io.ByteArrayDataOutput;
import com.google.common.io.ByteStreams;
import org.bukkit.entity.Player;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.messaging.PluginMessageListener;

public class BungeeSendInventoryAction implements InventoryAction, PluginMessageListener {

    private Plugin plugin;
    private byte[] msg;
    private String serverName;

    public BungeeSendInventoryAction(Plugin plugin, String server){
        plugin.getServer().getMessenger().registerIncomingPluginChannel(plugin,"BungeeCord",this);
        this.plugin = plugin;
        serverName = server;
    }

    @Override
    public void doAction(InventoryClickEvent event) {
        ByteArrayDataOutput out = ByteStreams.newDataOutput();
        out.writeUTF("PlayerCount");
        out.writeUTF(serverName);
        ((Player)event.getWhoClicked()).sendPluginMessage(plugin, "BungeeCord", out.toByteArray());
    }

    @Override
    public void onPluginMessageReceived(String channel, Player player, byte[] message) {

        player.sendPluginMessage(plugin,"BungeeCord",msg);
    }
}
