package fr.station47.stationAPI.api;

import fr.station47.stationAPI.api.commands.MainCommand;
import fr.station47.stationAPI.api.commands.SubCommand;
import fr.station47.theme.Theme;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.java.JavaPlugin;

public class SAPICommand extends MainCommand {
    public SAPICommand() {
        super("sapi", StationAPI.instance);
        addSubcommands(new SubCommand("reload","reload; recharge le plugin","sapi.reload") {
            @Override
            public boolean executeCommand(CommandSender sender, String[] args) {
                StationAPI.reload();
                Theme.sendMessage(sender,"Le plugin a été rechargé");
                return true;
            }
        });
    }
}
