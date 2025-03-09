package job.location.safety.checker;

import common.api.TravelAdvisoryService;
import org.osgi.framework.*;

public class Activator implements BundleActivator {
    private ServiceReference<TravelAdvisoryService> travelReference;
    private TravelAdvisoryService travelService;

    @Override
    public void start(BundleContext context) throws Exception {
        // Get reference to Travel Advisory Service
        travelReference = context.getServiceReference(TravelAdvisoryService.class);
        
        if (travelReference != null) {
            travelService = context.getService(travelReference);
            
            // Check job location safety for some example countries
            String[] jobLocations = {"UK", "USA", "Syria"};
            System.out.println("\n🔎 Job Location Safety Checker:");
            
            for (String country : jobLocations) {
                String safetyStatus = travelService.getSafetyStatus(country);
                System.out.println("📍 " + country + " - Safety Level: " + safetyStatus);
            }
            
            // Show travel alerts
            String[] alerts = travelService.getTravelAlerts();
            System.out.println("\n⚠️ Global Travel Alerts:");
            for (String alert : alerts) {
                System.out.println("- " + alert);
            }
        } else {
            System.out.println("❌ Job Location Safety Checker: Travel Advisory Service not available.");
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        if (travelReference != null) {
            context.ungetService(travelReference);
        }
        System.out.println("⛔ Job Location Safety Checker Stopped.");
    }
}
