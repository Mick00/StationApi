package fr.station47.stationAPI.api.runnableChain;

import fr.station47.stationAPI.api.Task;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.scheduler.BukkitRunnable;

import java.util.ArrayDeque;
import java.util.Queue;

public class RunnableChain {
    private Plugin plugin;
    private Queue<BukkitRunnable> tasks;
    private boolean exceptionFlag = false;
    public RunnableChain(Plugin plugin){
        this.plugin = plugin;
        tasks = new ArrayDeque<>();
    }

    public void startAsync(){
        nextAsync();
    }

    public RunnableChain thenAsync(TaskNoCatch task){
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    if(!exceptionFlag) {
                        task.execute();
                    }
                } catch (Exception ex){
                    exceptionFlag = true;
                }
                nextAsync();
            }
        };
        tasks.add(runnable);
        return this;
    }

    public RunnableChain then(TaskNoCatch task){
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    if(!exceptionFlag) {
                        task.execute();
                    }
                } catch (Exception ex){
                    exceptionFlag = true;
                }
                nextSync();
            }
        };
        tasks.add(runnable);
        return this;
    }

    public RunnableChain catchExceptionAsync(TaskNoCatch task){
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    if (exceptionFlag){
                        task.execute();
                        exceptionFlag = false;
                    }
                } catch (Exception ex){
                    exceptionFlag = true;
                }
                nextAsync();

            }
        };
        tasks.add(runnable);
        return this;
    }

    public RunnableChain catchException(TaskNoCatch task){
        BukkitRunnable runnable = new BukkitRunnable() {
            @Override
            public void run() {
                try {
                    if (exceptionFlag){
                        task.execute();
                        exceptionFlag = false;
                    }

                } catch (Exception ex){
                    exceptionFlag = true;
                }
                nextSync();
            }
        };
        tasks.add(runnable);
        return this;
    }

    private void nextAsync(){
        if (!tasks.isEmpty()){
            Bukkit.getLogger().info(tasks.size()+"");
            tasks.poll().runTaskAsynchronously(plugin);
        }
    }

    private void nextSync(){
        if (!tasks.isEmpty()){
            System.out.println(tasks.size());
            tasks.poll().runTask(plugin);
        }
    }
}
