package DecisionIQ.Backend.engine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import DecisionIQ.Backend.model.Product;
import DecisionIQ.Backend.model.RecommendationResult;
import DecisionIQ.Backend.model.UserPreference;

public class DecisionEngine {

    private final ScoreCalculator scoreCalculator;
    private final RecommendationExplainer explainer;

    public DecisionEngine() {

        scoreCalculator = new ScoreCalculator();

        explainer = new RecommendationExplainer();
    }

    public List<RecommendationResult> recommend(
            List<Product> products,
            UserPreference preference
    ) {

        List<RecommendationResult> results =
                new ArrayList<>();

        for (Product product : products) {

            if (product.getCategory()
                    .equalsIgnoreCase(
                            preference.getCategory()
                    )
                    &&
                    product.getPrice()
                            <= preference.getBudget()) {

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
                ).reversed()
        );

        return results;
    }
}