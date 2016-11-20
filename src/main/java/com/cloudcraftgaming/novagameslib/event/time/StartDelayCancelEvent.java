package com.cloudcraftgaming.novagameslib.event.time;

import com.cloudcraftgaming.novagameslib.arena.Arena;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Nova Fox on 11/19/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
@SuppressWarnings("unused")
public class StartDelayCancelEvent extends Event implements Cancellable {
    private final Integer id;
    private final String gameName;

    private Arena arena;

    private boolean cancelled;
    private static final HandlerList handlers = new HandlerList();

    /**
     * Constructor for StartDelayCancelEvent
     * @param _arena The arena that is involved in this event.
     */
    public StartDelayCancelEvent(Arena _arena) {
        arena = _arena;

        id = arena.getId();
        gameName = arena.getGameName();
    }

    /**
     * Gets the ID of the arena involved in this event.
     * @return The id of the arena involved in this event.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets the name of the minigame belonging to the arena involved in this event.
     * @return The name of the minigame belonging to the arena.
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Gets the arena involved in this event.
     * @return The arena involved in this event.
     */
    public Arena getArena() {
        return arena;
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
