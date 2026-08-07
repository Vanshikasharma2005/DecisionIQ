package DecisionIQ.Backend.engine;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import DecisionIQ.Backend.model.Product;
import DecisionIQ.Backend.model.UserPreference;

public class RecommendationExplainer {


    public List<String> explain(
            Product product,
            UserPreference preference
    ) {


        List<String> reasons = new ArrayList<>();


        for (Map.Entry<String, Integer> entry :
                preference.getPreferences().entrySet()) {


            String feature = entry.getKey();


            int importance = entry.getValue();


            double productScore =
                    product.getFeatureScore(feature);



            if (importance >= 70 &&
                    productScore >= 85) {


                reasons.add(
                        feature +
                        " matches your priority (" +
                        productScore +
                        "/100)"
                );

            }



            if (importance >= 70 &&
                    productScore < 60) {


                reasons.add(
                        feature +
                        " is weaker compared to your requirement"
                );

            }

        }


        return reasons;
    }

}