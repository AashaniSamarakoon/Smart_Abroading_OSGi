package flight.travel.planner;

import common.api.VisaProcessingService;
import org.osgi.framework.*;

public class Activator implements BundleActivator {
    private ServiceReference<VisaProcessingService> reference;
    private VisaProcessingService service;

    @Override
    public void start(BundleContext context) throws Exception {
        reference = context.getServiceReference(VisaProcessingService.class);
        if (reference != null) {
            service = context.getService(reference);
            String visaStatus = service.getVisaStatus("P12345678");

            if ("Approved".equals(visaStatus)) {
                System.out.println("Flight Travel Planner: Visa approved! Recommending flights.");
            } else {
                System.out.println("Flight Travel Planner: Visa not approved. Cannot book travel yet.");
            }
        } else {
            System.out.println("Flight Travel Planner: No VisaProcessingService available.");
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        if (reference != null) {
            context.ungetService(reference);
            System.out.println("Flight Travel Planner Stopped.");
        }
    }
}
