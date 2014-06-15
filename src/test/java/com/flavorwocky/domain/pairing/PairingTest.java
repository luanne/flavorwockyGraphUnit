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
    public void testSaveFirstPairing() {

        //Check that the first pairing in the graph is created correctly

        Ingredient ing1 = new Ingredient("Chicken");
        Ingredient ing2 = new Ingredient("Garlic");
        Pairing pairing = new Pairing();
        pairing.setFirstIngredient(ing1);
        pairing.setSecondIngredient(ing2);
        pairing.setAffinity(Affinity.EXTREMELY_GOOD);
        pairing.save();

        String ingredientSubgraph = "create (i1:Ingredient {name: 'Chicken'}), (i2:Ingredient {name: 'Garlic'}), (i1)<-[:hasIngredient]-(p:Pairing {affinity: 0.45, allAffinities: [0.45]}), (p)-[:hasIngredient]->(i2) merge (i1)-[:pairsWith]-(i2)";

        GraphUnit.assertSameGraph(getGraphDb(), ingredientSubgraph);

    }

    @Test
    public void testSavePairingWithExistingIngredient() {
        Ingredient ing1 = new Ingredient("Lemon");
        Ingredient ing2 = new Ingredient("Garlic");
        Pairing pairing = new Pairing();
        pairing.setFirstIngredient(ing1);
        pairing.setSecondIngredient(ing2);
        pairing.setAffinity(Affinity.TRIED_TESTED);
        pairing.save();

        String ingredientSubgraph = "create (i1:Ingredient {name: 'Lemon'}), (i2:Ingredient {name: 'Garlic'}), (i3:Ingredient {name: 'Chicken'})," +
                " (i1)<-[:hasIngredient]-(p:Pairing {affinity: 0.6, allAffinities:[0.6]})-[:hasIngredient]->(i2) , " +
                "(i2)<-[:hasIngredient]-(p2:Pairing {affinity: 0.45, allAffinities:[0.45]})-[:hasIngredient]->(i3) " +
                " merge (i1)-[:pairsWith]-(i2) merge (i3)-[:pairsWith]-(i2)";

        GraphUnit.assertSameGraph(getGraphDb(), ingredientSubgraph);


    }

    @Test
    public void testUpdatePairing() {
        Ingredient ing1 = new Ingredient("Lemon");
        Ingredient ing2 = new Ingredient("Garlic");
        Pairing pairing = new Pairing();
        pairing.setFirstIngredient(ing1);
        pairing.setSecondIngredient(ing2);
        pairing.setAffinity(Affinity.EXTREMELY_GOOD);
        pairing.save();

        String ingredientSubgraph = "create (i1:Ingredient {name: 'Lemon'}), (i2:Ingredient {name: 'Garlic'}), (i1)<-[:hasIngredient]-(p:Pairing {affinity: 0.6, allAffinities:[0.6,0.45]}), (p)-[:hasIngredient]->(i2) merge (i1)-[:pairsWith]-(i2)";

        GraphUnit.assertSubgraph(getGraphDb(), ingredientSubgraph);

    }

}
