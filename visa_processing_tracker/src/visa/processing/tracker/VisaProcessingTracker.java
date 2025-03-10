package visa.processing.tracker;

import common.api.VisaProcessingService;
import java.util.HashMap;
import java.util.Map;

public class VisaProcessingTracker implements VisaProcessingService {
    private final Map<String, String> visaStatus = new HashMap<>();

    public VisaProcessingTracker() {
        visaStatus.put("P12345678", "Approved");
        visaStatus.put("P98765432", "Pending");
        visaStatus.put("P55555555", "Rejected");
        visaStatus.put("P99999999", "Approved");
        visaStatus.put("P33333333", "Rejected");
        visaStatus.put("P44444444", "Pending");
    }

    @Override
    public String getVisaStatus(String passportNumber) {
        return visaStatus.getOrDefault(passportNumber, "Invalid Passport Number");
    }
}
