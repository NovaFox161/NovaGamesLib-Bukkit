package com.cloudcraftgaming.novagameslib.command;

import com.cloudcraftgaming.novagameslib.data.ArenaDataManager;
import com.cloudcraftgaming.novagameslib.data.PlayerDataManager;
import com.cloudcraftgaming.novagameslib.utils.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.entity.Player;

/**
 * Created by Nova Fox on 12/24/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
@SuppressWarnings("WeakerAccess")
public class AdminCommand {
    /**
     * A built in method for handling admin commands so that other minigames plugins do not need to code in the general commands.
     * This only handles admin commands built into the API, additional commands must be handled externally in another plugin.
     * @param player The player who sent the command.
     * @param args The command args.
     * @param gameName The name of the game.
     * @param adminPerm The permission to use any admin related commands.
     * @param setPerm  The permission to use any set related  commands.
     */
    public static void adminCommand(Player player, String[] args, String gameName, String adminPerm, String setPerm) {
        if (player.hasPermission(adminPerm)) {
            if (args.length == 1) {
                //base <type>
                String type = args[0];
                if (type.equalsIgnoreCase("set")) {
                    SetCommand.setCommand(player, args, setPerm);
                } else if (type.equalsIgnoreCase("create") || type.equalsIgnoreCase("createArena")) {
                    int id = ArenaDataManager.createArena(gameName);
                    player.sendMessage(MessageManager.getMessage("Command.Create", id));
                } else if (type.equalsIgnoreCase("tool") || type.equalsIgnoreCase("arenaTool")) {
                    PlayerDataManager.setArenaToolEnabled(player, !PlayerDataManager.hasArenaToolEnabled(player));
                    String msgOr = MessageManager.getMessagesYml().getString("Command.Tool");
                    String msg = msgOr.replaceAll("%value%", String.valueOf(PlayerDataManager.hasArenaToolEnabled(player)));
                    player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                } else {
                    player.sendMessage(MessageManager.getMessage("Notifications.Args.Invalid"));
                }
            } else if (args.length == 2) {
                //base <type> <id>
                String type = args[0];
                try {
                    Integer id = Integer.valueOf(args[1]);
                    if (type.equalsIgnoreCase("enable") || type.equalsIgnoreCase("enableArena")) {
                        if (ArenaDataManager.canBeLoaded(id)) {
                            ArenaDataManager.enableArena(id);
                            player.sendMessage(MessageManager.getMessage("Command.Set.Enable", id));
                        } else {
                            player.sendMessage(MessageManager.getMessage("Notifications.Enable.Cannot"));
                        }
                    } else if (type.equalsIgnoreCase("disable") || type.equalsIgnoreCase("disableArena")) {
                        ArenaDataManager.disableArena(id);
                        player.sendMessage(MessageManager.getMessage("Command.Set.Disable", id));
                    } else if (type.equalsIgnoreCase("reload") || type.equalsIgnoreCase("reloadArena")) {
                        //Will add later
                    }

                } catch (NumberFormatException e) {
                    player.sendMessage(MessageManager.getMessage("Notifications.Int.Arena"));
                }
            }
        } else {
            player.sendMessage(MessageManager.getMessage("Notifications.NoPerm"));
        }
    }
}