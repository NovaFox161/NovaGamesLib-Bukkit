package com.cloudcraftgaming.novagameslib.mechanics;

import com.cloudcraftgaming.novagameslib.arena.Arena;
import com.cloudcraftgaming.novagameslib.arena.ArenaManager;
import com.cloudcraftgaming.novagameslib.data.ArenaDataManager;
import com.cloudcraftgaming.novagameslib.event.mechanics.WorldTimeUpdateEvent;
import com.cloudcraftgaming.novagameslib.time.TimeManager;
import com.sun.istack.internal.Nullable;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.HashMap;

/**
 * Created by Nova Fox on 11/19/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class CustomDayNightCycle {
    private static CustomDayNightCycle instance;

    private final HashMap<String, Long> worldTimes = new HashMap<>();

    private CustomDayNightCycle() {} //Prevent initialization.

    public static CustomDayNightCycle getInstance() {
        if (instance == null) {
            instance = new CustomDayNightCycle();
        }
        return instance;
    }

    /**
     * Starts the custom day light cycle for the specified arena.
     * Only call this once (at the start of the game)!!! Once called, NovaGames will handle the rest!
     * !!!! DO NOT USE IF MORE THAN ONE (1) ARENA IS IN THE SAME WORLD !!!!
     * @param id The id of the arena.
     * @param timeOverride The Bukkit time to set the world to (in ticks). If null, will set to <code>12000</code>.
     */
    public void startCustomDayLightCycle(int id, @Nullable Long timeOverride) {
        if (ArenaManager.getManager().arenaLoaded(id)) {
            Arena arena = ArenaManager.getManager().getArena(id);
            Location arenaLoc = ArenaDataManager.getMainSpawnLocation(id);
            if (timeOverride == null) {
                arenaLoc.getWorld().setTime(12000);
            } else {
                arenaLoc.getWorld().setTime(timeOverride);
            }
            if (worldTimes.containsKey(arenaLoc.getWorld().getName())) {
                worldTimes.remove(arenaLoc.getWorld().getName());
            }
            worldTimes.put(arenaLoc.getWorld().getName(), arenaLoc.getWorld().getTime());

            //Call TimerManager to start the custom world time clock.
            TimeManager.getManager().startCustomWorldTime(id, arena.getGameId());
        }
    }

    /**
     * Sets the specified arena's world to the right time.
     * DO NOT CALL THIS!!!NovaGames will handle this!!!!
     * @param id The id of the arena.
     * @param world The world of the arena.
     */
    public void updateTimeToCustomTime(int id, World world) {
        Integer ticksPerSecond = getCustomTicksPerSecond(id);
        Long oldTime = 0L;
        world.setTime(worldTimes.get(world.getName()) + ticksPerSecond);
        if (worldTimes.containsKey(world.getName())) {
            oldTime = worldTimes.get(world.getName());
            worldTimes.remove(world.getName());
        }
        worldTimes.put(world.getName(), world.getTime());

        //Call worldTimeUpdateEvent
        WorldTimeUpdateEvent event = new WorldTimeUpdateEvent(id, world, oldTime);
        Bukkit.getServer().getPluginManager().callEvent(event);
    }

    /**
     * Gets the ticks per second a world should have based on the custom arena Day/Night Cycle.
     * This will calculate this for you so you do not have to figure out the maths for this.
     * @param id The id of the arena.
     * @return The number of ticks there should be in a second for the arena.
     */
    public Integer getCustomTicksPerSecond(int id) {
        Integer customDaylightCycleTime = ArenaDataManager.getDayLength(id) + ArenaDataManager.getNightLength(id);
        Integer totalCustomSeconds = customDaylightCycleTime * 60;
        Integer multiplier = 1200 / totalCustomSeconds;
        return 20 * multiplier;
    }
}