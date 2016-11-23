package com.cloudcraftgaming.novagameslib.listener;

import com.cloudcraftgaming.novagameslib.NovaGamesLib;
import com.cloudcraftgaming.novagameslib.arena.Arena;
import com.cloudcraftgaming.novagameslib.arena.ArenaManager;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.PlayerDeathEvent;

/**
 * Created by Nova Fox on 11/23/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
@SuppressWarnings("unused")
public class PlayerDeathListener implements Listener {
    @EventHandler(priority = EventPriority.MONITOR)
    public void onPlayerDeath(PlayerDeathEvent event) {
        Player player = event.getEntity();
        if (ArenaManager.getManager().isInGame(player)) {
            Arena arena = ArenaManager.getManager().getArena(player);
            if (NovaGamesLib.plugin.getConfig().getString("Stats.Track.Enabled").equalsIgnoreCase("True")) {
                if (NovaGamesLib.plugin.getConfig().getString("Stats.Track.Deaths").equalsIgnoreCase("True")) {
                    arena.setDeaths(player, arena.getDeaths(player) + 1);
                }
                if (NovaGamesLib.plugin.getConfig().getString("Stats.Track.Kills").equalsIgnoreCase("True")) {
                    if (player.getKiller() != null) {
                        arena.setKills(player.getKiller(), arena.getKills(player.getKiller()) + 1);
                    }
                }
            }
        }
    }
}