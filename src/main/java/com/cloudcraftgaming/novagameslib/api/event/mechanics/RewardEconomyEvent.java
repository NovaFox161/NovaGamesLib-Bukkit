package com.cloudcraftgaming.novagameslib.api.event.mechanics;

import com.cloudcraftgaming.novagameslib.api.data.ArenaDataManager;
import com.cloudcraftgaming.novagameslib.api.game.WinType;
import org.bukkit.event.Cancellable;
import org.bukkit.event.Event;
import org.bukkit.event.HandlerList;

/**
 * Created by Nova Fox on 2/6/17.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
public class RewardEconomyEvent extends Event implements Cancellable {
    private final Integer id;
    private final String gameName;
    private final WinType winType;

    private Double amount;

    private boolean cancelled;
    private static final HandlerList handlers = new HandlerList();

    /**
     * Constructor for RewardEconomyEvent.
     * <br></br>
     * This event is triggered at the end of a minigame automatically and can be cancelled if no rewards are to be issued.
     * @param _id The ID of the arena involved in this event.
     * @param _winType The {@link WinType} of the arena involved in this event.
     * @param _amount The amount of money to be paid out to each winning player.
     */
    public RewardEconomyEvent(Integer _id, WinType _winType, Double _amount) {
        id = _id;
        gameName = ArenaDataManager.getGameName(id);
        winType = _winType;

        amount = _amount;
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
     * Gets the amount of money to pay out to the winners of the minigame.
     * @return The amount of money to pay out to the winners of the minigame.
     */
    public Double getAmount() {
        return amount;
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
     * Sets the amount of money to pay out to the winners of the minigame.
     * @param _amount The amount of monet to pay out to the winners of the minigame.
     */
    public void setAmount(Double _amount) {
        amount = _amount;
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