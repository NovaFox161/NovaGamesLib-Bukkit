package com.cloudcraftgaming.novagameslib.command;

import com.cloudcraftgaming.novagameslib.game.MinigameEventHandler;
import com.cloudcraftgaming.novagameslib.utils.MessageManager;
import org.bukkit.entity.Player;

/**
 * Created by Nova Fox on 12/24/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
public class PlayerCommand {

    /**
     * A built in method for handling player commands so that other minigames plugins do not need to code in the general commands.
     * This only handles player commands built into the API, additional commands must be handled externally in another plugin.
     * @param player The player who issued the command.
     * @param args The command args.
     */
    public static void playerCommand(Player player, String[] args) {
        if (args.length == 1) {
            //base <type>
            String type = args[0];
            if (type.equalsIgnoreCase("join")) {
                player.sendMessage(MessageManager.getMessage("Notifications.Args.Few"));
            } else if (type.equalsIgnoreCase("quit")) {
                MinigameEventHandler.quitMinigame(player);
            } else if (type.equalsIgnoreCase("Spectate")) {
                player.sendMessage(MessageManager.getMessage("Notifications.Args.Few"));
            } else if (type.equalsIgnoreCase("info")) {
                player.sendMessage(MessageManager.getMessage("Notifications.Args.Few"));
            } else {
                player.sendMessage(MessageManager.getMessage("Notifications.Args.Invalid"));
            }
        } else if (args.length == 2) {
            //base <type> <id>
            String type = args[0];
            String idString = args[1];
            try {
                Integer  id = Integer.valueOf(idString);
                if (type.equalsIgnoreCase("join")) {
                    MinigameEventHandler.joinMinigame(player, id);
                } else if (type.equalsIgnoreCase("spectate")) {
                    MinigameEventHandler.spectateMinigame(player, id);
                } else if (type.equalsIgnoreCase("info")) {
                    //Will add later
                } else if (type.equalsIgnoreCase("quit")) {
                    player.sendMessage(MessageManager.getMessage("Notifications.Args.Many"));
                } else {
                    player.sendMessage(MessageManager.getMessage("Notifications.Args.Invalid"));
                }
            } catch (NumberFormatException e) {
                player.sendMessage(MessageManager.getMessage("Notifications.Int.Arena"));
            }
        } else if (args.length > 2) {
            player.sendMessage(MessageManager.getMessage("Notifications.Args.Many"));
        }
    }
}