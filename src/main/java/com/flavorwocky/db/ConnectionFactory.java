package com.flavorwocky.db;

import com.flavorwocky.exception.DbException;
import org.neo4j.graphdb.GraphDatabaseService;

/**
 * Created by luanne on 11/06/14.
 */
public class ConnectionFactory {

    private final static ConnectionFactory instance = new ConnectionFactory();

    private static GraphDatabaseService db;


    private ConnectionFactory() {
    }

    public static ConnectionFactory getInstance() {
        return instance;
    }

    /**
     * Get a handle to the graph database
     *
     * @return GraphDatabaseService
     */
    public GraphDatabaseService getDb() {
        return db;
    }

    public void shutdownDb() {
        db.shutdown();
    }

    /**
     * Set the handle to the graph database (injected)
     *
     * @param graphService an instance of GraphDatabaseService
     */
    public void setGraphService(GraphService graphService) {
        try {
            db = graphService.getGraphDbService();
        } catch (Exception e) {
            throw new DbException("Could not obtain a connection ", e);
        }
    }


}
