package com.cloudcraftgaming.novagameslib.api.game;

import com.cloudcraftgaming.novagameslib.NovaGamesLib;
import com.cloudcraftgaming.novagameslib.api.arena.ArenaBase;
import com.cloudcraftgaming.novagameslib.api.arena.ArenaManager;
import com.cloudcraftgaming.novagameslib.api.arena.ArenaStatus;
import com.cloudcraftgaming.novagameslib.api.database.DatabaseManager;
import com.cloudcraftgaming.novagameslib.api.event.minigame.*;
import com.cloudcraftgaming.novagameslib.api.mechanics.Rewards;
import com.cloudcraftgaming.novagameslib.api.player.PlayerStats;
import com.cloudcraftgaming.novagameslib.api.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

import static com.cloudcraftgaming.novagameslib.NovaGamesLib.plugin;

/**
 * Created by Nova Fox on 11/19/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
@SuppressWarnings("unused")
public class MinigameEventHandler {
    /**
     * Called when a player joins a minigame.
     * This will call the {@link MinigameJoinEvent} event and execute unless cancelled.
     * @param player The player joining.
     * @param id The id of the arena the player is joining.
     * @return <code>true</code> if not cancelled and successful, else <code>false</code>.
     */
    public static Boolean joinMinigame(Player player, int id) {
        if (ArenaManager.getManager().arenaLoaded(id)) {
            ArenaBase arenaBase = ArenaManager.getManager().getArena(id);
            arenaBase.getPlayers().add(player.getUniqueId());
            arenaBase.setPlayerCount(arenaBase.getPlayerCount() + 1);
            MinigameJoinEvent event = new MinigameJoinEvent(player, id);
            Bukkit.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                //ArenaDataManager.updateArenaInfo(id);
                if (FileManager.verbose()) {
                    plugin.getLogger().info(player.getName() + " has joined arenaBase " + id);
                }
                arenaBase.setPlayerStats(new PlayerStats(player.getUniqueId(), arenaBase.getGameName()), false);
                return true;
            } else {
                if (arenaBase.getPlayers().contains(player.getUniqueId())) {
                    arenaBase.getPlayers().remove(player.getUniqueId());
                }
                arenaBase.setPlayerCount(arenaBase.getPlayerCount() - 1);
            }
        }
        return false;
    }

    /**
     * Called when a player spectates an arena.
     * This will call the {@link MinigameSpectateEvent} event and execute unless cancelled.
     * @param player The player spectating.
     * @param id The id of the arena the player is spectating.
     * @return <code>true</code> if not cancelled and successful, else <code>false</code>.
     */
    public static Boolean spectateMinigame(Player player, int id) {
        if (ArenaManager.getManager().arenaLoaded(id)) {
            ArenaBase arenaBase = ArenaManager.getManager().getArena(id);
            arenaBase.getSpectators().add(player.getUniqueId());
            MinigameSpectateEvent event = new MinigameSpectateEvent(player, id);
            Bukkit.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                if (FileManager.verbose()) {
                    plugin.getLogger().info(player.getName() + " is spectating arenaBase " + id);
                }
                return true;
            } else {
                if (arenaBase.getSpectators().contains(player.getUniqueId())) {
                    arenaBase.getSpectators().remove(player.getUniqueId());
                }
            }
        }
        return false;
    }

    /**
     * Called when a player quits a minigame.
     *  This will call the {@link MinigameQuitEvent} event and execute.
     * @param player The player quitting the minigame.
     * @return <code>true</code> if not cancelled and successful, else <code>false</code>.
     */
    public static Boolean quitMinigame(Player player) {
        ArenaBase arenaBase = ArenaManager.getManager().getArena(player);
        MinigameQuitEvent event = new MinigameQuitEvent(player, arenaBase.getId());
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (arenaBase.getPlayers().contains(player.getUniqueId())) {
            arenaBase.getPlayers().remove(player.getUniqueId());
            arenaBase.setPlayerCount(arenaBase.getPlayerCount() - 1);
            if (FileManager.verbose()) {
                plugin.getLogger().info(player.getName() + " has quit arenaBase " + arenaBase.getId());
            }
            if (NovaGamesLib.plugin.getConfig().getString("Stats.Track.Enabled").equalsIgnoreCase("True")) {
                if (arenaBase.getArenaStatus().equals(ArenaStatus.INGAME) || arenaBase.getGameState().equals(GameState.INGAME)) {
                    UUID uuid = player.getUniqueId();
                    PlayerStats oldStats = DatabaseManager.getManager().getPlayerStats(uuid, arenaBase.getGameName());
                    PlayerStats newStats = arenaBase.getPlayerStats(uuid);

                    //Player quit, lost by default.
                    newStats.setLoses(1);

                    newStats.calculate(oldStats);
                    DatabaseManager.getManager().addPlayerStats(newStats);
                }
            }
        } else if (arenaBase.getSpectators().contains(player.getUniqueId())) {
            arenaBase.getSpectators().remove(player.getUniqueId());
            if (FileManager.verbose()) {
                plugin.getLogger().info(player.getName() + "no longer spectating arenaBase " + arenaBase.getId());
            }
        }
        return true;
    }

    /**
     * Called when a minigame is to start.
     * This will call the {@link MinigameStartEvent} event and execute.
     * @param id The id of the arena that is starting.
     * @return <code>true</code> if not cancelled and successful, else <code>false</code>.
     */
    public static Boolean startMinigame(Integer id) {
        MinigameStartEvent event = new MinigameStartEvent(ArenaManager.getManager().getArena(id));
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            if (FileManager.verbose()) {
                plugin.getLogger().info("Minigame in arena " + id + " has started.");
            }
            //ArenaDataManager.updateArenaInfo(id);
            for (UUID pId : event.getArenaBase().getPlayers()) {
                PlayerStats newStats = new PlayerStats(pId, event.getGameName());
                newStats.setTimesPlayed(1);
                event.getArenaBase().setPlayerStats(newStats);
            }
        }
        return false;
    }

    /**
     * Called when a minigame ends.
     * This will call the {@link MinigameEndEvent} event and execute.
     * @param id The id of the arena that has ended.
     * @return <code>true</code> if not cancelled and successful, else <code>false</code>.
     */
    public static Boolean endMinigame(Integer id) {
        ArenaBase arenaBase = ArenaManager.getManager().getArena(id);
        if (NovaGamesLib.plugin.getConfig().getString("Stats.Track.Enabled").equalsIgnoreCase("True")) {
            if (arenaBase.getArenaStatus().equals(ArenaStatus.INGAME) || arenaBase.getGameState().equals(GameState.INGAME)) {
                //Calculate stats
                for (UUID pId : arenaBase.getPlayers()) {
                    PlayerStats oldStats = DatabaseManager.getManager().getPlayerStats(pId, arenaBase.getGameName());
                    PlayerStats newStats = arenaBase.getPlayerStats(pId);

                    //Check wins, as they are already calculated by this point in time.
                    if (arenaBase.getWinningPlayers().contains(pId)) {
                        newStats.setWins(1);
                    } else {
                        newStats.setLoses(1);
                    }
                    newStats.calculate(oldStats);
                    DatabaseManager.getManager().addPlayerStats(newStats);
                }

                //Finish up.
                Rewards.distrobuteAwards(id);
            }
        }
        MinigameEndEvent event = new MinigameEndEvent(arenaBase);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (FileManager.verbose()) {
            NovaGamesLib.plugin.getLogger().info("Minigame in arenaBase " + id + " has ended.");
        }
        //ArenaDataManager.updateArenaInfo(id);
        return true;
    }
}