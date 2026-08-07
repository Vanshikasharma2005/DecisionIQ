package DecisionIQ.Backend.model;

import java.util.Map;

public class UserPreference {

    private int budget;

    private Map<String,Integer> preferences;


    public UserPreference() {
    }


    public int getBudget() {
        return budget;
    }


    public void setBudget(int budget) {
        this.budget = budget;
    }


    public Map<String,Integer> getPreferences() {
        return preferences;
    }


    public void setPreferences(Map<String,Integer> preferences) {
        this.preferences = preferences;
    }
}