package job.market.tracker;

import java.util.HashMap;
import java.util.Map;

import common.api.JobMarketService;

public class JobMarketTracker implements JobMarketService {

    private final Map<String, String[]> jobListings = new HashMap<>();

    
    public JobMarketTracker() {
    	

        jobListings.put("Canada", new String[]{"Software Engineer", "Data Analyst"});
        jobListings.put("Germany", new String[]{"Mechanical Engineer", "IT Consultant"});
    }
    
    
    @Override
    public String[] getAvailableJobs(String country) {
        return jobListings.getOrDefault(country, new String[]{"No job listings available"});
    }
}
