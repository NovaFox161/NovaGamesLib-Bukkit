package com.cloudcraftgaming.novagameslib.arena;

import com.cloudcraftgaming.novagameslib.NovaGamesLib;
import com.cloudcraftgaming.novagameslib.data.ArenaDataManager;
import com.cloudcraftgaming.novagameslib.event.arena.ArenaLoadEvent;
import com.cloudcraftgaming.novagameslib.event.arena.ArenaReloadEvent;
import com.cloudcraftgaming.novagameslib.event.arena.ArenaUnloadEvent;
import com.cloudcraftgaming.novagameslib.game.GameState;
import com.cloudcraftgaming.novagameslib.utils.FileManager;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.ArrayList;

import static com.cloudcraftgaming.novagameslib.NovaGamesLib.plugin;

/**
 * Created by Nova Fox on 11/17/16.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ArenaManager {
	private static ArenaManager instance;

	private final ArrayList<IArena> arenas = new ArrayList<>();

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
		for (IArena a : arenas) {
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
		for (IArena a : arenas) {
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
		for (IArena a : arenas) {
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
	public IArena getArena(int id) {
		for (IArena a : arenas) {
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
	public IArena getArena(Player player) {
		for (IArena a : arenas) {
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
	public ArrayList<IArena> getAllLoadedArenas() {
		return arenas;
	}

	//Functionals
	/**
	 * Called when loading an arena, often from another plugin.
	 * This will call the {@link ArenaLoadEvent} event.
	 * If not cancelled, will mark the specified arena as loaded.
	 * This will NOT mark any actual arena data!!!! YOU MUST SET THAT YOURSELF!!!
	 * @param arenaBase The arena to mark as loaded.
	 * @return <code>true</code> if not cancelled and successful, else <code>false</code>.
	 */
	public Boolean loadArena(ArenaBase arenaBase) {
		if (!arenaLoaded(arenaBase.getId())) {
			ArenaLoadEvent event = new ArenaLoadEvent(arenaBase);
			Bukkit.getServer().getPluginManager().callEvent(event);

			if (!event.isCancelled()) {
				arenas.add(arenaBase);
				//ArenaDataManager.updateArenaInfo(arena.getId());
				if (FileManager.verbose()) {
					plugin.getLogger().info("Loaded arena " + arenaBase.getId());
				}
				return true;
			}

		}
		return false;
	}

	/**
	 * Called when loading an arena. This will call the {@link ArenaLoadEvent} event.
	 * If not cancelled, this will either load the arena from scratch, or copy the arena from the event.
	 * @param id The id of the arena to load.
	 * @return <code>true</code> if not cancelled and successful, else <code>false</code>.
	 */
	public Boolean loadArena(int id, String gameName, Boolean useTeams) {
		if (!arenaLoaded(id)) {
			if (ArenaDataManager.arenaExists(id)) {
				if (ArenaDataManager.canBeLoaded(id)) {
					ArenaLoadEvent event = new ArenaLoadEvent(id, gameName);
					Bukkit.getServer().getPluginManager().callEvent(event);

					if (!event.isCancelled()) {
						if (event.shouldLetNovaGamesHandle()) {
							ArenaBase arenaBase = new ArenaBase(id, gameName, useTeams);
							arenaBase.setArenaStatus(ArenaStatus.EMPTY);
							arenaBase.setGameState(GameState.NOT_STARTED);
							arenaBase.setPlayerCount(0);
							arenaBase.setJoinable(true);
							arenas.add(arenaBase);
							//ArenaDataManager.updateArenaInfo(id);
							if (FileManager.verbose()) {
								plugin.getLogger().info("Loaded arena " + id);
							}
							return true;
						} else {
							if (event.getArenaBase() != null) {
								arenas.add(event.getArenaBase());
							}
							//ArenaDataManager.updateArenaInfo(id);
							if (FileManager.verbose()) {
								plugin.getLogger().info("Loaded arena " + id);
							}
							return true;
						}
					}
				}
			}
		}
		return false;
	}

	/**
	 * Called when unloading an arena, often from another plugin.
	 * This will call the {@link ArenaUnloadEvent} event.
	 * If not cancelled, will mark the specified arena as unloaded.
	 * This will NOT mark any actual arena data!!!! YOU MUST SET THAT YOURSELF!!!
	 * @param arenaBase The arena to mark as unloaded.
	 * @return <code>true</code> if not cancelled and successful, else <code>false</code>.
	 */
	public Boolean unloadArena(ArenaBase arenaBase) {
		if (arenaLoaded(arenaBase.getId())) {
			ArenaUnloadEvent event = new ArenaUnloadEvent(arenaBase);
			Bukkit.getServer().getPluginManager().callEvent(event);

			if (!event.isCancelled()) {
				arenas.remove(arenaBase);
				//ArenaDataManager.updateArenaInfo(arena.getId());
				if (FileManager.verbose()) {
					plugin.getLogger().info("Unloaded arena " + arenaBase.getId());
				}
				return true;
			}
		}
		return false;
	}

	/**
	 * Called when unloading an arena. This will call the {@link ArenaUnloadEvent} event.
	 * If not cancelled, this will either unload the arena, or copy the arena from the event.
	 * @param id The id of the arena to unload.
	 * @return <code>true</code> if not cancelled and successful, else <code>false</code>.
	 */
	public Boolean unloadArena(int id) {
		if (arenaLoaded(id)) {
			ArenaUnloadEvent event = new ArenaUnloadEvent(id, ArenaDataManager.getGameName(id));
			Bukkit.getServer().getPluginManager().callEvent(event);

			if (!event.isCancelled()) {
				if (event.shouldLetNovaGamesHandle()) {
					IArena arena = getArena(id);
					arenas.remove(arena);
					//ArenaDataManager.updateArenaInfo(id);
					if (FileManager.verbose()) {
						plugin.getLogger().info("Unloaded arena " + id);
					}
					return true;
				} else {
					if (event.getArenaBase() != null) {
						arenas.remove(event.getArenaBase());
					}
					//ArenaDataManager.updateArenaInfo(id);
					if (FileManager.verbose()) {
						NovaGamesLib.plugin.getLogger().info("Unloaded arena " + id);
					}
					return true;
				}
			}
		}
		return false;
	}

	/**
	 * Called when reloading an arena. This will call the {@link ArenaReloadEvent} event.
	 * If not cancelled, this will reload the arena.
	 * @param id The id of the arena to reload.
	 * @return <code>true</code> if not cancelled and successful, else <code>false</code>.
	 */
	public Boolean reloadArena(int id) {
		if (arenaLoaded(id)) {
			String gameName = getArena(id).getGameName();
			Boolean useTeams = getArena(id).useTeams();
			ArenaReloadEvent event = new ArenaReloadEvent(getArena(id));
			Bukkit.getServer().getPluginManager().callEvent(event);

			if (!event.isCancelled()) {
				if (FileManager.verbose()) {
					NovaGamesLib.plugin.getLogger().info("Reloading arena " + id);
				}
				unloadArena(id);
				return loadArena(id, gameName, useTeams);
			}
		}
		return false;
	}
}