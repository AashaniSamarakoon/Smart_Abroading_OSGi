package work.study.location.advisor;

import common.api.JobMarketService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.util.Arrays;

public class Activator implements BundleActivator {
	   private ServiceReference<JobMarketService> serviceReference;

	    @Override
	    public void start(BundleContext context) throws Exception {
	        System.out.println("🔍 Work Study Location Advisor Starting...");

	        // Get reference to JobMarketService
	        serviceReference = context.getServiceReference(JobMarketService.class);
	        
	        if (serviceReference != null) {
	            JobMarketService jobMarketService = context.getService(serviceReference);
	            if (jobMarketService != null) {
	                String country = "Canada";  // Example country
	                String[] jobs = jobMarketService.getAvailableJobs(country);

	                System.out.println("✅ Available Jobs in " + country + ": " + Arrays.toString(jobs));
	            }
	        } else {
	            System.out.println("⚠ Job Market Service not found!");
	        }
	    }
	    
	    //
	    @Override
	    public void stop(BundleContext context) throws Exception {
	        System.out.println("⛔ Work Study Location Advisor Stopping...");
	        if (serviceReference != null) {
	            context.ungetService(serviceReference);
	        }
	    }

	}