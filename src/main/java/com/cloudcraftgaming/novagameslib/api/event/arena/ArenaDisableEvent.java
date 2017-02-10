package com.cloudcraftgaming.novagameslib.api.event.arena;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Nova Fox on 11/18/16.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
@SuppressWarnings("unused")
public class ArenaDisableEvent extends Event implements Cancellable {
	private final Integer arenaId;

	private Boolean autoUnloadBool;

	private boolean cancelled;
	private static final HandlerList handlers = new HandlerList();

	/**
	 * Constructor for ArenaDisableEvent
	 * @param _arenaId The id of the arena to be disabled.
	 */
	public ArenaDisableEvent(Integer _arenaId) {
		arenaId = _arenaId;
		autoUnloadBool = true;
	}

	/**
	 * Gets the ID of the arena that will be disabled.
	 * @return The ID of the arena that will be disabled.
	 */
	public Integer getArenaId() {
		return arenaId;
	}

	/**
	 * Gets whether or not the NovaGames API will auto unload the arena or let the minigames plugin handle it.
	 * By default, this is <code>true</code>.
	 * @return Whether or not the NovaGames will auto unload the arena or let an external plugin handle it.
	 */
	public Boolean autoUnload() {
		return autoUnloadBool;
	}

	/**
	 * Sets whether or not the NovaGames API will auto unload the arena or let the minigames plugin handle it.
	 * By default, this is <code>true</code>.
	 * @param value Whether or not the NovaGames will auto unload the arena or let an external plugin handle it.
	 */
	public void setAutoUnload(Boolean value) {
		autoUnloadBool = value;
	}

	/**
	 * Whether or not the event is cancelled.
	 * @return Whether or not the event is cancelled.
	 */
	public boolean isCancelled() {
		return cancelled;
	}

	/**
	 * Sets whether or not the event is cancelled.
	 * @param value Whether or not the event is cancelled.
	 */
	public void setCancelled(boolean value) {
		cancelled = value;
	}

	/**
	 * Bukkit method for getting handlers.
	 * @return This event's HandlerList.
	 */
	public HandlerList getHandlers() {
		return handlers;
	}

	/**
	 * Bukkit method for getting handlers.
	 * @return This event's HandlerList.
	 */
	public static HandlerList getHandlerList() {
		return handlers;
	}
}