package com.cloudcraftgaming.novagameslib.event.time;

import com.cloudcraftgaming.novagameslib.arena.Arena;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Nova Fox on 11/19/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
@SuppressWarnings("unused")
public class GameTimerEndEvent extends Event {
    private final Integer id;
    private final String gameName;

    private Arena arena;
    private Boolean endGameBool;

    private static final HandlerList handlers = new HandlerList();

    /**
     * Constructor for GameTimerEndEvent
     *
     * @param _arena      The arena that is involved in this event.
     */
    public GameTimerEndEvent(Arena _arena) {
        arena = _arena;
        endGameBool = true;

        id = arena.getId();
        gameName = arena.getGameName();
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
     * Gets the arena involved in this event.
     *
     * @return The arena involved in this event.
     */
    public Arena getArena() {
        return arena;
    }

    /**
     * Whether or not to end the game.
     * By default, this is <code>true</code>.
     *
     * @return Whether or not to end the game.
     */
    public Boolean endGame() {
        return endGameBool;
    }

    /**
     * Sets whether or not to end the game.
     *By default, this is <code>true</code>.
     * @param value Whether or not to end the game.
     */
    public void setEndGame(boolean value) {
        endGameBool = value;
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