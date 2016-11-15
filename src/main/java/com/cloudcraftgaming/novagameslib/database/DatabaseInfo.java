package com.cloudcraftgaming.novagameslib.database;

import java.sql.Connection;

/**
 * Created by Nova Fox on 11/15/16.
 * Website: www.cloudcraftgaming.com
 * For Project: NovaGamesLib
 */
@SuppressWarnings("unused")
public class DatabaseInfo {
    private MySQL mySQL;
    private Connection con;
    private String prefix;

    public DatabaseInfo(MySQL _mySQL, Connection _con, String _prefix) {
        mySQL = _mySQL;
        con = _con;
        prefix = _prefix;
    }

    public MySQL getMySQL() {
        return mySQL;
    }

    public Connection getConnection() {
        return con;
    }

    public String getPrefix() {
        return prefix;
    }
}