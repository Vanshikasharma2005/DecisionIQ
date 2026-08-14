package DecisionIQ.Backend.engine;

import java.util.Map;

import DecisionIQ.Backend.model.Product;
import DecisionIQ.Backend.model.UserPreference;

public class ScoreCalculator {

    public double calculateScore(
            Product product,
            UserPreference preference
    ) {

        double totalScore = 0;
        double totalWeight = 0;

        // Calculate preference-based score
        if (preference.getPreferences() != null) {

            for (Map.Entry<String, Integer> entry :
                    preference.getPreferences().entrySet()) {

                String featureName = entry.getKey();

                int userImportance = entry.getValue();

                double productScore =
                        product.getFeatureScore(featureName);

                totalScore +=
                        productScore * userImportance;

                totalWeight += userImportance;
            }
        }

        // If no preferences are provided
        if (totalWeight == 0) {
            return 0;
        }

        double preferenceScore =
                totalScore / totalWeight;

        // Budget adjustment
        double budget = preference.getBudget();

        if (budget > 0) {

            double price = product.getPrice();

            // Product is within budget
            if (price <= budget) {

                // Small bonus for being comfortably within budget
                double budgetBonus =
                        ((budget - price) / budget) * 5;

                preferenceScore += budgetBonus;
            }

            // Product is above budget
            else {

                double overBudgetRatio =
                        (price - budget) / budget;

                // Apply penalty based on how much
                // the product exceeds the budget
                double budgetPenalty =
                        overBudgetRatio * 20;

                preferenceScore -= budgetPenalty;
            }
        }

        // Keep score between 0 and 100
        if (preferenceScore < 0) {
            preferenceScore = 0;
        }

        if (preferenceScore > 100) {
            preferenceScore = 100;
        }

        return preferenceScore;
    }
}