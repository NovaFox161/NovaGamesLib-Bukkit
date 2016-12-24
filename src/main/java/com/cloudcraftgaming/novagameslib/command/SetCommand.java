package com.cloudcraftgaming.novagameslib.command;

import com.cloudcraftgaming.novagameslib.data.ArenaDataManager;
import com.cloudcraftgaming.novagameslib.data.PlayerDataManager;
import com.cloudcraftgaming.novagameslib.team.Team;
import com.cloudcraftgaming.novagameslib.utils.MessageManager;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;

/**
 * Created by Nova Fox on 12/24/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
public class SetCommand {
    /**
     * A built in method for handling set commands so that other minigames plugins do not need to code in the general setters.
     * This only handles setters built into the API, additional setters must be handled externally in another plugin.
     * @param player The player who issued the command.
     * @param args The command args.
     * @param setPermission The permission to use the set command.
     */
    public static void setCommand(Player player, String[] args, String setPermission) {
        if (player.hasPermission(setPermission)) {
            if (args.length == 1) {
                //base set
                player.sendMessage(MessageManager.getMessage("Notifications.Args.Few"));
            } else if (args.length == 2) {
                //base set <id>
                player.sendMessage(MessageManager.getMessage("Notifications.Args.Few"));
            } else if (args.length == 3) {
                //base set <id> <type>
                String type = args[2];
                try {
                    Integer id = Integer.valueOf(args[1]);
                    if (ArenaDataManager.arenaExists(id)) {
                        if (type.equalsIgnoreCase("lobby") || type.equalsIgnoreCase("LobbyLocation") || type.equalsIgnoreCase("LobbyPosition")) {
                            ArenaDataManager.setLobbyLocation(id, player.getLocation());
                            player.sendMessage(MessageManager.getMessage("Command.Set.Lobby", id));
                        } else if (type.equalsIgnoreCase("end") || type.equalsIgnoreCase("endLocation") || type.equalsIgnoreCase("EndPosition")) {
                            ArenaDataManager.setEndLocation(id, player.getLocation());
                            player.sendMessage(MessageManager.getMessage("Command.Set.End", id));
                        } else if (type.equalsIgnoreCase("quit") || type.equalsIgnoreCase("QuitLocation") || type.equalsIgnoreCase("QuitPosition")) {
                            ArenaDataManager.setQuitLocation(id, player.getLocation());
                            player.sendMessage(MessageManager.getMessage("Command.Set.Quit", id));
                        } else if (type.equalsIgnoreCase("spectate") || type.equalsIgnoreCase("spectateLocation")
                                || type.equalsIgnoreCase("SpectatePosition")) {
                            ArenaDataManager.setSpectateLocation(id, player.getLocation());
                            player.sendMessage(MessageManager.getMessage("Command.Set.Spectate", id));
                        } else if (type.equalsIgnoreCase("Spawn") || type.equalsIgnoreCase("MainSpawn") || type.equalsIgnoreCase("SpawnOne")) {
                            ArenaDataManager.setMainSpawn(id, player.getLocation());
                            player.sendMessage(MessageManager.getMessage("Command.Set.Spawn.Main", id));
                        } else if (type.equals("SecondSpawn") || type.equalsIgnoreCase("SpawnTwo")) {
                            ArenaDataManager.setSecondarySpawn(id, player.getLocation());
                            player.sendMessage(MessageManager.getMessage("Command.Set.Spawn.Secondary", id));
                        } else if (type.equalsIgnoreCase("regen") || type.equalsIgnoreCase("RegenArea")) {
                            setRegen(player, id);
                        } else {
                            player.sendMessage(MessageManager.getMessage("Notifications.Args.Few"));
                        }
                    } else {
                        player.sendMessage(MessageManager.getMessage("Notifications.ArenaDoesNotExist"));
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(MessageManager.getMessage("Notifications.Int.Arena"));
                }
            } else if (args.length == 4) {
                //base set <id> <type> <value>
                String type = args[2];
                String valueString = args[3];
                try {
                    Integer id = Integer.valueOf(args[1]);
                    if (ArenaDataManager.arenaExists(id)) {
                        if (type.equalsIgnoreCase("minPlayers")) {
                            try {
                                Integer value = Integer.valueOf(valueString);
                                ArenaDataManager.setMinPlayers(id, value);
                                String msgOr = MessageManager.getMessagesYml().getString("Command.Set.MinPlayers");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%count%", valueString);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getMessage("Notifications.Int.MinPlayers"));
                            }
                        } else if (type.equalsIgnoreCase("maxPlayers")) {
                            try {
                                Integer value = Integer.valueOf(valueString);
                                ArenaDataManager.setMaxPlayers(id, value);
                                String msgOr = MessageManager.getMessagesYml().getString("Command.Set.MaxPlayers");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%count%", valueString);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getMessage("Notifications.Int.MaxPlayers"));
                            }
                        } else if (type.equalsIgnoreCase("minTeams")) {
                            try {
                                Integer value = Integer.valueOf(valueString);
                                ArenaDataManager.setMinTeamCount(id, value);
                                String msgOr = MessageManager.getMessagesYml().getString("Command.Set.MinTeams");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%count%", valueString);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getMessage("Notifications.Int.MinTeams"));
                            }
                        } else if (type.equalsIgnoreCase("maxTeams")) {
                            try {
                                Integer value = Integer.valueOf(valueString);
                                ArenaDataManager.setMaxTeamCount(id, value);
                                String msgOr = MessageManager.getMessagesYml().getString("Command.Set.MaxTeams");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%count%", valueString);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getMessage("Notifications.Int.MaxTeams"));
                            }
                        } else if (type.equalsIgnoreCase("displayName") || type.equalsIgnoreCase("name")) {
                            ArenaDataManager.setDisplayName(id, valueString);
                            String msgOr = MessageManager.getMessagesYml().getString("Command.Set.DisplayName");
                            String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%name%", valueString);
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                        } else if (type.equalsIgnoreCase("Wait") || type.equalsIgnoreCase("waitDelay")) {
                            try {
                                Integer value = Integer.valueOf(valueString);
                                ArenaDataManager.setWaitDelay(id, value);
                                String msgOr = MessageManager.getMessagesYml().getString("Command.Set.Time.Wait");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%time%", valueString);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getMessage("Notifications.Int.Time"));
                            }
                        } else if (type.equalsIgnoreCase("Start") || type.equalsIgnoreCase("StartDelay")) {
                            try {
                                Integer value = Integer.valueOf(valueString);
                                ArenaDataManager.setStartDelay(id, value);
                                String msgOr = MessageManager.getMessagesYml().getString("Command.Set.Time.Start");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%time%", valueString);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getMessage("Notifications.Int.Time"));
                            }
                        } else if (type.equalsIgnoreCase("GameTime") || type.equalsIgnoreCase("GameLength")) {
                            try {
                                Integer value = Integer.valueOf(valueString);
                                ArenaDataManager.setGameLength(id, value);
                                String msgOr = MessageManager.getMessagesYml().getString("Command.Set.Time.Game");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%time%", valueString);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getMessage("Notifications.Int.Time"));
                            }
                        } else if (type.equalsIgnoreCase("DayLength") || type.equalsIgnoreCase("Day") || type.equalsIgnoreCase("DayTime")) {
                            try {
                                Integer value = Integer.valueOf(valueString);
                                ArenaDataManager.setDayLength(id, value);
                                String msgOr = MessageManager.getMessagesYml().getString("Command.Set.Time.Day");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%time%", valueString);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getMessage("Notifications.Int.Time"));
                            }
                        } else if (type.equalsIgnoreCase("NightLength") || type.equalsIgnoreCase("Night") || type.equalsIgnoreCase("NightTime")) {
                            try {
                                Integer value = Integer.valueOf(valueString);
                                ArenaDataManager.setNightLength(id, value);
                                String msgOr = MessageManager.getMessagesYml().getString("Command.Set.Time.Night");
                                String msg = msgOr.replaceAll("%id%", String.valueOf(id)).replaceAll("%time%", valueString);
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getMessage("Notifications.Int.Time"));
                            }
                        } else if (type.equalsIgnoreCase("TeamSpawn") || type.equalsIgnoreCase("TeamSpawnLocation")) {
                            if (Team.exists(valueString)) {
                                ArenaDataManager.setTeamSpawn(id, player.getLocation(), valueString);
                                String msgOr = MessageManager.getMessagesYml().getString("Command.Set.Team.Spawn");
                                String msg = msgOr.replaceAll("%team%", valueString).replaceAll("%id%", String.valueOf(id));
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                            } else {
                                player.sendMessage(MessageManager.getMessage("Notifications.Team.Invalid"));
                            }
                        } else if (type.equalsIgnoreCase("useTeams")) {
                            Boolean value = Boolean.valueOf(valueString);
                            ArenaDataManager.setUseTeams(id, value);
                            String msgOr = MessageManager.getMessagesYml().getString("Command.Set.UseTeams");
                            String msg = msgOr.replace("%value%", String.valueOf(value)).replaceAll("%id%", String.valueOf(id));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                        } else if (type.equalsIgnoreCase("hideNames")) {
                            Boolean value = Boolean.valueOf(valueString);
                            ArenaDataManager.setHideNames(id, value);
                            String msgOr = MessageManager.getMessagesYml().getString("Command.Set.HideNames");
                            String msg = msgOr.replace("%value%", String.valueOf(value)).replaceAll("%id%", String.valueOf(id));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                        } else if (type.equalsIgnoreCase("friendlyFire") || type.equalsIgnoreCase("allowFriendlyFire")) {
                            Boolean value = Boolean.valueOf(valueString);
                            ArenaDataManager.setAllowFriendlyFire(id, value);
                            String msgOr = MessageManager.getMessagesYml().getString("Command.Set.FriendlyFire");
                            String msg = msgOr.replace("%value%", String.valueOf(value)).replaceAll("%id%", String.valueOf(id));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                        } else if (type.equalsIgnoreCase("blockBreak") || type.equalsIgnoreCase("allowBlockBreak")) {
                            Boolean value = Boolean.valueOf(valueString);
                            ArenaDataManager.setAllowBlockBreak(id, value);
                            String msgOr = MessageManager.getMessagesYml().getString("Command.Set.Block.Break");
                            String msg = msgOr.replace("%value%", String.valueOf(value)).replaceAll("%id%", String.valueOf(id));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                        } else if (type.equalsIgnoreCase("blockPlace") || type.equalsIgnoreCase("allowBlockPlace")) {
                            Boolean value = Boolean.valueOf(valueString);
                            ArenaDataManager.setAllowBlockPlace(id, value);
                            String msgOr = MessageManager.getMessagesYml().getString("Command.Set.Block.Place");
                            String msg = msgOr.replace("%value%", String.valueOf(value)).replaceAll("%id%", String.valueOf(id));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                        } else if (type.equalsIgnoreCase("lateJoin") || type.equalsIgnoreCase("allowLateJoin")) {
                            Boolean value = Boolean.valueOf(valueString);
                            ArenaDataManager.setAllowLateJoin(id, value);
                            String msgOr = MessageManager.getMessagesYml().getString("Command.Set.LateJoin");
                            String msg = msgOr.replace("%value%", String.valueOf(value)).replaceAll("%id%", String.valueOf(id));
                            player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                        } else if (type.equalsIgnoreCase("gameMode") || type.equalsIgnoreCase("gm")) {
                            if (ArenaDataManager.setGameMode(id, valueString)) {
                                String msgOr = MessageManager.getMessagesYml().getString("Command.Set.GameMode.Success");
                                String msg = msgOr.replace("%value%", valueString.toUpperCase()).replaceAll("%id%", String.valueOf(id));
                                player.sendMessage(ChatColor.translateAlternateColorCodes('&', msg));
                            } else {
                                player.sendMessage(MessageManager.getMessage("Notifications.GameMode.Invalid"));
                            }
                        } else {
                            player.sendMessage(MessageManager.getMessage("Notifications.Args.Invalid"));
                        }
                    } else {
                        player.sendMessage(MessageManager.getMessage("Notifications.ArenaDoesNotExist"));
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(MessageManager.getMessage("Notifications.Int.Arena"));
                }
                /*
            } else if (args.length == 5) {
                //base set <id> <type> <value> <value2>
                String type = args[2];
                String valueString = args[3];
                String valueString2 = args[4];
                try {
                    Integer id = Integer.valueOf(args[1]);
                    if (ArenaDataManager.arenaExists(id)) {
                        if (type.equalsIgnoreCase("wall") || type.equalsIgnoreCase("walls") || type.equalsIgnoreCase("teamWall")) {
                            try {
                                Integer wallIndex = Integer.valueOf(valueString2);
                                setWalls(player, id, valueString, wallIndex);
                            } catch (NumberFormatException e) {
                                player.sendMessage(MessageManager.getMessage("Notifications.Int.Wall"));
                            }
                        } else {
                            player.sendMessage(MessageManager.getMessage("Notifications.Args.Invalid"));
                        }
                    }
                } catch (NumberFormatException e) {
                    player.sendMessage(MessageManager.getMessage("Notifications.Int.Arena"));
                }
                */
            } else if (args.length > 5) {
                player.sendMessage(MessageManager.getMessage("Notifications.Args.Many"));
            }
        } else {
            player.sendMessage(MessageManager.getMessage("Notifications.NoPerm"));
        }
    }

    private static void setRegen(Player player, int id) {
        if (PlayerDataManager.hasLocationOneSaved(player) && PlayerDataManager.hasLocationTwoSaved(player)) {
            Location loc1 = PlayerDataManager.getSaveLocationOne(player);
            Location loc2 = PlayerDataManager.getSaveLocationTwo(player);
            ArenaDataManager.setRegenArea(id, loc1, loc2);
            PlayerDataManager.deleteSaveLocationOne(player);
            PlayerDataManager.deleteSaveLocationTwo(player);
            player.sendMessage(MessageManager.getMessage("Command.Set.Regen", id));
        } else {
            player.sendMessage(MessageManager.getMessage("Command.Set.Loc.Need"));
        }
    }
}