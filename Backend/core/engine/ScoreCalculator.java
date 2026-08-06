package core.engine;

import core.model.Product;
import core.model.UserPreference;
import java.util.Map;

public class ScoreCalculator {


    public double calculateScore(
            Product product,
            UserPreference preference
    ){

        double totalScore = 0;

        double totalWeight = 0;


        for(Map.Entry<String,Integer> entry :
                preference.getPreferences().entrySet()){


            String featureName =
                    entry.getKey();


            int userImportance =
                    entry.getValue();


            int productScore =
                    product.getFeatureScore(
                            featureName
                    );


            totalScore +=
                    productScore * userImportance;


            totalWeight += userImportance;
        }


        if(totalWeight == 0){

            return 0;
        }


        return totalScore / totalWeight;
    }
}