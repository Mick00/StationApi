package fr.station47.stationAPI.api;

import de.slikey.effectlib.effect.WarpEffect;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scheduler.BukkitRunnable;

public class Warmup {

    private final Player player;
    private final boolean allowMov;
    private final Task task;
    private Task cancelledTask;
    private int left;
    private int initialX;
    private int initialY;
    private int initialZ;
    private boolean cancelled;

    public Warmup(Player player,Boolean allowMov, int from, Task task){
        cancelled = false;
        this.player = player;
        this.allowMov = allowMov;
        this.left = from;
        this.task = task;
        if (!allowMov){
            initialX = player.getLocation().getBlockX();
            initialY = player.getLocation().getBlockY();
            initialZ = player.getLocation().getBlockZ();
        }
    }


    public void start() {
        player.sendTitle("",ChatColor.YELLOW+""+ChatColor.BOLD+left+" secondes",3,22,0);
        this.left-=1;
        update();
    }

    public void update(){
        BukkitRunnable title = new BukkitRunnable() {
            @Override
            public void run() {

                if (!cancelled && (allowMov || (initialX == player.getLocation().getBlockX()
                                && initialY == player.getLocation().getBlockY()
                                && initialZ == player.getLocation().getBlockZ()))) {

                    if (left > 0) {
                        ChatColor color = ChatColor.YELLOW;
                        if (left == 2){
                            color = ChatColor.GOLD;
                        } else if (left == 1){
                            color = ChatColor.RED;
                        }
                        /*WarpEffect ve = new WarpEffect(CrafterClub.getEffectManager());
                        ve.setEntity(player);
                        ve.iterations = 5;
                        ve.start();*/
                        player.sendTitle("", color+""+ChatColor.BOLD+left + " secondes", 0, 22, 0);
                        left-=1;
                        update();
                    } else {
                        task.execute();
                    }
                } else {
                    cancelled = true;
                    player.sendTitle("", ChatColor.RED+"action cancellée",0,40,10);
                    cancelledTask.execute();
                }
            }
        };
        title.runTaskLater(StationAPI.instance,20);
    }

    public void cancel(boolean cancel){
        this.cancelled = cancel;
    }

    public boolean isCancelled(){
        return cancelled;
    }

    public void ifCancelled(Task task){
        this.cancelledTask = task;
    }
}
