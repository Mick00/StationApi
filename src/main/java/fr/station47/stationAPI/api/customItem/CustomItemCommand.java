package fr.station47.stationAPI.api.customItem;

import fr.station47.stationAPI.api.StationAPI;
import fr.station47.stationAPI.api.Utils;
import fr.station47.stationAPI.api.commands.MainCommand;
import fr.station47.stationAPI.api.commands.SubCommand;
import fr.station47.stationAPI.api.customItem.nbt.AttributesType;
import fr.station47.stationAPI.api.customItem.nbt.Modifier;
import fr.station47.stationAPI.api.customItem.nbt.NBTmodifier;
import fr.station47.stationAPI.api.customItem.nbt.SlotType;
import fr.station47.stationAPI.api.upgrade.Upgrade;
import fr.station47.theme.Theme;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.Material;
import org.bukkit.command.CommandSender;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.ItemMeta;

import java.util.*;
import java.util.stream.Collectors;

public class CustomItemCommand extends MainCommand{
    public CustomItemCommand(String label) {
        super(label,StationAPI.instance);
        giveCustomItem();
        renameItem();
        enchantItem();
        modifyLore();
        modifyNBT();
        giveUpgrade();
    }

    private void giveCustomItem(){
        SubCommand subCommand = new SubCommand("give","NomItem [joueur] [quantité]","citem.give") {
            @Override
            public boolean executeCommand(CommandSender sender, String[] args) {
                Player target;
                int quantity;
                if (args.length == 3) {
                    if (Utils.isNumber(args[2])) {
                        quantity = Integer.valueOf(args[2]);
                    } else {
                        quantity = 1;
                    }
                } else {
                    quantity = 1;
                }
                if (args.length == 2) {
                    if (Utils.isNumber(args[1])) {
                        target = ((Player) sender);
                        quantity = Integer.valueOf(args[1]);
                    } else {
                        target = Bukkit.getPlayer(args[1]);
                        if (Objects.isNull(target)){
                            sender.sendMessage(Theme.getTheme().getChatPrefix()+"Joueur introuvable");
                        }
                        quantity = 1;
                    }
                } else {
                    target = ((Player) sender);
                }
                Optional<CustomItem> foundItem = StationAPI.customItemHandler.getCustomItems().stream()
                        .filter(c -> c.getName().equals(args[0]))
                        .findFirst();
                if (foundItem.isPresent()){
                    ItemStack item = foundItem.get().getItem();
                    item.setAmount(quantity);
                    target.getInventory().addItem(item);
                } else {
                    sender.sendMessage(Theme.getTheme().getChatPrefix()+"Item introuvable. Voici les options: "
                            +StationAPI.customItemHandler.getCustomItems().stream().map(CustomItem::getName)
                            .collect(Collectors.joining(", ")));
                }


                return true;
            }
        };
        addSubcommands(subCommand);
    }

    private void giveUpgrade(){
        SubCommand subCommand = new SubCommand("upgrade","NomItem niveau [joueur] [quantité]","citem.give") {
            @Override
            public boolean executeCommand(CommandSender sender, String[] args) {
                Player target;
                int quantity;
                if (args.length == 4) {
                    if (Utils.isNumber(args[3])) {
                        quantity = Integer.valueOf(args[3]);
                    } else {
                        quantity = 1;
                    }
                } else {
                    quantity = 1;
                }
                if (args.length == 3) {
                    if (Utils.isNumber(args[2])) {
                        target = ((Player) sender);
                        quantity = Integer.valueOf(args[2]);
                    } else {
                        target = Bukkit.getPlayer(args[2]);
                        if (Objects.isNull(target)){
                            sender.sendMessage(Theme.getTheme().getChatPrefix()+"Joueur introuvable");
                        }
                        quantity = 1;
                    }
                } else {
                    target = ((Player) sender);
                }
                ItemStack item = null;
                if (args.length > 0) {
                    if (args.length > 1 && Utils.isNumber(args[1])) {
                        Upgrade upgrade = StationAPI.upgradeHandler.getRegisteredUpgrade().get(ChatColor.translateAlternateColorCodes('&', args[0]));
                        if (upgrade == null) {
                            sender.sendMessage("Invalide");
                            return true;
                        }
                        item = upgrade.getItem(Integer.valueOf(args[1]));
                        item.setAmount(quantity);
                        target.getInventory().addItem(item);
                    } else {
                        sender.sendMessage(Theme.getTheme().getChatPrefix()+"Ajouter un niveau");
                    }
                    return true;
                } else {
                    sender.sendMessage(StationAPI.upgradeHandler.getRegisteredUpgrade().keySet().stream().collect(Collectors.joining(ChatColor.RESET+", ")));
                }
                return true;
            }
        };
        addSubcommands(subCommand);
    }

    private void renameItem(){
        SubCommand sub = new SubCommand("rename","rename nom; renommer item en main","citem.give") {
            @Override
            public boolean executeCommand(CommandSender sender, String[] args) {
                if (sender instanceof Player){
                    if (args.length > 0) {
                        PlayerInventory inv = ((Player) sender).getInventory();
                        ItemStack item = inv.getItemInMainHand();
                        ItemMeta meta = item.getItemMeta();
                        meta.setDisplayName(ChatColor.translateAlternateColorCodes('&',String.join(" ",args)));
                        item.setItemMeta(meta);
                    } else {
                        sender.sendMessage(Theme.getTheme().getChatPrefix()+"Veuiller spécifier un nom");
                    }
                }
                return true;
            }
        };
        addSubcommands(sub);
    }

    private void enchantItem(){
        SubCommand subCommand = new SubCommand("enchant","enchant type niv; enchanter item en main","citem.enchant") {
            @Override
            public boolean executeCommand(CommandSender sender, String[] args) {
                if (sender instanceof Player){
                    if (args.length == 2) {
                        PlayerInventory inv = ((Player) sender).getInventory();
                        ItemStack item = inv.getItemInMainHand();
                        ItemMeta meta = item.getItemMeta();
                        try {
                            if (item.getType()!= Material.AIR && Utils.isNumber(args[1])) {
                                meta.addEnchant(Enchantment.getByName(args[0].toUpperCase()), Integer.valueOf(args[1]), true);
                            } else {
                                sender.sendMessage(Theme.getTheme().getChatPrefix()+"Le niveau entré est invalide");
                            }
                        }  catch (IllegalArgumentException exception){
                            sender.sendMessage(Theme.getTheme().getChatPrefix()+"Enchantement introuvable, voici la liste:");
                            sender.sendMessage(Arrays.stream(Enchantment.values()).map(e -> e.getName()).collect(Collectors.joining(", ")));
                        }
                        item.setItemMeta(meta);
                    } else {
                        sender.sendMessage(Theme.getTheme().getChatPrefix()+"Veuiller spécifier un nom et un niveau");
                    }
                }
                return true;
            }
        };
        addSubcommands(subCommand);
    }

    private void modifyLore(){
        SubCommand subCommand = new SubCommand("lore","set/add/insert/clear/remove","citem.lore") {
            @Override
            public boolean executeCommand(CommandSender sender, String[] args) {
                if (sender instanceof Player){
                    PlayerInventory inv = ((Player) sender).getInventory();
                    ItemStack item = inv.getItemInMainHand();
                    ItemMeta meta = item.getItemMeta();
                    List<String> lore = meta.getLore();
                    if (lore==null){
                        lore = new ArrayList<>(1);
                    }
                    if (args.length > 0){
                        switch (args[0]){
                            case "add":
                                lore.add(ChatColor.translateAlternateColorCodes('&',String.join(" ", Arrays.copyOfRange(args,1,args.length))));
                                sender.sendMessage(Theme.getTheme().getChatPrefix()+"Ligne ajoutée");
                                break;
                            case "set":
                                if (args.length > 2 && Utils.isNumber(args[1])){
                                    int index = Integer.valueOf(args[1]);
                                    if (index<lore.size()) {
                                        lore.set(index, ChatColor.translateAlternateColorCodes('&', String.join(" ", Arrays.copyOfRange(args, 2, args.length))));
                                        sender.sendMessage(Theme.getTheme().getChatPrefix() + "Ligne définie");
                                    } else {
                                        sender.sendMessage(Theme.getTheme().getChatPrefix()+"Indexe trop grand");
                                    }
                                }else {
                                    sender.sendMessage(Theme.getTheme().getChatPrefix()+"Entrer un numéro à partir de 0");
                                }
                                break;
                            case "insert":
                                if (args.length > 2 && Utils.isNumber(args[1])){
                                    int index = Integer.valueOf(args[1]);
                                    if (index<lore.size()) {
                                        lore.add(index, ChatColor.translateAlternateColorCodes('&', String.join(" ", Arrays.copyOfRange(args, 2, args.length))));
                                        sender.sendMessage(Theme.getTheme().getChatPrefix() + "Ligne définie");
                                    } else {
                                        sender.sendMessage(Theme.getTheme().getChatPrefix()+"Indexe trop grand");
                                    }
                                }else {
                                    sender.sendMessage(Theme.getTheme().getChatPrefix()+"Entrer un numéro à partir de 0");
                                }
                                break;
                            case "clear":
                                lore.clear();
                                sender.sendMessage(Theme.getTheme().getChatPrefix()+"Lore retiré");
                                break;
                            case "remove":
                                if (Utils.isNumber(args[0])) {
                                    lore.remove(args[0]);
                                    sender.sendMessage(Theme.getTheme().getChatPrefix()+"Ligne retirée");
                                } else {
                                    sender.sendMessage(Theme.getTheme().getChatPrefix()+"Entrer un numéro à partir de 0");
                                }
                        }
                        meta.setLore(lore);
                        item.setItemMeta(meta);
                    }

                }
                return false;
            }
        };
        addSubcommands(subCommand);
    }

    private void modifyNBT(){
        SubCommand sub = new SubCommand("nbt","","citem.nbt") {
            @Override
            public boolean executeCommand(CommandSender sender, String[] args) {
                if (sender instanceof Player){
                    Player player = ((Player) sender);
                    PlayerInventory inv = player.getInventory();
                    inv.addItem(NBTmodifier.set(inv.getItemInMainHand(),
                            new Modifier(AttributesType.MAX_HEALTH, SlotType.MAINHAND,0.1,1)));
                }
                return false;
            }
        };
        addSubcommands(sub);
    }
}
