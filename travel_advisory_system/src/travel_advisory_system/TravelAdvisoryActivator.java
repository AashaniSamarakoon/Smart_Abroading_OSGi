package travel_advisory_system;

import common.api.TravelAdvisoryService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class TravelAdvisoryActivator implements BundleActivator {
    private ServiceRegistration<?> registration;

    @Override
    public void start(BundleContext context) throws Exception {
        TravelAdvisoryService service = new TravelAdvisoryServiceImpl();
        registration = context.registerService(TravelAdvisoryService.class, service, null);
        System.out.println("Travel Advisory Service Started.");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        if (registration != null) {
            registration.unregister();
        }
        System.out.println("Travel Advisory Service Stopped.");
    }
}

