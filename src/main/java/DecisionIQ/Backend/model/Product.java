package DecisionIQ.Backend.model;

import java.util.List;

public class Product {

    private int id;
    private String name;
    private String brand;
    private String category;
    private double price;
    private List<Feature> features;

    public Product(
            int id,
            String name,
            String brand,
            String category,
            double price,
            List<Feature> features
    ) {
        this.id = id;
        this.name = name;
        this.brand = brand;
        this.category = category;
        this.price = price;
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

    public double getPrice() {
        return price;
    }

    public List<Feature> getFeatures() {
        return features;
    }

    public int getFeatureScore(String featureName) {

        for (Feature feature : features) {

            if (feature.getName().equalsIgnoreCase(featureName)) {
                return feature.getScore();
            }
        }

        return 0;
    }

    @Override
    public String toString() {

        return name +
                " | " +
                brand +
                " | ₹" +
                price +
                " | " +
                features;
    }
}