package DecisionIQ.Backend.repository;

import DecisionIQ.Backend.model.Product;

import java.util.List;

public class ProductRepository {

    private final CSVProductReader reader;

    public ProductRepository() {
        reader = new CSVProductReader();
    }

    public List<Product> getProducts() {
        return reader.readProducts("Datasets/products.csv");
    }
}