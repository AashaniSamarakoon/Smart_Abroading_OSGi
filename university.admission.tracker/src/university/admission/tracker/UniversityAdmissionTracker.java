package university.admission.tracker;

import java.util.HashMap;
import java.util.Map;

import common.api.UniversityAdmissionService;

public class UniversityAdmissionTracker implements UniversityAdmissionService {
    private final Map<String, String> admissionResults = new HashMap<>();

    public UniversityAdmissionTracker() {
        admissionResults.put("S12345", "Accepted");
        admissionResults.put("S67890", "Rejected");
    }

    @Override
    public String getAdmissionStatus(String studentId) {
        return admissionResults.getOrDefault(studentId, "Pending");
    }
}
