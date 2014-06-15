package com.flavorwocky.db;

import org.neo4j.graphdb.GraphDatabaseService;
import org.neo4j.test.TestGraphDatabaseFactory;

/**
 * Created by luanne on 11/06/14.
 */
public class ImpermanentGraphService implements GraphService {

    private static void registerShutdownHook(final GraphDatabaseService graphDb) {
        Runtime.getRuntime().addShutdownHook(new Thread() {
            @Override
            public void run() {
                graphDb.shutdown();
            }
        });
    }

    @Override
    /**
     * Get an instance of an ImpermanentGraphService
     */
    public GraphDatabaseService getGraphDbService() {
        GraphDatabaseService graphDb = new TestGraphDatabaseFactory().newImpermanentDatabaseBuilder().newGraphDatabase();
        registerShutdownHook(graphDb);
        return graphDb;
    }
}
