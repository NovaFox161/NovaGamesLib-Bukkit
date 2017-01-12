package com.cloudcraftgaming.novagameslib.kit;

import org.bukkit.ChatColor;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.HashMap;

/**
 * Created by Nova Fox on 1/12/17.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
@SuppressWarnings("WeakerAccess")
public class Kit {
    private final String name;
    private final HashMap<Integer, ItemStack> items;

    //Data
    private String displayName;
    private String description;
    private Double cost;
    private Integer delay;
    private String displayItemName;
    private Short displayItemDurability;

    /**
     * Creates a new Kit Object for storing Kit data in memory when working with kits.
     * To create a new Kit, use {@link com.cloudcraftgaming.novagameslib.data.KitDataManager#createKit(String, Player)}
     * @param _name The name of the kit.
     * @param _items A HashMap (int key is item slot, ItemStack) containing items in the kit.
     */
    public Kit(String _name, HashMap<Integer, ItemStack> _items) {
        name = _name;
        items = _items;

        cost = 0d;
        delay = 0;
        displayItemDurability = 0;
    }

    //Getters - General kit
    /**
     * Gets the name of this kit.
     * @return The name of this kit.
     */
    public String getName() {
        return name;
    }

    /**
     * Gets the Items in the kit, Int key is their respective inventory slot.
     * @return The items in the kit.
     */
    public HashMap<Integer, ItemStack> getItems() {
        return items;
    }

    //Getters - Kit Data
    /**
     * Gets the display name of the kit WITHOUT color codes being translated.
     * Use {@link #getDisplayName()} to get the display name with color codes translated.
     * @return The display name of the kit WITHOUT color codes being translated.
     */
    public String getDisplayNameRaw() {
        return displayName;
    }

    /**
     * Gets the kit's display name WITH color codes translated.
     * Use {@link #getDisplayNameRaw()} to get the display name without color codes translated.
     * @return The display name of the kit WITH color codes translated.
     */
    public String getDisplayName() {
        return ChatColor.translateAlternateColorCodes('&', displayName);
    }

    /**
     * Gets the description of the kit WITHOUT color codes being translated.
     * Use {@link #getDescription()} to get the description with color codes translated.
     * @return The description of the kit WITHOUT color codes being translated.
     */
    public String getDescriptionRaw() {
        return description;
    }

    /**
     * Gets the description of the kit WITH color codes being translated.
     * Use {@link #getDescriptionRaw()} to get the description without color codes translated.
     * @return The description of the kit WITH color codes being translated.
     */
    public String getDescription() {
        return ChatColor.translateAlternateColorCodes('&', description);
    }

    /**
     * Gets the cost of the kit.
     * @return The cost of the kit, <code>0.0</code> if no cost.
     */
    public Double getCost() {
        return cost;
    }

    /**
     * Gets the time delay, in seconds, for the kit.
     * @return The delay for the kit, <code>0</code> if not enabled.
     */
    public Integer getDelay() {
        return delay;
    }

    /**
     * Gets the name of the item that is to be displayed in the kit selector GUI.
     * @return The name of the item to display in the kit selector GUI.
     */
    public String getDisplayItemName() {
        return displayItemName;
    }

    /**
     * Gets the durability/damage modifier of the item to be displayed in the kit selector GUI.
     * @return The durability/damage modifier of the item to display in the kit selector GUI.
     */
    public Short getDisplayItemDurability() {
        return displayItemDurability;
    }

    //Setters - Kit Data
    /**
     * Sets the display name of the kit.
     * **NOTE** Please do NOT translate the color codes!!! Leave it in raw form!
     * @param _displayName The raw display name of the kit.
     */
    public void setDisplayName(String _displayName) {
        displayName = _displayName;
    }

    /**
     * Sets the description of the kit.
     * *NOTE** Please do NOT translate the color codes!!! Leave it in raw form!
     * @param _description The raw description of the kit.
     */
    public void setDescription(String _description) {
        description = _description;
    }

    /**
     * Sets the cost of the kit. Set to <code>0.0</code> to disable.
     * @param _cost The cost of the kit.
     */
    public void setCost(Double _cost) {
        cost = _cost;
    }

    /**
     * Set the time delay, in seconds, to use the kit.
     * @param _delay The amount of time in seconds.
     */
    public void setDelay(Integer _delay) {
        delay = _delay;
    }

    /**
     * Sets the name of the item to be displayed in the kit selector GUI.
     * @param _itemName The name of the item to be displayed in the kit selector GUI.
     */
    public void setDisplayItemName(String _itemName) {
        displayItemName = _itemName;
    }

    /**
     * Sets the durability/damage modifier of the item to be displayed in the kit selector GUI.
     * @param _itemDur The durability/damage modifier of the item to be displayed in the kit selector GUI.
     */
    public void setDisplayItemDurability(Short _itemDur) {
        displayItemDurability = _itemDur;
    }
}