package flight.travel.planner;

import common.api.VisaProcessingService;
import common.api.TravelAdvisoryService;
import org.osgi.framework.*;
import java.util.Scanner;

public class FlightTravelPlannerActivator implements BundleActivator {
    private ServiceReference<VisaProcessingService> visaReference;
    private ServiceReference<TravelAdvisoryService> advisoryReference;
    private VisaProcessingService visaService;
    private TravelAdvisoryService advisoryService;
    private volatile boolean running = true; // Flag to control loop

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Flight Travel Planner: Attempting to retrieve required services.");

        visaReference = context.getServiceReference(VisaProcessingService.class);
        advisoryReference = context.getServiceReference(TravelAdvisoryService.class);

        if (visaReference != null) {
            visaService = context.getService(visaReference);
            System.out.println("Found Visa Processing Service.");
        } else {
            System.out.println("Visa Processing Service not found.");
        }

        if (advisoryReference != null) {
            advisoryService = context.getService(advisoryReference);
            System.out.println("Found Travel Advisory Service.");
        } else {
            System.out.println("Travel Advisory Service not found.");
        }

        if (visaService == null || advisoryService == null) {
            System.out.println("Required services not available.");
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
            System.out.println("Visa Status for " + passportNumber + ": " + visaStatus);

            if ("Invalid Passport Number".equals(visaStatus)) {
                System.out.println("Please enter a valid passport number.");
                continue;
            }

            if (!"Approved".equals(visaStatus)) {
                System.out.println("Visa not approved. Cannot proceed with travel booking.");
                continue;
            }

            System.out.print("\nEnter your destination country (or type 'exit' to stop): ");
            String destinationCountry = scanner.nextLine().trim();
            if (destinationCountry.equalsIgnoreCase("exit")) {
                running = false;
                break;
            }

            String safetyStatus = advisoryService.getSafetyStatus(destinationCountry);
            System.out.println("Travel Safety Status for " + destinationCountry + ": " + safetyStatus);

            if ("Safe".equals(safetyStatus) || "Very Safe".equals(safetyStatus)) {
                System.out.println("Flight Travel Planner: Visa approved and destination is safe. Recommending flights.");
            } else if ("Moderate Risk".equals(safetyStatus)) {
                System.out.println("Flight Travel Planner: Visa approved but destination has moderate risk. Proceed with caution.");
            } else if ("High Risk".equals(safetyStatus)) {
                System.out.println("Flight Travel Planner: Visa approved but destination has high risk. Not recommending travel.");
            } else {
                System.out.println("Flight Travel Planner: Visa approved but destination safety status is unknown.");
            }
        }

        scanner.close();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        running = false; // Stop the loop
        if (visaReference != null) {
            context.ungetService(visaReference);
        }
        if (advisoryReference != null) {
            context.ungetService(advisoryReference);
        }
        System.out.println("Flight Travel Planner Stopped.");
    }
}
