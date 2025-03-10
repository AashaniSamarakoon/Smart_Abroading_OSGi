package visa.eligibility.checker;

import common.api.JobMarketService;
import common.api.VisaProcessingService;
import org.osgi.framework.*;
import java.util.Scanner;

public class ServiceActivator implements BundleActivator {
    private ServiceReference<JobMarketService> jobMarketReference;
    private ServiceReference<VisaProcessingService> visaReference;
    private JobMarketService jobMarketService;
    private VisaProcessingService visaService;
    private volatile boolean running = true; 

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Visa Eligibility Checker: Attempting to retrieve required services...\n");

        jobMarketReference = context.getServiceReference(JobMarketService.class);
        visaReference = context.getServiceReference(VisaProcessingService.class);

        if (jobMarketReference != null) {
            jobMarketService = context.getService(jobMarketReference);
            System.out.println("Found Job Market Service.");
        } else {
            System.out.println("Job Market Service not found!");
        }

        if (visaReference != null) {
            visaService = context.getService(visaReference);
            System.out.println("Found Visa Processing Service.\n");
        } else {
            System.out.println("Visa Processing Service not found!\n");
        }

        if (jobMarketService == null || visaService == null) {
            System.out.println("Visa Eligibility Checker: Required services not available.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        while (running) {
            System.out.print("\nEnter your passport number to check visa status (or type 'exit' to stop): ");
            String passportNumber = scanner.nextLine().trim();
            if (passportNumber.equalsIgnoreCase("exit")) {
                running = false;
                break;
            }

            String visaStatus = visaService.getVisaStatus(passportNumber);
            System.out.println("\nVisa Status for " + passportNumber + ": " + visaStatus);

            if ("Invalid Passport Number".equals(visaStatus)) {
                System.out.println("Please enter a valid passport number.");
                continue;
            }

            if (!"Approved".equals(visaStatus)) {
                System.out.println("Visa not approved. Cannot check job eligibility.");
                continue;
            }

            System.out.print("\nEnter a country to check job availability (or type 'exit' to stop): ");
            String country = scanner.nextLine().trim();
            if (country.equalsIgnoreCase("exit")) {
                running = false;
                break;
            }

            String[] jobs = jobMarketService.getAvailableJobs(country);

            if (jobs.length > 0 && !jobs[0].equals("No job listings available for this location.")) {
                System.out.println("\nVisa Eligibility Checker: You are eligible to work in " + country + ".");
                System.out.println("Job Opportunities: " + String.join(", ", jobs));
            } else {
                System.out.println("\nVisa Eligibility Checker: No jobs available in " + country + ".");
            }
        }

        scanner.close();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        running = false; 
        if (jobMarketReference != null) {
            context.ungetService(jobMarketReference);
        }
        if (visaReference != null) {
            context.ungetService(visaReference);
        }
        System.out.println("Visa Eligibility Checker Stopped.");
    }
}
