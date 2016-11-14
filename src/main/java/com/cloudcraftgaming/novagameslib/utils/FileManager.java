package com.cloudcraftgaming.novagameslib.utils;

import com.cloudcraftgaming.novagameslib.NovaGamesLib;

import java.io.File;
import java.util.List;

/**
 * Created by Nova Fox on 11/14/16.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
public class FileManager {
    private static Double conVersion = 1.0;
    //static Double msgVersion = 1.0;

    public static void createConfigFile() {
        File file = new File(NovaGamesLib.plugin.getDataFolder() + "/config.yml");
        if (!(file.exists())) {
            NovaGamesLib.plugin.getLogger().info("Generating config.yml...");

            NovaGamesLib.plugin.getConfig().addDefault("DO NOT DELETE.A", "NovaGamesLib is developed and managed by Shades161 (NovaFox161)");
            NovaGamesLib.plugin.getConfig().addDefault("DO NOT DELETE.B", "This plugin is an API and is useless on its own!");
            NovaGamesLib.plugin.getConfig().addDefault("Config Version", conVersion);
            NovaGamesLib.plugin.getConfig().addDefault("Check for Updates", true);
            NovaGamesLib.plugin.getConfig().addDefault("Language", "English");
            NovaGamesLib.plugin.getConfig().addDefault("Console.Verbose", true);

            NovaGamesLib.plugin.getConfig().addDefault("Database.MySQL.Use", false);
            NovaGamesLib.plugin.getConfig().addDefault("Database.MySQL.Hostname", "localhost");
            NovaGamesLib.plugin.getConfig().addDefault("Database.MySQL.Port", 3306);
            NovaGamesLib.plugin.getConfig().addDefault("Database.MySQL.Database", "novagameslib");
            NovaGamesLib.plugin.getConfig().addDefault("Database.MySQL.Prefix", "ngl_");
            NovaGamesLib.plugin.getConfig().addDefault("Database.MySQL.Username", "root");
            NovaGamesLib.plugin.getConfig().addDefault("Database.MySQL.Password", "password");

            NovaGamesLib.plugin.getConfig().addDefault("Game.Regeneration.Save.ToMemory", false);
            NovaGamesLib.plugin.getConfig().addDefault("Game.Regeneration.Backup.World", true);
            NovaGamesLib.plugin.getConfig().addDefault("Game.Regeneration.Backup.AutoRemove", true);


            NovaGamesLib.plugin.getConfig().addDefault("Game.Commands.Block", true);

            List<String> blockedCommands = NovaGamesLib.plugin.getConfig().getStringList("Game.Commands.Blocked");
            blockedCommands.add("gm");
            blockedCommands.add("gamemode");
            blockedCommands.add("spawn");
            blockedCommands.add("warp");
            blockedCommands.add("hub");
            blockedCommands.add("home");
            blockedCommands.add("tp");
            blockedCommands.add("tpa");
            blockedCommands.add("fly");
            blockedCommands.add("kill");
            NovaGamesLib.plugin.getConfig().set("Game.Commands.Blocked", blockedCommands);



            NovaGamesLib.plugin.getConfig().addDefault("Chat.PerGame", true);
            NovaGamesLib.plugin.getConfig().addDefault("Chat.PerWorldChatPlus.CompatibilityMode", true);

            NovaGamesLib.plugin.getConfig().options().copyDefaults(true);
            NovaGamesLib.plugin.saveConfig();

            NovaGamesLib.plugin.getConfig().options().copyDefaults(true);
            NovaGamesLib.plugin.saveConfig();
        }
    }

    public static boolean checkFileVersions() {
        if (!(NovaGamesLib.plugin.getConfig().getDouble("Config Version") == conVersion)) {
            NovaGamesLib.plugin.getLogger().severe("Config.yml outdated! Copy your settings, delete the file and restart the server!");
            NovaGamesLib.plugin.getLogger().severe("Disabling plugin to prevent further errors...");
            NovaGamesLib.plugin.getServer().getPluginManager().disablePlugin(NovaGamesLib.plugin);
            return false;
        }
        /*
        if (!(MessageManager.getMessagesYml().getDouble("Message Version") == msgVersion)) {
            Main.plugin.getLogger().severe("Your message file is outdated! Copy your messages, delete the Messages folder, and restart the server!");
            Main.plugin.getLogger().severe("Disabling plugin to prevent further errors...");
            Main.plugin.getServer().getPluginManager().disablePlugin(Main.plugin);
            return false;
        }
        */
        return true;
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