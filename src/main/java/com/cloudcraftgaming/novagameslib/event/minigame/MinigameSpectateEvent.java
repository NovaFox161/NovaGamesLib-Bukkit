package com.cloudcraftgaming.novagameslib.event.minigame;

import org.bukkit.entity.Player;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Nova Fox on 11/19/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
@SuppressWarnings("unused")
public class MinigameSpectateEvent extends Event implements Cancellable {
    private Player player;
    private Integer arenaId;

    private boolean cancelled;
    private static final HandlerList handlers = new HandlerList();

    /**
     * Constructor for MinigameSpectateEvent
     * @param _player The player spectating a minigame.
     * @param _arenaId The id of the arena the player is spectating.
     */
    public MinigameSpectateEvent(Player _player, Integer _arenaId) {
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
     * Gets the ID of the arena the player is spectating.
     * @return The ID of the arena the player is spectating.
     */
    public Integer getArenaId() {
        return arenaId;
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