package com.flavorwocky.db;

import com.flavorwocky.exception.DbException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

/**
 * Created by luanne on 11/06/14.
 */
public class ConnectionFactory {

    private final static ConnectionFactory instance = new ConnectionFactory();
    private static Connection serverConnection = null;

    private static String jdbcUrl;


    private ConnectionFactory() {
    }

    public static ConnectionFactory getInstance() {
        return instance;
    }

    public static void setJdbcUrl(String jdbcUrl) {
        ConnectionFactory.jdbcUrl = jdbcUrl;
    }


    /**
     * Get a JDBC connection to a Neo4j Server
     *
     * @return Connection
     * @throws SQLException if the connection couldn't be established
     */
    public Connection getServerConnection() throws DbException {
        if (serverConnection == null) {
            try {
                serverConnection = DriverManager.getConnection(jdbcUrl);
            } catch (SQLException sqle) {
                throw new DbException("Could not obtain a connection to " + jdbcUrl, sqle);
            }
        }
        return serverConnection;
    }


    /**
     * Close the connection to the Neo4j Server
     *
     * @throws SQLException if something went wrong
     */
    public void closeServerConnection() throws DbException {
        try {
            if (serverConnection != null) {
                serverConnection.close();
            }
        } catch (SQLException sqle) {
            throw new DbException("Could not close connection to " + jdbcUrl, sqle);
        }

    }


}
