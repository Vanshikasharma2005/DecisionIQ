package DecisionIQ.Backend.controller;

import java.util.List;

import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import DecisionIQ.Backend.model.RecommendationResult;
import DecisionIQ.Backend.model.UserPreference;
import DecisionIQ.Backend.service.DecisionService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "http://localhost:5173")
public class DecisionController {

    private final DecisionService decisionService;

    public DecisionController(
            DecisionService decisionService
    ) {
        this.decisionService = decisionService;
    }

    @PostMapping("/recommend")
    public List<RecommendationResult> recommend(
            @RequestBody UserPreference preference
    ) {

        return decisionService.getRecommendations(
                preference
        );
    }
}