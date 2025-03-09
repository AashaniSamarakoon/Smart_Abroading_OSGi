package travel_advisory_system;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class Activator implements BundleActivator {
    private ServiceRegistration<?> serviceRegistration;

    @Override
    public void start(BundleContext context) throws Exception {
        TravelAdvisoryService advisoryService = new TravelAdvisoryServiceImpl();
        serviceRegistration = context.registerService(TravelAdvisoryService.class.getName(), advisoryService, null);
        System.out.println("Travel Advisory Publisher Started...");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        serviceRegistration.unregister();
        System.out.println("Travel Advisory Publisher Stopped...");
    }
}
