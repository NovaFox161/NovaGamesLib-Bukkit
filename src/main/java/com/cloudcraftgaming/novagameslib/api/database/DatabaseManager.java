package com.cloudcraftgaming.novagameslib.api.database;

import com.cloudcraftgaming.novagameslib.NovaGamesLib;
import com.cloudcraftgaming.novagameslib.api.player.PlayerStats;
import com.cloudcraftgaming.novagameslib.api.utils.FileManager;

import java.sql.*;
import java.util.UUID;

/**
 * Created by Nova Fox on 2/4/2017.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
@SuppressWarnings("SqlResolve")
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

    //Stats
    /**
     * Adds or updates a player's stats in the database for a specific game.
     * @param stats The stats to insert into the database.
     * @return <code>true</code> if successful, otherwise <code>false</code>.
     */
    public Boolean addPlayerStats(PlayerStats stats) {
        if (databaseInfo != null) {
            try {
                if (databaseInfo.getMySQL().checkConnection()) {
                    String playerStatsTableName = databaseInfo.getPrefix() + "PLAYER_STATS";

                    Statement statement = databaseInfo.getConnection().createStatement();
                    String query = "SELECT * FROM " + playerStatsTableName + " WHERE PLAYER_UUID = '" + stats.getPlayerUUID() + "';";
                    ResultSet res = statement.executeQuery(query);

                    //Check if stats for player AND game are saved.
                    Boolean hasStats = false;
                    while (res.next()) {
                        if (res.getString("PLAYER_UUID") != null && res.getString("GAME_NAME").equals(stats.getGameName())) {
                            hasStats = true;
                            break;
                        }
                    }

                    if (!hasStats) {
                        //Stats not saved, add defaults now.
                        String insertCommand = "INSERT INTO " + playerStatsTableName +
                                "(PLAYER_UUID, GAME_NAME," +
                                " TOTAL_KILLS, TOTAL_DEATHS, TOTAL_SCORED," +
                                " MOST_KILLS, MOST_DEATHS, MOST_SCORED," +
                                " LEAST_DEATHS, WINS, LOSES, TIMES_PLAYED)" +
                                " VALUES (?, ?, ?, ?, ? ,?, ?, ?, ?, ?, ?, ?);";
                        PreparedStatement ps = databaseInfo.getConnection().prepareStatement(insertCommand);

                        ps.setString(1, stats.getPlayerUUID().toString());
                        ps.setString(2, stats.getGameName());
                        ps.setInt(3, stats.getTotalKills());
                        ps.setInt(4, stats.getTotalDeaths());
                        ps.setInt(5, stats.getTotalScored());
                        ps.setInt(6, stats.getMostKills());
                        ps.setInt(7, stats.getMostDeaths());
                        ps.setInt(8, stats.getMostScored());
                        ps.setInt(9, stats.getLeastDeaths());
                        ps.setInt(10, stats.getWins());
                        ps.setInt(11, stats.getLoses());
                        ps.setInt(12, stats.getTimesPlayed());

                        ps.executeUpdate();
                        ps.close();
                        statement.close();
                        return true;
                    } else {
                        //Stats saved, update.
                        String updateCommand = "UPDATE " + playerStatsTableName + " SET TOTAL_KILLS= ?, TOTAL_DEATHS= ?, TOTAL_SCORED= ?," +
                                " MOST_KILLS=?, MOST_DEATHS=?, MOST_SCORED=?, LEAST_DEATHS=?," +
                                " WINS=?, LOSES=?, TIMES_PLAYED=? WHERE (PLAYER_UUID=? AND GAME_NAME=?);";
                        PreparedStatement ps = databaseInfo.getConnection().prepareStatement(updateCommand);

                        ps.setInt(1, stats.getTotalKills());
                        ps.setInt(2, stats.getTotalDeaths());
                        ps.setInt(3, stats.getTotalScored());
                        ps.setInt(4, stats.getMostKills());
                        ps.setInt(5, stats.getMostDeaths());
                        ps.setInt(6, stats.getMostScored());
                        ps.setInt(7, stats.getLeastDeaths());
                        ps.setInt(8, stats.getWins());
                        ps.setInt(9, stats.getLoses());
                        ps.setInt(10, stats.getTimesPlayed());
                        ps.setString(11, stats.getPlayerUUID().toString());
                        ps.setString(12, stats.getGameName());

                        ps.execute();
                        statement.close();
                        return true;
                    }
                }
            } catch (SQLException e) {
                NovaGamesLib.plugin.getLogger().severe("Failed to input player stats!");
                if (FileManager.debug()) {
                    e.printStackTrace();
                }
            }
        }
        return false;
    }

    /**
     * Gets the {@link PlayerStats} of the specified player for the corresponding game.
     * <br><br>
     *     Fails silently: <br>
     *     This will return blank (everything = 0) stats should it fail.
     * @param uuid The UUID of the player whose stats you wish to get.
     * @param gameName The name of the game that the stats are from.
     * @return A {@link PlayerStats} object with the player's stats.
     */
    public PlayerStats getPlayerStats(UUID uuid, String gameName) {
       PlayerStats stats = new PlayerStats(uuid, gameName);
       if (databaseInfo != null) {
           try {
               if (databaseInfo.getMySQL().checkConnection()) {
                   String statsTableName = databaseInfo.getPrefix() + "PLAYER_STATS";

                   Statement statement = databaseInfo.getConnection().createStatement();
                   String query = "SELECT * FROM " + statsTableName + " WHERE PLAYER_UUID ='" + uuid.toString() + "';";
                   ResultSet res = statement.executeQuery(query);

                   while (res.next()) {
                       if (res.getString("GAME_NAME").equalsIgnoreCase(gameName)) {
                           stats.setTotalKills(res.getInt("TOTAL_KILLS"));
                           stats.setTotalDeaths(res.getInt("TOTAL_DEATHS"));
                           stats.setTotalScored(res.getInt("TOTAL_SCORED"));

                           stats.setMostKills(res.getInt("MOST_KILLS"));
                           stats.setMostDeaths(res.getInt("MOST_DEATHS"));
                           stats.setMostScored(res.getInt("MOST_SCORED"));

                           stats.setLeastDeaths(res.getInt("LEAST_DEATHS"));

                           stats.setWins(res.getInt("WINS"));
                           stats.setLoses(res.getInt("LOSES"));
                           stats.setTimesPlayed(res.getInt("TIMES_PLAYED"));
                           break;
                       }
                   }
                   statement.close();
               }
           } catch (SQLException e) {
               NovaGamesLib.plugin.getLogger().severe("Failed to retrieve player stats!");
               if (FileManager.debug()) {
                   e.printStackTrace();
               }
           }
       }
       return stats;
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
                if (FileManager.debug()) {
                    e.printStackTrace();
                }
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
                if (FileManager.debug()) {
                    e.printStackTrace();
                }
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
                NovaGamesLib.plugin.getLogger().info("Failed to create needed MySQL tables!");
                if (FileManager.debug()) {
                    e.printStackTrace();
                }
            }
        }
    }
}