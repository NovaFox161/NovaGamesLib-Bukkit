package com.cloudcraftgaming.novagameslib.mechanics;

import com.cloudcraftgaming.novagameslib.NovaGamesLib;
import com.cloudcraftgaming.novagameslib.arena.ArenaBase;
import com.cloudcraftgaming.novagameslib.arena.ArenaManager;
import com.cloudcraftgaming.novagameslib.event.mechanics.RewardEconomyEvent;
import com.cloudcraftgaming.novagameslib.event.mechanics.RewardItemEvent;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.OfflinePlayer;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;

import java.util.UUID;

/**
 * Created by Nova Fox on 12/28/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
@SuppressWarnings("WeakerAccess")
public class Rewards {
    /**
     * Determines what rewards to issue. This method is automatically called internally and should be ignored!!!!!
     * <br> <br>
     *     This will NOT handle messages!
     * @param id The ID of the arena involved.
     */
    public static void distrobuteAwards(Integer id) {
        econAwards(id);
        itemAwards(id);
    }

    /**
     * Issues the {@link com.cloudcraftgaming.novagameslib.event.mechanics.RewardEconomyEvent} event at the end of a game.
     * <br></br>
     * **DO NOT CALL THIS METHOD!!!** Listen to {@link com.cloudcraftgaming.novagameslib.event.mechanics.RewardEconomyEvent} instead!!!.
     * @param id The id of the arena involved.
     * @return <c>true</c> if successful and not cancelled, else <c>false</c>.
     */
    public static Boolean econAwards(Integer id) {
        if (NovaGamesLib.econ != null) {
            if (ArenaManager.getManager().arenaLoaded(id)) {
                ArenaBase a = ArenaManager.getManager().getArena(id);
                RewardEconomyEvent event = new RewardEconomyEvent(id, a.getWinType(), 100.0);
                Bukkit.getServer().getPluginManager().callEvent(event);

                if (!event.isCancelled()) {
                    if (event.getAmount() > 0) {
                        ArenaBase arenaBase = ArenaManager.getManager().getArena(id);
                        for (UUID pId : arenaBase.getWinningPlayers()) {
                            OfflinePlayer offlineP = Bukkit.getOfflinePlayer(pId);
                            NovaGamesLib.econ.depositPlayer(offlineP, event.getAmount());
                        }
                    }
                }
            }
        }
        return false;
    }

    public static Boolean itemAwards(Integer id) {
        if (ArenaManager.getManager().arenaLoaded(id)) {
            ArenaBase a = ArenaManager.getManager().getArena(id);
            RewardItemEvent event = new RewardItemEvent(id, a.getWinType(), new ItemStack(Material.DIAMOND, 1));
            Bukkit.getServer().getPluginManager().callEvent(event);

            if (!event.isCancelled()) {
                if (event.getItemStack() != null && event.getItemStack().getAmount() > 0) {
                    ArenaBase arenaBase = ArenaManager.getManager().getArena(id);
                    for (UUID pId : arenaBase.getWinningPlayers()) {
                        Player p = Bukkit.getPlayer(pId);
                        p.getInventory().setItem(p.getInventory().firstEmpty(), event.getItemStack());
                    }
                }
            }
        }
        return false;
    }
}