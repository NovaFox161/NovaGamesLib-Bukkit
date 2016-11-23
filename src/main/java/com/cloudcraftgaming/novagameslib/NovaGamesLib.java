package com.cloudcraftgaming.novagameslib;

import com.cloudcraftgaming.novagameslib.arena.ArenaManager;
import com.cloudcraftgaming.novagameslib.data.ArenaDataManager;
import com.cloudcraftgaming.novagameslib.data.DataCache;
import com.cloudcraftgaming.novagameslib.database.DatabaseInfo;
import com.cloudcraftgaming.novagameslib.database.MySQL;
import com.cloudcraftgaming.novagameslib.listener.*;
import com.cloudcraftgaming.novagameslib.utils.FileManager;
import com.cloudcraftgaming.novagameslib.utils.MessageManager;
import com.cloudcraftgaming.novagameslib.utils.PluginChecker;
import org.bukkit.Bukkit;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.java.JavaPlugin;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nova Fox on 8/3/2016.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
public class NovaGamesLib extends JavaPlugin {
    public static NovaGamesLib plugin;
    public Plugin perWorldChatPlus;

    private DatabaseInfo databaseInfo;

    @Override
    public void onDisable() {
        //Save everything and stuff
        unloadArenasShutdown();

        //Update database

        disconnectFromMySQL();
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
        connectToMySQL();
        createTablesInMySQL();

        //Finally do a few more things.
        PluginChecker.checkForPerWorldChatPlus();

        loadArenasStartup();
    }

    private void connectToMySQL() {
        if (FileManager.useMySQL()) {
            String hostName = getConfig().getString("Database.MySQL.Hostname");
            String port = String.valueOf(getConfig().getInt("Database.MySQL.Port"));
            String database = getConfig().getString("Database.MySQL.Database");
            String user = getConfig().getString("Database.MySQL.Username");
            String pass = getConfig().getString("Database.MySQL.Password");
            String prefix = getConfig().getString("Database.MySQL.Prefix");

            MySQL mySQL = new MySQL(hostName, port, database, user, pass);
            try {
                Connection mySQLConnection = mySQL.openConnection();
                databaseInfo = new DatabaseInfo(mySQL, mySQLConnection, prefix);
                if (FileManager.verbose()) {
                    getLogger().info("Connected to MySQL database!");
                }
            } catch (Exception e) {
                getLogger().warning("Failed to connect to MySQL database! Is it properly configured?");
            }
        }
    }

    private void disconnectFromMySQL() {
        if (databaseInfo != null) {
            try {
                databaseInfo.getMySQL().closeConnection();
                if (FileManager.verbose()) {
                    getLogger().info("Successfully disconnected from MySQL Database!");
                }
            } catch (SQLException e) {
                getLogger().warning("MySQL Connection may not have closed properly! Data may be invalidated!");
            }
        }
    }

    private void createTablesInMySQL() {
        if (FileManager.useMySQL() && databaseInfo != null) {
            if (FileManager.verbose()) {
                getLogger().info("Creating needed tables that do not exist!");
            }
            try {
                Statement statement = databaseInfo.getConnection().createStatement();

                String arenaTableName = databaseInfo.getPrefix() + "ARENAS";
                String playerStatsTableName = databaseInfo.getPrefix() + "PLAYER_STATS";
                String createArenasTable = "CREATE TABLE IF NOT EXISTS " + arenaTableName +
                        " (ID INTEGER not NULL, " +
                        " GAME_NAME VARCHAR(255) not NULL, " +
                        " ENABLED BOOLEAN not NULL, " +
                        " JOINABLE BOOLEAN not NULL, " +
                        " STATUS VARCHAR(255) not NULL, " +
                        " STATE VARCHAR(255) not NULL, " +
                        " PLAYER_COUNT INTEGER not NULL, " +
                        " MAX_PLAYERS INTEGER not NULL, " +
                        " PLAYERS LONGTEXT not NULL, " +
                        " SPECTATORS LONGTEXT not NULL, " +
                        " PRIMARY KEY (ID))";
                String createPlayerStatsTable = "CREATE TABLE IF NOT EXISTS " + playerStatsTableName +
                        "(PLAYER_UUID VARCHAR(255) not NULL, " +
                        " GAME_NAME VARCHAR(255) not NULL, " +
                        " TOTAL_KILLS INTEGER not NULL, " +
                        " TOTAL_DEATHS INTEGER not NULL, " +
                        " TOTAL_SCORED INTEGER not NULL, " +
                        " MOST_KILLS INTEGER not NULL, " +
                        " MOST_DEATHS INTEGER not NULL, " +
                        " MOST_SCORED INTEGER not NULL, " +
                        " LEAST_DEATHS INTEGER not NULL, " +
                        " WINS INTEGER not NULL, " +
                        " LOSES INTEGER not NULL, " +
                        " TIMES_PLAYED INTEGER not NULL, " +
                        " PRIMARY KEY (PLAYER_UUID, GAME_NAME))";
                statement.executeUpdate(createArenasTable);
                statement.executeUpdate(createPlayerStatsTable);
                statement.close();
                if (FileManager.verbose()) {
                    getLogger().info("[OYAGames] Successfully created needed tables!");
                }
            } catch (SQLException e) {
                getLogger().info("MySQL Error; Exit code: 00101");
            }
        }
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

    //Getters
    @SuppressWarnings("unused")
    public DatabaseInfo getDatabaseInfo() {
        return databaseInfo;
    }
}