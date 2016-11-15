package com.cloudcraftgaming.novagameslib.utils;

import com.cloudcraftgaming.novagameslib.NovaGamesLib;
import org.bukkit.ChatColor;
import org.bukkit.block.Sign;
import org.bukkit.event.block.SignChangeEvent;

/**
 * Created by Nova Fox on 11/15/16.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
@SuppressWarnings("WeakerAccess, unused")
public class SignUtils {
    /**
     * Creates a join sign for NovaGames.
     * @param event The SignChangeEvent to make a join sign on.
     * @param id The id of the arena.
     */
    public static void setJoinSign(SignChangeEvent event, int id) {
        String title = NovaGamesLib.plugin.getConfig().getString("Sign.Title");
        String join = NovaGamesLib.plugin.getConfig().getString("Sign.Join");
        event.setLine(0, ChatColor.DARK_BLUE + title);
        event.setLine(1, ChatColor.DARK_GREEN + join);
        event.setLine(2, String.valueOf(id));
        NovaGamesLib.plugin.getLogger().info("Created join sign!");
    }

    /**
     * Creates a join sign for NovaGames.
     * @param sign The sign to make a join sign on.
     * @param id The id of the arena.
     */
    public static void setJoinSign(Sign sign, int id) {
        String title = NovaGamesLib.plugin.getConfig().getString("Sign.Title");
        String join = NovaGamesLib.plugin.getConfig().getString("Sign.Join");
        sign.setLine(0, ChatColor.DARK_BLUE + title);
        sign.setLine(1, ChatColor.DARK_GREEN + join);
        sign.setLine(2, String.valueOf(id));
        NovaGamesLib.plugin.getLogger().info("Created join sign!");
    }

    /**
     * Creates a quit sign for NovaGames.
     * @param event The SignChangeEvent to make a quit sign on.
     */
    public static void setQuitSign(SignChangeEvent event) {
        String title = NovaGamesLib.plugin.getConfig().getString("Sign.Title");
        String quit = NovaGamesLib.plugin.getConfig().getString("Sign.Quit");
        event.setLine(0, ChatColor.DARK_BLUE + title);
        event.setLine(1, ChatColor.DARK_RED + quit);
        NovaGamesLib.plugin.getLogger().info("Created quit sign!");
    }

    /**
     * Creates a quit sign for NovaGames.
     * @param sign The sign to make a quit sign on.
     */
    public static void setQuitSign(Sign sign) {
        String title = NovaGamesLib.plugin.getConfig().getString("Sign.Title");
        String quit = NovaGamesLib.plugin.getConfig().getString("Sign.Quit");
        sign.setLine(0, ChatColor.DARK_BLUE + title);
        sign.setLine(1, ChatColor.DARK_RED + quit);
        NovaGamesLib.plugin.getLogger().info("Created quit sign!");
    }

    /**
     * Creates a spectate sign for NovaGames.
     * @param event The SignChangeEvent to make a spectate sign on.
     * @param id The id of the arena.
     */
    public static void setSpectateSign(SignChangeEvent event, int id) {
        String title = NovaGamesLib.plugin.getConfig().getString("Sign.Title");
        String spectate = NovaGamesLib.plugin.getConfig().getString("Sign.Spectate");
        event.setLine(0, ChatColor.DARK_BLUE + title);
        event.setLine(1, ChatColor.DARK_AQUA + spectate);
        event.setLine(2, String.valueOf(id));
        NovaGamesLib.plugin.getLogger().info("Created spectate sign!");
    }

    /**
     * Creates a spectate sign for NovaGames.
     * @param sign The sign to make a spectate sign on.
     * @param id The id of the arena.
     */
    public static void setSpectateSign(Sign sign, int id) {
        String title = NovaGamesLib.plugin.getConfig().getString("Sign.Title");
        String spectate = NovaGamesLib.plugin.getConfig().getString("Sign.Spectate");
        sign.setLine(0, ChatColor.DARK_BLUE + title);
        sign.setLine(1, ChatColor.DARK_AQUA + spectate);
        sign.setLine(2, String.valueOf(id));
        NovaGamesLib.plugin.getLogger().info("Created spectate sign!");
    }

    /**
     * Checks if the sign is an NovaGames join sign.
     * @param event The SignChangeEvent to check.
     * @return <code>true</code> if a join sign, else <code>false</code>.
     */
    public static Boolean isJoinSign(SignChangeEvent event) {
        String title = NovaGamesLib.plugin.getConfig().getString("Sign.Title").toLowerCase();
        String join = NovaGamesLib.plugin.getConfig().getString("Sign.Join").toLowerCase();
        return event.getLine(0).toLowerCase().contains(title) && event.getLine(1).toLowerCase().contains(join);
    }

    /**
     * Checks if the sign is an NovaGames join sign.
     * @param sign The sign to check.
     * @return <code>true</code> if a join sign, else <code>false</code>.
     */
    public static Boolean isJoinSign(Sign sign) {
        String title = NovaGamesLib.plugin.getConfig().getString("Sign.Title").toLowerCase();
        String join = NovaGamesLib.plugin.getConfig().getString("Sign.Join").toLowerCase();
        return sign.getLine(0).toLowerCase().contains(title) && sign.getLine(1).toLowerCase().contains(join);
    }

    /**
     * Checks if the sign is an NovaGames quit sign.
     * @param event The SignChangeEvent to check.
     * @return <code>true</code> if a quit sign, else <code>false</code>.
     */
    public static Boolean isQuitSign(SignChangeEvent event) {
        String title = NovaGamesLib.plugin.getConfig().getString("Sign.Title").toLowerCase();
        String quit = NovaGamesLib.plugin.getConfig().getString("Sign.Quit").toLowerCase();
        return event.getLine(0).toLowerCase().contains(title) && event.getLine(1).toLowerCase().contains(quit);
    }

    /**
     * Checks if the sign is an NovaGames quit sign.
     * @param sign The sign to check.
     * @return <code>true</code> if a quit sign, else <code>false</code>.
     */
    public static Boolean isQuitSign(Sign sign) {
        String title = NovaGamesLib.plugin.getConfig().getString("Sign.Title").toLowerCase();
        String quit = NovaGamesLib.plugin.getConfig().getString("Sign.Quit").toLowerCase();
        return sign.getLine(0).toLowerCase().contains(title) && sign.getLine(1).toLowerCase().contains(quit);
    }

    /**
     * Checks if the sign is an NovaGames spectate sign.
     * @param event The SignChangeEvent to check.
     * @return <code>true</code> if a spectate sign, else <code>false</code>.
     */
    public static Boolean isSpectateSign(SignChangeEvent event) {
        String title = NovaGamesLib.plugin.getConfig().getString("Sign.Title").toLowerCase();
        String spectate = NovaGamesLib.plugin.getConfig().getString("Sign.Spectate").toLowerCase();
        return event.getLine(0).toLowerCase().contains(title) && event.getLine(1).toLowerCase().contains(spectate);
    }

    /**
     * Checks if the sign is an NovaGames spectate sign.
     * @param sign The sign to check.
     * @return <code>true</code> if a spectate sign, else <code>false</code>.
     */
    public static Boolean isSpectateSign(Sign sign) {
        String title = NovaGamesLib.plugin.getConfig().getString("Sign.Title").toLowerCase();
        String spectate = NovaGamesLib.plugin.getConfig().getString("Sign.Spectate").toLowerCase();
        return sign.getLine(0).toLowerCase().contains(title) && sign.getLine(1).toLowerCase().contains(spectate);
    }

    /**
     * Sets the arena Id from the sign.
     * @param event The SignChangeEvent to get the Id from.
     * @return Returns the id from the sign, <code>-1</code> if invalid.
     */
    public static Integer getIdFromSign(SignChangeEvent event) {
        if (isJoinSign(event) || isSpectateSign(event)) {
            try {
                return Integer.valueOf(event.getLine(2));
            } catch (NumberFormatException e) {
                //Not an integer, so not an ID.
            }
        }
        return -1;
    }

    /**
     * Sets the arena Id from the sign.
     * @param sign The sign to get the Id from.
     * @return Returns the id from the sign, <code>-1</code> if invalid.
     */
    public static Integer getIdFromSign(Sign sign) {
        if (isJoinSign(sign) || isSpectateSign(sign)) {
            try {
                return Integer.valueOf(sign.getLine(2));
            } catch (NumberFormatException e) {
                //Not an integer, so not an ID.
            }
        }
        return -1;
    }
}