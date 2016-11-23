package com.cloudcraftgaming.novagameslib.listener;

import com.cloudcraftgaming.novagameslib.arena.Arena;
import com.cloudcraftgaming.novagameslib.arena.ArenaManager;
import com.cloudcraftgaming.novagameslib.data.ArenaDataManager;
import org.bukkit.GameMode;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.PlayerMoveEvent;

/**
 * Created by Nova Fox on 11/23/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
@SuppressWarnings("unused")
public class PlayerMoveListener implements Listener {

    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerMove(PlayerMoveEvent event) {
        Player player = event.getPlayer();
        if (ArenaManager.getManager().isInGame(player)) {
            Arena arena = ArenaManager.getManager().getArena(player);
            if (!player.getGameMode().equals(ArenaDataManager.getGameMode(arena.getId()))) {
                player.setGameMode(ArenaDataManager.getGameMode(arena.getId()));
            }
        } else if (ArenaManager.getManager().isSpectating(player)) {
            if (!player.getGameMode().equals(GameMode.SPECTATOR)) {
                player.setGameMode(GameMode.SPECTATOR);
            }
        }
    }
}