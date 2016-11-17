package com.cloudcraftgaming.novagameslib.arena;

import org.bukkit.entity.Player;

import java.util.ArrayList;

/**
 * Created by Nova Fox on 11/17/16.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ArenaManager {
	private static ArenaManager instance;

	private final ArrayList<Arena> arenas = new ArrayList<>();

	/**
	 * Constructor for ArenaManger. Private to prevent multiple instances.
	 */
	private ArenaManager() {} //Prevent initialization.

	/**
	 * Gets the instance of ArenaManager.
	 * @return The instance of the ArenaManager.
	 */
	public static ArenaManager getManager() {
		if (instance == null) {
			instance = new ArenaManager();
		}
		return instance;
	}

	//Booleans/Checkers
	/**
	 * Checks if the arena is loaded.
	 * @param i The ID of the arena to check.
	 * @return <code>true</code> if loaded, else <code>false</code>.
	 */
	public Boolean arenaLoaded(int i) {
		for (Arena a : arenas) {
			if (a.getId() == i) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the player is in a game.
	 * Use {@link #isSpectating(Player)} to check if a player is spectating a game.
	 * @param player The player to check.
	 * @return <code>true</code> if in a game, else <code>false</code>.
	 */
	public Boolean isInGame(Player player) {
		for (Arena a : arenas) {
			if (a.getPlayers().contains(player.getUniqueId())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Checks if the player is spectating a game.
	 * Use {@link #isInGame(Player)} to check if a player is in a game.
	 * @param player The player to check.
	 * @return <cide>true</cide> if spectating, else <code>false</code>.
	 */
	public Boolean isSpectating(Player player) {
		for (Arena a : arenas) {
			if (a.getSpectators().contains(player.getUniqueId())) {
				return true;
			}
		}
		return false;
	}

	//Getters
	/**
	 * Gets the arena with the specified ID.
	 * You should check if this exists first with {@link #arenaLoaded(int)} or you may get null.
	 * @param id The ID of the arena.
	 * @return The Arena requested.
	 */
	public Arena getArena(int id) {
		for (Arena a : arenas) {
			if (a.getId() == id) {
				return a;
			}
		}
		return null;
	}

	/**
	 * Gets the arena with the specified player, whether playing or spectating.
	 * You should check if the player is in a game with {@link #isInGame(Player)}
	 * or spectating with {@link #isSpectating(Player)} to avoid a null return.
	 * @param player The player to get.
	 * @return The arena containing the specified player.
	 */
	public Arena getArena(Player player) {
		for (Arena a : arenas) {
			if (a.getPlayers().contains(player.getUniqueId())) {
				return a;
			}
			if (a.getSpectators().contains(player.getUniqueId())) {
				return a;
			}
		}
		return null;
	}

	/**
	 * Gets a list of all arenas currently loaded.
	 * Please use wisely as this can cause issues if you change the set!
	 * @return An ArrayList containing all loaded arenas.
	 */
	public ArrayList<Arena> getAllLoadedArenas() {
		return arenas;
	}

	//Functionals - Grab from GitHub once events are added due to errors.
}