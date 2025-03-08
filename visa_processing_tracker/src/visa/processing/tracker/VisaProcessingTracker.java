package visa.processing.tracker;

import common.api.VisaProcessingService;

import java.util.HashMap;
import java.util.Map;


public class VisaProcessingTracker implements VisaProcessingService {
    private final Map<String, String> visaStatus = new HashMap<>();

    public VisaProcessingTracker() {
        visaStatus.put("P12345678", "Approved");
        visaStatus.put("P98765432", "Pending");
    }

    @Override
    public String getVisaStatus(String passportNumber) {
        return visaStatus.getOrDefault(passportNumber, "Rejected");
    }
}