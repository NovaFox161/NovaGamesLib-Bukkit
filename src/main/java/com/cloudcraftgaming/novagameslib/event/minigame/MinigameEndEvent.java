package com.cloudcraftgaming.novagameslib.event.minigame;

import com.cloudcraftgaming.novagameslib.arena.Arena;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Nova Fox on 11/19/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
@SuppressWarnings("unused")
public class MinigameEndEvent extends Event {
    private final Integer id;
    private final String gameName;

    private Arena arena;

    private static final HandlerList handlers = new HandlerList();

    /**
     * Constructor  for MinigameEndEvent
     * @param _arena The arena in which a game is ending.
     */
    public MinigameEndEvent(Arena _arena) {
        id = _arena.getId();
        gameName = _arena.getGameName();

        arena = _arena;
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