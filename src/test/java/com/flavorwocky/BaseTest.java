package com.flavorwocky;

import com.flavorwocky.db.ConnectionFactory;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.neo4j.graphdb.GraphDatabaseService;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.sql.SQLException;

/**
 * Created by luanne on 11/06/14.
 */
public class BaseTest {

    static GraphDatabaseService graphDb = null;

    @BeforeClass
    public static void setupGraphDb() {
        new ClassPathXmlApplicationContext("test-graph-context.xml");
        graphDb = ConnectionFactory.getInstance().getDb();
        System.out.println("graphDb = " + graphDb);
    }

    @AfterClass
    public static void destroyGraph() throws SQLException {
        graphDb.shutdown();
    }


    public GraphDatabaseService getGraphDb() {
        return graphDb;
    }
}
