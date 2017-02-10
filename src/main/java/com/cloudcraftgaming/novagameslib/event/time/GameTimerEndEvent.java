package com.cloudcraftgaming.novagameslib.event.time;

import com.cloudcraftgaming.novagameslib.arena.ArenaBase;
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

    private ArenaBase arenaBase;
    private Boolean endGameBool;

    private static final HandlerList handlers = new HandlerList();

    /**
     * Constructor for GameTimerEndEvent
     *
     * @param _arenaBase      The arenaBase that is involved in this event.
     */
    public GameTimerEndEvent(ArenaBase _arenaBase) {
        arenaBase = _arenaBase;
        endGameBool = true;

        id = arenaBase.getId();
        gameName = arenaBase.getGameName();
    }

    /**
     * Gets the ID of the arenaBase involved in this event.
     *
     * @return The id of the arenaBase involved in this event.
     */
    public Integer getId() {
        return id;
    }

    /**
     * Gets the name of the minigame belonging to the arenaBase involved in this event.
     *
     * @return The name of the minigame belonging to the arenaBase.
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Gets the arenaBase involved in this event.
     *
     * @return The arenaBase involved in this event.
     */
    public ArenaBase getArenaBase() {
        return arenaBase;
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