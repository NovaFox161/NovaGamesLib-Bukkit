package com.cloudcraftgaming.novagameslib.listener;

import com.cloudcraftgaming.novagameslib.data.PlayerDataManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerJoinEvent;

/**
 * Created by Nova Fox on 11/23/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
@SuppressWarnings("unused")
public class PlayerJoinListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoinHandleFiles(PlayerJoinEvent event) {
        Player player = event.getPlayer();
        if (PlayerDataManager.hasPlayerData(player)) {
            PlayerDataManager.updatePlayerData(player);
        } else {
            PlayerDataManager.createPlayerData(player);
        }
    }
}