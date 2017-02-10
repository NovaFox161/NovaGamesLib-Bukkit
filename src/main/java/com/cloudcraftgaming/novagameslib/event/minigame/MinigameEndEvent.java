package com.cloudcraftgaming.novagameslib.event.minigame;

import com.cloudcraftgaming.novagameslib.arena.ArenaBase;
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

    private ArenaBase arenaBase;

    private static final HandlerList handlers = new HandlerList();

    /**
     * Constructor  for MinigameEndEvent
     * @param _arenaBase The arenaBase in which a game is ending.
     */
    public MinigameEndEvent(ArenaBase _arenaBase) {
        id = _arenaBase.getId();
        gameName = _arenaBase.getGameName();

        arenaBase = _arenaBase;
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