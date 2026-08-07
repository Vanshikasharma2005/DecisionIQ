package DecisionIQ.Backend.model;

import java.util.List;

public class RecommendationResult {

    private Product product;
    private double score;
    private List<String> reasons;

    public RecommendationResult(
            Product product,
            double score,
            List<String> reasons
    ) {
        this.product = product;
        this.score = score;
        this.reasons = reasons;
    }

    public Product getProduct() {
        return product;
    }

    public double getScore() {
        return score;
    }

    public List<String> getReasons() {
        return reasons;
    }
}