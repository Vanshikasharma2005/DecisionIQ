package DecisionIQ.Backend.service;


import java.util.List;

import DecisionIQ.Backend.engine.DecisionEngine;
import DecisionIQ.Backend.model.Product;
import DecisionIQ.Backend.model.RecommendationResult;
import DecisionIQ.Backend.model.UserPreference;
import DecisionIQ.Backend.repository.CSVProductReader;


public class RecommendationService {


    private final DecisionEngine decisionEngine;
    private final CSVProductReader productReader;


    public RecommendationService() {

        this.decisionEngine = new DecisionEngine();
        this.productReader = new CSVProductReader();

    }


    public List<RecommendationResult> getRecommendations(
            UserPreference preference
    ) {


        List<Product> products =
                productReader.readProducts(
                        "Datasets/products.csv"
                );


        return decisionEngine.recommend(
                products,
                preference
        );

    }

}