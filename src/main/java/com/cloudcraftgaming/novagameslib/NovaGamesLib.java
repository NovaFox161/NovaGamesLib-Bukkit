package com.cloudcraftgaming.novagameslib;

import com.cloudcraftgaming.novagameslib.database.DatabaseInfo;
import com.cloudcraftgaming.novagameslib.database.MySQL;
import com.cloudcraftgaming.novagameslib.utils.FileManager;
import com.cloudcraftgaming.novagameslib.utils.MessageManager;
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

    private DatabaseInfo databaseInfo;

    @Override
    public void onDisable() {
        //Save everything and stuff

        disconnectFromMySQL();
    }

    @Override
    public void onEnable() {
        plugin = this;

        //Do file things
        FileManager.createConfigFile();
        MessageManager.createMessageFile();

        if (!FileManager.checkFileVersions()) {
            return;
        }

        //Register events and commands

        //Do database things
        connectToMySQL();
        createTablesInMySQL();

        //Finally do a few more things.
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

    //Getters
    public DatabaseInfo getDatabaseInfo() {
        return databaseInfo;
    }
}