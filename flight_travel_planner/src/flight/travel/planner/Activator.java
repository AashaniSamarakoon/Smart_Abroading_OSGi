package flight.travel.planner;

import common.api.VisaProcessingService;
import common.api.TravelAdvisoryService;
import org.osgi.framework.*;

public class Activator implements BundleActivator {
    private ServiceReference<VisaProcessingService> visaReference;
    private ServiceReference<TravelAdvisoryService> advisoryReference;
    private VisaProcessingService visaService;
    private TravelAdvisoryService advisoryService;

    @Override
    public void start(BundleContext context) throws Exception {
        // Get the VisaProcessingService reference
        visaReference = context.getServiceReference(VisaProcessingService.class);
        if (visaReference != null) {
            visaService = context.getService(visaReference);
            String visaStatus = visaService.getVisaStatus("P12345678");

            if ("Approved".equals(visaStatus)) {
                // Get the TravelAdvisoryService reference
                advisoryReference = context.getServiceReference(TravelAdvisoryService.class);
                if (advisoryReference != null) {
                    advisoryService = context.getService(advisoryReference);

                    // Check the travel advisory status for the destination country
                    String destinationCountry = "USA"; // Example destination country
                    String safetyStatus = advisoryService.getSafetyStatus(destinationCountry);

                    if ("Safe".equals(safetyStatus)) {
                        System.out.println("Flight Travel Planner: Visa approved and destination is safe! Recommending flights.");
                    } else if ("Moderate Risk".equals(safetyStatus)) {
                        System.out.println("Flight Travel Planner: Visa approved but destination has moderate risk. Proceed with caution.");
                    } else if ("High Risk".equals(safetyStatus)) {
                        System.out.println("Flight Travel Planner: Visa approved but destination has high risk. Not recommending travel.");
                    } else {
                        System.out.println("Flight Travel Planner: Visa approved but destination safety status is unknown.");
                    }
                } else {
                    System.out.println("Flight Travel Planner: No TravelAdvisoryService available.");
                }
            } else {
                System.out.println("Flight Travel Planner: Visa not approved. Cannot book travel yet.");
            }
        } else {
            System.out.println("Flight Travel Planner: No VisaProcessingService available.");
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        if (visaReference != null) {
            context.ungetService(visaReference);
        }
        if (advisoryReference != null) {
            context.ungetService(advisoryReference);
        }
        System.out.println("Flight Travel Planner Stopped.");
    }
}