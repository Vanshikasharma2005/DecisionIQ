package core.engine;

import core.model.Product;
import core.model.UserPreference;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class DecisionEngine {


    private ScoreCalculator scoreCalculator;


    public DecisionEngine(){

        scoreCalculator = new ScoreCalculator();
    }



    public List<Product> recommend(
            List<Product> products,
            UserPreference preference
    ){

        List<Product> filteredProducts =
                new ArrayList<>();


        for(Product product : products){


            if(product.getCategory()
                    .equalsIgnoreCase(
                            preference.getCategory()
                    )
                    &&
                    product.getPrice()
                    <= preference.getBudget()){


                filteredProducts.add(product);
            }
        }



        filteredProducts.sort(

                Comparator.comparingDouble(
                        (Product product) ->

                                scoreCalculator.calculateScore(
                                        product,
                                        preference
                                )

                ).reversed()

        );


        return filteredProducts;
    }
}