package fr.station47.stationAPI.api.destinations;

import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.entity.Player;

public class Destination {

    private String name;
    private Location location;
    private String permission;
    private boolean hidden;
    private double price;

    public Destination(String name, Location location){
        this.setName(name);
        this.setLocation(location);
        permission = "none";
        setPrice(100D);
    }

    public Destination(String name, Location location, String permission){
        this.setName(name);
        this.setLocation(location);
        this.setPermission(permission);
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Location getLocation() {
        return location;
    }

    public void setLocation(Location location) {
        this.location = location;
    }

    public String getPermission() {
        return permission;
    }

    public void setPermission(String permission) {
        this.permission = permission;
    }

    public boolean isHidden() {
        return hidden;
    }

    public void setHidden(boolean hidden) {
        this.hidden = hidden;
    }

    public boolean teleport(Player player){
        if (player.hasPermission(permission)){
            player.teleport(location);
            return true;
        }
        return false;
    }

    @Override
    public String toString() {
        String[] data = new String[] {
                name,
                permission,
                String.valueOf(hidden),
                location.getWorld().getName(),
                String.valueOf(location.getBlockX()),
                String.valueOf(location.getBlockY()),
                String.valueOf(location.getBlockZ()),
                String.valueOf(location.getPitch()),
                String.valueOf(location.getYaw()),
                String.valueOf(price)
        };
        return String.join("`", data);
    }

    public static Destination fromString(String s){
        String[] data = s.split("`");
        Destination d = new Destination(data[0],new Location(Bukkit.getWorld(data[3]),Double.valueOf(data[4]),Double.valueOf(data[5]),Double.valueOf(data[6]), Float.valueOf(data[7]),Float.valueOf(data[8])));
        d.setHidden(Boolean.valueOf(data[2]));
        d.setPermission(data[1]);
        d.setPrice(Double.valueOf(data[9]));
        return d;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
