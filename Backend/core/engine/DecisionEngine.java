package core.engine;

import core.model.Product;
import core.model.RecommendationResult;
import core.model.UserPreference;
import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;


public class DecisionEngine {


    private ScoreCalculator scoreCalculator;

    private RecommendationExplainer explainer;



    public DecisionEngine(){

        scoreCalculator =
                new ScoreCalculator();

        explainer =
                new RecommendationExplainer();
    }



    public List<RecommendationResult> recommend(
            List<Product> products,
            UserPreference preference
    ){


        List<RecommendationResult> results =
                new ArrayList<>();



        for(Product product : products){


            if(product.getCategory()
                    .equalsIgnoreCase(
                            preference.getCategory()
                    )
                    &&
                    product.getPrice()
                    <= preference.getBudget()){


                double score =
                        scoreCalculator.calculateScore(
                                product,
                                preference
                        );


                List<String> reasons =
                        explainer.explain(
                                product,
                                preference
                        );


                results.add(
                        new RecommendationResult(
                                product,
                                score,
                                reasons
                        )
                );
            }
        }



        results.sort(

                Comparator.comparingDouble(
                        RecommendationResult::getScore
                )
                .reversed()

        );


        return results;
    }
}
