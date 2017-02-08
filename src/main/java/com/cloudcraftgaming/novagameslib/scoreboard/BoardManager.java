package com.cloudcraftgaming.novagameslib.scoreboard;

import com.cloudcraftgaming.novagameslib.arena.Arena;
import com.cloudcraftgaming.novagameslib.arena.ArenaManager;
import com.cloudcraftgaming.novagameslib.data.ArenaDataManager;
import org.bukkit.Bukkit;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.scoreboard.*;

import java.util.HashMap;
import java.util.UUID;

/**
 * Created by Nova Fox on 11/17/16.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
@SuppressWarnings({"unused", "WeakerAccess", "deprecation"})
public class BoardManager {
	private final int id;
	private final Boolean useTeams;
	private final HashMap<Integer, ScoreboardLine> lines = new HashMap<>();

	private ScoreboardManager sbManager;

	private Scoreboard scoreboard;

	//EDIT CONSTRUCTOR ONCE ArenaDataManager IS ADDED!!!!
	/**
	 * Constructor for BoardManager
	 * @param _id The id of the arena.
	 */
	public BoardManager(int _id, Boolean _useTeams) {
		id = _id;
		useTeams = _useTeams;
		sbManager = Bukkit.getScoreboardManager();
		scoreboard = sbManager.getNewScoreboard();

		Objective boardObj = scoreboard.registerNewObjective("Board", "dummy");
		boardObj.setDisplaySlot(DisplaySlot.SIDEBAR);

		Team spectators = scoreboard.registerNewTeam("spectators");
		spectators.setDisplayName(ChatColor.GOLD + "Spectators");
		Team allPlayers = scoreboard.registerNewTeam("allPlayers");

		//Register a team for all 8 possible teams.
		if (useTeams) {
			for (com.cloudcraftgaming.novagameslib.team.Team team : com.cloudcraftgaming.novagameslib.team.Team.allTeams()) {
				Team boardTeam = scoreboard.registerNewTeam(team.name());
				boardTeam.setDisplayName(com.cloudcraftgaming.novagameslib.team.Team.getColor(team) + team.name());
				boardTeam.setAllowFriendlyFire(ArenaDataManager.allowsFriendlyFire(id));
				if (ArenaDataManager.getHideName(id)) {
					boardTeam.setNameTagVisibility(NameTagVisibility.HIDE_FOR_OTHER_TEAMS);
				}
			}
		}

		//Register 15 displays and 1 display for the scoreboard title.
		for (int i = 0; i < 16; i++) {
			lines.put(i, new ScoreboardLine(i));
		}
	}

	//Getters
	/**
	 * Gets the scoreboard for the arena.
	 * @return The scoreboard for the arena.
	 */
	public Scoreboard getScoreboard() {
		return scoreboard;
	}

	/**
	 * Gets the currently displayed text for the line at the specified index.
	 * @param index The index of the line.
	 * @return The currently displayed text for the line.
	 */
	public String getCurrentText(int index) {
		if (lines.containsKey(index)) {
			return lines.get(index).getCurrentText();
		} else {
			return "";
		}
	}

	/**
	 * Gets the previously displayed text for the line at the specified index.
	 * @param index The index of the line.
	 * @return The previously displayed text for the line.
	 */
	public String getPreviousText(int index) {
		if (lines.containsKey(index)) {
			return lines.get(index).getPreviousText();
		} else {
			return "";
		}
	}

	//Setters
	/**
	 * Changes the text for a specific line.
	 * Set index to <code>0</code> to change the scoreboard title.
	 * @param index The index of the line.
	 * @param newText The new text to display.
	 * @return <code>true</code> if successful, else <code>false</code>.
	 */
	public Boolean changeLineText(int index, String newText) {
		if (lines.containsKey(index)) {
			lines.get(index).setCurrentText(newText);
			return updateDisplay();
		}
		return false;
	}

	//Functionals
	/**
	 * Updates the display for the scoreboard.
	 * @return <code>true</code> is successful, else <code>false</code>.
	 */
	public Boolean updateDisplay() {
		for (ScoreboardLine line : lines.values()) {
			if (line.hasChanged()) {
				Objective boardObj = scoreboard.getObjective("Board");
				if (line.getIndex() == 0) {
					boardObj.setDisplayName(line.getCurrentText());
				} else {
					//Change score.
					scoreboard.resetScores(line.getPreviousText());
					Score score = boardObj.getScore(line.getCurrentText());
					score.setScore(line.getIndex());
				}

			}
		}
		updatePlayersBoards();
		return true;
	}

	/**
	 * Updates the scoreboard for all players and spectators in the arena.
	 * Automatically called when using methods here.
	 * Scoreboards will not reflect changes until this method is called!
	 */
	public void updatePlayersBoards() {
		Arena arena = ArenaManager.getManager().getArena(id);
		for (UUID pId : arena.getPlayers()) {
			Bukkit.getPlayer(pId).setScoreboard(scoreboard);
		}
		for (UUID pId : arena.getSpectators()) {
			Bukkit.getPlayer(pId).setScoreboard(scoreboard);
		}
	}

	/**
	 * Adds the player to the spectators team.
	 * Use {@link #addPlayerToScoreboard(Player, com.cloudcraftgaming.novagameslib.team.Team)} to add a player to the scoreboard.
	 * @param player The player to add.
	 * @return <code>true</code> if successful, else <code>false</code>.
	 */
	public Boolean addSpectatorToScoreboard(Player player) {
		Team spectators = scoreboard.getTeam("spectators");
		spectators.addPlayer(player);
		updatePlayersBoards();
		return true;
	}

	/**
	 * Adds the player to the scoreboard under the proper team.
	 * Use {@link #addPlayerToScoreboard(Player)} to add a player to a scoreboard without teams.
	 * Use {@link #addSpectatorToScoreboard(Player)} to add a spectator to the scoreboard.
	 * @param player The player to add.
	 * @param team The team to add them to.
	 * @return <code>true</code> is successful, else <code>false</code>.
	 */
	public Boolean addPlayerToScoreboard(Player player, com.cloudcraftgaming.novagameslib.team.Team team) {
		if (useTeams) {
			Team boardTeam = scoreboard.getTeam(team.name());
			boardTeam.addPlayer(player);
			updatePlayersBoards();
			return true;
		} else {
			return addPlayerToScoreboard(player);
		}
	}

	/**
	 * Adds the player to the scoreboard, ignoring teams.
	 * Use {@link #addPlayerToScoreboard(Player, com.cloudcraftgaming.novagameslib.team.Team)} to add a player to a scoreboard with teams.
	 * Use {@link #addSpectatorToScoreboard(Player)} to add a spectator to the scoreboard.
	 * @param player The player to add.
	 * @return <code>true</code> if successful, else <code>false</code>.
	 */
	public Boolean addPlayerToScoreboard(Player player) {
		if (!useTeams) {
			Team allPlayersTeam = scoreboard.getTeam("allPlayers");
			allPlayersTeam.addPlayer(player);
			updatePlayersBoards();
			return true;
		}
		return false;
	}

	/**
	 * Removes the player from the spectators team.
	 * Use {@link #removePlayerFromScoreboard(Player, com.cloudcraftgaming.novagameslib.team.Team)} to remove a player rather than a spectator.
	 * @param player The player to remove.
	 * @return <code>true</code> us successful, else <code>false</code>.
	 */
	public Boolean removeSpectatorFromBoard(Player player) {
		Team spectators = scoreboard.getTeam("spectators");
		spectators.removePlayer(player);
		player.setScoreboard(sbManager.getNewScoreboard());
		updatePlayersBoards();
		return true;
	}

	/**
	 * Removes the player from the scoreboard.
	 * Use {@link #removePlayerFromScoreboard(Player)} to remove a player without a team.
	 * Use {@link #removeSpectatorFromBoard(Player)} to remove a spectator rather than a player.
	 * @param player The player to remove.
	 * @param team The team to remove them from.
	 * @return <code>true</code> if successful, else <code>false</code>.
	 */
	public Boolean removePlayerFromScoreboard(Player player, com.cloudcraftgaming.novagameslib.team.Team team) {
		if (useTeams) {
			Team boardTeam = scoreboard.getTeam(team.name());
			boardTeam.removePlayer(player);
			player.setScoreboard(sbManager.getNewScoreboard());
			updatePlayersBoards();
			return true;
		} else {
			return removePlayerFromScoreboard(player);
		}
	}

	/**
	 * Removes the player from the scoreboard ignoring teams.
	 * Use {@link #removePlayerFromScoreboard(Player, com.cloudcraftgaming.novagameslib.team.Team)} to remove from a specific team.
	 * Use {@link #removeSpectatorFromBoard(Player)} to remove a spectator rather than a player.
	 * @param player The player to remove.
	 * @return <code>true</code> if successful, else <code>false</code>.
	 */
	public Boolean removePlayerFromScoreboard(Player player) {
		if (!useTeams) {
			Team allPlayersTeam = scoreboard.getTeam("allPlayers");
			allPlayersTeam.removePlayer(player);
			updatePlayersBoards();
			return true;
		}
		return false;
	}
}