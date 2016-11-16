package com.cloudcraftgaming.novagameslib.team;

import org.bukkit.ChatColor;

import java.util.ArrayList;

@SuppressWarnings({"unused", "WeakerAccess"})
public enum Team {
	RED(1), BLUE(2),
	GREEN(3), PURPLE(4),
	AQUA(5), WHITE(6),
	GRAY(7), YELLOW(8);

	private int numValue;

	Team(int _numValue) {
		this.numValue = _numValue;
	}

	/**
	 * Gets the number value for the team.
	 * @return The team's value.
	 */
	public int getValue() {
		return numValue;
	}

	/**
	 * Checks if the teamName is a valid team.
	 * @param teamName The name to check.
	 * @return <code>true</code> if valid, else <code>false</code>.
	 */
	public static Boolean exists(String teamName) {
		String teamNameUse = teamName.toUpperCase();
		switch (teamNameUse) {
			case "RED":
				return true;
			case "BLUE":
				return true;
			case "GREEN":
				return true;
			case "PURPLE":
				return true;
			case "AQUA":
				return true;
			case "WHITE":
				return true;
			case "GRAY":
				return true;
			case "YELLOW":
				return true;
		}
		return false;
	}

	/**
	 * Gets the team by it's value.
	 * @param value The value of the team.
	 * @return The team with the specified value.
	 */
	public static Team valueOf(int value) {
		if (value == 1) {
			return RED;
		} else if (value == 2) {
			return BLUE;
		} else if (value == 3) {
			return GREEN;
		} else if (value == 4) {
			return PURPLE;
		} else if (value == 5) {
			return AQUA;
		} else if (value == 6) {
			return WHITE;
		} else if (value == 7) {
			return GRAY;
		} else if (value == 8) {
			return  YELLOW;
		} else {
			return RED;
		}
	}

	/**
	 * Gets a list of all valid teams.
	 * Generally when needing to loop through all teams.
	 * @return An ArrayList of all teams.
	 */
	public static ArrayList<Team> allTeams() {
		ArrayList<Team> allTeams = new ArrayList<>();
		allTeams.add(RED);
		allTeams.add(BLUE);
		allTeams.add(GREEN);
		allTeams.add(PURPLE);
		allTeams.add(AQUA);
		allTeams.add(WHITE);
		allTeams.add(GRAY);
		allTeams.add(YELLOW);
		return allTeams;
	}

	/**
	 * Gets the ChatColor for the specified team.
	 * @param team The team whose color to get.
	 * @return The color of the specified team.
	 */
	public static ChatColor getColor(Team team) {
		if (team.equals(RED)) {
			return ChatColor.DARK_RED;
		} else if (team.equals(BLUE)) {
			return ChatColor.DARK_BLUE;
		} else if (team.equals(GREEN)) {
			return ChatColor.DARK_GREEN;
		} else if (team.equals(PURPLE)) {
			return ChatColor.DARK_PURPLE;
		} else if (team.equals(AQUA)) {
			return ChatColor.AQUA;
		} else if (team.equals(WHITE)) {
			return ChatColor.WHITE;
		} else if (team.equals(GRAY)) {
			return ChatColor.GRAY;
		} else if (team.equals(YELLOW)) {
			return ChatColor.YELLOW;
		} else {
			return ChatColor.WHITE;
		}
	}
}