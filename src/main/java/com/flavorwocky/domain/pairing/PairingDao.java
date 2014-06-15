package com.flavorwocky.domain.pairing;

import com.flavorwocky.db.ConnectionFactory;
import com.flavorwocky.exception.DbException;
import org.neo4j.cypher.CypherException;
import org.neo4j.cypher.javacompat.ExecutionEngine;
import org.neo4j.graphdb.GraphDatabaseService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by luanne on 11/06/14.
 */
public class PairingDao {

    protected void save(Pairing pairing) throws DbException {
        final GraphDatabaseService db = ConnectionFactory.getInstance().getDb();

        final String query = "merge (i1:Ingredient {name: {ing1}}) " +
                "merge (i2:Ingredient {name: {ing2}}) " +
                "with i1,i2 " +
                "merge (i1)<-[:hasIngredient]-(p:Pairing)-[:hasIngredient]->(i2) " +
                "on create set p.affinity={affinity} " +
                "on match set p.allAffinities=coalesce(p.allAffinities,[]) + {affinity} " +
                "merge (i1)-[:pairsWith]-(i2)";

        Map<String, Object> params = new HashMap<>();
        params.put("ing1", pairing.getFirstIngredient().getName());
        params.put("ing2", pairing.getSecondIngredient().getName());
        params.put("affinity", pairing.getAffinity().getWeight());

        ExecutionEngine engine = new ExecutionEngine(db);
        try {
            engine.execute(query, params);
        } catch (CypherException ce) {
            throw new DbException("Error saving pairing", ce);
        }

    }
}
