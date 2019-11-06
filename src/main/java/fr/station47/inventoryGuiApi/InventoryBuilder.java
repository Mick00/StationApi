package fr.station47.inventoryGuiApi;

import fr.station47.inventoryGuiApi.inventoryAction.CloseEventAction;
import fr.station47.inventoryGuiApi.inventoryAction.InventoryAction;
import fr.station47.inventoryGuiApi.inventoryAction.InventoryItem;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.Plugin;

public class InventoryBuilder {

    public static boolean debug = true;
    private Inventory inv;
    private InventoryListener listener;
    private Plugin plugin;
    private boolean destroyOnLeave = false;
    private static int instance = 1;
    private String invName;
    private ItemStack[] invContent;

    /**
     * Creates instance of an inventory builder
     * @param invSize size of the inventory must be multiple of 9
     * @param name Name of the inventory
     * @param plugin Plugin creating the inventory (to register the listener)
     */
    public InventoryBuilder(int invSize, String name, Plugin plugin){
        this.inv = Bukkit.createInventory(null, invSize,name);
        invContent = new ItemStack[invSize];
        this.invName = name;
        listener = new InventoryListener(invSize,name);
        this.plugin = plugin;
    }

    public InventoryBuilder setItem(int slot, ItemStack item){
        this.invContent[slot] = item;
        return this;
    }

    /**
     * ListenTo(true) must be set for this have effect
     * @param slot slot in inv
     * @param action action to run on clic
     * @return
     */
    public InventoryBuilder setOnAction(int slot, InventoryAction action){
        listener.setAction(slot,action);
        return this;
    }

    public InventoryBuilder setItemAndAction(int slot, ItemStack item, InventoryAction action){
        setItem(slot,item);
        setOnAction(slot,action);
        return this;
    }

    public InventoryBuilder setInventoryItem(InventoryItem item){
        setItem(item.getSlot(),item.getItemStack());
        setOnAction(item.getSlot(),item.getAction(plugin));
        return this;
    }

    public InventoryListener getListener(){
        return this.listener;
    }

    /**
     * Register a listener to run the onclick Action
     * @param listen
     * @return
     */
    public InventoryBuilder listenTo(boolean listen){
        if (listen) {
            Bukkit.getPluginManager().registerEvents(listener, plugin);
        } else {
            HandlerList.unregisterAll(listener);
        }
        return this;
    }

    public InventoryBuilder unregisterListenerOnInvclose(boolean destroy){
        destroyOnLeave = destroy;
        listener.setDestroyOnLeave(destroyOnLeave);
        return this;
    }

    /**
     * Listening must be on
     * Run the action on close
     * @param action
     * @return
     */
    public InventoryBuilder setActionOnClose(CloseEventAction action){
        listener.setcloseAction(action);
        return this;
    }

    /**
     * Returns the built inventory
     * @return
     */
    public Inventory build() {
        Inventory copyInv;
        if (destroyOnLeave){
            String uInvID = this.invName+getInvisibleKey();
            copyInv = Bukkit.createInventory(null, inv.getSize(), uInvID);
            listener.setName(uInvID);
        } else {
            copyInv = Bukkit.createInventory(null, inv.getSize(), this.invName);
        }
        copyInv.setContents(this.invContent);
        return copyInv;
    }

    private static String getInvisibleKey(){
        instance++;
        StringBuilder sb = new StringBuilder();
        for (int i = 1; i<instance; i++){
            sb.append("&");
            sb.append(i%10);
        }
        pdebug("IUID: "+instance+ "/" +sb.toString());
        return ChatColor.translateAlternateColorCodes('&',sb.toString());
    }

    public static void pdebug(String s){
        if (debug){
            System.out.println(s);
        }
    }
}
