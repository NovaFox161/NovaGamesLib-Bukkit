package com.cloudcraftgaming.novagameslib.internal.utils;

import com.arsenarsen.updater.Updater;
import com.cloudcraftgaming.perworldchatplus.Main;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Nova Fox on 11/14/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
public class UpdateChecker {
    //TODO: Add links and ID for NGL-B
    public static void checkForUpdates() {
        if (Main.plugin.getConfig().getString("Check for Updates").equalsIgnoreCase("True")) {
            Main.plugin.getLogger().info("Checking for updates...");
            Updater updater = new Updater(Main.plugin, 92965);
            Updater.UpdateAvailability upAv = updater.checkForUpdates();
            if (upAv == Updater.UpdateAvailability.UPDATE_AVAILABLE) {
                if (Main.plugin.getConfig().getString("Download Updates").equalsIgnoreCase("True")) {
                    Main.plugin.getLogger().info("Attempting to download new NovaGamesLib-Bukkit version...");
                    updater.update();
                } else {
                    Main.plugin.getLogger().info("New Version of NovaGamesLib-Bukkit found: " + updater.getLatest());
                    Main.plugin.getLogger().info("Download at: link");
                }
            } else if (upAv == Updater.UpdateAvailability.NO_UPDATE) {
                Main.plugin.getLogger().info("No new updates found! You are using the latest version of NovaGamesLib-Bukkit!");
            } else {
                Main.plugin.getLogger().severe("Failed to retrieve updates! Reason: " + upAv.name() + ". Please report this to Shades161 (NovaFox161) on her Dev Bukkit!!");
            }
        } else {
            Main.plugin.getLogger().info("Update checking disabled! Please enable to stay up to date on the latest version of NovaGamesLib-Bukkit!");
        }
    }

    public static void checkForUpdates(Player p) {
        if (Main.plugin.getConfig().getString("Check for Updates").equalsIgnoreCase("True")) {
            Main.plugin.getLogger().info("Checking for updates...");
            Updater updater = new Updater(Main.plugin, 92965);
            Updater.UpdateAvailability upAv = updater.checkForUpdates();
            if (upAv == Updater.UpdateAvailability.UPDATE_AVAILABLE) {
                Main.plugin.getLogger().info("New Version of NovaGamesLib-Bukkit found: " + updater.getLatest());
                Main.plugin.getLogger().info("Download at: link");
                p.sendMessage(ChatColor.GREEN + "New Version of NovaGamesLib-Bukkit found: " + updater.getLatest());
                p.sendMessage(ChatColor.BLUE + "Download at: link");
            } else if (upAv == Updater.UpdateAvailability.NO_UPDATE) {
                Main.plugin.getLogger().info("No new updates found! You are using the latest version of NovaGamesLib-Bukkit!");
                p.sendMessage(ChatColor.GREEN + "NovaGamesLib-Bukkit is up to date!");
            } else {
                Main.plugin.getLogger().severe("Failed to retrieve updates! Reason: " + upAv.name() + ". Please report this to Shades161 (NovaFox161) on her Dev Bukkit!!");
            }
        } else {
            Main.plugin.getLogger().info("Update checking disabled! Please enable to stay up to date on the latest version of NovaGamesLib-Bukkit!");
        }
    }
}