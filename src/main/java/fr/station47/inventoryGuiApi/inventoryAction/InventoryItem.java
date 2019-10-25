package fr.station47.inventoryGuiApi.inventoryAction;

import fr.station47.inventoryGuiApi.ConfirmMenu;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.Plugin;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/**
 * Class to wrap items for GUI purpose
 */
public class InventoryItem {
    private int slot;
    private String name;
    private Material material;
    private int amount;
    private List<String> lore;
    private InventoryAction action = null;
    private boolean cancel = true;
    private boolean confirm;
    private String confirmTitle = "Voulez-vous confirmer?", confirmText = "Confirmer", cancelText = "Annuler";

    public InventoryItem(int slot, String name, Material material) {
        this.slot = slot;
        this.name = name;
        this.material = material;
        this.amount = 1;
        this.lore = new ArrayList<>();
        this.action = new MultiAction();
        this.confirm = false;
    }

    /**
     * Build the item
     * @return
     */
    public ItemStack getItemStack(){
        ItemStack item = new ItemStack(material,amount);
        ItemMeta meta = item.getItemMeta();
        meta.setDisplayName(name);
        meta.setLore(lore);
        item.setItemMeta(meta);
        return item;
    }

    /**
     * Returns action to run on click.
     * @param plugin
     * @return
     */
    public InventoryAction getAction(Plugin plugin){
        if (confirm){
             return  e -> { new ConfirmMenu(confirmTitle, confirmText, cancelText, action, plugin).open(e.getWhoClicked());
                            e.setCancelled(cancel);
                            };
        } else {
            return action;
        }
    }

    public int getSlot() {
        return slot;
    }

    public InventoryItem setSlot(int slot) {
        this.slot = slot;
        return this;
    }

    public String getName() {
        return name;
    }

    public InventoryItem setName(String name) {
        this.name = name;
        return this;
    }

    public InventoryItem setMaterial(Material material){
        this.material = material;
        return this;
    }

    public Material getMaterial(){return material;}

    public InventoryItem setAmount(int amount){
        this.amount = amount;
        return this;
    }

    public int getAmount(){return amount;}

    public List<String> getLore() {
        return lore;
    }

    /**
     * Add a lore line. Automatically set the texts white
     * @param line
     * @return
     */
    public InventoryItem addLore(String line){
        lore.add(ChatColor.WHITE + line);
        return this;
    }

    public InventoryItem setLore(List<String> lore) {
        this.lore = lore;
        return this;
    }

    /**
     * Use add action instead
     * @param action
     * @return
     */
    public InventoryItem setAction(InventoryAction action) {
        this.action = action;
        return this;
    }

    /**
     * Add an action to run on click. lowest priority goes first.
     * @param priority
     * @param action
     * @return
     */
    public InventoryItem addAction(int priority, InventoryAction action){
        if (this.action instanceof MultiAction){
            ((MultiAction) this.action).addAction(priority, action);
        }
        return this;
    }
    /**
     * Add an action to run on click. Default priotity to 10
     * @param action
     * @return
     */
    public InventoryItem addAction(InventoryAction action){
        if (this.action instanceof MultiAction){
            ((MultiAction) this.action).addAction(10,action);
        }
        return this;
    }


    public boolean isConfirm() {
        return confirm;
    }

    /**
     * Whether to confirm user's choice before running the actions
     * @param confirm
     * @return
     */
    public InventoryItem setConfirm(boolean confirm) {
        this.confirm = confirm;
        return this;
    }

    /**
     * Sets the confirmation messages.
     * @param confirmTitle Inventory title
     * @param confirmText Item name for the confirm item
     * @param cancelText item name for the cancel item
     * @return
     */
    public InventoryItem confirmMessage(String confirmTitle, String confirmText, String cancelText){
        this.confirmTitle = confirmTitle;
        this.confirmText = confirmText;
        this.cancelText = cancelText;
        return this;
    }

    public void putIn(Inventory inv){
        inv.setItem(slot,getItemStack());
    }
}
