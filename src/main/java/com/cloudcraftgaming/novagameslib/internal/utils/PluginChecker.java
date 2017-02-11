package com.cloudcraftgaming.novagameslib.internal.utils;

import com.cloudcraftgaming.novagameslib.NovaGamesLib;
import com.cloudcraftgaming.novagameslib.api.utils.FileManager;

/**
 * Created by Nova Fox on 11/23/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
public class PluginChecker {
    /**
     * Checks if PerWorldChatPlus is installed on the server.
     * If so, it will determine if it should use Compatibility mode.
     */
    public static void checkForPerWorldChatPlus() {
        if (NovaGamesLib.plugin.getConfig().getString("Chat.PerWorldChatPlus.CompatibilityMode").equalsIgnoreCase("True")) {
            if (NovaGamesLib.plugin.getServer().getPluginManager().getPlugin("PerWorldChatPlus") != null) {
                if (NovaGamesLib.plugin.getServer().getPluginManager().getPlugin("PerWorldChatPlus").getDescription().getVersion().startsWith("5")) {
                    NovaGamesLib.plugin.perWorldChatPlus = NovaGamesLib.plugin.getServer().getPluginManager().getPlugin("PerWorldChatPlus");
                    if (FileManager.verbose()) {
                        NovaGamesLib.plugin.getLogger().info("PerWorldChatPlus detected! Will use compatibility mode for chat!");
                    }
                } else {
                    if (FileManager.verbose()) {
                        NovaGamesLib.plugin.getLogger().info("PerWorldChatPlus is installed! But it is outdated!!");
                    }
                    NovaGamesLib.plugin.getConfig().set("Chat.PerWorldChatPlus.CompatibilityMode", false);
                    NovaGamesLib.plugin.saveConfig();
                }
            } else {
                NovaGamesLib.plugin.getConfig().set("Chat.PerWorldChatPlus.CompatibilityMode", false);
                NovaGamesLib.plugin.saveConfig();
                if (FileManager.verbose()) {
                    NovaGamesLib.plugin.getLogger().info("PerWorldChatPlus not found! Turning off compatibility mode!");
                }
            }
        }
    }
}