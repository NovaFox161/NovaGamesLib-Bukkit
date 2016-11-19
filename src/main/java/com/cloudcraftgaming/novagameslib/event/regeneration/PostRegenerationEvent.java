package com.cloudcraftgaming.novagameslib.event.regeneration;

import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Nova Fox on 11/19/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
@SuppressWarnings("unused")
public class PostRegenerationEvent extends Event {
    private final Integer arenaId;

    private Boolean autoReload;

    private static final HandlerList handlers = new HandlerList();

    /**
     * Constructor for PostRegenerationEvent
     *
     * @param _arenaId  The id of the arena that has been regenerated.
     */
    public PostRegenerationEvent(Integer _arenaId) {
        arenaId = _arenaId;
        autoReload = true;
    }

    /**
     * Gets the ID of the arena that has been regenerated.
     *
     * @return The ID of the arena that has been regenerated.
     */
    public Integer getArenaId() {
        return arenaId;
    }

    /**
     * Gets whether or not NovaGames should automatically reload the arena or let another minigame plugin handle it.
     * By default, this is <code>true</code>.
     * @return Whether or not NovaGames should automatically reload the arena.
     */
    public Boolean autoReloadArena() {
        return autoReload;
    }

    /**
     * Sets whether or not NovaGames will automatically reload the arena or let another minigame plugin handle this.
     * By default, this is <code>true</code>.
     * @param value Whether or not NovaGames will automatically reload the arena.
     */
    public void setAutoReloadArena(Boolean value) {
        autoReload = value;
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