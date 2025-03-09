package accommodation.cost.tracker;

import common.api.AccommodationCostService;
import java.util.HashMap;
import java.util.Map;

public class AccommodationCostServiceImpl implements AccommodationCostService {

    private final Map<String, Map<String, Double>> costData = new HashMap<>();

    public AccommodationCostServiceImpl() {
        // Sample data
        Map<String, Double> usaCosts = new HashMap<>();
        usaCosts.put("New York", 200.0);
        usaCosts.put("Los Angeles", 180.0);
        
        Map<String, Double> ukCosts = new HashMap<>();
        ukCosts.put("London", 220.0);
        ukCosts.put("Manchester", 150.0);

        costData.put("USA", usaCosts);
        costData.put("UK", ukCosts);
    }

    @Override
    public double getCost(String country, String city) {
        return costData.getOrDefault(country, new HashMap<>()).getOrDefault(city, -1.0);
    }
}
