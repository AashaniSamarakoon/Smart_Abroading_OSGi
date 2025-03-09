package scholarship_funding_finder;

import java.util.HashMap;
import java.util.Map;

import common.api.ScholarshipService;

public class ScholarshipFundingFinderPublisher implements ScholarshipService {
	private final Map<String, String[]> scholarships = new HashMap<>();

    public ScholarshipFundingFinderPublisher() {
        scholarships.put("Computer Science", new String[]{"Google Scholarship", "MIT Fellowship"});
        scholarships.put("Engineering", new String[]{"NASA Grant", "Tesla Scholarship"});
    }

    @Override
    public String[] getAvailableScholarships(String field) {
        return scholarships.getOrDefault(field, new String[]{"No scholarships available"});
    }
}
