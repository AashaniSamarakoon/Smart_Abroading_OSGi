package cost.of.living.planner;

import common.api.AccommodationCostService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class CostOfLivingPlannerActivator implements BundleActivator {

    private ServiceReference<AccommodationCostService> serviceReference;

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("🏠 Cost of Living Planner Starting...");

        // Get reference to AccommodationCostService
        serviceReference = context.getServiceReference(AccommodationCostService.class);

        if (serviceReference != null) {
            AccommodationCostService costService = context.getService(serviceReference);
            if (costService != null) {
                // Example cities to check costs
                String country = "USA";
                String city = "New York";

                double cost = costService.getCost(country, city);

                if (cost != -1.0) {
                    System.out.println("✅ Accommodation cost in " + city + ", " + country + ": $" + cost + " per night");
                } else {
                    System.out.println("⚠ No accommodation cost data available for " + city + ", " + country);
                }
            }
        } else {
            System.out.println("⚠ Accommodation Cost Service not found!");
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("⛔ Cost of Living Planner Stopping...");
        if (serviceReference != null) {
            context.ungetService(serviceReference);
        }
    }
}
