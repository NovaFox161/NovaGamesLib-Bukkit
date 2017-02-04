package com.cloudcraftgaming.novagameslib.database;

import com.cloudcraftgaming.novagameslib.NovaGamesLib;
import com.cloudcraftgaming.novagameslib.utils.FileManager;

import java.sql.Connection;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by Nova Fox on 2/4/2017.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
public class DatabaseManager {
    private static DatabaseManager instance;

    private DatabaseInfo databaseInfo;

    //Instance handling
    private DatabaseManager() {} //Prevent initialization.

    /**
     * Gets the instance of the {@link DatabaseManager}
     * @return The instance of the {@link DatabaseManager}
     */
    public static DatabaseManager getManager() {
        if (instance == null) {
            instance = new DatabaseManager();
        }
        return instance;
    }

    //Initialization and such
    /**
     * Connects to the database and loads everything needed.
     * <br> <br>
     *    **DO NOT USE** NovaGamesLib will handle this part internally!!!
     */
    public void init() {
        connectToMySQL();
        createTablesInMySQL();
    }

    /**
     * Disconnects from the database.
     * <br> <br>
     *     **DO NOT USE** NovaGamesLib will handle this part internally!!!
     */
    public void terminate() {
        disconnectFromMySQL();
    }

    //Getters
    /**
     * Gets the {@link DatabaseInfo} object that stores relevant DB info.
     * @return the {@link DatabaseInfo} object that stores relevant DB info.
     */
    public DatabaseInfo getDatabaseInfo() {
        return databaseInfo;
    }

    //Private methods
    private void connectToMySQL() {
        if (FileManager.useMySQL()) {
            String hostName = NovaGamesLib.plugin.getConfig().getString("Database.MySQL.Hostname");
            String port = String.valueOf(NovaGamesLib.plugin.getConfig().getInt("Database.MySQL.Port"));
            String database = NovaGamesLib.plugin.getConfig().getString("Database.MySQL.Database");
            String user = NovaGamesLib.plugin.getConfig().getString("Database.MySQL.Username");
            String pass = NovaGamesLib.plugin.getConfig().getString("Database.MySQL.Password");
            String prefix = NovaGamesLib.plugin.getConfig().getString("Database.MySQL.Prefix");

            MySQL mySQL = new MySQL(hostName, port, database, user, pass);
            try {
                Connection mySQLConnection = mySQL.openConnection();
                databaseInfo = new DatabaseInfo(mySQL, mySQLConnection, prefix);
                if (FileManager.verbose()) {
                    NovaGamesLib.plugin.getLogger().info("Connected to MySQL database!");
                }
            } catch (Exception e) {
                NovaGamesLib.plugin.getLogger().warning("Failed to connect to MySQL database! Is it properly configured?");
            }
        }
    }

    private void disconnectFromMySQL() {
        if (databaseInfo != null) {
            try {
                databaseInfo.getMySQL().closeConnection();
                if (FileManager.verbose()) {
                    NovaGamesLib.plugin.getLogger().info("Successfully disconnected from MySQL Database!");
                }
            } catch (SQLException e) {
                NovaGamesLib.plugin.getLogger().warning("MySQL Connection may not have closed properly! Data may be invalidated!");
            }
        }
    }

    private void createTablesInMySQL() {
        if (FileManager.useMySQL() && databaseInfo != null) {
            if (FileManager.verbose()) {
                NovaGamesLib.plugin.getLogger().info("Creating needed tables that do not exist!");
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
                    NovaGamesLib.plugin.getLogger().info("Successfully created needed tables!");
                }
            } catch (SQLException e) {
                NovaGamesLib.plugin.getLogger().info("MySQL Error; Exit code: 00101");
            }
        }
    }
}