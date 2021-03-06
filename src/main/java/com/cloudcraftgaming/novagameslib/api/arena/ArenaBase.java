package com.cloudcraftgaming.novagameslib.api.arena;

import com.cloudcraftgaming.novagameslib.api.game.GameGoal;
import com.cloudcraftgaming.novagameslib.api.game.GameState;
import com.cloudcraftgaming.novagameslib.api.game.WinType;
import com.cloudcraftgaming.novagameslib.api.player.PlayerStats;
import com.cloudcraftgaming.novagameslib.api.scoreboard.BoardManager;
import com.cloudcraftgaming.novagameslib.api.team.Team;
import com.cloudcraftgaming.novagameslib.api.team.Teams;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Nova Fox on 11/17/16.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ArenaBase {
	private final int id;
	private final String gameName;
	private final Boolean useTeamsVar;
	private final Teams teams;
	private final BoardManager boardManager;
	private final ArrayList<UUID> players = new ArrayList<>();
	private final ArrayList<UUID> spectators = new ArrayList<>();

	private int playerCount;

	private ArenaStatus arenaStatus;
	private GameState gameState;
	private GameGoal gameGoal;
	private Boolean joinable;

	//Scores and stats
	private final HashMap<UUID, PlayerStats> playerStats = new HashMap<>();

	private WinType winType;
	private final ArrayList<UUID> winningPlayers = new ArrayList<>();
	private final ArrayList<Team> winningTeams = new ArrayList<>();

	//Timer related
	private Integer waitId;
	private Integer startId;
	private Integer gameId;

	private Boolean isOnMainBoardBool;

	/**
	 * Creates an ArenaBase object.
	 * @param _id The ID of the arena.
	 * @param _gameName The name of the minigame using this arena.
	 */
	public ArenaBase(int _id, String _gameName, Boolean _useTeams) {
		id = _id;
		gameName = _gameName;
		playerCount = 0;
		arenaStatus = ArenaStatus.EMPTY;
		gameState = GameState.NOT_STARTED;
		joinable = true;
		waitId = 0;
		startId = 0;
		gameId = 0;
		useTeamsVar = _useTeams;
		teams = new Teams(id);
		isOnMainBoardBool = true;
		boardManager = new BoardManager(id, useTeamsVar);
	}

	//Getters
	/**
	 * Gets and returns the arena's numerical ID.
	 * @return The arena's ID.
	 */
	public int getId() {
		return id;
	}

	/**
	 * Gets the name of the minigame using this arena.
	 * @return The name of the minigame using this arena.
	 */
	public String getGameName() {
		return gameName;
	}

	/**
	 * Gets all players in the arena
	 * @return a list of players in the arena.
	 */
	public ArrayList<UUID> getPlayers() {
		return players;
	}

	/**
	 * Gets the current spectators of the arena
	 * @return An ArrayList of spectators.
	 */
	public ArrayList<UUID> getSpectators() {
		return spectators;
	}

	/**
	 * Gets the amount of players in the game.
	 * @return The current player count.
	 */
	public int getPlayerCount() {
		return playerCount;
	}

	/**
	 * Gets the current status of the arena.
	 * Use {@link #getGameState()} for the game's current state.
	 * @return the current arena status.
	 */
	public ArenaStatus getArenaStatus() {
		return arenaStatus;
	}

	/**
	 * Gets the current state of the game.
	 * Use {@link #getArenaStatus()} for the arena's current status.
	 * @return the current arena status.
	 */
	public GameState getGameState() {
		return gameState;
	}

	/**
	 * Gets the GameGoal of the minigame. In other words the target goal of the game.
	 * @return The GameGoal of the minigame.
	 */
	public GameGoal getGameGoal() {
		return gameGoal;
	}

	/**
	 * Gets whether or not player may join the arena.
	 * @return <code>true</code> if players can join, else <code>false</code>.
	 */
	public Boolean isJoinable() {
		return joinable;
	}

	/**
	 * Gets the arena's wait id. This ID is assigned and used by the time keeper.
	 * It should not be edited unless you know what you are doing.
	 * @return The arena's current wait id.
	 */
	public Integer getWaitId() {
		return waitId;
	}

	/**
	 * Gets the arena's start id. This ID is assigned and used by the time keeper.
	 * It should not be edited unless you know what you are doing.
	 * @return The arena's current start id.
	 */
	public Integer getStartId() {
		return startId;
	}

	/**
	 * Gets the arena's game id. This ID is assigned and used by the time keeper.
	 * It should not be edited unless you know what you are doing.
	 * @return The arena's current game id.
	 */
	public Integer getGameId() {
		return gameId;
	}

	/**
	 * Gets the ArenaBase object.
	 * Used when extending this class and needing to access this object directly.
	 * @return The ArenaBase object.
	 */
	public ArenaBase getArenaBase() {
		return this;
	}

	/**
	 * Gets if the arena is using teams.
	 * @return <code>true</code> if using teams, else <code>false</code>.
	 */
	public Boolean useTeams() {
		return useTeamsVar;
	}

	/**
	 * Gets the arena's teams object.
	 * @return The arena's teams object.
	 */
	public Teams getTeams() {
		return teams;
	}

	/**
	 * Gets the arena's scoreboard.
	 * @return The arena's scoreboard.
	 */
	public BoardManager getScoreboardManager() {
		return boardManager;
	}

	/**
	 * Gets the specified player's stats.
	 * @param uuid The UUID of the player whose stats to get.
	 * @return The specified player's stats.
	 */
	public PlayerStats getPlayerStats(UUID uuid) {
		return playerStats.get(uuid);
	}

	/**
	 * Gets a {@link HashMap} of players' stats.
	 * @return A {@link HashMap} of players's stats.
	 */
	public HashMap<UUID, PlayerStats> getPlayerStats() {
		return playerStats;
	}

	/**
	 * The WinType of the minigame, in other words the way in which the minigame ended.
	 * @return The WinTye of the minigame.
	 */
	public WinType getWinType() {
		return winType;
	}

	/**
	 * Gets an ArrayList of all players that won.
	 * @return An ArrayList of all players that won.
	 */
	public ArrayList<UUID> getWinningPlayers() {
		return winningPlayers;
	}

	/**
	 * Gets an ArrayList of all Teams that won.
	 * @return An ArrayList of all Teams that won.
	 */
	public ArrayList<Team> getWinningTeams() {
		return winningTeams;
	}

	/**
	 * Gets whether ot not the arena currently has the Main Scoreboard displayed.
	 * Ignore if not using teams.
	 * @return Whether or not the Main Scoreboard is displayed.
	 */
	public Boolean isOnMainBoard() {
		return isOnMainBoardBool;
	}

	//Setters
	/**
	 * Sets the player count in game.
	 * @param _playerCount the new amount of players in game.
	 */
	public void setPlayerCount(int _playerCount) {
		playerCount = _playerCount;
	}

	/**
	 * Sets the arena status for the arena.
	 * @param _arenaStatus the new arena status for the arena.
	 */
	public void setArenaStatus(ArenaStatus _arenaStatus) {
		arenaStatus = _arenaStatus;
	}

	/**
	 * Sets the game state for the arena.
	 * @param _gameState the new game state for the arena.
	 */
	public void setGameState(GameState _gameState) {
		gameState = _gameState;
	}

	/**
	 * Sets the GameGoal of the minigame. In other words the goal of the game.
	 * @param _gameGoal The GameGoal of the minigame.
	 */
	public void setGameGoal(GameGoal _gameGoal) {
		gameGoal = _gameGoal;
	}

	/**
	 * Sets whether or not the arena is currently joinable.
	 * @param _joinable Whether or not players can join the arena.
	 */
	public void setJoinable(Boolean _joinable) {
		joinable = _joinable;
	}

	/**
	 * Sets the arena's wait id.  This ID is assigned and used by the time keeper.
	 * It should not be edited unless you know what you are doing.
	 * @param _waitId The new wait id.
	 */
	public void setWaitId(Integer _waitId) {
		waitId = _waitId;
	}

	/**
	 * Sets the arena's start id. This ID is assigned and used by the time keeper.
	 * It should not be edited unless you know what you are doing.
	 * @param _startId The new start id.
	 */
	public void setStartId(Integer _startId) {
		startId = _startId;
	}

	/**
	 * Sets the arena's game id. This ID is assigned and used by the time keeper.
	 * It should not be edited unless you know what you are doing.
	 * @param _gameId The new game id.
	 */
	public void setGameId(Integer _gameId) {
		gameId = _gameId;
	}

	/**
	 * Adds the specified player's {@link PlayerStats}.
	 * @param _stats The {@link PlayerStats} of the player with their UUID respectively.
	 */
	public void setPlayerStats(PlayerStats _stats) {
		if (playerStats.containsKey(_stats.getPlayerUUID())) {
			playerStats.remove(_stats.getPlayerUUID());
		}
		playerStats.put(_stats.getPlayerUUID(), _stats);
	}

	/**
	 * Adds the specified player's {@link PlayerStats} and allows for manually overwriting if required.
	 * @param _stats The {@link PlayerStats} of the player with their UUID respectively.
	 * @param overwrite Whether or not to manually overwrite the stats if they already exist.
	 */
	public void setPlayerStats(PlayerStats _stats, Boolean overwrite) {
		if (playerStats.containsKey(_stats.getPlayerUUID())) {
			if (overwrite) {
				playerStats.remove(_stats.getPlayerUUID());
				playerStats.put(_stats.getPlayerUUID(), _stats);
			}
		} else {
			playerStats.put(_stats.getPlayerUUID(), _stats);
		}
	}

	/**
	 * Sets the WinType of the minigame, in other words the way in which the game ended.
	 * @param _winType The WinType of the minigame.
	 */
	public void setWinType(WinType _winType) {
		winType = _winType;
	}

	/**
	 * Sets whether or not the arena currently has the Main Scoreboard displayed.
	 * Ignore if not using teams.
	 * @param value Whether or not the Main Scoreboard is displayed.
	 */
	public void setIsOnMainBoard(Boolean value) {
		isOnMainBoardBool = value;
	}
}