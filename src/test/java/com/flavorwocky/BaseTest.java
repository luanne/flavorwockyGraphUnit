package com.flavorwocky;

import com.flavorwocky.db.ConnectionFactory;
import com.sun.jersey.api.client.Client;
import com.sun.jersey.api.client.ClientResponse;
import com.sun.jersey.api.client.WebResource;
import junit.framework.Assert;
import org.apache.commons.codec.CharEncoding;
import org.junit.AfterClass;
import org.junit.BeforeClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;
import java.net.URLEncoder;
import java.sql.SQLException;
import java.util.Properties;

/**
 * Created by luanne on 11/06/14.
 */
public class BaseTest {

    private static Client client = Client.create();
    private static String restTestUrl;


    public BaseTest() throws IOException {
        ApplicationContext testContext = new ClassPathXmlApplicationContext("test-graph-context.xml");
    }


    @BeforeClass
    public static void initGraph() {

        WebResource webResource = client.resource(getRestTestUrl() + "clear");

        ClientResponse response = webResource.type("text/plain")
                .post(ClientResponse.class, "");
        if (response.getStatus() != 200) {
            throw new RuntimeException("Could not clear the database");
        }
    }

    @AfterClass
    public static void destroyGraph() throws SQLException {
        ConnectionFactory.getInstance().closeServerConnection();
    }

    private static String getRestTestUrl() {
        if (restTestUrl == null) {
            Properties props = new Properties();
            try {
                props.load(BaseTest.class.getClassLoader().getResourceAsStream("testprops.properties"));
                restTestUrl = props.getProperty("dbUrl");

            } catch (IOException e) {
                throw new RuntimeException("Could not load testprops.properties");
            }
        }
        return restTestUrl;
    }

    /**
     * Assert that the graph created by the given cypher matches a subgraph in the database
     *
     * @param cypher data creating cypher
     */
    public void assertSubgraph(String cypher) {
        assertGraph(getRestTestUrl() + "assertSubgraph", cypher);

    }

    /**
     * Assert that the graph created by the given cypher matches the database
     *
     * @param cypher data creating cypher
     */
    public void assertSamegraph(String cypher) {
        assertGraph(getRestTestUrl() + "assertSameGraph", cypher);
    }

    private void assertGraph(String endpoint, String cypher) {
        try {
            WebResource webResource = client.resource(endpoint);

            ClientResponse response = webResource.type("text/plain")
                    .post(ClientResponse.class, URLEncoder.encode(cypher, CharEncoding.UTF_8));

            if (response.getStatus() != 200) {
                Assert.fail(response.getEntity(String.class));
            }
        } catch (Exception e) {
            Assert.fail(e.getMessage());

        }

    }
}
