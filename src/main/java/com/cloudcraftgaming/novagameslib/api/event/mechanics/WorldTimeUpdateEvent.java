package com.cloudcraftgaming.novagameslib.api.event.mechanics;

import com.cloudcraftgaming.novagameslib.api.data.ArenaDataManager;
import org.bukkit.World;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Nova Fox on 11/18/16.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
@SuppressWarnings("unused")
public class WorldTimeUpdateEvent extends Event {
	private final Integer id;
	private final String gameName;

	private World world;
	private Long oldTime;

	private static final HandlerList handlers = new HandlerList();

	/**
	 * Constructor  for WorldTimeUpdateEvent
	 *
	 * @param _id      The id of the arena involved in this event.
	 * @param _world   The world this arena resides in and has had its time updated.
	 * @param _oldTime The world time (in ticks) before this update.
	 */
	public WorldTimeUpdateEvent(Integer _id, World _world, Long _oldTime) {
		id = _id;
		gameName = ArenaDataManager.getGameName(id);

		world = _world;
		oldTime = _oldTime;
	}

	/**
	 * Gets the ID of the arena involved in this event.
	 *
	 * @return The id of the arena involved in this event.
	 */
	public Integer getId() {
		return id;
	}

	/**
	 * Gets the name of the minigame belonging to the arena involved in this event.
	 *
	 * @return The name of the minigame belonging to the arena.
	 */
	public String getGameName() {
		return gameName;
	}

	/**
	 * Gets the world the arena resides in and has had its time updated.
	 *
	 * @return The world the arena resides in and has had its time updated.
	 */
	public World getWorld() {
		return world;
	}

	/**
	 * Gets the world time (in ticks) before this update.
	 *
	 * @return The world time (in ticks) before this update.
	 */
	public Long getOldTime() {
		return oldTime;
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