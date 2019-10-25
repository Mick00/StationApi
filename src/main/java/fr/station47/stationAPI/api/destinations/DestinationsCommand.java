package fr.station47.stationAPI.api.destinations;

import fr.station47.stationAPI.api.StationAPI;
import fr.station47.stationAPI.api.Utils;
import fr.station47.stationAPI.api.commands.MainCommand;
import fr.station47.stationAPI.api.commands.SubCommand;
import fr.station47.theme.Theme;
import net.citizensnpcs.api.npc.NPC;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

public class DestinationsCommand extends MainCommand {

    private DestinationsManager destinations;

    public DestinationsCommand(String label, DestinationsManager destinations) {
        super(label, StationAPI.instance);
        this.destinations = destinations;
        marchand();
        gotoDestination();
        list();
        tp();
        setDestination();
        deleteDestination();
        hideDestination();
    }

    private void marchand(){
        SubCommand sub = new SubCommand("marchand","nomDuMarchand - Trouve le marchand le plus près","none") {
            @Override
            public boolean executeCommand(CommandSender sender, String[] args) {
                if(destinations.npcShopExists(args[0])){
                    Player player = (Player)sender;
                    NPC npc = destinations.findNearestMerchant(player.getLocation(),args[0]);
                    if (npc !=null) {
                        player.setCompassTarget(npc.getStoredLocation());
                        player.sendMessage(Theme.getTheme().getChatPrefix()+"Votre boussole pointe désormais vers le marchand le plus près");
                    } else {
                        player.sendMessage(Theme.getTheme().getChatPrefix()+"Aucun marchand trouvé dans ce monde");
                    }
                } else {
                    sender.sendMessage(Theme.getTheme().getChatPrefix()+"Aucun marchand trouvé");
                }
                return true;
            }
        };
        sub.addAlias("mar");
        addSubcommands(sub);
    }

    private void gotoDestination(){
        SubCommand sub = new SubCommand("destination","nomDeLaDestination - Indique la destination sur votre boussole","none") {
            @Override
            public boolean executeCommand(CommandSender sender, String[] args) {
                if (args.length > 0){
                    Destination d;
                    if (destinations.destinationExists(args[0]) && !(d=destinations.getDestination(args[0])).isHidden() && sender.hasPermission(d.getPermission())){
                        ((Player) sender).setCompassTarget(d.getLocation());
                        sender.sendMessage(Theme.getTheme().getChatPrefix()+"Votre boussole pointe désormais vers votre nouvelle destination");
                    } else {
                        sender.sendMessage(Theme.getTheme().getChatPrefix()+"Cette destination n'existe pas");
                    }
                    return true;
                }
                return false;
            }
        };
        sub.addAlias("des");
        addSubcommands(sub);
    }

    private void list(){
        SubCommand sub = new SubCommand("liste","marchands/destinations","none") {
            @Override
            public boolean executeCommand(CommandSender sender, String[] args) {
                if (args.length > 0) {
                    switch (args[0]) {
                        case "destinations":
                            sender.sendMessage(Theme.getTheme().getChatPrefix()+"Voici les destinations possibles");
                            for (Destination d : destinations.getDestinations().values()) {
                                if ((d.getPermission().equals("none") || sender.hasPermission(d.getPermission())) && !d.isHidden()) {
                                    sender.sendMessage(" - " + d.getName());
                                }
                            }
                            return true;
                        case "marchands":
                            sender.sendMessage(Theme.getTheme().getChatPrefix() + "Voici les marchands disponibles");
                            for (String s : destinations.getMerchants().keySet()) {
                                sender.sendMessage(" - " + s);
                            }
                            return true;
                        default:
                            return false;
                    }
                }
                return false;
            }
        };
        sub.addAlias("list");
        addSubcommands(sub);
    }

    public void tp(){
        SubCommand sub = new SubCommand("tp","NomDeLaDestination - Tp vers destination","des.tp") {
            @Override
            public boolean executeCommand(CommandSender sender, String[] args) {
                if (args.length == 1){
                    Player player = (Player) sender;
                    Destination d = destinations.getDestination(args[0]);
                    if (d!=null && (player.hasPermission(d.getPermission()) || player.hasPermission("des.bypass"))){
                        player.teleport(d.getLocation());
                    } else {
                        player.sendMessage(Theme.getTheme().getChatPrefix()+"Destination inconnue ou vous n'avez pas la permission");
                    }
                    return true;
                }
                return false;
            }
        };
        addSubcommands(sub);
    }

    public void setDestination(){
        SubCommand subCommand = new SubCommand("set","nomDeLaDestination - Defini une nouvelle destination","des.set") {
            @Override
            public boolean executeCommand(CommandSender sender, String[] args) {
                if (args.length == 1){
                    Player player = (Player) sender;
                    Destination destination = new Destination(args[0],player.getLocation());
                    destinations.add(destination);
                    sender.sendMessage(Theme.getTheme().getChatPrefix()+destination.getName()+ " a été créée");
                    destinations.createShop();
                    return true;
                }
                return false;
            }
        };
        addSubcommands(subCommand);
    }

    public void deleteDestination(){
        SubCommand sub = new SubCommand("sup","nomDeLaDestination - Supprime la destination","des.del") {
            @Override
            public boolean executeCommand(CommandSender sender, String[] args) {
                if (args.length>=1 && destinations.destinationExists(args[0])){
                    destinations.remove(args[0].toLowerCase());
                    sender.sendMessage(Theme.getTheme().getChatPrefix()+"La destination a été enlevée");
                    destinations.createShop();
                }
                return false;
            }
        };
        addSubcommands(sub);
    }

    public void hideDestination(){
        SubCommand sub = new SubCommand("hide","NomDeLaDestination - Cache une destination de la liste","des.hide") {
            @Override
            public boolean executeCommand(CommandSender sender, String[] args) {
                if (args.length >=1 && destinations.destinationExists(args[0])){
                    Destination destination = destinations.getDestination(args[0]);
                    destination.setHidden(!destination.isHidden());
                    sender.sendMessage(Theme.getTheme().getChatPrefix()+destination.getName()+ " est "+(destination.isHidden()?"cachée":"visible"));
                    return true;
                }
                return false;
            }
        };
        addSubcommands(sub);
    }

    public void setPerm(){
        SubCommand sub = new SubCommand("perm","nomDeLaDestination permission - Défini une permission","des.perm") {
            @Override
            public boolean executeCommand(CommandSender sender, String[] args) {
                if (args.length >= 2 && destinations.destinationExists(args[0])){
                    destinations.getDestination(args[0]).setPermission(args[1]);
                    sender.sendMessage(Theme.getTheme().getChatPrefix()+"La permission pour la destination a été définie");
                    return true;
                } else {
                    sender.sendMessage(Theme.getTheme().getChatPrefix()+"La destination est invalide");
                }
                return false;
            }
        };
        addSubcommands(sub);
    }

    public void price(){
        SubCommand sub = new SubCommand("price","NomDeLaDestination prix - Defini le prix d'un parchemin de tp","des.price") {
            @Override
            public boolean executeCommand(CommandSender sender, String[] args) {
                if (args.length >= 2 && destinations.destinationExists(args[0])){
                    if (Utils.isNumber(args[2])){
                        destinations.getDestination(args[0]).setPrice(Double.valueOf(args[1]));
                        sender.sendMessage(Theme.getTheme().getChatPrefix()+"Le prix pour la destination a été définie");
                        destinations.createShop();
                    }else {
                        sender.sendMessage(Theme.getTheme().getChatPrefix()+"Le prix est invalide");
                    }
                    return true;
                } else {
                   return false;
                }
            }
        };
        addSubcommands(sub);
    }
}


