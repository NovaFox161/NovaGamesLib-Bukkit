package com.cloudcraftgaming.novagameslib.listener;

import com.cloudcraftgaming.novagameslib.NovaGamesLib;
import com.cloudcraftgaming.novagameslib.arena.ArenaManager;
import com.cloudcraftgaming.novagameslib.utils.MessageManager;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerCommandPreprocessEvent;

/**
 * Created by Nova Fox on 11/23/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
@SuppressWarnings("unused")
public class CommandListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onCommand(PlayerCommandPreprocessEvent event) {
        if (!event.isCancelled()) {
            if (ArenaManager.getManager().isInGame(event.getPlayer()) || ArenaManager.getManager().isSpectating(event.getPlayer())) {
                if (NovaGamesLib.plugin.getConfig().getString("Commands.Block").equalsIgnoreCase("True")) {
                    for (String blockedCommand : NovaGamesLib.plugin.getConfig().getStringList("Commands.Blocked")) {
                        if (event.getMessage().contains(blockedCommand)) {
                            event.setCancelled(true);
                            event.getPlayer().sendMessage(MessageManager.getMessage("Notifications.Player.Command.Blocked"));
                        }
                    }
                }
            }
        }
    }
}