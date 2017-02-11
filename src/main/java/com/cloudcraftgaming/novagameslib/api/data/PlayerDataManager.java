package com.cloudcraftgaming.novagameslib.api.data;

import com.cloudcraftgaming.novagameslib.NovaGamesLib;
import com.cloudcraftgaming.novagameslib.api.utils.FileManager;
import com.cloudcraftgaming.novagameslib.api.utils.InventoryToBase64;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.configuration.file.YamlConfiguration;
import org.bukkit.entity.Player;
import org.bukkit.inventory.Inventory;

import java.io.File;
import java.io.IOException;

/**
 * Created by Nova Fox on 11/17/16.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class PlayerDataManager {
	//Booleans/Checkers
	/**
	 * Checks if the specified player has a data file.
	 * @param player The player to check
	 * @return <code>true</code> if they have a data file, else <code>false</code>.
	 */
	public static Boolean hasPlayerData(Player player) {
		return getPlayerDataFile(player).exists();
	}

	/**
	 * Checks if the player has saved location 1.
	 * These locations are used for setting cuboid regions.
	 * Use {@link #hasLocationTwoSaved(Player)} to check if the player has the second location saved.
	 * @param player The player to check.
	 * @return <code>true</code> if the player has location 1 saved, else <code>false</code>.
	 */
	public static Boolean hasLocationOneSaved(Player player) {
		return getPlayerDataYml(player).contains("Loc.Loc1");
	}

	/**
	 * Checks if the player has saved location 2.
	 * These locations are used for setting cuboid regions.
	 * Use {@link #hasLocationOneSaved(Player)} to check if the player has the first location saved.
	 * @param player The player to check.
	 * @return <code>true</code> if the player has location 2, else <code>false</code>.
	 */
	public static Boolean hasLocationTwoSaved(Player player) {
		return getPlayerDataYml(player).contains("Loc.Loc2");
	}

	//Data getters
	/**
	 * Gets the player's data file for NovaGames. Only if the player has one.
	 * Use {@link #getPlayerDataYml(Player)} to get the player's data Yml.
	 * @param player The player to get.
	 * @return The player's NovaGames data file.
	 */
	public static File getPlayerDataFile(Player player) {
		return new File(NovaGamesLib.plugin.getDataFolder() + "/Data/PlayerData/" + player.getUniqueId() + ".yml");
	}

	/**
	 * Gets the player's data yml for NovaGames. Only if the player has one.
	 * Use {@link #getPlayerDataFile(Player)} to get the player's data File.
	 * @param player The player to get.
	 * @return The player's NovaGames data yml.
	 */
	public static YamlConfiguration getPlayerDataYml(Player player) {
		return YamlConfiguration.loadConfiguration(getPlayerDataFile(player));
	}

	/**
	 * Checks if the player has the arena tool (A stick) enabled for setting cuboid locations.
	 * @param player The player to check.
	 * @return <code>true</code> if enabled, else <code>false</code>.
	 */
	public static Boolean hasArenaToolEnabled(Player player) {
		return Boolean.valueOf(getPlayerDataYml(player).getString("ArenaTool"));
	}

	/**
	 * Gets location one that the player has saved.
	 * Be sure to use {@link #hasLocationOneSaved(Player)} before to avoid null errors.
	 * Use {@link #getSaveLocationTwo(Player)} to get the second location.
	 * @param player The player to check.
	 * @return Location one that the player has set.
	 */
	public static Location getSaveLocationOne(Player player) {
		YamlConfiguration data = getPlayerDataYml(player);
		World world = Bukkit.getWorld(data.getString("Loc.Loc1.world"));
		Double x = data.getDouble("Loc.Loc1.x");
		Double y = data.getDouble("Loc.Loc1.y");
		Double z = data.getDouble("Loc.Loc1.z");
		return new Location(world, x, y, z);
	}

	/**
	 * Gets location two that the player has saved.
	 * Be sure to use {@link #hasLocationTwoSaved(Player)} before to avoid null errors.
	 * Use {@link #getSaveLocationOne(Player)} to get the first location.
	 * @param player The player to check.
	 * @return Location two that the player has set.
	 */
	public static Location getSaveLocationTwo(Player player) {
		YamlConfiguration data = getPlayerDataYml(player);
		World world = Bukkit.getWorld(data.getString("Loc.Loc2.world"));
		Double x = data.getDouble("Loc.Loc2.x");
		Double y = data.getDouble("Loc.Loc2.y");
		Double z = data.getDouble("Loc.Loc2.z");
		return new Location(world, x, y, z);
	}

	//Data setters
	/**
	 * Sets whether or not the arena tool is enabled for the player.
	 * @param player The player to set.
	 * @param enabled Whether or not the arena tool is enabled.
	 */
	public static void setArenaToolEnabled(Player player, Boolean enabled) {
		YamlConfiguration data = getPlayerDataYml(player);
		data.set("ArenaTool", enabled);
		savePlayerData(data, getPlayerDataFile(player));
	}

	/**
	 * Saves the location as location one for the specified player.
	 * Use {@link #saveLocationTwo(Player, Location)} to save location two.
	 * @param player The player to set.
	 * @param loc The location to set.
	 */
	public static void saveLocationOne(Player player, Location loc) {
		YamlConfiguration data = getPlayerDataYml(player);
		data.set("Loc.Loc1.world", loc.getWorld().getName());
		data.set("Loc.Loc1.x", loc.getX());
		data.set("Loc.Loc1.y", loc.getY());
		data.set("Loc.Loc1.z", loc.getZ());
		savePlayerData(data, getPlayerDataFile(player));
	}

	/**
	 * Saves the location as location two for the specified player.
	 * Use {@link #saveLocationOne(Player, Location)} to save location one.
	 * @param player The player to set.
	 * @param loc The location to set.
	 */
	public static void saveLocationTwo(Player player, Location loc) {
		YamlConfiguration data = getPlayerDataYml(player);
		data.set("Loc.Loc2.world", loc.getWorld().getName());
		data.set("Loc.Loc2.x", loc.getX());
		data.set("Loc.Loc2.y", loc.getY());
		data.set("Loc.Loc2.z", loc.getZ());
		savePlayerData(data, getPlayerDataFile(player));
	}

	//Functionals
	/**
	 * Creates the player's NovaGames data file if one does not already exist.
	 * @param player The player to create a file for.
	 */
	public static void createPlayerData(Player player) {
		if (!hasPlayerData(player)) {
			if (FileManager.verbose()) {
				NovaGamesLib.plugin.getLogger().info("Generating player data for: " + player.getName());
			}
			YamlConfiguration data = getPlayerDataYml(player);
			data.addDefault("DO NOT DELETE.A", "NovaGamesLib is developed and managed by Shades161 (NovaFox161)");
			data.addDefault("DO NOT DELETE.B", "This plugin is an API and is useless on its own!");

			data.addDefault("ArenaTool", false);

			data.options().copyDefaults(true);
			savePlayerData(data, getPlayerDataFile(player));

			data.options().copyDefaults(true);
			savePlayerData(data, getPlayerDataFile(player));
		}

	}

	/**
	 * Saves the player's data yml.
	 * @param dataYml The instance of the player data yml.
	 * @param dataFile The player's data file: {@link #getPlayerDataFile(Player)}
	 */
	public static void savePlayerData(YamlConfiguration dataYml, File dataFile) {
		try {
			dataYml.save(dataFile);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	/**
	 * Updates the player's data file with new values or fixes/removes old values.
	 * If a file does not exist, one will be created.
	 * @param player The player to update a file for.
	 */
	public static void updatePlayerData(Player player) {
		if (hasPlayerData(player)) {
			if (FileManager.verbose()) {
				NovaGamesLib.plugin.getLogger().info("Updating player data for: " + player.getName());
			}
			YamlConfiguration data = getPlayerDataYml(player);
			data.set("ArenaTool", false);
			savePlayerData(data, getPlayerDataFile(player));
			if (hasLocationOneSaved(player)) {
				deleteSaveLocationOne(player);
			}
			if (hasLocationTwoSaved(player)) {
				deleteSaveLocationTwo(player);
			}
		}
	}

	/**
	 * Deletes location one from the player's data file if it exists.
	 * Use {@link #deleteSaveLocationTwo(Player)} to delete location two.
	 * @param player The player to delete the location from.
	 */
	public static void deleteSaveLocationOne(Player player) {
		if (hasLocationOneSaved(player)) {
			YamlConfiguration data = getPlayerDataYml(player);
			data.set("Loc.Loc1", null);
			savePlayerData(data, getPlayerDataFile(player));
		}
	}

	/**
	 * Deletes location two from the player's data file if it exists.
	 * Use {@link #deleteSaveLocationOne(Player)} to delete location one.
	 * @param player The player to delete the location from.
	 */
	public static void deleteSaveLocationTwo(Player player) {
		if (hasLocationTwoSaved(player)) {
			YamlConfiguration data = getPlayerDataYml(player);
			data.set("Loc.Loc2", null);
			savePlayerData(data, getPlayerDataFile(player));
		}
	}

	/**
	 * Saves the player's enderchest to file.
	 * Only use if needed.
	 * @param player The player to save an enderchest from.
	 */
	public static void saveEnderchest(Player player) {
		String invString = InventoryToBase64.toBase64(player.getEnderChest());
		YamlConfiguration playerData = getPlayerDataYml(player);
		playerData.set("EnderChest", invString);
		savePlayerData(playerData, getPlayerDataFile(player));
	}

	/**
	 * Loads and updates the player's enderchest from file.
	 * @param player The player to get an enderchest for.
	 */
	public static void resetEnderchest(Player player) {
		if (getPlayerDataYml(player).contains("EnderChest")) {
			YamlConfiguration playerData = getPlayerDataYml(player);
			try {
				Inventory enderchest = InventoryToBase64.fromBase64(playerData.getString("EnderChest"));
				for (int i = 0; i < enderchest.getSize(); i++) {
					if (enderchest.getItem(i) != null) {
						player.getEnderChest().setItem(i, enderchest.getItem(i));
					}
				}
				playerData.set("EnderChest", null);
				savePlayerData(playerData, getPlayerDataFile(player));
			} catch (IOException e) {
				if (FileManager.verbose()) {
					NovaGamesLib.plugin.getLogger().warning("Failed to load " + player.getName() + "'s enderchest!");
				}
			}
		}
	}

	/**
	 * Saves the player's inventory to file.
	 * Only use if needed.
	 * @param player The player to save an inventory for.
	 */
	public static void saveInventory(Player player) {
		YamlConfiguration playerData = getPlayerDataYml(player);
		YamlConfiguration playerDataToSave = InventoryToBase64.toYml(player, playerData);
		savePlayerData(playerDataToSave, getPlayerDataFile(player));
	}

	/**
	 * Loads and updates the player's inventory from file.
	 * @param player The player to load an inventory for.
	 */
	public static void loadInventory(Player player) {
		YamlConfiguration playerData = getPlayerDataYml(player);
		if (playerData.contains("Inventory")) {
			InventoryToBase64.fromYml(player, playerData);
			playerData.set("Inventory.Armor", null);
			playerData.set("Inventory.Content", null);
			savePlayerData(playerData, getPlayerDataFile(player));
		}
	}

	/**
	 * Saves the player's exp to file.
	 * @param player The player to save exp for.
	 */
	public static void saveExperience(Player player) {
		YamlConfiguration data = getPlayerDataYml(player);
		data.set("xp.ToLevel", player.getExp());
		data.set("xp.Level", player.getLevel());
		savePlayerData(data, getPlayerDataFile(player));
	}

	/**
	 * Loads and updates the player's exp from file.
	 * @param player The player to load exp for.
	 */
	public static void loadExperience(Player player) {
		YamlConfiguration data = getPlayerDataYml(player);
		player.setExp((float)data.getDouble("xp.ToLevel"));
		player.setLevel(data.getInt("xp.Level"));
		data.set("xp", null);
		savePlayerData(data, getPlayerDataFile(player));
	}
}