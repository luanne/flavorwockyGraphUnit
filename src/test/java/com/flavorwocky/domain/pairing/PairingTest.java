package com.flavorwocky.domain.pairing;

import com.flavorwocky.BaseTest;
import com.flavorwocky.domain.ingredient.Ingredient;
import com.graphaware.test.unit.GraphUnit;
import org.junit.Test;

/**
 * Created by luanne on 11/06/14.
 */
public class PairingTest extends BaseTest {

    @Test
    public void testSavePairing() {

        Ingredient ing1 = new Ingredient("Chicken");
        Ingredient ing2 = new Ingredient("Garlic");
        Pairing pairing = new Pairing();
        pairing.setFirstIngredient(ing1);
        pairing.setSecondIngredient(ing2);
        pairing.setAffinity(Affinity.EXTREMELY_GOOD);
        pairing.save();

        String ingredientSubgraph = "create (i1:Ingredient {name: 'Chicken'}), (i2:Ingredient {name: 'Garlic'}), (i1)<-[:hasIngredient]-(p:Pairing {affinity: 0.45, allAffinities: [0.45]}), (p)-[:hasIngredient]->(i2) merge (i1)-[:pairsWith]-(i2)";

        GraphUnit.assertSameGraph(getGraphDb(), ingredientSubgraph);


        ing1 = new Ingredient("Lemon");
        ing2 = new Ingredient("Garlic");
        pairing = new Pairing();
        pairing.setFirstIngredient(ing1);
        pairing.setSecondIngredient(ing2);
        pairing.setAffinity(Affinity.TRIED_TESTED);
        pairing.save();

        ingredientSubgraph = "create (i1:Ingredient {name: 'Lemon'}), (i2:Ingredient {name: 'Garlic'}), (i1)<-[:hasIngredient]-(p:Pairing {affinity: 0.6, allAffinities:[0.6]}), (p)-[:hasIngredient]->(i2) merge (i1)-[:pairsWith]-(i2)";

        GraphUnit.assertSubgraph(getGraphDb(), ingredientSubgraph);

        ing1 = new Ingredient("Lemon");
        ing2 = new Ingredient("Garlic");
        pairing = new Pairing();
        pairing.setFirstIngredient(ing1);
        pairing.setSecondIngredient(ing2);
        pairing.setAffinity(Affinity.EXTREMELY_GOOD);
        pairing.save();

        ingredientSubgraph = "create (i1:Ingredient {name: 'Lemon'}), (i2:Ingredient {name: 'Garlic'}), (i1)<-[:hasIngredient]-(p:Pairing {affinity: 0.6, allAffinities:[0.6,0.45]}), (p)-[:hasIngredient]->(i2) merge (i1)-[:pairsWith]-(i2)";

        GraphUnit.assertSubgraph(getGraphDb(), ingredientSubgraph);
    }
}
