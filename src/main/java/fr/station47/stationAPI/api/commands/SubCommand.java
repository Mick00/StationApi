package fr.station47.stationAPI.api.commands;

import org.bukkit.command.CommandSender;

import java.util.ArrayList;
import java.util.List;

public abstract class SubCommand {
    private String permission;
    private String usage;
    private String label;
    private List<String> aliases;

    public SubCommand(String label, String usage, String permission){
        this.label = label;
        this.usage = usage;
        this.permission = permission;
        aliases = new ArrayList<>();
    }

    public abstract boolean executeCommand(CommandSender sender, String[] args);

    public String getPermission() {
        return permission;
    }

    public String getUsage(){
        return this.usage;
    }

    public String getLabel(){
        return label;
    }

    public boolean isLabel(String c){
        return c.equals(label) || aliases.contains(c);
    }

    public void addAlias(String alias){
        aliases.add(alias);
    }
}
