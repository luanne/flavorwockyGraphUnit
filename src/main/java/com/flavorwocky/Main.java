package com.flavorwocky;

import com.flavorwocky.db.ConnectionFactory;
import com.flavorwocky.domain.ingredient.Ingredient;
import com.flavorwocky.domain.pairing.Affinity;
import com.flavorwocky.domain.pairing.Pairing;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * Created by luanne on 11/06/14.
 */
public class Main {

    public static void main(String[] args) {
        new ClassPathXmlApplicationContext("graph-context.xml"); //Trigger injection of the Graph Database

        //Save a pairing
        Ingredient ing1 = new Ingredient("Avocado");
        Ingredient ing2 = new Ingredient("Lime");
        Pairing pairing = new Pairing();
        pairing.setFirstIngredient(ing1);
        pairing.setSecondIngredient(ing2);
        pairing.setAffinity(Affinity.TRIED_TESTED);
        pairing.save();

        ConnectionFactory.getInstance().shutdownDb();

    }
}
