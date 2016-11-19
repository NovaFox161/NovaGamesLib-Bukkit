package com.cloudcraftgaming.novagameslib.event.regeneration;

import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Nova Fox on 11/19/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
@SuppressWarnings("unused")
public class PreRegenerationEvent extends Event implements Cancellable {
    private final Integer arenaId;

    private Boolean reloadWorldBool;
    private Boolean regenAllBlocksBool;
    private Boolean clearGroundItemsBool;
    private Boolean removeAllEntitiesBool;
    private Boolean resetDoorsBool;
    private Boolean clearContainerBlocksBool;

    private boolean cancelled;
    private static final HandlerList handlers = new HandlerList();

    /**
     * Constructor for PreRegenerationEvent
     *
     * @param _arenaId  The id of the arena that will be regenerated.
     */
    public PreRegenerationEvent(Integer _arenaId) {
        arenaId = _arenaId;

        reloadWorldBool = true;
        regenAllBlocksBool = true;
        clearGroundItemsBool = true;
        removeAllEntitiesBool = true;
        resetDoorsBool = true;
        clearContainerBlocksBool = true;
    }

    //Getters
    /**
     * Gets the ID of the arena that is going to be regenerated.
     *
     * @return The ID of the arena that will be regenerated.
     */
    public Integer getArenaId() {
        return arenaId;
    }

    /**
     * Gets whether or not to reload the world from file.
     * Turn this off if you have multiple arenas in a single world.
     * By default, this is <code>true</code>.
     * @return Whether or not to reload the world from file.
     */
    public Boolean reloadWorld() {
        return reloadWorldBool;
    }

    /**
     * Gets whether or not to regenerate all blocks in the arena from memory.
     * By default, this is <code>true</code>.
     * @return Whether or not to regenerate all blocks from memory.
     */
    public Boolean regenAllBlocks() {
        return regenAllBlocksBool;
    }

    /**
     * Gets whether or not to clear all ground items, or items floating on the ground.
     * By default, this is <code>true</code>.
     * @return Whether or not to clear all ground items.
     */
    public Boolean clearGroundItems() {
        return clearGroundItemsBool;
    }

    /**
     * Gets whether or not to remove all entities (such as monsters and animals) from the arena.
     * By default, this is <code>true</code>.
     * @return Whether or not to remove all entities from the arena.
     */
    public Boolean removeAllEntities() {
        return removeAllEntitiesBool;
    }

    /**
     * Gets whether or not to reset all doors (and such blocks) to the closed state.
     * By default, this is <code>true</code>.
     * @return Whether or not to reset all doors (and such blocks)
     */
    public Boolean resetDoors() {
        return resetDoorsBool;
    }

    /**
     * Gets whether or not to clear container blocks (such as chests).
     * By default, this is <code>true</code>.
     * @return Whether or not to clear all container blocks (such as chests).
     */
    public Boolean clearContainerBlocks() {
        return clearContainerBlocksBool;
    }

    /**
     * Whether or not the event is cancelled.
     *
     * @return Whether or not the event is cancelled.
     */
    public boolean isCancelled() {
        return cancelled;
    }

    //Setters
    /**
     * Sets whether or not to reload the world from file.
     * Turn this off if you have multiple arenas in a single world.
     * By default, this is <code>true</code>.
     * !!!! IF MORE THAN ONE ARENA IS IN A SINGLE WORLD, IT IS IMPORTANT TO TURN THIS OFF !!!!
     * @param value Whether or not to reload the world from file.
     */
    public void setReloadWorld(Boolean value) {
        reloadWorldBool = value;
    }

    /**
     * Sets whether or not to regenerate all blocks in the arena from memory.
     * By default, this is <code>true</code>.
     * @param value Whether or not to regenerate all blocks from memory.
     */
    public void setRegenAllBlocks(Boolean value) {
        regenAllBlocksBool = value;
    }

    /**
     * Sets whether or not to clear all ground items, or items floating on the ground.
     * By default, this is <code>true</code>.
     * @param value Whether or not to clear all ground items.
     */
    public void setClearGroundItems(Boolean value) {
        clearGroundItemsBool = value;
    }

    /**
     * Sets whether or not to remove all entities (such as monsters and animals) from the arena.
     * By default, this is <code>true</code>.
     * @param value Whether or not to remove all entities from the arena.
     */
    public void setRemoveAllEntities(Boolean value) {
        removeAllEntitiesBool = value;
    }

    /**
     * Sets whether not not to reset all doors (and such blocks) to the closed state.
     * By default, this is <code>true</code>.
     * @param value Whether or not to reset all doors (and such blocks)
     */
    public void setResetDoors(Boolean value) {
        resetDoorsBool = value;
    }

    /**
     * Sets whether not not to clear container blocks (such as chests).
     * By default, this is <code>true</code>.
     * @param value Whether or not to clear all container blocks (such as chests)
     */
    public void setClearContainerBlocks(Boolean value) {
        clearContainerBlocksBool = value;
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