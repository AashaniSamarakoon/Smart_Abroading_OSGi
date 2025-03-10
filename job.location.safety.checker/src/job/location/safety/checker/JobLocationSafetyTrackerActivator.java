package job.location.safety.checker;

import common.api.TravelAdvisoryService;
import org.osgi.framework.*;
import java.util.Scanner;

public class JobLocationSafetyTrackerActivator implements BundleActivator {
    private ServiceReference<TravelAdvisoryService> travelReference;
    private TravelAdvisoryService travelService;
    private volatile boolean running = true;  // Flag to control loop

    @Override
    public void start(BundleContext context) throws Exception {
        // Get reference to Travel Advisory Service
        travelReference = context.getServiceReference(TravelAdvisoryService.class);
        
        if (travelReference != null) {
            travelService = context.getService(travelReference);
            System.out.println("\nJob Location Safety Checker Started!");

            Scanner scanner = new Scanner(System.in);

            // Loop to continuously check location safety
            while (running) {
                System.out.print("\n Enter a country to check its safety (or type 'exit' to stop): ");
                String country = scanner.nextLine().trim();

                if (country.equalsIgnoreCase("exit")) {
                    running = false;
                    break;
                }

                String safetyStatus = travelService.getSafetyStatus(country);
                System.out.println(" "+country + "'s Safety Level: " + safetyStatus);
            }

            scanner.close();
        } else {
            System.out.println("❌ Job Location Safety Checker: Travel Advisory Service not available.");
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        running = false; // Stop the loop
        if (travelReference != null) {
            context.ungetService(travelReference);
        }
        System.out.println("Job Location Safety Checker Stopped.");
    }
}
