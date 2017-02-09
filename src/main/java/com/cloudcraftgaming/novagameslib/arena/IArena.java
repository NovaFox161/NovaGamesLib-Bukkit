package com.cloudcraftgaming.novagameslib.arena;

import com.cloudcraftgaming.novagameslib.game.GameGoal;
import com.cloudcraftgaming.novagameslib.game.GameState;
import com.cloudcraftgaming.novagameslib.game.WinType;
import com.cloudcraftgaming.novagameslib.player.PlayerStats;
import com.cloudcraftgaming.novagameslib.scoreboard.BoardManager;
import com.cloudcraftgaming.novagameslib.team.Team;
import com.cloudcraftgaming.novagameslib.team.Teams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Nova Fox on 2/9/17.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
@SuppressWarnings({"UnnecessaryInterfaceModifier", "unused"})
public interface IArena {

    //Getters
    /**
     * Gets and returns the arena's numerical ID.
     * @return The arena's ID.
     */
    public int getId();

    /**
     * Gets the name of the minigame using this arena.
     * @return The name of the minigame using this arena.
     */
    public String getGameName();

    /**
     * Gets all players in the arena
     * @return a list of players in the arena.
     */
    public ArrayList<UUID> getPlayers();

    /**
     * Gets the current spectators of the arena
     * @return An ArrayList of spectators.
     */
    public ArrayList<UUID> getSpectators();

    /**
     * Gets the amount of players in the game.
     * @return The current player count.
     */
    public int getPlayerCount();

    /**
     * Gets the current status of the arena.
     * Use {@link #getGameState()} for the game's current state.
     * @return the current arena status.
     */
    public ArenaStatus getArenaStatus();

    /**
     * Gets the current state of the game.
     * Use {@link #getArenaStatus()} for the arena's current status.
     * @return the current arena status.
     */
    public GameState getGameState();

    /**
     * Gets the GameGoal of the minigame. In other words the target goal of the game.
     * @return The GameGoal of the minigame.
     */
    public GameGoal getGameGoal();

    /**
     * Gets whether or not player may join the arena.
     * @return <code>true</code> if players can join, else <code>false</code>.
     */
    public Boolean isJoinable();

    /**
     * Gets the arena's wait id. This ID is assigned and used by the time keeper.
     * It should not be edited unless you know what you are doing.
     * @return The arena's current wait id.
     */
    public Integer getWaitId();

    /**
     * Gets the arena's start id. This ID is assigned and used by the time keeper.
     * It should not be edited unless you know what you are doing.
     * @return The arena's current start id.
     */
    public Integer getStartId();

    /**
     * Gets the arena's game id. This ID is assigned and used by the time keeper.
     * It should not be edited unless you know what you are doing.
     * @return The arena's current game id.
     */
    public Integer getGameId();

    /**
     * Gets if the arena is using teams.
     * @return <code>true</code> if using teams, else <code>false</code>.
     */
    public Boolean useTeams();

    /**
     * Gets the arena's teams object.
     * @return The arena's teams object.
     */
    public Teams getTeams();

    /**
     * Gets the arena's scoreboard.
     * @return The arena's scoreboard.
     */
    public BoardManager getScoreboardManager();

    /**
     * Gets the specified player's stats.
     * @param uuid The UUID of the player whose stats to get.
     * @return The specified player's stats.
     */
    public PlayerStats getPlayerStats(UUID uuid);

    /**
     * Gets a {@link HashMap} of players' stats.
     * @return A {@link HashMap} of players's stats.
     */
    public HashMap<UUID, PlayerStats> getPlayerStats();

    /**
     * The WinType of the minigame, in other words the way in which the minigame ended.
     * @return The WinTye of the minigame.
     */
    public WinType getWinType();

    /**
     * Gets an ArrayList of all players that won.
     * @return An ArrayList of all players that won.
     */
    public ArrayList<UUID> getWinningPlayers();

    /**
     * Gets an ArrayList of all Teams that won.
     * @return An ArrayList of all Teams that won.
     */
    public ArrayList<Team> getWinningTeams();

    /**
     * Gets whether ot not the arena currently has the Main Scoreboard displayed.
     * Ignore if not using teams.
     * @return Whether or not the Main Scoreboard is displayed.
     */
    public Boolean isOnMainBoard();

    //Setters
    /**
     * Sets the player count in game.
     * @param _playerCount the new amount of players in game.
     */
    public void setPlayerCount(int _playerCount);

    /**
     * Sets the arena status for the arena.
     * @param _arenaStatus the new arena status for the arena.
     */
    public void setArenaStatus(ArenaStatus _arenaStatus);

    /**
     * Sets the game state for the arena.
     * @param _gameState the new game state for the arena.
     */
    public void setGameState(GameState _gameState);

    /**
     * Sets the GameGoal of the minigame. In other words the goal of the game.
     * @param _gameGoal The GameGoal of the minigame.
     */
    public void setGameGoal(GameGoal _gameGoal);

    /**
     * Sets whether or not the arena is currently joinable.
     * @param _joinable Whether or not players can join the arena.
     */
    public void setJoinable(Boolean _joinable);

    /**
     * Sets the arena's wait id.  This ID is assigned and used by the time keeper.
     * It should not be edited unless you know what you are doing.
     * @param _waitId The new wait id.
     */
    public void setWaitId(Integer _waitId);

    /**
     * Sets the arena's start id. This ID is assigned and used by the time keeper.
     * It should not be edited unless you know what you are doing.
     * @param _startId The new start id.
     */
    public void setStartId(Integer _startId);

    /**
     * Sets the arena's game id. This ID is assigned and used by the time keeper.
     * It should not be edited unless you know what you are doing.
     * @param _gameId The new game id.
     */
    public void setGameId(Integer _gameId);

    /**
     * Adds the specified player's {@link PlayerStats}.
     * @param _stats The {@link PlayerStats} of the player with their UUID respectively.
     */
    public void setPlayerStats(PlayerStats _stats);

    /**
     * Adds the specified player's {@link PlayerStats} and allows for manually overwriting if required.
     * @param _stats The {@link PlayerStats} of the player with their UUID respectively.
     * @param overwrite Whether or not to manually overwrite the stats if they already exist.
     */
    public void setPlayerStats(PlayerStats _stats, Boolean overwrite);

    /**
     * Sets the WinType of the minigame, in other words the way in which the game ended.
     * @param _winType The WinType of the minigame.
     */
    public void setWinType(WinType _winType);

    /**
     * Sets whether or not the arena currently has the Main Scoreboard displayed.
     * Ignore if not using teams.
     * @param value Whether or not the Main Scoreboard is displayed.
     */
    public void setIsOnMainBoard(Boolean value);
}