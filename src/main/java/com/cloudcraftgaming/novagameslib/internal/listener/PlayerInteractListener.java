package com.cloudcraftgaming.novagameslib.internal.listener;

import com.cloudcraftgaming.novagameslib.NovaGamesLib;
import com.cloudcraftgaming.novagameslib.api.data.PlayerDataManager;
import com.cloudcraftgaming.novagameslib.api.event.region.LocationOneSetEvent;
import com.cloudcraftgaming.novagameslib.api.event.region.LocationTwoSetEvent;
import com.cloudcraftgaming.novagameslib.api.game.MinigameEventHandler;
import com.cloudcraftgaming.novagameslib.api.utils.SignUtils;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.block.Sign;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

/**
 * Created by Nova Fox on 11/23/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
@SuppressWarnings("unused")
public class PlayerInteractListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onPlayerInteract(PlayerInteractEvent event) {
        Player player = event.getPlayer();
        if (event.getAction().equals(Action.RIGHT_CLICK_BLOCK)) {
            if (event.getClickedBlock().getState() instanceof Sign) {
                Sign sign = (Sign) event.getClickedBlock().getState();
                if (NovaGamesLib.plugin.getConfig().getString("Sign.Manage").equalsIgnoreCase("True")) {
                    String usePerm = NovaGamesLib.plugin.getConfig().getString("Sign.Permissions.Use");
                    if (SignUtils.isJoinSign(sign)) {
                        if (player.hasPermission(usePerm)) {
                            Integer arenaId = SignUtils.getIdFromSign(sign);
                            MinigameEventHandler.joinMinigame(player, arenaId);
                        }
                    } else if (SignUtils.isSpectateSign(sign)) {
                        if (player.hasPermission(usePerm)) {
                            Integer arenaId = SignUtils.getIdFromSign(sign);
                            MinigameEventHandler.spectateMinigame(player, arenaId);
                        }
                    } else if (SignUtils.isQuitSign(sign)) {
                        if (player.hasPermission(usePerm)) {
                            MinigameEventHandler.quitMinigame(player);
                        }
                    }
                }
            } else {
                if (event.getItem() != null) {
                    if (event.getItem().getType().equals(Material.STICK)) {
                        if (player.hasPermission("NovaGames.use.command.set")) {
                            if (PlayerDataManager.hasArenaToolEnabled(player)) {
                                //Call location two set event.
                                LocationTwoSetEvent setEvent = new LocationTwoSetEvent(player, event.getClickedBlock().getLocation());
                                Bukkit.getServer().getPluginManager().callEvent(setEvent);

                                if (!setEvent.isCancelled()) {
                                    PlayerDataManager.saveLocationTwo(player, setEvent.getLocationTwo());
                                }
                            }
                        }
                    }
                }
            }
        } else if (event.getAction().equals(Action.LEFT_CLICK_BLOCK)) {
            if (event.getItem() != null) {
                if (event.getItem().getType().equals(Material.STICK)) {
                    if (player.hasPermission("NovaGames.use.command.set")) {
                        if (PlayerDataManager.hasArenaToolEnabled(player)) {
                            //Call location one set event
                            LocationOneSetEvent setEvent = new LocationOneSetEvent(player, event.getClickedBlock().getLocation());
                            Bukkit.getServer().getPluginManager().callEvent(setEvent);

                            if (!setEvent.isCancelled()) {
                                PlayerDataManager.saveLocationOne(player, setEvent.getLocationOne());
                            }
                        }
                    }
                }
            }
        }
    }
}