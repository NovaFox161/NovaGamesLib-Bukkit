package com.cloudcraftgaming.novagameslib.event.mechanics;

import com.cloudcraftgaming.novagameslib.data.ArenaDataManager;
import com.cloudcraftgaming.novagameslib.game.WinType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;
import org.bukkit.inventory.ItemStack;

/**
 * Created by Nova Fox on 2/6/17.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
public class RewardItemEvent extends Event implements Cancellable {
    private final Integer id;
    private final String gameName;
    private final WinType winType;

    private ItemStack itemStack;

    private boolean cancelled;
    private static final HandlerList handlers = new HandlerList();

    /**
     * Constructor for RewardItemEvent.
     * <br></br>
     * This event is triggered at the end of a minigame automatically and can be cancelled if no rewards are to be issued.
     * @param _id The ID of the arena involved in this event.
     * @param _winType The {@link WinType} of the arena involved in this event.
     * @param _itemStack The ItemStack to give to players, complete with amount and any lore, etc.
     */
    public RewardItemEvent(Integer _id, WinType _winType, ItemStack _itemStack) {
        id = _id;
        gameName = ArenaDataManager.getGameName(id);
        winType = _winType;

        itemStack = _itemStack;
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
     * Gets the WinType of the minigame, or in other words the way in which the game ended.
     * @return The WinType of the minigame.
     */
    public WinType getWinType() {
        return winType;
    }

    /**
     * Gets the item to give to the winning players. This includes all item data and amount.
     * @return The item to give to the winning players.
     */
    public ItemStack getItemStack() {
        return itemStack;
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

    /**
     * Gets whether or not the minigame is cancelled.
     * @return Whether or not the minigame is cancelled.
     */
    @Override
    public boolean isCancelled() {
        return cancelled;
    }

    /**
     * Sets the item to give to the winning players. This includes all item data and amount.
     * @param _itemStack THe Item to give to the winning players.
     */
    public void setItemStack(ItemStack _itemStack) {
        itemStack = _itemStack;
    }

    /**
     * Sets whether or not the minigame is cancelled.
     * @param cancel Whether or not the minigame is cancelled.
     */
    @Override
    public void setCancelled(boolean cancel) {
        cancelled = cancel;
    }
}