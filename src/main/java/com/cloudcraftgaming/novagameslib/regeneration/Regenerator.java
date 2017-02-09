package com.cloudcraftgaming.novagameslib.regeneration;

import com.cloudcraftgaming.novagameslib.NovaGamesLib;
import com.cloudcraftgaming.novagameslib.arena.ArenaBase;
import com.cloudcraftgaming.novagameslib.arena.ArenaManager;
import com.cloudcraftgaming.novagameslib.arena.ArenaStatus;
import com.cloudcraftgaming.novagameslib.data.ArenaDataManager;
import com.cloudcraftgaming.novagameslib.event.regeneration.PostRegenerationEvent;
import com.cloudcraftgaming.novagameslib.event.regeneration.PreRegenerationEvent;
import com.cloudcraftgaming.novagameslib.event.regeneration.SaveArenaDataEvent;
import com.cloudcraftgaming.novagameslib.region.Cuboid;
import com.cloudcraftgaming.novagameslib.utils.FileManager;
import com.cloudcraftgaming.novagameslib.utils.FileUtils;
import org.bukkit.*;
import org.bukkit.block.Block;
import org.bukkit.block.BlockState;
import org.bukkit.entity.Creature;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Item;
import org.bukkit.entity.Player;
import org.bukkit.inventory.InventoryHolder;
import org.bukkit.material.MaterialData;
import org.bukkit.material.Openable;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.List;

/**
 * Created by Nova Fox on 11/19/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
@SuppressWarnings({"unused, deprecation", "WeakerAccess", "ResultOfMethodCallIgnored"})
public class Regenerator {
    private final  static HashMap<Location, BlockState> originalBlocks = new HashMap<>();

    /**
     * Called when saving the arena data (regeneration data) before a game.
     * This method will call the {@link SaveArenaDataEvent} event before saving data.
     * @param id The id of the arena.
     */
    public static void saveDataBeforeGame(int id) {
        SaveArenaDataEvent event = new SaveArenaDataEvent(id);
        Bukkit.getServer().getPluginManager().callEvent(event);

        if (!event.isCancelled()) {
            saveAllBlocks(id);
            saveWorld(id);
        }
    }

    /**
     * Saves the world to a backup folder to be loaded from after the game is over.
     * This is useful for large arenas or to maximize performance.
     * @param id The id of the arena.
     */
    public static void saveWorld(int id) {

        if (FileManager.verbose()) {
            NovaGamesLib.plugin.getLogger().info("Saving world for arena id: " + id);
        }
        String name = ArenaDataManager.getRegenArea(id).getWorld().getName();
        if (Bukkit.getServer().getWorld(name) == null) {
            Bukkit.getServer().createWorld(new WorldCreator(name));
        }
        World world = Bukkit.getServer().getWorld(name);
        world.save();
        if (NovaGamesLib.plugin.getConfig().getString("Regeneration.Backup.World").equalsIgnoreCase("True")) {
            if (FileManager.verbose()) {
                NovaGamesLib.plugin.getLogger().info("Saving world in OYAGames-MANAGER folder as backup!");
            }
            File srcFolder = Bukkit.getWorld(name).getWorldFolder();
            File destFolder = new File(NovaGamesLib.plugin.getDataFolder() + "/Arenas/Worlds" + File.separator + name);
            destFolder.mkdirs();
            //make sure source exists
            if(!srcFolder.exists()) {
                if (FileManager.verbose()) {
                    NovaGamesLib.plugin.getLogger().info("Failed to find folder for world: " + name);
                }
            } else {
                try {
                    FileUtils.copyFolder(srcFolder, destFolder);
                } catch (IOException e) {
                    if (FileManager.verbose()) {
                        NovaGamesLib.plugin.getLogger().info("Error copying world: " + name);
                    }
                }
            }
        }
        world.setAutoSave(false);
        if (FileManager.verbose()) {
            NovaGamesLib.plugin.getLogger().info("Saved world for arena id: " + id);
        }
    }

    /**
     * This method is used before the game starts to save all block data for the arena so that broken/placed blocks are reset.
     * This will save blocks in memory, not on file (by default, is editable).
     * @param id The id of the arena.
     */
    public static void saveAllBlocks(int id) {
        if (NovaGamesLib.plugin.getConfig().getString("Regeneration.Save.ToMemory").equalsIgnoreCase("True")) {
            if (FileManager.verbose()) {
                NovaGamesLib.plugin.getLogger().info("Saving regen area data for arena id: " + id);
            }
            Cuboid originalRegenArea = ArenaDataManager.getRegenArea(id);
            for (Block block : originalRegenArea.getBlocks()) {
                Location loc = block.getLocation();
                BlockState state = block.getState();
                originalBlocks.put(loc, state);
            }
            if (FileManager.verbose()) {
                NovaGamesLib.plugin.getLogger().info("Successfully saved regen area data to memory!");
            }
        }
    }

    /**
     * This method is used after a game to completely regenerate the arena.
     * Will skip block regen in the event that it is not needed/does not exist.
     * This can be used to regenerate everything all at once rather than calling everything separately.
     * @param id The id of the arena to regenerate
     */
    public static void regenerateArena(int id) {
        PreRegenerationEvent preRegenEvent = new PreRegenerationEvent(id);
        Bukkit.getServer().getPluginManager().callEvent(preRegenEvent);

        if (!preRegenEvent.isCancelled()) {
            if (FileManager.verbose()) {
                NovaGamesLib.plugin.getLogger().info("Starting regeneration of arena id: " + id);
                NovaGamesLib.plugin.getLogger().info("This may take some time depending on arena size (Sorry!).");
            }
            ArenaBase arenaBase = ArenaManager.getManager().getArena(id);
            arenaBase.setArenaStatus(ArenaStatus.REGENERATING);
            arenaBase.setJoinable(false);
            //ArenaDataManager.updateArenaInfo(id);

            Cuboid regenArea = ArenaDataManager.getRegenArea(id);

            if (preRegenEvent.reloadWorld()) {
                reloadWorld(id);
            }
            if (preRegenEvent.regenAllBlocks()) {
                regenAllBlocks(id, regenArea);
            }
            if (preRegenEvent.clearGroundItems()) {
                clearGroundItems(id, regenArea);
            }
            if (preRegenEvent.removeAllEntities()) {
                removeAllEntities(id, regenArea);
            }
            if (preRegenEvent.resetDoors()) {
                resetDoors(id, regenArea);
            }
            if (preRegenEvent.clearContainerBlocks()) {
                clearContainerBlocks(id, regenArea);
            }

            if (FileManager.verbose()) {
                NovaGamesLib.plugin.getLogger().info("Successfully regenerated arena id: " + id);
            }

            PostRegenerationEvent postRegenEvent = new PostRegenerationEvent(id);
            Bukkit.getServer().getPluginManager().callEvent(postRegenEvent);

            if (postRegenEvent.autoReloadArena()) {
                ArenaManager.getManager().reloadArena(id);
            }
        }
    }

    /**
     * Reloads the world that the arena is in.
     * If the world has been backed up, it will also revert to the pregame backup.
     * !!!! DO NOT USE IF MORE THAN ONE ARENA IS IN A SINGLE WORLD !!!!
     * @param id The id of the arena.
     */
    public static void reloadWorld(int id) {
        if (FileManager.verbose()) {
            NovaGamesLib.plugin.getLogger().info("Reloading world for arena id: " + id);
        }
        String name = ArenaDataManager.getRegenArea(id).getWorld().getName();
        World world = Bukkit.getServer().getWorld(name);
        if (world.getPlayers().size() > 0) {
            for (Player p : world.getPlayers()) {
                p.teleport(ArenaDataManager.getQuitLocation(id));
            }
        }
        if (world.getPlayers().isEmpty()) {
            File destFolder = Bukkit.getWorld(name).getWorldFolder();
            File srcFolder = new File(NovaGamesLib.plugin.getDataFolder() + "/Arenas/Worlds" + File.separator + name);

            Bukkit.getServer().unloadWorld(world, false);

            if (NovaGamesLib.plugin.getConfig().getString("Regeneration.Backup.World").equalsIgnoreCase("True")) {
                if (FileManager.verbose()) {
                    NovaGamesLib.plugin.getLogger().info("Loading world from OYAGames folder!");
                }
                if (!srcFolder.exists()) {
                    if (FileManager.verbose()) {
                        NovaGamesLib.plugin.getLogger().info("Failed to find folder for world: " + name);
                    }
                } else {
                    try {
                        FileUtils.copyFolder(srcFolder, destFolder);
                        if (NovaGamesLib.plugin.getConfig().getString("Regeneration.Backup.AutoRemove").equalsIgnoreCase("True")) {
                            FileUtils.deleteFile(srcFolder);
                        }
                    } catch (IOException e) {
                        if (FileManager.verbose()) {
                            NovaGamesLib.plugin.getLogger().info("Error copying world: " + name);
                        }
                    }
                }
                if (FileManager.verbose()) {
                    NovaGamesLib.plugin.getLogger().info("Copied from OYAGames folder!");
                }
            }
            Bukkit.getServer().createWorld(new WorldCreator(name));
            if (FileManager.verbose()) {
                NovaGamesLib.plugin.getLogger().info("Successfully reloaded world for arena id: " + id);
            }
        } else {
            if (FileManager.verbose()) {
                NovaGamesLib.plugin.getLogger().info("Failed to reload world! Players are in world!!");
            }
        }
    }

    /**
     * Regenerates all blocks within the regen area.
     * @param id The id of the arena.
     * @param regenArea The cuboid region to regenerate.
     */
    public static void regenAllBlocks(int id, Cuboid regenArea) {
        if (NovaGamesLib.plugin.getConfig().getString("Regeneration.Save.ToMemory").equalsIgnoreCase("True")) {
            if (FileManager.verbose()) {
                NovaGamesLib.plugin.getLogger().info("Regenerating all blocks for arena id: " + id);
            }
            for (Block block : regenArea.getBlocks()) {
                Location loc = block.getLocation();
                if (originalBlocks.containsKey(loc)) {
                    BlockState originalBlock = originalBlocks.get(loc);
                    Integer bId = originalBlock.getTypeId();
                    byte blockDat = originalBlock.getRawData();
                    block.setTypeIdAndData(bId, blockDat, false);
                }
            }
            if (FileManager.verbose()) {
                NovaGamesLib.plugin.getLogger().info("Regenerated all blocks for arena id: " + id);
            }
        }
    }

    /**
     * Clears all items that were dropped/left on the ground
     * @param id The id of the arena
     * @param regenArea The cuboid region to check and clear.
     */
    public static void clearGroundItems(int id, Cuboid regenArea) {
        if (FileManager.verbose()) {
            NovaGamesLib.plugin.getLogger().info("Clearing ground items (regenerating) for arena id: " + id + "...");
        }
        List<Entity> entList = regenArea.getWorld().getEntities();
        for (Entity entity : entList) {
            if (entity instanceof Item) {
                if (regenArea.contains(entity.getLocation())) {
                    entity.remove();
                }
            }
        }
        if (FileManager.verbose()) {
            NovaGamesLib.plugin.getLogger().info("Successfully cleared all items for arena id: " + id);
        }
    }

    /**
     * Removes all monsters/animals/etc from the regen area.
     * @param id The id of the arena
     * @param regenArea The cuboid region to clear.
     */
    public static void removeAllEntities(int id, Cuboid regenArea) {
        if (FileManager.verbose()) {
            NovaGamesLib.plugin.getLogger().info("Removing all mobs/animals/etc for arena id: " + id);
        }
        List<Entity> entList = regenArea.getWorld().getEntities();
        for (Entity entity : entList) {
            if (entity instanceof Creature && !(entity instanceof Player)) {
                if (regenArea.contains(entity.getLocation())) {
                    entity.remove();
                }
            }
        }
        if (FileManager.verbose()) {
            NovaGamesLib.plugin.getLogger().info("Successfully removed all mobs/animals/etc for arena id: " + id);
        }
    }

    /**
     * Resets all doors or openable blocks to closed.
     * Currently will not reset doors held open with RedStone.
     * @param id The id of the arena.
     * @param regenArea The cuboid region to reset.
     */
    public static void resetDoors(int id, Cuboid regenArea) {
        if (FileManager.verbose()) {
            NovaGamesLib.plugin.getLogger().info("Resetting all doors/trapdoors/etc for Arena Id: " + id);
        }
        for (Block block : regenArea.getBlocks()) {
            if (block == null || block.getType().equals(Material.AIR)) {
                continue;
            }
            Material mat = block.getType();
            if (isOpenable(mat)) {

                BlockState state = block.getState();
                Openable o = (Openable) state.getData();
                o.setOpen(false);
                state.setData((MaterialData) o);
                state.update();
            }
        }
        if (FileManager.verbose()) {
            NovaGamesLib.plugin.getLogger().info("Successfully reset all doors/trapdoors/etc for Arena Id: " + String.valueOf(id));
        }
    }

    /**
     * Clears the inventory of all container blocks such as chests.
     * @param id The id of the arena.
     * @param regenArena The regen area of the arena.
     */
    public static void clearContainerBlocks(int id, Cuboid regenArena) {
        if (FileManager.verbose()) {
            NovaGamesLib.plugin.getLogger().info("Clearing all container blocks in arena Id: " + id);
        }
        for (Block block : regenArena.getBlocks()) {
            if (block == null || block.getType().equals(Material.AIR)) {
                continue;
            }
            if(block.getState() instanceof InventoryHolder){
                InventoryHolder ih = (InventoryHolder)block.getState();
                ih.getInventory().clear();
            }
        }
    }

    /**
     * Checks to see if the block is openable (as in a door)
     * @param mat The material of the block to check.
     * @return True if it is an openable, else false.
     */
    public static boolean isOpenable(Material mat) {
        return mat.equals(Material.ACACIA_DOOR) || mat.equals(Material.BIRCH_DOOR) || mat.equals(Material.DARK_OAK_DOOR)
                || mat.equals(Material.SPRUCE_DOOR) || mat.equals(Material.JUNGLE_DOOR) || mat.equals(Material.IRON_DOOR)
                || mat.equals(Material.WOODEN_DOOR) || mat.equals(Material.TRAP_DOOR) || mat.equals(Material.FENCE_GATE)
                || mat.equals(Material.ACACIA_FENCE_GATE) || mat.equals(Material.BIRCH_FENCE_GATE) || mat.equals(Material.DARK_OAK_FENCE_GATE)
                || mat.equals(Material.SPRUCE_FENCE_GATE) || mat.equals(Material.JUNGLE_FENCE_GATE) || mat.equals(Material.IRON_TRAPDOOR);
    }
}