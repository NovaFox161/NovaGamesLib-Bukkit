package com.cloudcraftgaming.novagameslib.team;

/**
 * Created by Nova Fox on 11/16/16.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
@SuppressWarnings("unused")
public class TeamUtils {
	/**
	 * Determines the amount of teams the arena should have for a balanced game.
	 * @param minTeams The minimum amount of teams there should be.
	 * @param maxTeams The maximum amount of teams there can be.
	 * @param playerCount The current amount of players in the arena.
	 * @return The number of teams to provide a balanced game.
	 */
	public static Integer determineTeamAmount(Integer minTeams, Integer maxTeams, Integer playerCount) {
		if (minTeams < 2) {
			minTeams = 2;
		}
		if (maxTeams > 8) {
			maxTeams = 8;
		}
		if (playerCount.equals(minTeams)) {
			return minTeams;
		} else if (playerCount * 2 == maxTeams) {
			return maxTeams / 2;
		} else {
			return maxTeams / playerCount;
		}
	}
}