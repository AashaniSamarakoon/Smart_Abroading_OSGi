package scholarship_funding_finder;

import java.util.HashMap;
import java.util.Map;

import common.api.ScholarshipService;

public class ScholarshipFundingFinderPublisher implements ScholarshipService {
	private final Map<String, String[]> scholarships = new HashMap<>();

    public ScholarshipFundingFinderPublisher() {
    	scholarships.put("Computer Science", new String[]{
                "Google Scholarship", 
                "MIT Fellowship", 
                "Apple Scholars Program", 
                "Microsoft Research Fellowship", 
                "Amazon Scholarship"
        });
        
        scholarships.put("Engineering", new String[]{
                "NASA Grant", 
                "Tesla Scholarship", 
                "GE Engineering Fellowship", 
                "Lockheed Martin STEM Scholarship", 
                "General Electric (GE) Energy Scholarship"
        });

        scholarships.put("Mathematics", new String[]{
                "American Mathematical Society Scholarship", 
                "SIAM Undergraduate Research Fellowship", 
                "Putnam Competition Scholarship", 
                "Clay Mathematics Institute Fellowship", 
                "National Math Alliance Scholarship"
        });

        scholarships.put("Arts", new String[]{
                "Getty Foundation Scholar Grant", 
                "Smithsonian Artist Fellowship", 
                "National Endowment for the Arts Grant", 
                "Art Institute of Chicago Fellowship", 
                "National Museum of Women in the Arts Fellowship"
        });

        scholarships.put("Medical Sciences", new String[]{
                "Gates Cambridge Scholarship", 
                "Rhodes Scholarship", 
                "American Medical Association Scholarship", 
                "NIH Undergraduate Scholarship Program", 
                "Johns Hopkins Medical Research Grant"
        });

        scholarships.put("Law", new String[]{
                "Skadden Fellowship", 
                "Harry S. Truman Scholarship", 
                "Paul & Daisy Soros Fellowships for New Americans", 
                "American Bar Association Legal Opportunity Scholarship", 
                "Berkeley Law Scholarships"
        });

        scholarships.put("Business", new String[]{
                "Wharton Undergraduate Fellowships", 
                "Harvard Business School Fellowship", 
                "Stanford Business Scholarship", 
                "The Goldman Sachs Scholarship", 
                "KPMG Business & Technology Scholarship"
        });
    }

    @Override
    public String[] getAvailableScholarships(String field) {
        return scholarships.getOrDefault(field, new String[]{"No scholarships available"});
    }
}
