package com.cloudcraftgaming.novagameslib.command;

import com.cloudcraftgaming.novagameslib.utils.MessageManager;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;

/**
 * Created by Nova Fox on 12/24/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
@SuppressWarnings("unused")
public class BaseCommand {
    /**
     * A built in method for handling minigame commands so that other minigames plugins do not need to code in the general commands.
     * This only handles commands built into the API, additional commands must be handled externally in another plugin.
     * @param sender The sender of the command
     * @param args The command args.
     * @param gameName The name of the game, this is generally not needed but should be added.
     * @param cmdPerm The permission to use any minigame commands.
     * @param adminPerm The permission to use any admin related commands.
     * @param setPerm  The permission to use any set related  commands.
     */
    public static void baseCommand(CommandSender sender, String[] args, String gameName, String cmdPerm, String adminPerm, String setPerm) {
        if (sender.hasPermission(cmdPerm)) {
            if (sender instanceof Player) {
                Player player = (Player) sender;
                if (args.length < 1) {
                    player.sendMessage(MessageManager.getMessage("Notifications.Args.Few"));
                } else if (args.length == 1) {
                    if (args[0].equalsIgnoreCase("set") || args[0].equalsIgnoreCase("create") || args[0].equalsIgnoreCase("createArena")
                            || args[0].equalsIgnoreCase("tool") || args[0].equalsIgnoreCase("ArenaTool")) {
                        //send to admin command.
                        AdminCommand.adminCommand(player, args, gameName, adminPerm, setPerm);
                    } else {
                        PlayerCommand.playerCommand(player, args);
                    }
                } else if (args.length == 2) {
                    String type = args[0];
                    if (type.equalsIgnoreCase("set")) {
                        SetCommand.setCommand(player, args, setPerm);
                    } else if (type.equalsIgnoreCase("enable") || type.equalsIgnoreCase("enableArena")
                            || type.equalsIgnoreCase("disable") || type.equalsIgnoreCase("disableArena")
                            || type.equalsIgnoreCase("reload") || type.equalsIgnoreCase("reloadArena")) {
                        AdminCommand.adminCommand(player, args, gameName, adminPerm, setPerm);
                    } else {
                        PlayerCommand.playerCommand(player, args);
                    }
                } else if (args.length == 3) {
                    if (args[0].equalsIgnoreCase("set")) {
                        SetCommand.setCommand(player, args, setPerm);
                    } else {
                        player.sendMessage(MessageManager.getMessage("Notifications.Args.Invalid"));
                    }
                } else if (args.length == 4) {
                    if (args[0].equalsIgnoreCase("set")) {
                        SetCommand.setCommand(player, args, setPerm);
                    } else {
                        player.sendMessage(MessageManager.getMessage("Notifications.Args.Many"));
                    }
                } else if (args.length == 5) {
                    if (args[0].equalsIgnoreCase("set")) {
                        SetCommand.setCommand(player, args, setPerm);
                    } else {
                        player.sendMessage(MessageManager.getMessage("Notifications.Args.Many"));
                    }
                } else if (args.length > 4) {
                    player.sendMessage(MessageManager.getMessage("Notifications.Args.Many"));
                }
            } else {
                sender.sendMessage(MessageManager.getMessage("Notifications.PlayerOnly"));
            }
        } else {
            sender.sendMessage(MessageManager.getMessage("Notifications.NoPerm"));
        }
    }
}