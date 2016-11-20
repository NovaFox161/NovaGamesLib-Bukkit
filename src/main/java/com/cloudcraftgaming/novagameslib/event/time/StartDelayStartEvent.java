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
public class StartDelayStartEvent extends Event implements Cancellable {
    private final Integer id;
    private final String gameName;

    private Arena arena;
    private Integer startDelay;
    private Boolean goToGameStartBool;

    private boolean cancelled;
    private static final HandlerList handlers = new HandlerList();

    /**
     * Constructor for StartDelayStartEvent
     * @param _arena The arena that is involved in this event.
     * @param _startDelay The start delay (in seconds).
     */
    public StartDelayStartEvent(Arena _arena, Integer _startDelay) {
        arena = _arena;
        startDelay = _startDelay;
        goToGameStartBool = true;

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
     * Gets the current start delay in seconds.
     * @return The current wait delay in seconds.
     */
    public Integer getStartDelay() {
        return startDelay;
    }

    /**
     * Gets whether or not the arena will go on to starting the game.
     * By default, this is <code>true</code>.
     * @return Whether or not the arena will go on to starting the game.
     */
    public Boolean goToGameStart() {
        return goToGameStartBool;
    }

    /**
     * Sets the start delay, in seconds.
     * @param _startDelay The new start delay, in seconds.
     */
    public void setStartDelay(Integer _startDelay) {
        startDelay = _startDelay;
    }

    /**
     * Sets whether or not the arena will go on to starting the game.
     * By default, this is <code>true</code>.
     * @param value Whether or not the arena will go on to starting the game.
     */
    public void setGoToGameStart(Boolean value) {
        goToGameStartBool = value;
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