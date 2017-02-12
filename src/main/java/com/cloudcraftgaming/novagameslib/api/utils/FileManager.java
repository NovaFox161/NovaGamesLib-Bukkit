package com.cloudcraftgaming.novagameslib.api.utils;

import com.cloudcraftgaming.novagameslib.NovaGamesLib;
import org.bukkit.Material;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;
import java.util.List;

import static com.cloudcraftgaming.novagameslib.NovaGamesLib.plugin;

/**
 * Created by Nova Fox on 11/14/16.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
@SuppressWarnings("WeakerAccess, unused")
public class FileManager {
    private static Double conVersion = 1.0;
    static Double msgVersion = 1.0;

    public static void createConfigFile() {
        File file = new File(plugin.getDataFolder() + "/config.yml");
        if (!(file.exists())) {
            plugin.getLogger().info("Generating config.yml...");

            plugin.getConfig().addDefault("DO NOT DELETE.A", "NovaGamesLib is developed and managed by Shades161 (NovaFox161)");
            plugin.getConfig().addDefault("DO NOT DELETE.B", "This plugin is an API and is useless on its own!");
            plugin.getConfig().addDefault("Config Version", conVersion);
            plugin.getConfig().addDefault("Check for Updates", true);
            plugin.getConfig().addDefault("Download Updates", false);
            plugin.getConfig().addDefault("Language", "English");
            plugin.getConfig().addDefault("Console.Verbose", true);
            plugin.getConfig().addDefault("Console.Debug", false);

            plugin.getConfig().addDefault("Database.MySQL.Use", false);
            plugin.getConfig().addDefault("Database.MySQL.Hostname", "localhost");
            plugin.getConfig().addDefault("Database.MySQL.Port", 3306);
            plugin.getConfig().addDefault("Database.MySQL.Database", "novagameslib");
            plugin.getConfig().addDefault("Database.MySQL.Prefix", "ngl_");
            plugin.getConfig().addDefault("Database.MySQL.Username", "root");
            plugin.getConfig().addDefault("Database.MySQL.Password", "password");

            plugin.getConfig().addDefault("Regeneration.Handle", true);
            plugin.getConfig().addDefault("Regeneration.Save.ToMemory", false);
            plugin.getConfig().addDefault("Regeneration.Backup.World", true);
            plugin.getConfig().addDefault("Regeneration.Backup.AutoRemove", true);

            plugin.getConfig().addDefault("Stats.Track.Enabled", true);
            plugin.getConfig().addDefault("Stats.Track.Kills", true);
            plugin.getConfig().addDefault("Stats.Track.Deaths", true);
            plugin.getConfig().addDefault("Stats.Track.Wins", true);
            plugin.getConfig().addDefault("Stats.Track.Loses", true);

            plugin.getConfig().addDefault("Sign.Manage", true);
            plugin.getConfig().addDefault("Sign.Permissions.Place", "NovaGames.Sign.place");
            plugin.getConfig().addDefault("Sign.Permissions.Use", "NovaGames.Sign.Use");
            plugin.getConfig().addDefault("Sign.Title", "[Minigame]");
            plugin.getConfig().addDefault("Sign.Join", "Join");
            plugin.getConfig().addDefault("Sign.Quit", "Quit");
            plugin.getConfig().addDefault("Sign.Spectate", "Spectate");

            plugin.getConfig().addDefault("Commands.Block", true);
            List<String> blockedCommands = plugin.getConfig().getStringList("Commands.Blocked");
            blockedCommands.add("gm");
            blockedCommands.add("gamemode");
            blockedCommands.add("spawn");
            blockedCommands.add("warp");
            blockedCommands.add("hub");
            blockedCommands.add("lobby");
            blockedCommands.add("fly");
            blockedCommands.add("give");
            blockedCommands.add("god");
            blockedCommands.add("home");
            blockedCommands.add("tp");
            plugin.getConfig().set("Commands.Blocked", blockedCommands);

            plugin.getConfig().addDefault("Chat.Handle", true);
            plugin.getConfig().addDefault("Chat.PerGame", true);
            plugin.getConfig().addDefault("Chat.PerTeam", false);
            plugin.getConfig().addDefault("Chat.Prefix", "&4[&2MG_%id%&4]");
            plugin.getConfig().addDefault("Chat.PerWorldChatPlus.CompatibilityMode", true);

            plugin.getConfig().options().copyDefaults(true);
            plugin.saveConfig();

            plugin.getConfig().options().copyDefaults(true);
            plugin.saveConfig();
        }
    }

    public static void createPluginCacheFile() {
        if (!getPluginCacheFile().exists()) {
           NovaGamesLib.plugin.getLogger().info("Generating plugin cache file...");

            YamlConfiguration cache = getPluginCacheYml();
            cache.addDefault("DO NOT DELETE.A", "NovaGamesLib is developed and managed by Shades161 (NovaFox161)");
            cache.addDefault("DO NOT DELETE.B", "This plugin is an API and is useless on its own!");
            cache.addDefault("ArenaAmount", 0);
            cache.addDefault("NextId", 1);

            cache.options().copyDefaults(true);
            savePluginCache(cache);

            cache.options().copyDefaults(true);
            savePluginCache(cache);
        }
    }

    /**
     * Creates a default yml file for kits if one does not exist.
     * This should only be used by NovaGamesLib on enable.
     */
    public static void createKitsFile() {
        if (!getKitsFile().exists()) {
            NovaGamesLib.plugin.getLogger().info("Generating kits.yml...");
            YamlConfiguration kits = getKitsYml();

            kits.addDefault("DO NOT DELETE.A", "NovaGamesLib is developed and managed by Shades161 (NovaFox161)");
            kits.addDefault("DO NOT DELETE.B", "This plugin is an API and is useless on its own!");

            //Create an example kit.
            List<String> exampleKit = kits.getStringList("Kits.Example.Items.List");
            exampleKit.add(Material.STICK.name());
            exampleKit.add(Material.MUSHROOM_SOUP.name());
            exampleKit.add(Material.WOOD_SWORD.name());
            kits.set("Kits.Example.Items.List", exampleKit);
            kits.addDefault("Kits.Example.Items." + Material.STICK.name() + ".Amount", 1);
            kits.addDefault("Kits.Example.Items." + Material.MUSHROOM_SOUP.name() + ".Amount", 3);
            kits.addDefault("Kits.Example.Items." + Material.WOOD_SWORD.name() + ".Amount", 1);

            kits.options().copyDefaults(true);
            saveKitFile(kits);

            kits.options().copyDefaults(true);
            saveKitFile(kits);
        }
    }

    public static boolean checkFileVersions() {
        if (!(plugin.getConfig().getDouble("Config Version") == conVersion)) {
            plugin.getLogger().severe("Config.yml outdated! Copy your settings, delete the file and restart the server!");
            plugin.getLogger().severe("Disabling plugin to prevent further errors...");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return false;
        }
        if (!(MessageManager.getMessagesYml().getDouble("Message Version") == msgVersion)) {
            plugin.getLogger().severe("Your message file is outdated! Copy your messages, delete the Messages folder, and restart the server!");
            plugin.getLogger().severe("Disabling plugin to prevent further errors...");
            plugin.getServer().getPluginManager().disablePlugin(plugin);
            return false;
        }
        return true;
    }

    public static Boolean verbose() {
        return NovaGamesLib.plugin.getConfig().getString("Console.Verbose").equalsIgnoreCase("True");
    }

    public static Boolean debug() {
        return NovaGamesLib.plugin.getConfig().getString("Console.Debug").equalsIgnoreCase("True");
    }

    public static Boolean useMySQL() {
        return plugin.getConfig().getString("Database.MySQL.Use").equalsIgnoreCase("True");
    }

    //Getters
    /**
     * Gets the plugin.yml file in the /Cache/ folder.
     * @return The plugin cache file.
     */
    public static File getPluginCacheFile() {
        return new File(NovaGamesLib.plugin.getDataFolder() + "/Cache/plugin.yml");
    }

    /**
     * Gets the plugin cache file as a YML file.
     * @return the plugin cache yml.
     */
    public static YamlConfiguration getPluginCacheYml() {
        return YamlConfiguration.loadConfiguration(getPluginCacheFile());
    }

    /**
     * Gets the kits.yml in the /Kits/ folder.
     * @return The kits file.
     */
    public static File getKitsFile() {
        return new File(NovaGamesLib.plugin.getDataFolder() + "/Kits/kits.yml");
    }

    /**
     * Gets the kits file as a YML file.
     * @return the kits yml.
     */
    public static YamlConfiguration getKitsYml() {
        return YamlConfiguration.loadConfiguration(getKitsFile());
    }

    //Savers
    public static void saveCustomConfig(FileConfiguration ymlConfig, File ymlFile) {
        try {
            ymlConfig.save(ymlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the plugin cache.
     * @param cache The instance of the cache to save.
     */
    public static void savePluginCache(YamlConfiguration cache) {
        try {
            cache.save(getPluginCacheFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * Saves the kits file.
     * @param kitYml The instance of the kits file to save.
     */
    public static void saveKitFile(YamlConfiguration kitYml) {
        try {
            kitYml.save(getKitsFile());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}