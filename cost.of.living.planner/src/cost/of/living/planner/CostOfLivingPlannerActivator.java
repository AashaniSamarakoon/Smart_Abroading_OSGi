package cost.of.living.planner;

import common.api.AccommodationCostService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.util.Scanner;

public class CostOfLivingPlannerActivator implements BundleActivator {

    private ServiceReference<AccommodationCostService> serviceReference;
    private static final Scanner scanner = new Scanner(System.in);

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Cost of Living Planner Starting...");

        // Get reference to AccommodationCostService
        serviceReference = context.getServiceReference(AccommodationCostService.class);

        if (serviceReference != null) {
            AccommodationCostService costService = context.getService(serviceReference);

            if (costService != null) {
                while (true) {
                    System.out.println("\n==========================================");
                    System.out.println("Accommodation Cost Analysis Tool");
                    System.out.println("==========================================");

                    // Get user input for country and city
                    System.out.print("Enter country: ");
                    String country = scanner.nextLine().trim();

                    System.out.print("Enter city: ");
                    String city = scanner.nextLine().trim();

                    // Fetch cost data
                    double nightlyCost = costService.getCost(country, city);

                    if (nightlyCost != -1.0) {
                        System.out.println("\n✅ Accommodation Cost Details for " + city + ", " + country);
                        System.out.println("----------------------------------------------------");
                        System.out.printf("Nightly Cost: $%.2f per night\n", nightlyCost);

                        // Get number of nights from user
                        System.out.print("Enter number of nights you plan to stay: ");
                        int nights = scanner.nextInt();

                        // Get number of travelers
                        System.out.print("Enter number of people sharing the cost: ");
                        int people = scanner.nextInt();
                        scanner.nextLine(); // Consume newline

                        // Calculate total cost
                        double totalCost = nightlyCost * nights;
                        double costPerPerson = totalCost / people;

                        // Estimate monthly rent based on nightly cost
                        double estimatedMonthlyRent = nightlyCost * 30; // Approximate rent for a month

                        // Display cost breakdown
                        System.out.println("----------------------------------------------------");
                        System.out.printf("Total cost for %d nights: $%.2f\n", nights, totalCost);
                        System.out.printf("Cost per person (if split among %d people): $%.2f\n", people, costPerPerson);
                        System.out.printf("Estimated Monthly Rent: $%.2f\n", estimatedMonthlyRent);
                        System.out.println("----------------------------------------------------");

                        // Ask if user wants to compare another city
                        System.out.print("Do you want to compare another city? (yes/no): ");
                        String choice = scanner.nextLine().trim().toLowerCase();
                        if (!choice.equals("yes")) {
                            break;
                        }
                    } else {
                        System.out.println("No accommodation cost data available for " + city + ", " + country);
                    }
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
