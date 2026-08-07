package DecisionIQ.Backend.service;

import DecisionIQ.Backend.engine.DecisionEngine;
import DecisionIQ.Backend.model.Product;
import DecisionIQ.Backend.model.RecommendationResult;
import DecisionIQ.Backend.model.UserPreference;
import DecisionIQ.Backend.repository.ProductRepository;

import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DecisionService {

    private final DecisionEngine decisionEngine;
    private final ProductRepository productRepository;

    public DecisionService() {

        this.decisionEngine = new DecisionEngine();
        this.productRepository = new ProductRepository();
    }

    public List<RecommendationResult> getRecommendations(
            UserPreference preference
    ) {

        List<Product> products =
                productRepository.getProducts();

        return decisionEngine.recommend(
                products,
                preference
        );
    }
}