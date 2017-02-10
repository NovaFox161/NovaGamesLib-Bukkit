package com.cloudcraftgaming.novagameslib.api.event.region;

import org.bukkit.Location;
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
public class LocationOneSetEvent extends Event implements Cancellable {

    private final Player player;
    private Location locationOne;

    private boolean cancelled;
    private static final HandlerList handlers = new HandlerList();

    /**
     * Constructor for LocationOneSet
     * @param _player The player involved in this event.
     * @param _locationOne The location that will be Location One.
     */
    public LocationOneSetEvent(Player _player, Location _locationOne) {
        player = _player;
        locationOne = _locationOne;
    }

    /**
     * Gets the player involved in this event.
     * @return The player involved in this event.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets Location One, or the location the player has set.
     * @return Location One, or the location the player has set.
     */
    public Location getLocationOne() {
        return locationOne;
    }

    /**
     * Sets what Location One will be.
     * @param _locationOne What Location One will be.
     */
    public void setLocationOne(Location _locationOne) {
        locationOne = _locationOne;
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