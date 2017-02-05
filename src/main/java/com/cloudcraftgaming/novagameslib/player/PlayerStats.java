package com.cloudcraftgaming.novagameslib.player;

import com.cloudcraftgaming.novagameslib.database.DatabaseManager;

import java.util.UUID;

/**
 * Created by Nova Fox on 2/4/2017.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib-Bukkit
 */
public class PlayerStats {
    private final UUID playerUUID;
    private final String gameName;

    private Integer totalKills;
    private Integer totalDeaths;
    private Integer totalScored;
    private Integer mostKills;
    private Integer mostDeaths;
    private Integer mostScored;
    private Integer leastDeaths;
    private Integer wins;
    private Integer loses;
    private Integer timesPlayed;

    /**
     * Creates a new (blank, everything = 0) player stats object.
     * To get updated stats use {@link DatabaseManager#getPlayerStats(UUID, String)}
     * @param _playerUUID The UUID of the player whose stats you wish to get.
     * @param _gameName The name of the game the stats are from.
     */
    public PlayerStats(UUID _playerUUID, String _gameName) {
        playerUUID = _playerUUID;
        gameName = _gameName;

        totalKills = 0;
        totalDeaths = 0;
        totalScored = 0;
        mostKills = 0;
        mostDeaths = 0;
        mostScored = 0;
        leastDeaths = 0;
        wins = 0;
        loses = 0;
        timesPlayed = 0;
    }

    //Getters
    /**
     * Gets the UUID of the player whose stats these are.
     * @return The UUID of the player whose stats these are.
     */
    public UUID getPlayerUUID() {
        return playerUUID;
    }

    /**
     * Gets the name of the game these stats are from.
     * @return The name of the game these stats are from.
     */
    public String getGameName() {
        return gameName;
    }

    /**
     * Gets the total amount of kills the player has in this game.
     * @return The total amount of kills the player has in this game.
     */
    public Integer getTotalKills() {
        return totalKills;
    }

    /**
     * Gets the total amount of deaths the player has in this game.
     * @return The total amount of kills the player has in this game.
     */
    public Integer getTotalDeaths() {
        return totalDeaths;
    }

    /**
     * Gets the total amount of points scored by the player in this game.
     * @return The total amount of points scored by the player in this game.
     */
    public Integer getTotalScored() {
        return totalScored;
    }

    /**
     * Gets the most kills the player has in a single round of this game.
     * @return THe most kills the player has in a single round of this game.
     */
    public Integer getMostKills() {
        return mostKills;
    }

    /**
     * Gets the most deaths the player has in a single round of this game.
     * @return The most deaths the player has in a single round of this game.
     */
    public Integer getMostDeaths() {
        return mostDeaths;
    }

    /**
     * Gets the most points scored by the player in a single round of this game.
     * @return The most points scored by the player in a single round of this game.
     */
    public Integer getMostScored() {
        return mostScored;
    }

    /**
     * Gets the least amount of deaths the player has in a single round of this game.
     * @return The least amount of deaths the player has in a single round of this game.
     */
    public Integer getLeastDeaths() {
        return leastDeaths;
    }

    /**
     * Gets the total amount of times the player has won this game.
     * @return The total amount of times the player has won this game.
     */
    public Integer getWins() {
        return wins;
    }

    /**
     * Gets the total amount of times the player has lost this game.
     * @return The total amount of times the player has lost this game.
     */
    public Integer getLoses() {
        return loses;
    }

    /**
     * Gets the total amount of times the player has played this game.
     * @return The total amount of times the player has lost this game.
     */
    public Integer getTimesPlayed() {
        return timesPlayed;
    }

    //Setters
    /**
     * Sets the total amount of kills the player has in this game.
     * @param _totalKills The total amount of kills the player has in this game.
     */
    public void setTotalKills(Integer _totalKills) {
        totalKills = _totalKills;
    }

    /**
     * Sets the total amount of deaths the player has in this game.
     * @param _totalDeaths The total amount of deaths the player has in this game.
     */
    public void setTotalDeaths(Integer _totalDeaths) {
        totalDeaths = _totalDeaths;
    }

    /**
     * Sets the total amount of points scored by the player in this game.
     * @param _totalScored The total amount of points scored by the player in this game.
     */
    public void setTotalScored(Integer _totalScored) {
        totalScored = _totalScored;
    }

    /**
     * Sets the most kills the player has in a single round in this game.
     * @param _mostKills The most kills the player has in a single round in this game.
     */
    public void setMostKills(Integer _mostKills) {
        mostKills = _mostKills;
    }

    /**
     * Sets the most deaths the player has in a single round of this game.
     * @param _mostDeaths The most deaths the player has in a single round of this game.
     */
    public void setMostDeaths(Integer _mostDeaths) {
        mostDeaths = _mostDeaths;
    }

    /**
     * Sets the most points scored by the player in a single round of this game.
     * @param _mostScored The most points scored by the player in a single round of this game.
     */
    public void setMostScored(Integer _mostScored) {
        mostScored = _mostScored;
    }

    /**
     * Sets the least deaths the player has in a single round of this game.
     * @param _leastDeaths The least deaths the player has in a single round of this game.
     */
    public void setLeastDeaths(Integer _leastDeaths) {
        leastDeaths = _leastDeaths;
    }

    /**
     * Sets the total amount of wins the player has in this game.
     * @param _wins The total amount of wins the player has in this game.
     */
    public void setWins(Integer _wins) {
        wins = _wins;
    }

    /**
     * Sets the total amount of loses the player has in this game.
     * @param _loses The total amount of deaths the player has in this game.
     */
    public void setLoses(Integer _loses) {
        loses = _loses;
    }

    /**
     * Sets the total amount of times the player has played this game.
     * @param _timesPlayed The total amount of times the player has played this game.
     */
    public void setTimesPlayed(Integer _timesPlayed) {
        timesPlayed = _timesPlayed;
    }

    //Functionals
    /**
     * Calculates the new stats by combining previous stats.
     * @param _stats The stats to combine.
     */
    public void calculate(PlayerStats _stats) {
        totalKills = totalKills + _stats.getTotalKills();
        totalDeaths = totalDeaths + _stats.getTotalDeaths();
        totalScored = totalScored + _stats.getTotalScored();

        if (_stats.getMostKills() > mostKills) {
            mostKills = _stats.getMostKills();
        }
        if (_stats.getMostDeaths() > mostDeaths) {
            mostDeaths = _stats.getMostDeaths();
        }
        if (_stats.getMostScored() > mostScored) {
            mostScored = _stats.getMostScored();
        }

        if (_stats.getLeastDeaths() < leastDeaths) {
            leastDeaths = _stats.getLeastDeaths();
        }

        wins = wins + _stats.getWins();
        loses = loses + _stats.getLoses();
        timesPlayed = timesPlayed + _stats.getTimesPlayed();
    }
}