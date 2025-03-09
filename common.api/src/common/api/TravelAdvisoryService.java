package common.api;

public interface TravelAdvisoryService {
    String getSafetyStatus(String country);
    String[] getTravelAlerts();
}
