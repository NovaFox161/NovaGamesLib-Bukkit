package com.cloudcraftgaming.novagameslib.utils;

import com.cloudcraftgaming.novagameslib.NovaGamesLib;

import java.io.File;
import java.util.List;

import static com.cloudcraftgaming.novagameslib.NovaGamesLib.plugin;

/**
 * Created by Nova Fox on 11/14/16.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
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
            plugin.getConfig().addDefault("Language", "English");
            plugin.getConfig().addDefault("Console.Verbose", true);

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

    /*
    public static void saveCustomConfig(FileConfiguration ymlConfig, File ymlFile) {
        try {
            ymlConfig.save(ymlFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
}