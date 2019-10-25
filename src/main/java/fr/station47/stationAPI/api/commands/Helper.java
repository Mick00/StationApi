package fr.station47.stationAPI.api.commands;

import fr.station47.theme.Theme;
import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public class Helper {

    private MainCommand command;
    private List<String> messages;

    public Helper(MainCommand command){
        this.command = command;
        messages = new ArrayList<>();
    }

    public void addMessage(String message){
        messages.add(message);
    }

    public void help(CommandSender player){
        player.sendMessage(Theme.getTheme().getChatPrefix()+"Menu de commande");
        for (String s : messages) {
            player.sendMessage(s);
        }
        for (SubCommand subCommand: command.getSubcommands()){
            if (subCommand.getPermission().equals("none") || player.hasPermission(subCommand.getPermission())){
                player.sendMessage(" - /"+command.getLabel()+" "+subCommand.getLabel()+" "+subCommand.getUsage());
            }
        }
    }
}
