package com.cloudcraftgaming.novagameslib.api.event.time;

import com.cloudcraftgaming.novagameslib.api.arena.ArenaBase;
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

    private ArenaBase arenaBase;
    private Integer startDelay;
    private Boolean goToGameStartBool;

    private boolean cancelled;
    private static final HandlerList handlers = new HandlerList();

    /**
     * Constructor for StartDelayStartEvent
     * @param _arenaBase The arenaBase that is involved in this event.
     * @param _startDelay The start delay (in seconds).
     */
    public StartDelayStartEvent(ArenaBase _arenaBase, Integer _startDelay) {
        arenaBase = _arenaBase;
        startDelay = _startDelay;
        goToGameStartBool = true;

        id = arenaBase.getId();
        gameName = arenaBase.getGameName();
    }

    /**
     * Gets the ID of the arenaBase involved in this event.
     * @return The id of the arenaBase involved in this event.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets the name of the minigame belonging to the arenaBase involved in this event.
     * @return The name of the minigame belonging to the arenaBase.
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Gets the arenaBase involved in this event.
     * @return The arenaBase involved in this event.
     */
    public ArenaBase getArenaBase() {
        return arenaBase;
    }

    /**
     * Gets the current start delay in seconds.
     * @return The current wait delay in seconds.
     */
    public Integer getStartDelay() {
        return startDelay;
    }

    /**
     * Gets whether or not the arenaBase will go on to starting the game.
     * By default, this is <code>true</code>.
     * @return Whether or not the arenaBase will go on to starting the game.
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
     * Sets whether or not the arenaBase will go on to starting the game.
     * By default, this is <code>true</code>.
     * @param value Whether or not the arenaBase will go on to starting the game.
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