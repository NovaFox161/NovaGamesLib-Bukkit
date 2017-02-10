package com.cloudcraftgaming.novagameslib.utils;

import org.bukkit.ChatColor;
import org.bukkit.configuration.file.YamlConfiguration;

import java.io.File;
import java.io.IOException;

import static com.cloudcraftgaming.novagameslib.NovaGamesLib.plugin;

/**
 * Created by Nova Fox on 11/15/16.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
@SuppressWarnings("WeakerAccess, unused")
public class MessageManager {
    /**
     * Creates the messages.yml. This should only be used by NovaGamesLib on enable.
     */
    public static void createMessageFile() {
        if (!(getMessagesFile().exists())) {
            YamlConfiguration msg = getMessagesYml();
            msg.addDefault("DO NOT DELETE.A", "NovaGamesLib is developed and managed by Shades161 (NovaFox161)");
            msg.addDefault("DO NOT DELETE.B", "This plugin is an API and is useless on its own!");
            msg.addDefault("Message Version", FileManager.msgVersion);

            msg.addDefault("Command.Create", "&5Created new arena with Id: &6%id%");
            msg.addDefault("Command.Tool", "&3Arena tool set to: %value%!");
            msg.addDefault("Command.ReloadArena", "&3Reloaded arena &6%id%&3!");
            msg.addDefault("Command.Set.Enable", "&3Enabled arena &6%id%&3!");
            msg.addDefault("Command.Set.Disable", "&3Disabled arena &6%id%&3!");
            msg.addDefault("Command.Set.DisplayName", "&3Display name for arena &6%id% set to &6%name%&3!");
            msg.addDefault("Command.Set.End", "&3End position for arena &6%id% &3set!");
            msg.addDefault("Command.Set.Quit", "&3Quit position for arena &6%id% &3set!");
            msg.addDefault("Command.Set.Lobby", "&3Lobby position for arena &6%id% &3set!");
            msg.addDefault("Command.Set.Spectate", "&3Spectating position for arena &6%id% &3set!");
            msg.addDefault("Command.Set.Spawn.Main", "&3Main spawn position for arena &6%id% &3set!");
            msg.addDefault("Command.Set.Spawn.Secondary", "&3Secondary spawn position for arena &6%id% &3set!");
            msg.addDefault("Command.Set.Regen", "&3Regen area for arena &6%id% &3set!");
            msg.addDefault("Command.Set.Team.Spawn", "&3Spawn location for &6team %team%&3 in arena &3%id%&3!");
            msg.addDefault("Command.Set.Team.PlayArea", "&3Play area set for &6team %team%&3 in arena &6%id%&3!");
            msg.addDefault("Command.Set.Name", "&3Display name for arena &6%id% &3is now &6%name%&3!");
            msg.addDefault("Command.Set.MinPlayers", "&3Minimum players for arena &6%id% &3set to &6%count%&3!");
            msg.addDefault("Command.Set.MaxPlayers", "&3Maximum players for arena &6%id% &3set to &6%count%&3!");
            msg.addDefault("Command.Set.MinTeams", "&3Minimum teams for arena &6%id% &3set to &6%count%&3!");
            msg.addDefault("Command.Set.MaxTeams", "&3Maximum teams for arena &6%id% &3set to &6%count%&3!");
            msg.addDefault("Command.Set.Time.Wait", "&3Wait delay for arena &6%id% &3set to &6%time% seconds&3!");
            msg.addDefault("Command.Set.Time.Start", "&3Start delay for arena &6%id% &3set to &6%time% seconds&3!");
            msg.addDefault("Command.Set.Time.Game", "&3Game length for arena &6%id% &3set to &6%time% minutes&3!");
            msg.addDefault("Command.Set.Time.Day", "&3Day length for arena &6%id% &3set to &6%time% minutes&3!");
            msg.addDefault("Command.Set.Time.Night", "&3Night length for arena &6%id% &3set to &6%time% minutes&3!");
            msg.addDefault("Command.Set.LateJoin", "&3Late join for arena &6%id% &3set to &6%allowed%&3!");
            msg.addDefault("Command.Set.Loc.Loc1", "&3Location 1 set!");
            msg.addDefault("Command.Set.Loc.Loc1Only", "&3Set location 2 (right click) and then finalize your selection!");
            msg.addDefault("Command.Set.Loc.Loc2", "&3Location 2 set!");
            msg.addDefault("Command.Set.Loc.Loc2Only", "&3Set location 1 (left click) and then finalize your selection!");
            msg.addDefault("Command.Set.Loc.Both", "&3Locations 1 and 2 set! &6Use the correct command to finalize your selection!");
            msg.addDefault("Command.Set.Loc.Need", "&4You need to set both positions 1 and 2 in order to set this!");
            msg.addDefault("Command.Set.UseTeams", "&3Use teams set to &6%value% &3for arena &6%id%&3!");
            msg.addDefault("Command.Set.HideNames", "&3Hide names set to &6%value% &3for arena &6%id%&3!");
            msg.addDefault("Command.Set.FriendlyFire", "&3Friendly fire set to &6%value% &3for arena &6%id%&3!");
            msg.addDefault("Command.Set.Block.Break", "&3Block break set to &6%value% &3for arena &6%id%&3!");
            msg.addDefault("Command.Set.Block.Place", "&3Block place set to &6%value% &3for arena &6%id%&3!");
            msg.addDefault("Command.Set.LateJoin", "&3Allow late join set to &6%value% &3for arena &6%id%&3!");
            msg.addDefault("Command.Set.GameMode.Success", "&3GameMode set to &6%value% &3for arena &6%id%&3!");

            msg.addDefault("Kit.Get.Success", "&6You have successfully gotten kit &5%kit%&6!");
            msg.addDefault("Kit.Get.Failure.NotEnoughFunds", "&4You do not have enough %currency% to use that kit!");
            msg.addDefault("Kit.Get.Failure.VaultNotFound", "&4Unable to access account! Please try again later!");
            msg.addDefault("Kit.Apply.Failure.InGame", "&4Kits can only be applied before the game starts!");
            msg.addDefault("Kit.Apply.Success", "&6The selected will be applied when the game starts!");
            msg.addDefault("Kit.Command.List.Heading", "&4-~-&6Kit List&4-~-");
            msg.addDefault("Kit.Command.List.Hint", "&6Use &3/mr kit <kitName> &6to select a kit!");
            msg.addDefault("Kit.Command.Create.Failure", "&4Failed to create a new kit!");
            msg.addDefault("Kit.Command.Create.Success", "&4Successfully created kit %kit%!");
            msg.addDefault("Kit.Command.Delete.Failure", "&4Failed to delete the specified kit!");
            msg.addDefault("Kit.Command.Delete.Success", "&4Successfully deleted the specified kit!");
            msg.addDefault("Kit.Command.Set.DisplayName.Success", "&6Successfully changed display name!");
            msg.addDefault("Kit.Command.set.DisplayName.Failure", "&4Failed to change display name!");
            msg.addDefault("Kit.Command.Set.Description.Success", "&6Successfully changed description!");
            msg.addDefault("Kit.Command.Set.Description.Failure", "&4Failed to change description!");
            msg.addDefault("Kit.Command.Set.Cost.Success", "&6Successfully changed cost!");
            msg.addDefault("Kit.Command.Set.Cost.Failure", "&4Failed to change cost!");
            msg.addDefault("Kit.Notif.DoesNotExist", "&4The specified kit does not exist!");
            msg.addDefault("Kit.Notif.AlreadyExists", "&4A kit with that name already exists!");
            msg.addDefault("Kit.Notif.NoPerm", "&4You do not have permission to use that kit!");

            msg.addDefault("Notifications.Player.InGame", "&4You can only be in 1 minigame at a time!");
            msg.addDefault("Notifications.Player.HasItems", "&4Your inventory must be empty to join the minigame!");
            msg.addDefault("Notifications.Player.NotInGame", "&4You are not in a minigame!");
            msg.addDefault("Notifications.Player.Quit", "&4You have quit the mini game!");
            msg.addDefault("Notifications.Player.Spectate.Stop", "&4You have stopped spectating arena %id%!");
            msg.addDefault("Notifications.Player.Command.Blocked", "&4You cannot use that command while ingame!");
            msg.addDefault("Notifications.ArenaBase.Full", "&4You cannot join that arena because it is full!");
            msg.addDefault("Notifications.ArenaBase.State.Starting", "&4You cannot join that arena because it already starting!");
            msg.addDefault("Notifications.ArenaBase.State.InGame", "&4You cannot join that arena because it is already in game!");
            msg.addDefault("Notifications.ArenaBase.State.Regenerating", "&You cannot join that arena while it is regenerating!");
            msg.addDefault("Notifications.ArenaBase.Spectate.Cannot", "&4That arena is currently not spectatable!");
            msg.addDefault("Notifications.ArenaDoesNotExist", "&4The specified arena does not exist or is not enabled!");
            msg.addDefault("Notifications.NoPerm", "&4You do not have permission to do that!");
            msg.addDefault("Notifications.Enable.Cannot", "&4That arena cannot be enabled because it is missing needed settings!");
            msg.addDefault("Notifications.Args.Few", "&4Too few arguments for this command!");
            msg.addDefault("Notifications.Args.Many", "&4Too many arguments for this command!");
            msg.addDefault("Notifications.Args.Invalid", "&4Invalid arguments for this command!");
            msg.addDefault("Notifications.Int.ArenaBase", "&4Arena Id must be a valid Integer (EX: 1, 2, 3)!");
            msg.addDefault("Notifications.Int.MinPlayers", "&4Minimum players must be a number!");
            msg.addDefault("Notifications.Int.MaxPlayers", "&4Maximum players musts be a number!");
            msg.addDefault("Notifications.Int.MinTeams", "&4Minimum Teams must be a number!");
            msg.addDefault("Notifications.Int.MaxTeams", "&4Maximum Teams musts be a number!");
            msg.addDefault("Notifications.Int.Time", "&4Time must be a number!");
            msg.addDefault("Notifications.Int.Spawn", "&4Spawn value must be a number!");
            msg.addDefault("Notifications.Bool.Kits", "&4Can use kits must be 'True' or 'False'");
            msg.addDefault("Notifications.Bool.LateJoin", "&4Late join allowed must be 'True' or 'False'");
            msg.addDefault("Notifications.Team.Invalid", "&4Invalid team specified!");
            msg.addDefault("Notifications.GameMode.Invalid", "&4Invalid GameMode specified");
            msg.addDefault("Notifications.Error.General", "&4Oops! Something went wrong! Please try again!");

            msg.options().copyDefaults(true);
            saveMessages(msg);

            msg.options().copyDefaults(true);
            saveMessages(msg);
        }
    }

    /**
     * Gets the messages File.
     * Use {@link #getMessagesYml()} to get the messages YML.
     * @return The messages file.
     */
    public static File getMessagesFile() {
        String lang = plugin.getConfig().getString("Language");
        return new File(plugin.getDataFolder() + "/Messages/" + lang + ".yml");
    }

    /**
     * Gets the messages YML.
     * Use {@link #getMessagesFile()} to get the Messages File.
     * @return The messages yml.
     */
    public static YamlConfiguration getMessagesYml() {
        File file = getMessagesFile();
        return YamlConfiguration.loadConfiguration(file);
    }

    /**
     * Gets the message from the messages file, with translated color codes, at the specified location.
     * @param msgLoc The location of the message.
     * @return The message with chat color codes translated.
     */
    public static String getMessage(String msgLoc) {
        String msg = getMessagesYml().getString(msgLoc);
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    /**
     * Gets the message from the messages file, with translated color codes and all %id% vars replaced, at the specified location.
     * @param msgLoc The location of the message.
     * @param id The id to replace all %id% vars with.
     * @return The message wil chat color codes translated and all %id% vars replaced.
     */
    public static String getMessage(String msgLoc, int id) {
        String msgOr = getMessagesYml().getString(msgLoc);
        String msg = msgOr.replaceAll("%id%", String.valueOf(id));
        return ChatColor.translateAlternateColorCodes('&', msg);
    }

    /**
     * Saves the messages file.
     * @param msgYml The instance of the messages file to save.
     */
    public static void saveMessages(YamlConfiguration msgYml) {
        try {
            msgYml.save(getMessagesFile());
        } catch (IOException e) {
            plugin.getLogger().severe("Failed to save messages");
            e.printStackTrace();
        }
    }
}