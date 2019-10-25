package fr.station47.stationAPI.api.commands;

import fr.station47.stationAPI.api.StationAPI;
import fr.station47.theme.Theme;
import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MainCommand implements CommandExecutor{

    private List<SubCommand> subcommands;
    private String label;
    protected Helper helper;

    public MainCommand(String label, JavaPlugin plugin){
        subcommands = new ArrayList<>(1);
        helper = new Helper(this);
        this.label = label;
        plugin.getCommand(label).setExecutor(this);
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args){
        if (args.length == 0){
            if (!noArgs(sender)){
                helper.help(sender);
            }
            return true;
        }
        for (SubCommand subCommand: subcommands){
            if (subCommand.isLabel(args[0])){
                if (!hasPermission(sender,subCommand)){
                    sender.sendMessage(Theme.getTheme().getChatPrefix()+"Vous n'avez pas la permission");
                } else {
                    if (!subCommand.executeCommand(sender, Arrays.copyOfRange(args, 1, args.length))) {
                        sender.sendMessage(Theme.getTheme().getChatPrefix() + "/" + label + " " +subCommand.getLabel()+" "+ subCommand.getUsage());
                    }
                }
                return true;
            }
        }
        if (!noMatch(sender,Arrays.copyOfRange(args, 1, args.length))) {
            helper.help(sender);
        }
        return true;
    }

    private boolean hasPermission(CommandSender sender, SubCommand subCommand){
        return subCommand.getPermission().equals("none") || sender.hasPermission(subCommand.getPermission());
    }

    protected boolean noMatch(CommandSender sender, String[] args){
        return false;
    }

    protected boolean noArgs(CommandSender sender){
        return false;
    }

    public List<SubCommand> getSubcommands(){
        return subcommands;
    }

    public void addSubcommands(SubCommand subCommand){
        subcommands.add(subCommand);
    }

    public String getLabel(){
        return label;
    }
}
