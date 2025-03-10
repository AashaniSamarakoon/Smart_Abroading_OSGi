package travel_advisory_system;

import common.api.TravelAdvisoryService;

public class TravelAdvisoryServiceImpl implements TravelAdvisoryService {

    @Override
    public String getSafetyStatus(String country) {
        return switch (country.toLowerCase()) {
            case "uk" -> "Safe";
            case "germany" -> "Safe";
            case "usa" -> "Moderate Risk";
            case "syria" -> "High Risk";
            case "canada" -> "Very Safe";
            default -> "Unknown";
        };
    }

    @Override
    public String[] getTravelAlerts() {
        return new String[]{
            "COVID-19 entry restrictions", 
            "Political unrest in some regions", 
            "Extreme weather conditions in some areas"
        };
    }
}
