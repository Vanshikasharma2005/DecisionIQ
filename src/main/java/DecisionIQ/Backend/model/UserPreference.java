package DecisionIQ.Backend.model;

import java.util.HashMap;
import java.util.Map;

public class UserPreference {

    private double budget;
    private String category;
    private Map<String, Integer> preferences;

    public UserPreference() {
        this.preferences = new HashMap<>();
    }

    public UserPreference(double budget, String category) {
        this.budget = budget;
        this.category = category;
        this.preferences = new HashMap<>();
    }

    public void addPreference(String feature, int importance) {
        preferences.put(feature, importance);
    }

    public double getBudget() {
        return budget;
    }

    public void setBudget(double budget) {
        this.budget = budget;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public Map<String, Integer> getPreferences() {
        return preferences;
    }

    public void setPreferences(Map<String, Integer> preferences) {
        this.preferences = preferences;
    }

    public int getImportance(String feature) {
        return preferences.getOrDefault(feature, 0);
    }
}