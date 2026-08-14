package DecisionIQ.Backend.engine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import DecisionIQ.Backend.model.Product;
import DecisionIQ.Backend.model.RecommendationResult;
import DecisionIQ.Backend.model.UserPreference;

public class DecisionEngine {

    private final ScoreCalculator scoreCalculator;

    public DecisionEngine() {
        this.scoreCalculator = new ScoreCalculator();
    }

    public List<RecommendationResult> recommend(
            List<Product> products,
            UserPreference preference
    ) {

        List<RecommendationResult> results = new ArrayList<>();

        for (Product product : products) {

            double price = product.getPrice();

            double score = scoreCalculator.calculateScore(
                    product,
                    preference
            );

            List<String> reasons = new ArrayList<>();

            if (preference.getBudget() > 0) {

                if (price <= preference.getBudget()) {
                    reasons.add(
                            "price fits within your budget"
                    );
                } else {
                    reasons.add(
                            "price is above your budget"
                    );
                }
            }

            if (preference.getPreferences() != null) {

                for (String featureName :
                        preference.getPreferences().keySet()) {

                    if (featureName == null) {
                        continue;
                    }

                    double priority =
                            preference.getPreferences()
                                    .get(featureName);

                    double productScore =
                            product.getFeatureScore(featureName);

                    if (productScore >= priority) {

                        String cleanFeatureName =
                                featureName.trim().toLowerCase();

                        reasons.add(
                                cleanFeatureName
                                        + " matches your priority"
                        );
                    }
                }
            }

            results.add(
                    new RecommendationResult(
                            product,
                            score,
                            reasons
                    )
            );
        }

        results.sort(
                Comparator.comparingDouble(
                        RecommendationResult::getScore
                ).reversed()
        );

        return results;
    }
}