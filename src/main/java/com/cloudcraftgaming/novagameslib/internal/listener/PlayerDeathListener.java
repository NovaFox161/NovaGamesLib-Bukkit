package com.cloudcraftgaming.novagameslib.internal.listener;

import com.cloudcraftgaming.novagameslib.NovaGamesLib;
import com.cloudcraftgaming.novagameslib.api.arena.ArenaBase;
import com.cloudcraftgaming.novagameslib.api.arena.ArenaManager;
import com.cloudcraftgaming.novagameslib.api.player.PlayerStats;
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
            ArenaBase arenaBase = ArenaManager.getManager().getArena(player);
            if (NovaGamesLib.plugin.getConfig().getString("Stats.Track.Enabled").equalsIgnoreCase("True")) {
                if (NovaGamesLib.plugin.getConfig().getString("Stats.Track.Deaths").equalsIgnoreCase("True")) {
                    PlayerStats stats = arenaBase.getPlayerStats(player.getUniqueId());
                    stats.setMostDeaths(stats.getMostDeaths() + 1);
                    stats.setLeastDeaths(stats.getLeastDeaths() + 1);
                    stats.setTotalDeaths(stats.getTotalDeaths() + 1);
                }
                if (NovaGamesLib.plugin.getConfig().getString("Stats.Track.Kills").equalsIgnoreCase("True")) {
                    if (player.getKiller() != null) {
                        PlayerStats stats = arenaBase.getPlayerStats(player.getUniqueId());
                        stats.setMostKills(stats.getMostKills() + 1);
                        stats.setTotalKills(stats.getTotalKills() + 1);
                    }
                }
            }
        }
    }
}