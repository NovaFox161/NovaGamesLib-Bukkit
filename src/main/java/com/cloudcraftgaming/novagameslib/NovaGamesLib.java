package com.cloudcraftgaming.novagameslib;

import com.cloudcraftgaming.novagameslib.arena.ArenaManager;
import com.cloudcraftgaming.novagameslib.data.ArenaDataManager;
import com.cloudcraftgaming.novagameslib.data.DataCache;
import com.cloudcraftgaming.novagameslib.database.DatabaseManager;
import com.cloudcraftgaming.novagameslib.listener.*;
import com.cloudcraftgaming.novagameslib.utils.FileManager;
import com.cloudcraftgaming.novagameslib.utils.MessageManager;
import com.cloudcraftgaming.novagameslib.utils.PluginChecker;
import net.milkbowl.vault.economy.Economy;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.RegisteredServiceProvider;
import org.bukkit.plugin.java.JavaPlugin;

/**
 * Created by Nova Fox on 8/3/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
public class NovaGamesLib extends JavaPlugin {
    public static NovaGamesLib plugin;
    public Plugin perWorldChatPlus;
    public static Economy econ = null;

    @Override
    public void onDisable() {
        //Save everything and stuff
        unloadArenasShutdown();

        DatabaseManager.getManager().terminate();
    }

    @Override
    public void onEnable() {
        plugin = this;

        //Do file things
        FileManager.createConfigFile();
        MessageManager.createMessageFile();
        FileManager.createPluginCacheFile();
        FileManager.createKitsFile();

        if (!FileManager.checkFileVersions()) {
            return;
        }

        //Register events and commands
        getServer().getPluginManager().registerEvents(new SignChangeListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerQuitListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerMoveListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerJoinListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerInteractListener(), this);
        getServer().getPluginManager().registerEvents(new PlayerDeathListener(), this);
        getServer().getPluginManager().registerEvents(new CommandListener(), this);
        getServer().getPluginManager().registerEvents(new ChatListener(), this);

        //Do database things
        DatabaseManager.getManager().init();

        //Finally do a few more things.
        PluginChecker.checkForPerWorldChatPlus();
        setupEconomy();

        loadArenasStartup();
    }

    private void loadArenasStartup() {
        Bukkit.getScheduler().scheduleSyncDelayedTask(this, new Runnable() {
            @Override
            public void run() {
                if (FileManager.verbose()) {
                    getLogger().info("Loading all enabled arenas...");
                }
                for (Integer id : DataCache.getAllUsedIDs()) {
                    if (ArenaDataManager.arenaEnabled(id)) {
                        if (ArenaDataManager.arenaEnabled(id)) {
                            ArenaManager.getManager().loadArena(id, ArenaDataManager.getGameName(id),
                                    ArenaDataManager.usesTeams(id));
                        }
                    }
                }
                if (FileManager.verbose()) {
                    getLogger().info("All enabled arenas successfully loaded!");
                }
            }
        }, 20L * 10);
    }

    private void unloadArenasShutdown() {
        if (FileManager.verbose()) {
            getLogger().info("Unloading all loaded arenas! Plugin will disable shortly...");
        }
        for (Integer id : DataCache.getAllUsedIDs()) {
            if (ArenaDataManager.arenaExists(id)) {
                if (ArenaManager.getManager().arenaLoaded(id)) {
                    ArenaManager.getManager().unloadArena(id);
                }
            }
        }
        if (FileManager.verbose()) {
            getLogger().info("Unloaded all loaded arenas! Plugin will now be disabled!");
        }
    }

    private boolean setupEconomy() {
        if (getServer().getPluginManager().getPlugin("Vault") == null) {
            return false;
        }
        RegisteredServiceProvider<Economy> rsp = getServer().getServicesManager().getRegistration(Economy.class);
        if (rsp == null) {
            return false;
        }
        econ = rsp.getProvider();
        return econ != null;
    }

    //Some public stuffs.

    /**
     *
     * @param minigameHandler The Plugin handling the registered minigame.
     * @param gameName The name of the minigame.
     * @return <code>true</code> if successful, else <code>false</code>.
     */
    @SuppressWarnings("unused")
    public Boolean registerMinigame(Plugin minigameHandler, String gameName) {
        this.getLogger().info("Registering new Minigame: " + gameName + "...");

        minigameHandler.getLogger().info("Registered " + gameName + " with NovaGamesLib-Bukkit!");
        return false;
    }
}