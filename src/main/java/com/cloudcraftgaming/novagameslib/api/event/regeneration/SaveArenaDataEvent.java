package com.cloudcraftgaming.novagameslib.api.event.regeneration;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Nova Fox on 11/19/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
@SuppressWarnings("unused")
public class SaveArenaDataEvent extends Event implements Cancellable {
    private final Integer arenaId;

    private boolean cancelled;
    private static final HandlerList handlers = new HandlerList();

    /**
     * Constructor for SaveArenaDataEvent
     *
     * @param _arenaId  The id of the arena that is having its data saved.
     */
    public SaveArenaDataEvent(Integer _arenaId) {
        arenaId = _arenaId;
    }

    /**
     * Gets the ID of the arena that is having its data saved.
     *
     * @return The ID of the arena that will have its data saved.
     */
    public Integer getArenaId() {
        return arenaId;
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