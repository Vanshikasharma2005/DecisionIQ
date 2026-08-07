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


        if (totalWeight == 0) {
            return 0;
        }


        return totalScore / totalWeight;
    }

}