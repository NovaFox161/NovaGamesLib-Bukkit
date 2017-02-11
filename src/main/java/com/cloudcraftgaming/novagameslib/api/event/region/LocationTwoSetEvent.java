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
public class LocationTwoSetEvent extends Event implements Cancellable {

    private final Player player;
    private Location locationTwo;

    private boolean cancelled;
    private static final HandlerList handlers = new HandlerList();

    /**
     * Constructor for LocationTwoSet
     * @param _player The player involved in this event.
     * @param _locationTwo The location that will be Location Two.
     */
    public LocationTwoSetEvent(Player _player, Location _locationTwo) {
        player = _player;
        locationTwo = _locationTwo;
    }

    /**
     * Gets the player involved in this event.
     * @return The player involved in this event.
     */
    public Player getPlayer() {
        return player;
    }

    /**
     * Gets Location Two, or the location the player has set.
     * @return Location Two, or the location the player has set.
     */
    public Location getLocationTwo() {
        return locationTwo;
    }

    /**
     * Sets what Location Two will be.
     * @param _locationTwo What Location Two will be.
     */
    public void setLocationTwo(Location _locationTwo) {
        locationTwo = _locationTwo;
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