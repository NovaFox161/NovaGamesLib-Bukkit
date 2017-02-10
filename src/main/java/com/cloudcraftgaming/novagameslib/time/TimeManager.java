package com.cloudcraftgaming.novagameslib.time;

import com.cloudcraftgaming.novagameslib.NovaGamesLib;
import com.cloudcraftgaming.novagameslib.arena.ArenaBase;
import com.cloudcraftgaming.novagameslib.arena.ArenaManager;
import com.cloudcraftgaming.novagameslib.arena.ArenaStatus;
import com.cloudcraftgaming.novagameslib.data.ArenaDataManager;
import com.cloudcraftgaming.novagameslib.event.time.*;
import com.cloudcraftgaming.novagameslib.game.GameState;
import com.cloudcraftgaming.novagameslib.game.MinigameEventHandler;
import com.cloudcraftgaming.novagameslib.mechanics.CustomDayNightCycle;
import com.cloudcraftgaming.novagameslib.utils.FileManager;

import java.util.Random;

import static org.bukkit.Bukkit.getServer;

/**
 * Created by Nova Fox on 11/19/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class TimeManager {
    private static TimeManager instance;

    private TimeManager() {} //Prevent initialization

    /**
     * Gets the current instance of TimeManager as it is a singleton.
     * @return The current instance of TimeManager.
     */
    public static TimeManager getManager() {
        if (instance == null) {
            instance = new TimeManager();
        }
        return instance;
    }

    /**
     * Starts the Player Wait Delay for the arena.
     * This will call the {@link WaitDelayStartEvent} event.
     * @param id The id of the arena.
     */
    public void startWaitDelay(final int id) {
        if (ArenaManager.getManager().arenaLoaded(id)) {
            ArenaBase arenaBase = ArenaManager.getManager().getArena(id);
            if (arenaBase.getWaitId() == 0 && arenaBase.getArenaStatus().equals(ArenaStatus.WAITING_FOR_PLAYERS)) {
                Integer waitDelay = ArenaDataManager.getWaitDelay(id);
                Random rn = new Random();
                final Integer waitId = rn.nextInt(99999998) + 1;
                arenaBase.setStartId(0);
                arenaBase.setWaitId(waitId);
                WaitDelayStartEvent event = new WaitDelayStartEvent(arenaBase, waitDelay);
                getServer().getPluginManager().callEvent(event);

                final Boolean goToStart = event.goToStart();
                if (!event.isCancelled()) {
                    if (FileManager.verbose()) {
                        NovaGamesLib.plugin.getLogger().info("Starting wait delay for arenaBase " + id);
                    }
                    getServer().getScheduler().scheduleSyncDelayedTask(NovaGamesLib.plugin, new Runnable() {
                        @Override
                        public void run() {
                            if (ArenaManager.getManager().arenaLoaded(id)) {
                                ArenaBase arenaBase1 = ArenaManager.getManager().getArena(id);
                                if (arenaBase1.getWaitId().equals(waitId) && arenaBase1.getArenaStatus().equals(ArenaStatus.WAITING_FOR_PLAYERS)) {
                                    //Wait time over, begin start delay.
                                    if (FileManager.verbose()) {
                                        NovaGamesLib.plugin.getLogger().info("Wait delay over for arenaBase " + id);
                                    }
                                    if (goToStart) {
                                        startStartDelay(id);
                                    }
                                }
                            }
                        }
                    }, 20L * event.getWaitDelay());
                } else {
                    arenaBase.setWaitId(0);
                    arenaBase.setStartId(0);
                    if (event.goToStart()) {
                        startStartDelay(id);
                    }
                }
            }
        }
    }

    /**
     * Cancels the Player Wait Delay (if going) for the arena.
     * This will call the {@link WaitDelayCancelEvent} event.
     * @param id The id of the arena.
     */
    public void cancelWaitDelay(int id) {
        if (ArenaManager.getManager().arenaLoaded(id)) {
            ArenaBase arenaBase = ArenaManager.getManager().getArena(id);

            WaitDelayCancelEvent event = new WaitDelayCancelEvent(arenaBase);
            getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                arenaBase.setWaitId(0);
                arenaBase.setStartId(0);
                if (FileManager.verbose()) {
                    NovaGamesLib.plugin.getLogger().info("Wait delay cancelled for arenaBase " + id);
                }
            }
        }
    }

    /**
     * Starts the Start Delay for the arena before game start.
     * This will call the {@link StartDelayStartEvent} event.
     * @param id The id of the arena.
     */
    public void startStartDelay(final int id) {
        if (ArenaManager.getManager().arenaLoaded(id)) {
            ArenaBase arenaBase = ArenaManager.getManager().getArena(id);
            if (arenaBase.getStartId() == 0 && arenaBase.getArenaStatus().equals(ArenaStatus.WAITING_FOR_PLAYERS)) {
                Integer startDelay = ArenaDataManager.getStartDelay(id);
                Random rn = new Random();
                final Integer startId = rn.nextInt(99999998) + 1;
                arenaBase.setWaitId(0);
                arenaBase.setStartId(startId);
                StartDelayStartEvent event = new StartDelayStartEvent(arenaBase, startDelay);
                getServer().getPluginManager().callEvent(event);

                if (!event.isCancelled()) {
                    if (FileManager.verbose()) {
                        NovaGamesLib.plugin.getLogger().info("Starting start delay for arenaBase " + id);
                    }
                    arenaBase.setGameState(GameState.STARTING);
                    arenaBase.setArenaStatus(ArenaStatus.STARTING);
                    final Boolean goToGameStart = event.goToGameStart();
                    getServer().getScheduler().scheduleSyncDelayedTask(NovaGamesLib.plugin, new Runnable() {
                        @Override
                        public void run() {
                            if (ArenaManager.getManager().arenaLoaded(id)) {
                                ArenaBase arenaBase1 = ArenaManager.getManager().getArena(id);
                                if (arenaBase1.getStartId().equals(startId) && arenaBase1.getArenaStatus().equals(ArenaStatus.STARTING)) {
                                    if (FileManager.verbose()) {
                                        NovaGamesLib.plugin.getLogger().info("Start delay over for arenaBase " + id);
                                    }
                                    if (goToGameStart) {
                                        MinigameEventHandler.startMinigame(arenaBase1.getId());
                                    }
                                }
                            }
                        }
                    }, 20L * event.getStartDelay());
                } else {
                    arenaBase.setWaitId(0);
                    arenaBase.setStartId(0);
                    if (event.goToGameStart()) {
                        MinigameEventHandler.startMinigame(arenaBase.getId());
                    }
                }
            }
        }
    }

    /**
     * Cancels the Start Delay (if going) for the arena.
     * This will call the {@link StartDelayCancelEvent} event.
     * @param id The id of the arena.
     */
    public void cancelStartDelay(int id) {
        if (ArenaManager.getManager().arenaLoaded(id)) {
            ArenaBase arenaBase = ArenaManager.getManager().getArena(id);

            StartDelayCancelEvent event = new StartDelayCancelEvent(arenaBase);
            getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                arenaBase.setWaitId(0);
                arenaBase.setStartId(0);
                if (FileManager.verbose()) {
                    NovaGamesLib.plugin.getLogger().info("Start delay cancelled for arenaBase " + id);
                }
            }
        }
    }

    /**
     * Starts the Game Timer for the arena.
     * This will call the {@link GameTimerStartEvent} event on start.
     * Once the Game Timer is over, this will call the {@link GameTimerEndEvent} event.
     * @param id The id of the arena.
     */
    public void startGameTimer(final int id) {
        if (ArenaManager.getManager().arenaLoaded(id)) {
            ArenaBase arenaBase = ArenaManager.getManager().getArena(id);
            if (arenaBase.getGameId() == 0 && arenaBase.getArenaStatus().equals(ArenaStatus.INGAME)) {
                Integer gameTime = ArenaDataManager.getGameLength(id);
                Random rn = new Random();
                final Integer gameId = rn.nextInt(99999998) + 1;
                arenaBase.setStartId(0);
                arenaBase.setGameId(gameId);
                GameTimerStartEvent startEvent = new GameTimerStartEvent(arenaBase, gameTime);
                getServer().getPluginManager().callEvent(startEvent);

                if (!startEvent.isCancelled()) {
                    if (FileManager.verbose()) {
                        NovaGamesLib.plugin.getLogger().info("Starting game timer for arenaBase " + id);
                    }
                    getServer().getScheduler().scheduleSyncDelayedTask(NovaGamesLib.plugin, new Runnable() {
                        @Override
                        public void run() {
                            if (ArenaManager.getManager().arenaLoaded(id)) {
                                ArenaBase arenaBase1 = ArenaManager.getManager().getArena(id);
                                if (arenaBase1.getGameId().equals(gameId) && arenaBase1.getArenaStatus().equals(ArenaStatus.INGAME)) {
                                    GameTimerEndEvent endEvent = new GameTimerEndEvent(arenaBase1);
                                    getServer().getPluginManager().callEvent(endEvent);
                                    if (FileManager.verbose()) {
                                        NovaGamesLib.plugin.getLogger().info("Game timer over for arenaBase id " + id);
                                    }
                                    if (endEvent.endGame()) {
                                        MinigameEventHandler.endMinigame(id);
                                    }
                                }
                            }
                        }
                    }, 20L * 60 * startEvent.getGameLength());
                } else {
                    arenaBase.setStartId(0);
                    arenaBase.setGameId(0);
                }
            }
        }
    }

    /**
     * Starts a one (1) second clock to update the arena's world's time.
     * !!!This should not be used outside of OYAGames-MANAGER, use {@link CustomDayNightCycle#startCustomDayLightCycle(int, Long)} to start the custom world time!!!
     * @param id The id of the arena.
     * @param gameId The current gameId of the arena.
     */
    public void startCustomWorldTime(final int id, final int gameId) {
        getServer().getScheduler().scheduleSyncDelayedTask(NovaGamesLib.plugin, new Runnable() {
            @Override
            public void run() {
                if (ArenaManager.getManager().arenaLoaded(id)) {
                    ArenaBase arenaBase = ArenaManager.getManager().getArena(id);
                    if (arenaBase.getGameState().equals(GameState.INGAME) && arenaBase.getGameId().equals(gameId)) {
                        //Update current world time.
                        CustomDayNightCycle.getInstance().updateTimeToCustomTime(id, ArenaDataManager.getMainSpawnLocation(id).getWorld());
                        startCustomWorldTime(id, gameId);
                    }
                }
            }
        }, 20L);
    }

    /**
     * Starts a clock to switch the scoreboards every x seconds. This should only be used if the arena is using teams!!
     * @param id The id of the arena.
     * @param gameId The current gameId of the arena.
     * @param switchTime The time (in seconds) between scoreboard switches.
     */
    private void startBoardSwitchTime(final int id, final int gameId, final Integer switchTime) {
        if (ArenaManager.getManager().arenaLoaded(id)) {
            ArenaBase arenaBase = ArenaManager.getManager().getArena(id);
            if (arenaBase.getGameId().equals(gameId) && arenaBase.getGameState().equals(GameState.INGAME)) {
                getServer().getScheduler().scheduleSyncDelayedTask(NovaGamesLib.plugin, new Runnable() {
                    @Override
                    public void run() {
                        if (ArenaManager.getManager().arenaLoaded(id)) {
                            ArenaBase arenaBase1 = ArenaManager.getManager().getArena(id);
                            if (arenaBase1.getGameId().equals(gameId) && arenaBase1.getGameState().equals(GameState.INGAME)) {
                                if (!arenaBase1.isOnMainBoard()) {
                                    //Switch to main board.
                                    startBoardSwitchTime(id, arenaBase1.getGameId(), switchTime);
                                } else {
                                    //Switch to team boards.
                                    startBoardSwitchTime(id, arenaBase1.getGameId(), switchTime);
                                }
                            }
                        }
                    }
                }, 20L * switchTime);
            }
        }
    }
}