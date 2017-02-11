package com.cloudcraftgaming.novagameslib.api.event.minigame;

import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Nova Fox on 11/19/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
@SuppressWarnings("unused")
public class MinigameQuitEvent extends Event {
    private Player player;
    private Integer arenaId;

    private static final HandlerList handlers = new HandlerList();

    /**
     * Constructor for MinigameQuitEvent
     * @param _player The player quitting a minigame.
     * @param _arenaId The id of the arena the player is quitting from.
     */
    public MinigameQuitEvent(Player _player, Integer _arenaId) {
        player = _player;
        arenaId = _arenaId;
    }

    /**
     * Gets the player involved in this event.
     * @return The player involved in this event.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets the ID of the arena the player is quitting from.
     * @return The ID of the arena the player is quitting from.
     */
    public Integer getArenaId() {
        return arenaId;
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