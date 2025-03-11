package work.study.location.advisor;

import common.api.JobMarketService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.util.Arrays;
import java.util.Scanner;

public class Activator implements BundleActivator {
    private ServiceReference<JobMarketService> serviceReference;
    private static final Scanner scanner = new Scanner(System.in); // Keep Scanner static to avoid closing System.in

    // ANSI Escape Codes for Colors
    private static final String GREEN = "\u001B[32m";
    private static final String BLUE = "\u001B[34m";
    private static final String RED = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";
    private static final String RESET = "\u001B[0m"; // Reset color

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println(BLUE + "Work Study Location Advisor Starting..." + RESET);

        // Get reference to JobMarketService
        serviceReference = context.getServiceReference(JobMarketService.class);

        if (serviceReference != null) {
            JobMarketService jobMarketService = context.getService(serviceReference);

            if (jobMarketService != null) {
                while (true) {  // Allows user to query multiple countries
                    System.out.println("\n==============================================");
                    System.out.println(CYAN + "Job Market Search Tool" + RESET);
                    System.out.println("==============================================");

                    System.out.print(GREEN + "Enter a country to check available jobs (or type 'exit' to quit): " + RESET);
                    String country = scanner.nextLine().trim();

                    // Exit option
                    if (country.equalsIgnoreCase("exit")) {
                        System.out.println(RED + "\nExiting Work Study Location Advisor..." + RESET);
                        break;
                    }

                    String[] jobs = jobMarketService.getAvailableJobs(country);

                    if (jobs != null && jobs.length > 0) {
                        System.out.println("\n" + GREEN + "Available Jobs in " + country + ":" + RESET);
                        System.out.println("----------------------------------------------");
                        for (String job : jobs) {
                            System.out.println("📌" + job);
                        }
                        System.out.println("----------------------------------------------");
                    } else {
                        System.out.println(RED + "No job listings found for " + country + RESET);
                    }
                }
            }
        } else {
            System.out.println(RED + "Job Market Service not found!" + RESET);
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println(RED + "Work Study Location Advisor Stopping..." + RESET);

        if (serviceReference != null) {
            context.ungetService(serviceReference);
        }
    }
}
