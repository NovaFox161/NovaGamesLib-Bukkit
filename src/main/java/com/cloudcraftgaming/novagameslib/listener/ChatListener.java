package com.cloudcraftgaming.novagameslib.listener;

import com.cloudcraftgaming.novagameslib.NovaGamesLib;
import com.cloudcraftgaming.novagameslib.arena.Arena;
import com.cloudcraftgaming.novagameslib.arena.ArenaManager;
import com.cloudcraftgaming.novagameslib.data.ArenaDataManager;
import com.cloudcraftgaming.novagameslib.team.Team;
import com.cloudcraftgaming.perworldchatplus.chat.ChatMessage;
import com.cloudcraftgaming.perworldchatplus.chat.ChatRecipients;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;

import java.util.Set;

/**
 * Created by Nova Fox on 11/23/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
@SuppressWarnings("unused")
public class ChatListener implements Listener {
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncPlayerChatEvent event) {
        if (!event.isCancelled()) {
            if (NovaGamesLib.plugin.getConfig().getString("Chat.Handle").equalsIgnoreCase("True")) {
                if (ArenaManager.getManager().isInGame(event.getPlayer())
                        || ArenaManager.getManager().isSpectating(event.getPlayer())) {
                    Player player = event.getPlayer();
                    Arena arena = ArenaManager.getManager().getArena(player);
                    if (NovaGamesLib.plugin.perWorldChatPlus != null) {
                        if (ChatMessage.shouldBeGlobal(event.getMessage(), player)) {
                            event.setFormat(ArenaDataManager.getChatPrefix(arena.getId()) + event.getFormat());
                            event.setMessage(event.getMessage());
                        } else {
                            if (NovaGamesLib.plugin.getConfig().getString("Chat.PerGame").equalsIgnoreCase("True")) {
                                if (NovaGamesLib.plugin.getConfig().getString("Chat.PerTeam").equalsIgnoreCase("True")) {
                                    event.getRecipients().clear();
                                    event.getRecipients().add(player);

                                    for (Player p : Bukkit.getOnlinePlayers()) {
                                        if (arena.getSpectators().contains(p.getUniqueId())) {
                                            event.getRecipients().add(p);
                                        }
                                        if (arena.getPlayers().contains(p.getUniqueId())) {
                                            if (arena.useTeams() || arena.getTeams().isOnATeam(player.getUniqueId())) {
                                                Team team = arena.getTeams().getTeam(player.getUniqueId());
                                                if (arena.getTeams().isOnTeam(p.getUniqueId(), team)) {
                                                    event.getRecipients().add(p);
                                                }
                                            } else {
                                                event.getRecipients().add(p);
                                            }
                                        }
                                    }
                                    Set<Player> spies = ChatRecipients.getAllSpyReceivers(event.getRecipients(), player);
                                    for (Player p1 : spies) {
                                        event.getRecipients().add(p1);
                                    }
                                    event.setFormat(ArenaDataManager.getChatPrefix(arena.getId()) + event.getFormat());
                                    event.setMessage(event.getMessage());
                                } else {
                                    //Per game only
                                    event.getRecipients().clear();
                                    event.getRecipients().add(player);
                                    for (Player p : Bukkit.getOnlinePlayers()) {
                                        if (arena.getPlayers().contains(p.getUniqueId()) || arena.getSpectators().contains(p.getUniqueId())) {
                                            event.getRecipients().add(p);
                                        }
                                    }
                                    Set<Player> spies = ChatRecipients.getAllSpyReceivers(event.getRecipients(), player);
                                    for (Player p1 : spies) {
                                        event.getRecipients().add(p1);
                                    }
                                    event.setFormat(ArenaDataManager.getChatPrefix(arena.getId()) + event.getFormat());
                                    event.setMessage(event.getMessage());
                                }
                            } else {
                                //Not per game, just add minigames prefix so we know where this came from.
                                event.setFormat(ArenaDataManager.getChatPrefix(arena.getId()) + event.getFormat());
                                event.setMessage(event.getMessage());
                            }
                        }
                    } else {
                        //PerWorldChatPlus not being used
                        if (NovaGamesLib.plugin.getConfig().getString("Chat.PerGame").equalsIgnoreCase("True")) {
                            if (NovaGamesLib.plugin.getConfig().getString("Chat.PerTeam").equalsIgnoreCase("True")) {
                                for (Player p : Bukkit.getOnlinePlayers()) {
                                    if (arena.getSpectators().contains(p.getUniqueId())) {
                                        event.getRecipients().add(p);
                                    }
                                    if (arena.getPlayers().contains(p.getUniqueId())) {
                                        if (arena.useTeams() || arena.getTeams().isOnATeam(player.getUniqueId())) {
                                            Team team = arena.getTeams().getTeam(player.getUniqueId());
                                            if (arena.getTeams().isOnTeam(p.getUniqueId(), team)) {
                                                event.getRecipients().add(p);
                                            }
                                        } else {
                                            event.getRecipients().add(p);
                                        }
                                    }
                                }
                                event.setFormat(ArenaDataManager.getChatPrefix(arena.getId()) + event.getFormat());
                                event.setMessage(event.getMessage());
                            } else {
                                //Per game only
                                event.getRecipients().clear();
                                event.getRecipients().add(player);
                                for (Player p : Bukkit.getOnlinePlayers()) {
                                    if (arena.getPlayers().contains(p.getUniqueId()) || arena.getSpectators().contains(p.getUniqueId())) {
                                        event.getRecipients().add(p);
                                    }
                                }
                                event.setFormat(ArenaDataManager.getChatPrefix(arena.getId()) + event.getFormat());
                                event.setMessage(event.getMessage());
                            }
                        } else {
                            //Not per game, just add minigames prefix so we know where this came from.
                            event.setFormat(ArenaDataManager.getChatPrefix(arena.getId()) + event.getFormat());
                            event.setMessage(event.getMessage());
                        }
                    }
                }
            }
        }
    }
}