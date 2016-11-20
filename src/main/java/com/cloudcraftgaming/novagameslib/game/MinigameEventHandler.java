package com.cloudcraftgaming.novagameslib.game;

import com.cloudcraftgaming.novagameslib.NovaGamesLib;
import com.cloudcraftgaming.novagameslib.arena.Arena;
import com.cloudcraftgaming.novagameslib.arena.ArenaManager;
import com.cloudcraftgaming.novagameslib.event.minigame.*;
import com.cloudcraftgaming.novagameslib.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

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
            Arena arena = ArenaManager.getManager().getArena(id);
            arena.getPlayers().add(player.getUniqueId());
            arena.setPlayerCount(arena.getPlayerCount() + 1);
            MinigameJoinEvent event = new MinigameJoinEvent(player, id);
            Bukkit.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                //ArenaDataManager.updateArenaInfo(id);
                //DatabaseManager.setDefaultStats(OYAGamesManager.plugin.getDatabaseInfo(), player.getUniqueId(), arena.getGameName());
                if (FileManager.verbose()) {
                    plugin.getLogger().info(player.getName() + " has joined arena " + id);
                }
                return true;
            } else {
                if (arena.getPlayers().contains(player.getUniqueId())) {
                    arena.getPlayers().remove(player.getUniqueId());
                }
                arena.setPlayerCount(arena.getPlayerCount() - 1);
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
            Arena arena = ArenaManager.getManager().getArena(id);
            arena.getSpectators().add(player.getUniqueId());
            MinigameSpectateEvent event = new MinigameSpectateEvent(player, id);
            Bukkit.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                if (FileManager.verbose()) {
                    plugin.getLogger().info(player.getName() + " is spectating arena " + id);
                }
                return true;
            } else {
                if (arena.getSpectators().contains(player.getUniqueId())) {
                    arena.getSpectators().remove(player.getUniqueId());
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
        Arena arena = ArenaManager.getManager().getArena(player);
        MinigameQuitEvent event = new MinigameQuitEvent(player, arena.getId());
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (arena.getPlayers().contains(player.getUniqueId())) {
            arena.getPlayers().remove(player.getUniqueId());
            arena.setPlayerCount(arena.getPlayerCount() - 1);
            if (FileManager.verbose()) {
                plugin.getLogger().info(player.getName() + " has quit arena " + arena.getId());
            }
            /* UNBLOCK ONCE STATS TRACKING IS ADDED
            if (NovaGamesLib.plugin.getConfig().getString("Stats.Track.Enabled").equalsIgnoreCase("True")) {
                if (arena.getArenaStatus().equals(ArenaStatus.INGAME) || arena.getGameState().equals(GameState.INGAME)) {
                    UUID uuid = player.getUniqueId();
                    DatabaseManager.updateAllStats(OYAGamesManager.plugin.getDatabaseInfo(), uuid, arena.getGameName(), arena.getKills(player),
                            arena.getDeaths(player), arena.getScores(player), false);
                }
            }
            */
        } else if (arena.getSpectators().contains(player.getUniqueId())) {
            arena.getSpectators().remove(player.getUniqueId());
            if (FileManager.verbose()) {
                plugin.getLogger().info(player.getName() + "no longer spectating arena " + arena.getId());
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
        Arena arena = ArenaManager.getManager().getArena(id);
        /* UNBLOCK ONCE STATS TRACKING IS ADDED
        if (NovaGamesLib.plugin.getConfig().getString("Stats.Track.Enabled").equalsIgnoreCase("True")) {
            if (arena.getArenaStatus().equals(ArenaStatus.INGAME) || arena.getGameState().equals(GameState.INGAME)) {
                for (UUID pId : arena.getPlayers()) {
                    Player p = Bukkit.getPlayer(pId);
                    Boolean hasWon = arena.getWinningPlayers().contains(pId);
                    DatabaseManager.updateAllStats(OYAGamesManager.plugin.getDatabaseInfo(), pId, arena.getGameName(), arena.getKills(p),
                            arena.getDeaths(p), arena.getScores(p), hasWon);
                }
            }
        }
        */
        MinigameEndEvent event = new MinigameEndEvent(arena);
        Bukkit.getServer().getPluginManager().callEvent(event);
        if (FileManager.verbose()) {
            NovaGamesLib.plugin.getLogger().info("Minigame in arena " + id + " has ended.");
        }
        //ArenaDataManager.updateArenaInfo(id);
        return true;
    }
}