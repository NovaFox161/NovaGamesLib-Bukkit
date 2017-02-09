package com.cloudcraftgaming.novagameslib.event.arena;

import com.cloudcraftgaming.novagameslib.arena.IArena;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Nova Fox on 11/18/16.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ArenaReloadEvent extends Event implements Cancellable {
	private final Integer arenaId;
	private final String gameName;

	private IArena arena;

	private Boolean letNovaGamesHandle;

	private boolean cancelled;
	private static final HandlerList handlers = new HandlerList();

	/**
	 * Constructor for ArenaReloadEvent
	 *
	 * @param _arenaId  The id of the arena to be reloaded.
	 * @param _gameName The name of the game for the specific arena.
	 */
	public ArenaReloadEvent(Integer _arenaId, String _gameName) {
		arenaId = _arenaId;
		gameName = _gameName;
		letNovaGamesHandle = true;
	}

	/**
	 * Constructor for ArenaReloadEvent.
	 *
	 * @param _arena The ArenaObject to reload.
	 */
	public ArenaReloadEvent(IArena _arena) {
		arenaId = _arena.getId();
		gameName = _arena.getGameName();
		arena = _arena;
	}

	/**
	 * Gets the ID of the arena that will be reloaded.
	 *
	 * @return The ID of the arena that will be reloaded.
	 */
	public Integer getArenaId() {
		return arenaId;
	}

	/**
	 * Gets the name of the game belonging to the arena.
	 *
	 * @return The name of the game belonging to the arena.
	 */
	public String getGameName() {
		return gameName;
	}

	/**
	 * Gets the Arena Object for this event.
	 * This may be null, it is suggested you check first!!!
	 *
	 * @return The Arena Object for this event.
	 */
	public IArena getArena() {
		return arena;
	}

	/**
	 * Gets whether or not NovaGames will handle reloading this arena, or if the specific minigames plugin will.
	 * If not, NovaGames will simply 'reload' or mark the arena as being reloaded for NovaGames.
	 * This is <code>true</code> by default.
	 *
	 * @return <code>true</code> if NovaGames is to handle reloading, else <code>false</code>.
	 */
	public Boolean shouldLetNovaGamesHandle() {
		return letNovaGamesHandle;
	}

	/**
	 * Whether or not the event is cancelled.
	 *
	 * @return Whether or not the event is cancelled.
	 */
	public boolean isCancelled() {
		return cancelled;
	}

	/**
	 * Sets the Arena Object for this event.
	 * This is only needed if {@link #shouldLetNovaGamesHandle()} is <code>false</code>.
	 *
	 * @param _arena The Arena for this event.
	 */
	public void setArenaBase(IArena _arena) {
		arena = _arena;
	}

	/**
	 * Set whether or not NovaGames should handle the reloading of the arena.
	 *
	 * @param value Whether or not NovaGames should handle arena reloading.
	 */
	public void setLetNovaGamesHandle(Boolean value) {
		letNovaGamesHandle = value;
	}

	/**
	 * Sets whether or not the event is cancelled.
	 *
	 * @param value Whether or not the event is cancelled.
	 */
	public void setCancelled(boolean value) {
		cancelled = value;
	}

	/**
	 * Bukkit method for getting handlers.
	 *
	 * @return This event's HandlerList.
	 */
	public HandlerList getHandlers() {
		return handlers;
	}

	/**
	 * Bukkit method for getting handlers.
	 *
	 * @return This event's HandlerList.
	 */
	public static HandlerList getHandlerList() {
		return handlers;
	}
}