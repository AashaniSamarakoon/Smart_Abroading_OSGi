package visa.processing.tracker;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import common.api.VisaProcessingService;

public class VisaProcessingServiceActivator implements BundleActivator {
    private ServiceRegistration<?> registration;

    @Override
    public void start(BundleContext context) throws Exception {
        VisaProcessingService service = new VisaProcessingTracker();
        registration = context.registerService(VisaProcessingService.class.getName(), service, null);
        System.out.println("Visa Processing Tracker Started.");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        registration.unregister();
        System.out.println("Visa Processing Tracker Stopped.");
    }
}
