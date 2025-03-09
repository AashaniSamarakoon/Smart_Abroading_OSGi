package travel_advisory_system;

import common.api.TravelAdvisoryService;
import org.osgi.framework.*;
import java.util.HashMap;
import java.util.Map;
public class TravelAdvisoryServiceImpl implements TravelAdvisoryService {

    @Override
    public String getSafetyStatus(String country) {
        return switch (country.toLowerCase()) {
            case "uk" -> "Safe";
            case "usa" -> "Moderate Risk";
            case "syria" -> "High Risk";
            default -> "Unknown";
        };
    }

    @Override
    public String[] getTravelAlerts() {
        return new String[]{"COVID-19 entry restrictions", "Political unrest in some regions"};
    }
}