package DecisionIQ.Backend.engine;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Map;

import DecisionIQ.Backend.model.Product;
import DecisionIQ.Backend.model.RecommendationResult;
import DecisionIQ.Backend.model.UserPreference;

public class DecisionEngine {

    public List<RecommendationResult> recommend(
            List<Product> products,
            UserPreference preference
    ) {

        List<RecommendationResult> results = new ArrayList<>();

        if (products == null || preference == null) {
            return results;
        }

        int budget = preference.getBudget();
        Map<String, Integer> preferences = preference.getPreferences();

        if (preferences == null || preferences.isEmpty()) {
            return results;
        }

        for (Product product : products) {

            // Product rating currently stores the product price.
            double price = product.getRating();

            // Ignore products that are above the user's budget.
            if (price > budget) {
                continue;
            }

            double score = calculateScore(product, preference);

            List<String> reasons = new ArrayList<>();

            for (Map.Entry<String, Integer> entry : preferences.entrySet()) {

                String feature = entry.getKey();
                int priority = entry.getValue();

                double productScore =
                        product.getFeatureScore(feature);

                if (productScore > 0) {
                    reasons.add(
                            feature + " matches with priority " + priority
                    );
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


    public double calculateScore(
            Product product,
            UserPreference preference
    ) {

        if (product == null || preference == null) {
            return 0;
        }

        Map<String, Integer> preferences =
                preference.getPreferences();

        if (preferences == null || preferences.isEmpty()) {
            return 0;
        }

        double weightedScore = 0;
        double totalPriority = 0;

        for (Map.Entry<String, Integer> entry : preferences.entrySet()) {

            String feature = entry.getKey();
            int priority = entry.getValue();

            double productScore =
                    product.getFeatureScore(feature);

            weightedScore += productScore * priority;
            totalPriority += priority;
        }

        if (totalPriority == 0) {
            return 0;
        }

        return weightedScore / totalPriority;
    }
}