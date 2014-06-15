package com.flavorwocky.db;

import org.neo4j.graphdb.GraphDatabaseService;

/**
 * Created by luanne on 11/06/14.
 */
public interface GraphService {

    /**
     * Get an instance of a GraphDatabaseService
     *
     * @return an instance of GraphDatabaseService
     */
    public GraphDatabaseService getGraphDbService();
}
