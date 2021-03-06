package com.cloudcraftgaming.novagameslib.api.event.arena;

import com.cloudcraftgaming.novagameslib.api.arena.ArenaBase;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Nova Fox on 11/18/16.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ArenaUnloadEvent extends Event implements Cancellable {
	private final Integer arenaId;
	private final String gameName;

	private ArenaBase arenaBase;

	private Boolean letNovaGamesHandle;

	private boolean cancelled;
	private static final HandlerList handlers = new HandlerList();

	/**
	 * Constructor for ArenaUnloadEvent
	 * @param _arenaId The id of the arenaBase to be unloaded.
	 * @param _gameName The name of the game for the specific arenaBase.
	 */
	public ArenaUnloadEvent(Integer _arenaId, String _gameName) {
		arenaId = _arenaId;
		gameName = _gameName;
		letNovaGamesHandle = true;
	}

	/**
	 * Constructor for ArenaUnloadEvent.
	 * @param _arenaBase The ArenaObject to unload.
	 */
	public ArenaUnloadEvent(ArenaBase _arenaBase) {
		arenaId = _arenaBase.getId();
		gameName = _arenaBase.getGameName();
		arenaBase = _arenaBase;
	}

	/**
	 * Gets the ID of the arenaBase that will be unloaded.
	 *
	 * @return The ID of the arenaBase that will be unloaded.
	 */
	public Integer getArenaId() {
		return arenaId;
	}

	/**
	 * Gets the name of the game belonging to the arenaBase.
	 * @return The name of the game belonging to the arenaBase.
	 */
	public String getGameName() {
		return gameName;
	}

	/**
	 * Gets the ArenaBase Object for this event.
	 * This may be null, it is suggested you check first!!!
	 * @return The ArenaBase Object for this event.
	 */
	public ArenaBase getArenaBase() {
		return arenaBase;
	}

	/**
	 * Gets whether or not NovaGames will handle unloading this arenaBase, or if the specific minigames plugin will.
	 * If not, NovaGames will simply 'unload' or mark the arenaBase as being unloaded for NovaGames.
	 * This is <code>true</code> by default.
	 * @return <code>true</code> if NovaGames is to handle unloading, else <code>false</code>.
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
	 * Sets the ArenaBase Object for this event.
	 * This is only needed if {@link #shouldLetNovaGamesHandle()} is <code>false</code>.
	 * @param _arenaBase The ArenaBase for this event.
	 */
	public void setArenaBase(ArenaBase _arenaBase) {
		arenaBase = _arenaBase;
	}

	/**
	 * Set whether or not NovaGames should handle the unloading of the arenaBase.
	 * @param value Whether or not NovaGames should handle arenaBase unloading.
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