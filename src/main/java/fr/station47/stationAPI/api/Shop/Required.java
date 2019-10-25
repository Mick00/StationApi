package fr.station47.stationAPI.api.Shop;

import org.bukkit.entity.Player;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public abstract class Required {

    protected List<Requirement> req;

    public Required(){
        req = new ArrayList<>();
    }

    protected boolean passRequ(Player player){
        List<Requirement> unmet = req.stream()
                .filter(r->!r.test(player))
                .collect(Collectors.toList());
        if (unmet.size() > 0) {
            unmet.forEach(r->player.sendMessage(r.getErrorMsg()));
            return false;
        }
        return true;
    }

    public List<Requirement> getReq(){
        return req;
    }

    public void addRequirement(Requirement r){
        req.add(r);
    }

}
