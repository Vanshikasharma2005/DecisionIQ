package core.engine;

import core.model.Product;
import core.model.UserPreference;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;


public class ExplanationEngine {


    public List<String> generateExplanation(
            Product product,
            UserPreference preference
    ){


        List<String> reasons =
                new ArrayList<>();


        for(Map.Entry<String,Integer> entry :
                preference.getPreferences().entrySet()){


            String feature =
                    entry.getKey();


            int importance =
                    entry.getValue();


            int productScore =
                    product.getFeatureScore(feature);



            if(productScore >= 85 &&
                    importance >= 70){


                reasons.add(
                    "Strong " +
                    feature +
                    " match ("+
                    productScore+
                    "/100)"
                );
            }


            else if(productScore < 60 &&
                    importance >=70){


                reasons.add(
                    "Weak " +
                    feature +
                    " compared to your priority"
                );
            }
        }


        return reasons;
    }
}