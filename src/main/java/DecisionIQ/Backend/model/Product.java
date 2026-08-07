package DecisionIQ.Backend.model;

import java.util.List;

public class Product {

    private int id;
    private String name;
    private String brand;
    private String category;
    private double rating;
    private List<Feature> features;


    public Product() {
    }


    public Product(
            int id,
            String name,
            String brand,
            String category,
            double rating,
            List<Feature> features
    ) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.rating = rating;
        this.features = features;
    }


    public int getId() {
        return id;
    }


    public String getName() {
        return name;
    }


    public String getBrand() {
        return brand;
    }


    public String getCategory() {
        return category;
    }


    public double getRating() {
        return rating;
    }


    public List<Feature> getFeatures() {
        return features;
    }


    public double getFeatureScore(String featureName) {

        for(Feature feature : features){

            if(feature.getName().equalsIgnoreCase(featureName)){
                return feature.getScore();
            }
        }

        return 0;
    }
}