package fr.station47.stationAPI.api.Shop;

import org.bukkit.entity.Player;

import java.util.function.Predicate;

public class Requirement {

    private Predicate<Player> test;
    private String label;
    private String errorMsg;

    public Requirement(Predicate<Player> test, String label, String errorMsg) {
        this.test = test;
        this.label = label;
        this.errorMsg = errorMsg;
    }

    public boolean test(Player p) {return test.test(p);}

    public Predicate getTest() {
        return test;
    }

    public String getLabel() {
        return label;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setTest(Predicate test) {
        this.test = test;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }
}
