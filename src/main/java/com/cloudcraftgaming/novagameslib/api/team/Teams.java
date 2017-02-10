package com.cloudcraftgaming.novagameslib.api.team;

import com.cloudcraftgaming.novagameslib.api.arena.ArenaBase;
import com.cloudcraftgaming.novagameslib.api.arena.ArenaManager;
import org.bukkit.Bukkit;
import org.bukkit.scoreboard.Scoreboard;

import java.util.*;

@SuppressWarnings({"unused", "WeakerAccess"})
public class Teams {
	private final int id;

	private final HashMap<Team, List<UUID>> playersOnTeam = new HashMap<>();
	private final HashMap<Team, Integer> playerCountOnTeam = new HashMap<>();
	private final ArrayList<Team> teamsInGame = new ArrayList<>();
	private final HashMap<Team, Scoreboard> teamScoreboards = new HashMap<>();

	private Integer teamCount;

	/**
	 * Teams constructor. Creates a Teams object.
	 * @param id The id of the arena this belongs to.
	 */
	public Teams(int id) {
		this.id = id;
		teamCount = 0;
		teamsInGame.clear();
	}

	//Booleans/Checkers
	/**
	 * Checks if the player is on a team.
	 * Use {@link #isOnTeam(UUID, Team)} to check if the player is on a specific team.
	 * @param uuid The UUID of the player to check.
	 * @return <code>true</code> if on a team, else <code>false</code>.
	 */
	public Boolean isOnATeam(UUID uuid) {
		for (Team team : teamsInGame) {
			if (playersOnTeam.get(team).contains(uuid)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the player is on the specified team.
	 * Use {@link #isOnATeam(UUID)} to check if the player is on any team.
	 * @param uuid The UUID of the player.
	 * @param team The team to check.
	 * @return <code>true</code> if they are on that team, else <code>false</code>.
	 */
	public Boolean isOnTeam(UUID uuid, Team team) {
		return playersOnTeam.containsKey(team) && playersOnTeam.get(team).contains(uuid);
	}

	//Functionals
	/**
	 * Sets up the teams for the arena.
	 * @param minTeamCount The minimum amount of teams for the arena.
	 * @param maxTeamCount The maximum amount of teams for the arena.
	 * @param playerCount The current player count.
	 */
	public void setUpTeams(Integer minTeamCount, Integer maxTeamCount, Integer playerCount) {
		teamsInGame.clear();
		Integer teamAmount = TeamUtils.determineTeamAmount(minTeamCount, maxTeamCount, playerCount);
		int index = 1;
		for (Team team : Team.allTeams()) {
			if (index < teamAmount + 1) {
				registerTeam(team);
				index = index + 1;
			} else {
				break;
			}
		}
	}

	/**
	 * Gets the team with the least amount of players.
	 * @return The team with the least amount of players.
	 */
	public Team getTeamWithFewestPlayers() {
		if (teamsInGame.size() > 0) {
			Team teamWithFewestPlayers = teamsInGame.get(0);
			for (Team team : teamsInGame) {
				if (getPlayerCountOnTeam(team) < getPlayerCountOnTeam(teamWithFewestPlayers)) {
					teamWithFewestPlayers = team;
				}
			}
			return teamWithFewestPlayers;
		} else {
			return Team.RED;
		}
	}

	/**
	 * Assigns all players to a team.
	 * @param players A list of players to add to teams.
	 */
	public void assignTeams(List<UUID> players) {
		Collections.shuffle(players);
		for (UUID pId : players) {
			if (!isOnATeam(pId)) {
				addPlayerToTeam(pId, getTeamWithFewestPlayers());
			}
		}
	}

	/**
	 * Registers the team and sets it's default values.
	 * @param team The team to register.
	 */
	private void registerTeam(Team team) {
		playersOnTeam.put(team, new ArrayList<UUID>());
		teamsInGame.add(team);
		teamCount = teamCount + 1;
		setTeamPlayerCount(team, 0);
	}

	//Getters
	/**
	 * Gets the Id of the arena this Teams object belongs to.
	 * @return The ID of the arena.
	 */
	public int getId() {
		return this.id;
	}

	/**
	 * Gets all the players on the specific team.
	 * @param team The team to get.
	 * @return A list containing UUIDs of players on the team.
	 */
	public List<UUID> getPlayersOnTeam(Team team) {
		List<UUID> playersOnTeam = new ArrayList<>();
		ArenaBase arenaBase = ArenaManager.getManager().getArena(this.id);
		for (UUID pId : arenaBase.getPlayers()) {
			if (getTeam(pId).equals(team)) {
				playersOnTeam.add(pId);
			}
		}
		return playersOnTeam;
	}

	/**
	 * Gets a list of all teams currently in the game.
	 * @return A list containing all teams in the game.
	 */
	public List<Team> getTeamsInGame() {
		return this.teamsInGame;
	}

	/**
	 * Gets the amount of teams in the game.
	 * @return The amount of teams in the game.
	 */
	public Integer getTeamCount() {
		return this.teamCount;
	}

	/**
	 * Gets the amount of players on the specified team.
	 * @param team The team to get.
	 * @return The amount of players on the team.
	 */
	public Integer getPlayerCountOnTeam(Team team) {
		if (playerCountOnTeam.containsKey(team)) {
			return playerCountOnTeam.get(team);
		} else {
			return 0;
		}
	}

	/**
	 * Gets the team with the specified player on it.
	 * @param uuid The UUID of the player.
	 * @return The team with the specified player.
	 */
	public Team getTeam(UUID uuid) {
		for (Team team : teamsInGame) {
			if (playersOnTeam.containsKey(team)) {
				if (playersOnTeam.get(team).contains(uuid)) {
					return team;
				}
			}
		}
		return null;
	}

	//Getters
	/**
	 * Gets the scoreboard for the specified team.
	 * @param team The team to get.
	 * @return The team's scoreboard, a new scoreboard if one does not exist.
	 */
	public Scoreboard getTeamScoreboard(Team team) {
		if (teamScoreboards.containsKey(team)) {
			return teamScoreboards.get(team);
		} else {
			return Bukkit.getScoreboardManager().getNewScoreboard();
		}
	}

	//Setters
	/**
	 * Sets the number of players on the specified team.
	 * @param team The team to set.
	 * @param count The new number of players on the team.
	 */
	public void setTeamPlayerCount(Team team, int count) {
		if (playerCountOnTeam.containsKey(team)) {
			playerCountOnTeam.remove(team);
		}
		playerCountOnTeam.put(team, count);
	}

	/**
	 * Sets the teams scoreboard.
	 * @param team The team.
	 * @param scoreboard The new scoreboard.
	 */
	public void setTeamScoreboard(Team team, Scoreboard scoreboard) {
		if (teamScoreboards.containsKey(team)) {
			teamScoreboards.remove(team);
		}
		teamScoreboards.put(team, scoreboard);
	}

	//Adders
	/**
	 * Adds the specified player to the specified team.
	 * @param uuid The UUID of the player.
	 * @param team The team to put the player on.
	 */
	public void addPlayerToTeam(UUID uuid, Team team) {
		if (teamsInGame.contains(team)) {
			playersOnTeam.get(team).add(uuid);
			setTeamPlayerCount(team, getPlayerCountOnTeam(team) + 1);
		} else {
			registerTeam(team);
			addPlayerToTeam(uuid, team);
		}
	}

	//Removers
	/**
	 * Removes the specified player from their team.
	 * Should only be used when a player quits/is out the minigame or leaves the server.
	 * @param uuid The UUID of the player.
	 */
	public void removePlayerFromTeam(UUID uuid) {
		Team team = this.getTeam(uuid);
		playersOnTeam.get(team).remove(uuid);
		setTeamPlayerCount(team, getPlayerCountOnTeam(team) - 1);
        /*if (getPlayerCountOnTeam(team) <= 0) {
            //Would remove team, but the #CheckPlayerCount method already takes care of this.
        } */
	}
}