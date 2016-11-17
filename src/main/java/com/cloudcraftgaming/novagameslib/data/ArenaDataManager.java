package com.cloudcraftgaming.novagameslib.data;

import com.cloudcraftgaming.novagameslib.NovaGamesLib;
import com.cloudcraftgaming.novagameslib.region.Cuboid;
import com.cloudcraftgaming.novagameslib.team.Team;
import com.cloudcraftgaming.novagameslib.utils.FileManager;
import org.bukkit.*;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

/**
 * Created by Nova Fox on 11/17/16.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
@SuppressWarnings({"unused", "WeakerAccess"})
public class ArenaDataManager {
	//Functionals
	/**
	 * Creates a new arena.
	 * @param gameName The name of the game this is for (Ex. "Capture The Flag").
	 * @return The ID of the new arena.
	 */
	public static Integer createArena(String gameName) {
		Integer nextId = DataCache.useNextId();

		YamlConfiguration pluginCache = FileManager.getPluginCacheYml();
		pluginCache.set("ArenaAmount", pluginCache.getInt("ArenaAmount" + 1));
		FileManager.savePluginCache(pluginCache);

		createArenaFiles(nextId, gameName);

		if (FileManager.verbose()) {
			NovaGamesLib.plugin.getLogger().info("Created new arena with ID: " + nextId);
		}

		return nextId;
	}

	/**
	 * Creates a new arena.
	 * @param id The id of the new arena.
	 * @param gameName The name of the game this is for (Ex. "Capture The Flag").
	 */
	public static void createArenaFiles(int id, String gameName) {
		File configFile = new File(NovaGamesLib.plugin.getDataFolder() + "/Arenas/" + String.valueOf(id) + "/config.yml");
		YamlConfiguration config = YamlConfiguration.loadConfiguration(configFile);
		config.addDefault("Id", id);
		config.addDefault("Name", "Arena " + String.valueOf(id));
		config.addDefault("DisplayName", "&5Arena " + String.valueOf(id));
		config.addDefault("GameName", gameName);

		config.addDefault("Players.Min", 4);
		config.addDefault("Players.Max", 16);

		config.addDefault("Teams.Amount.Min", 2);
		config.addDefault("Teams.Amount.Max", 8);

		config.addDefault("Time.Delay.Wait", 90);
		config.addDefault("Time.Delay.Start", 30);
		config.addDefault("Time.Game.Length", 30);
		config.addDefault("Time.Game.Day", 3);
		config.addDefault("Time.Game.Night", 3);

		config.addDefault("Rules.GameMode", GameMode.SURVIVAL.name());
		config.addDefault("Rules.Teams.Use", false);
		config.addDefault("Rules.Teams.HideNames", false);
		config.addDefault("Rules.Teams.AllowFriendlyFire", false);
		config.addDefault("Rules.Block.Break", false);
		config.addDefault("Rules.Block.Place", false);
		config.addDefault("Rules.LateJoin.Allow", false);

		config.options().copyDefaults(true);
		saveArenaConfig(config, configFile);

		config.options().copyDefaults(true);
		saveArenaConfig(config, configFile);

		YamlConfiguration pluginCache = FileManager.getPluginCacheYml();
		List<String> arenas = pluginCache.getStringList("Arenas.All");
		arenas.add(String.valueOf(id));
		pluginCache.set("Arenas.All", arenas);
		FileManager.savePluginCache(pluginCache);
	}

	/**
	 * Saves the arena's config.
	 * @param yml The instance of the config to save.
	 * @param file The FILE the config came from: {@link #getArenaConfigFile(int)}.
	 * @return <code>true</code> is successful, else <code>false</code>.
	 */
	public static Boolean saveArenaConfig(YamlConfiguration yml, File file) {
		try {
			yml.save(file);
			return true;
		} catch (IOException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Called to enable an arena. This will call the {@link ArenaEnableEvent} event.
	 * If not cancelled will enable the specified arena.
	 * @param id The ID of the arena to enable.
	 * @return <code>true</code> if successful, else <code>false</code>.
	 */

	/* UNBLOCK ONCE EVENTS ARE ADDED
	public static Boolean enableArena(int id) {
		if (!arenaEnabled(id)) {
			if (canBeLoaded(id)) {
				ArenaEnableEvent event = new ArenaEnableEvent(id);
				Bukkit.getServer().getPluginManager().callEvent(event);

				if (!event.isCancelled()) {
					YamlConfiguration cache = FileManager.getPluginCacheYml();
					List<String> enabledArenas = cache.getStringList("Arenas.Enabled");
					enabledArenas.add(String.valueOf(id));
					cache.set("Arenas.Enabled", enabledArenas);
					FileManager.savePluginCache(cache);
					if (event.autoLoad()) {
						ArenaManager.getManager().loadArena(id, getGameName(id), usesTeams(id));
					} else {
						updateArenaInfo(id);
					}
					if (FileManager.verbose()) {
						OYAGamesManager.plugin.getLogger().info("Enabled arena " + id);
					}
					return true;
				}
			}
		}
		return false;
	}
	*/

	/**
	 * Called to disable an arena. This will call the {@link ArenaDisableEvent} event.
	 * If not cancelled will disable the specified arena.
	 * @param id The ID of the arena to disable.
	 * @return <code>true</code> if successful, else <code>false</code>.
	 */
	/* UNBLOCK ONCE EVENTS ARE ADDED
	public static Boolean disableArena(int id) {
		if (arenaEnabled(id)) {
			ArenaDisableEvent event = new ArenaDisableEvent(id);
			Bukkit.getServer().getPluginManager().callEvent(event);

			if (!event.isCancelled()) {
				YamlConfiguration cache = FileManager.getPluginCacheYml();
				List<String> enabledArenas = cache.getStringList("Arenas.Enabled");
				enabledArenas.remove(String.valueOf(id));
				cache.set("Arenas.Enabled", enabledArenas);
				FileManager.savePluginCache(cache);
				if (event.autoUnload()) {
					ArenaManager.getManager().unloadArena(id);
				} else {
					updateArenaInfo(id);
				}
				if (FileManager.verbose()) {
					OYAGamesManager.plugin.getLogger().info("Disabled arena " + id);
				}
				return true;
			}
		}
		return false;
	}
	*/

	//Booleans/Checkers
	/**
	 * Checks if the arena exists by seeing if it's config exists.
	 * @param id The id of the arena.
	 * @return <code>true</code> if it exists, else <code>false</code>.
	 */
	public static Boolean arenaExists(int id) {
		return getArenaConfigFile(id).exists();
	}

	/**
	 * Checks if the specified arena is enabled.
	 * @param id The id of the arena to check.
	 * @return <code>true</code> if the arena is enabled, else <code>false</code>.
	 */
	public static Boolean arenaEnabled(int id) {
		if (arenaExists(id)) {
			if (FileManager.getPluginCacheYml().contains("Arenas.Enabled")) {
				return FileManager.getPluginCacheYml().getStringList("Arenas.Enabled").contains(String.valueOf(id));
			}
		}
		return false;
	}

	/**
	 * Checks if the specified arena can be safely loaded without null error.
	 * @param id The id of the arena.
	 * @return <code>true</code> if it can be loaded, else <code>false</code>.
	 */
	public static Boolean canBeLoaded(int id) {
		if (arenaExists(id) && getArenaConfigYml(id).contains("Locations.End") && getArenaConfigYml(id).contains("Locations.Quit")
				&& getArenaConfigYml(id).contains("Locations.Lobby") && getArenaConfigYml(id).contains("Locations.Spectate")
				&& getArenaConfigYml(id).contains("Locations.Regen")) {

			if (usesTeams(id)) {
				for (Team team : Team.allTeams()) {
					if (!hasTeamSaved(id, team)) {
						return false;
					}
				}
			}
			return true;
		}
		return false;
	}

	/**
	 * Checks whether or not the needed team info has been saved in the arena's config.
	 * @param id The id of the arena.
	 * @param team The team to check.
	 * @return <code>true</code> if needed info is saved, else <code>false</code>.
	 */
	public static Boolean hasTeamSaved(int id, Team team) {
		YamlConfiguration config = getArenaConfigYml(id);
		return config.contains("Locations.Spawn." + team.name());
	}

	//Getters
	/**
	 * Gets the config FILE for the specified arena.
	 * Use {@link #getArenaConfigYml(int)} to get the config YML.
	 * @param id The id of the arena.
	 * @return The config file of the arena.
	 */
	public static File getArenaConfigFile(int id) {
		return new File(NovaGamesLib.plugin.getDataFolder() + "/Arenas/" + id + "/config.yml");
	}

	/**
	 * Gets the config YML for the specified arena.
	 * Use {@link #getArenaConfigFile(int)} to get the config FILE.
	 * @param id The id of the arena.
	 * @return The config YML of the arena.
	 */
	public static YamlConfiguration getArenaConfigYml(int id) {
		return YamlConfiguration.loadConfiguration(getArenaConfigFile(id));
	}

	//General getters
	/**
	 * Gets the arena's raw name.
	 * @param id The Id of the arena.
	 * @return The arena's raw name.
	 */
	public static String getName(int id) {
		return getArenaConfigYml(id).getString("Name");
	}

	/**
	 * Gets the arena's display name.
	 * @param id The id of the arena.
	 * @return The arena's display name with chat colors.
	 */
	public static String getDisplayName(int id) {
		String nameOr = getArenaConfigYml(id).getString("DisplayName");
		return ChatColor.translateAlternateColorCodes('&', nameOr) + ChatColor.RESET;
	}

	/**
	 * Gets the name of the game the arena belongs to.
	 * @param id The id of the arena.
	 * @return The name of the game the arena belongs to.
	 */
	public static String getGameName(int id) {
		return getArenaConfigYml(id).getString("GameName");
	}

	/**
	 * Gets the prefix for the chat for the arena.
	 * @param id The id of the arena.
	 * @return The properly formatted chat prefix.
	 */
	public static String getChatPrefix(int id) {
		String prefixOr = NovaGamesLib.plugin.getConfig().getString("Chat.Prefix");
		String prefix = prefixOr.replaceAll("%id%", String.valueOf(id));
		return ChatColor.translateAlternateColorCodes('&', prefix) + ChatColor.RESET;
	}

	/**
	 * Gets the GameMode the minigame is in.
	 * @param id The id of the Arena.
	 * @return The GameMode the minigame is in.
	 */
	public static GameMode getGameMode(int id) {
		return GameMode.valueOf(getArenaConfigYml(id).getString("Rules.GameMode"));
	}

	//Player related getters
	/**
	 * Gets the minimum players required for the game to start.
	 * @param id The Id of the arena.
	 * @return The minimum players needed to start the game.
	 */
	public static Integer getMinPlayers(int id) {
		return getArenaConfigYml(id).getInt("Players.Min");
	}

	/**
	 * Gets the maximum players allowed in the arena.
	 * @param id The Id of the arena.
	 * @return The maximum players allowed in the arena.
	 */
	public static Integer getMaxPlayers(int id) {
		return getArenaConfigYml(id).getInt("Players.Max");
	}

	//Team related getters
	/**
	 * Gets the minimum number of teams for the arena.
	 * @param id The Id of the arena.
	 * @return The minimum number of teams for the arena.
	 */
	public static Integer getMinTeamCount(int id) {
		return getArenaConfigYml(id).getInt("Teams.Amount.Min");
	}

	/**
	 * Gets the maximum number of teams for the arena.
	 * @param id The Id of the arena.
	 * @return The maximum number of teams for the arena.
	 */
	public static Integer getMaxTeamCount(int id) {
		return getArenaConfigYml(id).getInt("Teams.Amount.Max");
	}

	//Time related getters
	/**
	 * Gets the time, in seconds, to wait for more players after reaching the minimum player count.
	 * @param id The Id of the arena.
	 * @return The wait time, in seconds, to wait for more players.
	 */
	public static Integer getWaitDelay(int id) {
		return getArenaConfigYml(id).getInt("Time.Delay.Wait");
	}

	/**
	 * Gets the time, in seconds, before starting the game after waiting for players.
	 * @param id The Id of the arena.
	 * @return The start time, in seconds, before starting the game.
	 */
	public static Integer getStartDelay(int id) {
		return getArenaConfigYml(id).getInt("Time.Delay.Start");
	}

	/**
	 * Gets the time, in minutes, that the game can go for. This is to avoid never ending games.
	 * @param id The Id of the arena.
	 * @return The length of the game, in minutes.
	 */
	public static Integer getGameLength(int id) {
		return getArenaConfigYml(id).getInt("Time.Game.Length");
	}

	/**
	 * Gets the length of 1(one) day, in minutes, for the game.
	 * @param id The Id of the arena.
	 * @return The length of 1(one) day, in minutes.
	 */
	public static Integer getDayLength(int id) {
		return getArenaConfigYml(id).getInt("Time.Game.Day");
	}

	/**
	 * Gets the length of 1(one) night, in minutes, for the game.
	 * @param id The Id of the arena.
	 * @return The length of 1(one) night, in minutes.
	 */
	public static Integer getNightLength(int id) {
		return getArenaConfigYml(id).getInt("Time.Game.Night");
	}

	//Location getters
	/**
	 * Gets the lobby position of where players will wait before the game starts.
	 * @param id The Id of the arena.
	 * @return The location players will be teleported to before the game starts.
	 */
	public static Location getLobbyLocation(int id) {
		YamlConfiguration config = getArenaConfigYml(id);
		String worldName = config.getString("Locations.Lobby.world");
		Double x = config.getDouble("Locations.Lobby.x");
		Double y = config.getDouble("Locations.Lobby.y");
		Double z = config.getDouble("Locations.Lobby.z");
		Integer ya = config.getInt("Locations.Lobby.yaw");
		Integer pi = config.getInt("Locations.Lobby.pitch");
		World world = Bukkit.getWorld(worldName);
		return new Location(world, x, y, z, ya, pi);
	}

	/**
	 * Gets the quit position of where a player will be teleported to after quiting the game.
	 * @param id The Id of the arena.
	 * @return The location a player will be teleported to after quiting the game.
	 */
	public static Location getQuitLocation(int id) {
		YamlConfiguration config = getArenaConfigYml(id);
		String worldName = config.getString("Locations.Quit.world");
		Double x = config.getDouble("Locations.Quit.x");
		Double y = config.getDouble("Locations.Quit.y");
		Double z = config.getDouble("Locations.Quit.z");
		Integer ya = config.getInt("Locations.Quit.yaw");
		Integer pi = config.getInt("Locations.Quit.pitch");
		World world = Bukkit.getWorld(worldName);
		return new Location(world, x, y, z, ya, pi);
	}

	/**
	 * Gets the end position of where players will be teleported to when the game ends.
	 * @param id The Id of the arena.
	 * @return The location players will be teleported to when the game ends.
	 */
	public static Location getEndLocation(int id) {
		YamlConfiguration config = getArenaConfigYml(id);
		String worldName = config.getString("Locations.End.world");
		Double x = config.getDouble("Locations.End.x");
		Double y = config.getDouble("Locations.End.y");
		Double z = config.getDouble("Locations.End.z");
		Integer ya = config.getInt("Locations.End.yaw");
		Integer pi = config.getInt("Locations.End.pitch");
		World world = Bukkit.getWorld(worldName);
		return new Location(world, x, y, z, ya, pi);
	}

	/**
	 * Gets the location spectators are teleported to.
	 * @param id The id of the arena.
	 * @return The location spectators are teleported to.
	 */
	public static Location getSpectateLocation(int id) {
		YamlConfiguration config = getArenaConfigYml(id);
		String worldName = config.getString("Locations.Spectate.world");
		Double x = config.getDouble("Locations.Spectate.x");
		Double y = config.getDouble("Locations.Spectate.y");
		Double z = config.getDouble("Locations.Spectate.z");
		Integer ya = config.getInt("Locations.Spectate.yaw");
		Integer pi = config.getInt("Locations.Spectate.pitch");
		World world = Bukkit.getWorld(worldName);
		return new Location(world, x, y, z, ya, pi);
	}

	/**
	 * Gets the Main Spawn location to which players will be teleported on game start when not using teams.
	 * Can be overridden by other plugins.
	 * Use {@link #getSecondarySpawnLocation(int)} to get the Secondary Spawn Location.
	 * @param id The id of the arena.
	 * @return The Main spawn location.
	 */
	public static Location getMainSpawnLocation(int id) {
		YamlConfiguration config = getArenaConfigYml(id);
		String worldName = config.getString("Locations.Spawn.Main.world");
		Double x = config.getDouble("Locations.Spawn.Main.x");
		Double y = config.getDouble("Locations.Spawn.Main.y");
		Double z = config.getDouble("Locations.Spawn.Main.z");
		Integer ya = config.getInt("Locations.Spawn.Main.yaw");
		Integer pi = config.getInt("Locations.Spawn.Main.pitch");
		World world = Bukkit.getWorld(worldName);
		return new Location(world, x, y, z, ya, pi);
	}

	/**
	 * Gets the Secondary Spawn location to which players will be teleported on game start when not using teams.
	 * Can be overridden by other plugins.
	 * Use {@link #getMainSpawnLocation(int)} to get the Main Spawn Location.
	 * @param id The id of the arena.
	 * @return The Secondary spawn location.
	 */
	public static Location getSecondarySpawnLocation(int id) {
		YamlConfiguration config = getArenaConfigYml(id);
		String worldName = config.getString("Locations.Spawn.Secondary.world");
		Double x = config.getDouble("Locations.Spawn.Secondary.x");
		Double y = config.getDouble("Locations.Spawn.Secondary.y");
		Double z = config.getDouble("Locations.Spawn.Secondary.z");
		Integer ya = config.getInt("Locations.Spawn.Secondary.yaw");
		Integer pi = config.getInt("Locations.Spawn.Secondary.pitch");
		World world = Bukkit.getWorld(worldName);
		return new Location(world, x, y, z, ya, pi);
	}

	/**
	 * Gets the specified team's spawn location.
	 * @param id The id of the arena.
	 * @param team The team to get.
	 * @return The team's spawn location.
	 */
	public static Location getTeamSpawnLocation(int id, Team team) {
		YamlConfiguration config = getArenaConfigYml(id);
		String worldName = config.getString("Locations.Spawn." + team.name() + ".world");
		Double x = config.getDouble("Locations.Spawn." + team.name() + ".x");
		Double y = config.getDouble("Locations.Spawn." + team.name() + ".y");
		Double z = config.getDouble("Locations.Spawn." + team.name() + ".z");
		Integer ya = config.getInt("Locations.Spawn." + team.name() + ".yaw");
		Integer pi = config.getInt("Locations.Spawn." + team.name() + ".pitch");
		World world = Bukkit.getWorld(worldName);
		return new Location(world, x, y, z, ya, pi);
	}

	/**
	 * Gets the entire cuboid region that is to be regenerated.
	 * @param id The id of the arena.
	 * @return The entire cuboid region that is to be regenerated.
	 */
	public static Cuboid getRegenArea(int id) {
		YamlConfiguration config = getArenaConfigYml(id);
		World w1 = Bukkit.getWorld(config.getString("Locations.Regen.loc1.world"));
		Double x1 = config.getDouble("Locations.Regen.loc1.x");
		Double y1 = config.getDouble("Locations.Regen.loc1.y");
		Double z1 = config.getDouble("Locations.Regen.loc1.z");

		World w2 = Bukkit.getWorld(config.getString("Locations.Regen.loc2.world"));
		Double x2 = config.getDouble("Locations.Regen.loc2.x");
		Double y2 = config.getDouble("Locations.Regen.loc2.y");
		Double z2 = config.getDouble("Locations.Regen.loc2.z");

		Location loc1 = new Location(w1, x1, y1, z1);
		Location loc2 = new Location(w2, x2, y2, z2);
		return new Cuboid(loc1, loc2);
	}

	//Rule getters
	/**
	 * Gets whether or not this arena will use teams.
	 * @param id The id of the arena.
	 * @return <code>true</code> if use teams, else <code>false</code>.
	 */
	public static Boolean usesTeams(int id) {
		return getArenaConfigYml(id).getBoolean("Rules.Teams.Use");
	}

	/**
	 * Gets whether or not players' name tags are to be visible.
	 * @param id The Id of the arena.
	 * @return <code>true</code> is player names should NOT be visible, else <code>false</code>.
	 */
	public static Boolean getHideName(int id) {
		return getArenaConfigYml(id).getBoolean("Rules.Teams.HideNames");
	}

	/**
	 * Gets whether or not friendly fire is allowed.
	 * @param id The id of the arena.
	 * @return <code>true</code> if friendly fire is allowed, else <code>false</code>.
	 */
	public static Boolean allowsFriendlyFire(int id) {
		return getArenaConfigYml(id).getBoolean("Rules.Teams.AllowFriendlyFire");
	}

	/**
	 * Gets whether or not players can break blocks in the arena.
	 * Use {@link #allowsBlockPlace(int)} to check if block placing is allowed.
	 * @param id The id of the arena.
	 * @return <code>true</code> if block breaking is allowed, else <code>false</code>.
	 */
	public static Boolean allowsBlockBreak(int id) {
		return getArenaConfigYml(id).getBoolean("Rules.Block.Break");
	}

	/**
	 * Gets whether or not players can break place in the arena.
	 * Use {@link #allowsBlockBreak(int)} to check if block breaking is allowed.
	 * @param id The id of the arena.
	 * @return <code>true</code> if block placing is allowed, else <code>false</code>.
	 */
	public static Boolean allowsBlockPlace(int id) {
		return getArenaConfigYml(id).getBoolean("Rules.Block.Place");
	}

	/**
	 * Checks if the specified arena allows late joining
	 * @param id The arena id to check.
	 * @return <code>true</code> if the arena allows late joining, else <code>false</code>.
	 */
	public static Boolean allowsLateJoin(int id) {
		return getArenaConfigYml(id).getString("Rules.LateJoin.Allow").equalsIgnoreCase("True");
	}

	//General setters
	/**
	 *  Sets the arena's display name.
	 * @param id The Id of the arena.
	 * @param name The name you want it to have.
	 */
	public static void setDisplayName(int id, String name) {
		YamlConfiguration config = getArenaConfigYml(id);
		config.set("DisplayName", name);
		saveArenaConfig(config, getArenaConfigFile(id));
	}

	/**
	 * Sets the GameMode the minigame is in.
	 * @param id The Id of the Arena.
	 * @param gameModeString The GameMode the minigame is in.
	 * @return <code>true</code> if successful, else <code>false</code>.
	 */
	public static boolean setGameMode(int id, String gameModeString) {
		YamlConfiguration config = getArenaConfigYml(id);
		String gameModeStringCorrect = gameModeString.toUpperCase();
		try {
			GameMode gameMode = GameMode.valueOf(gameModeStringCorrect);
			config.set("Rules.GameMode", gameMode.toString());
			saveArenaConfig(config, getArenaConfigFile(id));
			return true;
		} catch (IllegalArgumentException e) {
			//Gamemode not correct.
		}
		return false;
	}

	//Player related setters
	/**
	 * Sets the minimum amount of players required to start the game.
	 * @param id The Id of the arena.
	 * @param minPlayers The minimum number of players.
	 */
	public static void setMinPlayers(int id, Integer minPlayers) {
		YamlConfiguration config = getArenaConfigYml(id);
		config.set("Players.Min", minPlayers);
		saveArenaConfig(config, getArenaConfigFile(id));
	}

	/**
	 * Sets the maximum amount of players for the arena.
	 * @param id The Id of the arena.
	 * @param maxPlayers The maximum number of players.
	 */
	public static void setMaxPlayers(int id, Integer maxPlayers) {
		YamlConfiguration config = getArenaConfigYml(id);
		config.set("Players.Max", maxPlayers);
		saveArenaConfig(config, getArenaConfigFile(id));
	}

	//Team related setters
	/**
	 * Sets the minimum amount of teams for the arena.
	 * @param id The Id of the arena.
	 * @param minCount The minimum number of teams.
	 */
	public static void setMinTeamCount(int id, Integer minCount) {
		YamlConfiguration config = getArenaConfigYml(id);
		config.set("Teams.Amount.Min", minCount);
		saveArenaConfig(config, getArenaConfigFile(id));
	}

	/**
	 * Sets the maximum amount of teams for the arena.
	 * @param id The Id of the arena.
	 * @param maxCount The maximum number of of teams.
	 */
	public static void setMaxTeamCount(int id, Integer maxCount) {
		YamlConfiguration config = getArenaConfigYml(id);
		config.set("Teams.Amount.Max", maxCount);
		saveArenaConfig(config, getArenaConfigFile(id));
	}

	//Time related setters
	/**
	 * Sets the time (in seconds) that an arena will wait for players after the minimum amount of players is reached.
	 * @param id The Id of the arena.
	 * @param time The time (in seconds).
	 */
	public static void setWaitDelay(int id, Integer time) {
		YamlConfiguration config = getArenaConfigYml(id);
		config.set("Time.Delay.Wait", time);
		saveArenaConfig(config, getArenaConfigFile(id));
	}

	/**
	 * Sets the delay (in seconds) before a game will begin after waiting for all players.
	 * @param id The Id of the arena.
	 * @param time The time (in seconds).
	 */
	public static void setStartDelay(int id, Integer time) {
		YamlConfiguration config = getArenaConfigYml(id);
		config.set("Time.Delay.Start", time);
		saveArenaConfig(config, getArenaConfigFile(id));
	}

	/**
	 * Sets the length of the game (in minutes) before the game will forcibly end (to avoid unending games).
	 * @param id The Id of the arena.
	 * @param time The length of the game (in minutes).
	 */
	public static void setGameLength(int id, Integer time) {
		YamlConfiguration config = getArenaConfigYml(id);
		config.set("Time.Game.Length", time);
		saveArenaConfig(config, getArenaConfigFile(id));
	}

	/**
	 * Sets the length of a single day (in minutes) for the game.
	 * @param id The Id of the arena.
	 * @param time The length of the day (in minutes).
	 */
	public static void setDayLength(int id, Integer time) {
		YamlConfiguration config = getArenaConfigYml(id);
		config.set("Time.Game.Time", time);
		saveArenaConfig(config, getArenaConfigFile(id));
	}

	/**
	 * Sets the length of a single night (in minutes) for the game.
	 * @param id The Id of the arena.
	 * @param time The length of the night (in minutes).
	 */
	public static void setNightLength(int id, Integer time) {
		YamlConfiguration config = getArenaConfigYml(id);
		config.set("Time.Game.Night", time);
		saveArenaConfig(config, getArenaConfigFile(id));
	}

	//Location related setters
	/**
	 * Sets the lobby position, or where players will wait before the game starts.
	 * @param id The Id of the arena.
	 * @param loc The location of the lobby/where players will be teleported.
	 */
	public static void setLobbyLocation(int id, Location loc) {
		YamlConfiguration config = getArenaConfigYml(id);
		config.set("Locations.Lobby.world", loc.getWorld().getName());
		config.set("Locations.Lobby.x", loc.getX());
		config.set("Locations.Lobby.y", loc.getY());
		config.set("Locations.Lobby.z", loc.getZ());
		config.set("Locations.Lobby.yaw", loc.getYaw());
		config.set("Locations.Lobby.pitch", loc.getPitch());
		saveArenaConfig(config, getArenaConfigFile(id));
	}

	/**
	 * Sets the location of where a player will be teleported after quiting the game.
	 * @param id The Id of the arena.
	 * @param loc The location of where the player will be teleported.
	 */
	public static void setQuitLocation(int id, Location loc) {
		YamlConfiguration config = getArenaConfigYml(id);
		config.set("Locations.Quit.world", loc.getWorld().getName());
		config.set("Locations.Quit.x", loc.getX());
		config.set("Locations.Quit.y", loc.getY());
		config.set("Locations.Quit.z", loc.getZ());
		config.set("Locations.Quit.yaw", loc.getYaw());
		config.set("Locations.Quit.pitch", loc.getPitch());
		saveArenaConfig(config, getArenaConfigFile(id));
	}

	/**
	 * Sets the location where players will be teleported at the end of the game.
	 * @param id The Id of the arena.
	 * @param loc The location where players will be teleported.
	 */
	public static void setEndLocation(int id, Location loc) {
		YamlConfiguration config = getArenaConfigYml(id);
		config.set("Locations.End.world", loc.getWorld().getName());
		config.set("Locations.End.x", loc.getX());
		config.set("Locations.End.y", loc.getY());
		config.set("Locations.End.z", loc.getZ());
		config.set("Locations.End.yaw", loc.getYaw());
		config.set("Locations.End.pitch", loc.getPitch());
		saveArenaConfig(config, getArenaConfigFile(id));
	}

	/**
	 * Sets the location spectators are teleported to.
	 * @param id The Id of the Arena
	 * @param loc THe spectating location.
	 */
	public static void setSpectateLocation(int id, Location loc) {
		YamlConfiguration config = getArenaConfigYml(id);
		config.set("Locations.Spectate.world", loc.getWorld().getName());
		config.set("Locations.Spectate.x", loc.getX());
		config.set("Locations.Spectate.y", loc.getY());
		config.set("Locations.Spectate.z", loc.getZ());
		config.set("Locations.Spectate.yaw", loc.getYaw());
		config.set("Locations.Spectate.pitch", loc.getPitch());
		saveArenaConfig(config, getArenaConfigFile(id));
	}

	/**
	 * Sets the Main Spawn location to which players will be teleported to when the game starts and not using Teams.
	 * Can be overridden by other plugins.
	 * Use {@link  #setSecondarySpawn(int, Location)} to set the Secondary Spawn Location.
	 * @param id The Id of the Arena.
	 * @param loc The Main spawn location.
	 */
	public static void setMainSpawn(int id, Location loc) {
		YamlConfiguration config = getArenaConfigYml(id);
		config.set("Locations.Spawn.Main.world", loc.getWorld().getName());
		config.set("Locations.Spawn.Main.x", loc.getX());
		config.set("Locations.Spawn.Main.y", loc.getY());
		config.set("Locations.Spawn.Main.z", loc.getZ());
		config.set("Locations.Spawn.Main.yaw", loc.getYaw());
		config.set("Locations.Spawn.Main.pitch", loc.getPitch());
		saveArenaConfig(config, getArenaConfigFile(id));
	}

	/**
	 * Sets the Secondary Spawn location to which players will be teleported to when the starts and not using Teams.
	 * Can be overridden by other plugins.
	 * Use {@link #setMainSpawn(int, Location)} to set the Main Spawn Location.
	 * @param id the id of the arena.
	 * @param loc The Secondary spawn location.
	 */
	public static void setSecondarySpawn(int id, Location loc) {
		YamlConfiguration config = getArenaConfigYml(id);
		config.set("Locations.Spawn.Secondary.world", loc.getWorld().getName());
		config.set("Locations.Spawn.Secondary.x", loc.getX());
		config.set("Locations.Spawn.Secondary.y", loc.getY());
		config.set("Locations.Spawn.Secondary.z", loc.getZ());
		config.set("Locations.Spawn.Secondary.yaw", loc.getYaw());
		config.set("Locations.Spawn.Secondary.pitch", loc.getPitch());
		saveArenaConfig(config, getArenaConfigFile(id));
	}

	/**
	 * Sets the specified team's spawn point.
	 * @param id The id of the arena.
	 * @param loc The spawn location.
	 * @param teamName The name of the team (case insensitive).
	 * @return <code>true</code> is successful, else <code>false</code>.
	 */
	public static Boolean setTeamSpawn(int id, Location loc, String teamName) {
		teamName = teamName.toUpperCase();
		if (Team.exists(teamName)) {
			YamlConfiguration config = getArenaConfigYml(id);
			config.set("Locations.Spawn." + teamName + ".world", loc.getWorld().getName());
			config.set("Locations.Spawn." + teamName + ".x", loc.getX());
			config.set("Locations.Spawn." + teamName + ".y", loc.getY());
			config.set("Locations.Spawn." + teamName + ".z", loc.getZ());
			config.set("Locations.Spawn." + teamName + ".yaw", loc.getY());
			config.set("Locations.Spawn." + teamName + ".pitch", loc.getPitch());
			saveArenaConfig(config, getArenaConfigFile(id));
			return true;
		}
		return false;
	}

	/**
	 * Sets the total cuboid region to be regenerated after a game.
	 * @param id The id of the arena.
	 * @param loc1 The first corner of the region
	 * @param loc2 The second corner of the region
	 */
	public static void setRegenArea(int id, Location loc1, Location loc2) {
		YamlConfiguration config = getArenaConfigYml(id);
		config.set("Locations.Regen.loc1.world", loc1.getWorld().getName());
		config.set("Locations.Regen.loc1.x", loc1.getX());
		config.set("Locations.Regen.loc1.y", loc1.getY());
		config.set("Locations.Regen.loc1.z", loc1.getZ());
		config.set("Locations.Regen.loc2.world", loc2.getWorld().getName());
		config.set("Locations.Regen.loc2.x", loc2.getX());
		config.set("Locations.Regen.loc2.y", loc2.getY());
		config.set("Locations.Regen.loc2.z", loc2.getZ());
		saveArenaConfig(config, getArenaConfigFile(id));
	}

	//Rule related setters
	/**
	 * Sets whether or not to use teams.
	 * @param id The ID of the arena.
	 * @param value Whether or not to use teams.
	 */
	public static void setUseTeams(int id, Boolean value) {
		YamlConfiguration config = getArenaConfigYml(id);
		config.set("Rules.Teams.Use", value);
		saveArenaConfig(config, getArenaConfigFile(id));
	}

	/**
	 * Sets whether or not players' name tags are visible.
	 * @param id The Id of the arena.
	 * @param value True or false, if name tags are visible.
	 */
	public static void setHideNames(int id, Boolean value) {
		YamlConfiguration config = getArenaConfigYml(id);
		config.set("Rules.Teams.HideNames", value);
		saveArenaConfig(config, getArenaConfigFile(id));
	}

	/**
	 * Sets whether or not friendly fire is allowed.
	 * @param id The Id of the arena.
	 * @param value True of false, if friendly fire is allowed.
	 */
	public static void setAllowFriendlyFire(int id, Boolean value) {
		YamlConfiguration config = getArenaConfigYml(id);
		config.set("Rules.Teams.AllowFriendlyFire", value);
		saveArenaConfig(config, getArenaConfigFile(id));
	}

	/**
	 * Sets whether or not block breaking is allowed.
	 * @param id The id of the arena.
	 * @param value Whether or not block breaking is allowed.
	 */
	public static void setAllowBlockBreak(int id, Boolean value) {
		YamlConfiguration config = getArenaConfigYml(id);
		config.set("Rules.Block.Break", value);
		saveArenaConfig(config, getArenaConfigFile(id));
	}

	/**
	 * Sets whether or not block placing is allowed.
	 * @param id The id of the arena.
	 * @param value Whether or not block breaking is allowed.
	 */
	public static void setAllowBlockPlace(int id, Boolean value) {
		YamlConfiguration config = getArenaConfigYml(id);
		config.set("Rules.Block.Place", value);
		saveArenaConfig(config, getArenaConfigFile(id));
	}

	/**
	 * Sets whether or not late joining is allowed (joining after game start).
	 * @param id the id of the arena.
	 * @param value Whether or not late joining is allowed.
	 */
	public static void setAllowLateJoin(int id, Boolean value) {
		YamlConfiguration config = getArenaConfigYml(id);
		config.set("Rules.LateJoin.Allow", value);
		saveArenaConfig(config, getArenaConfigFile(id));
	}
}