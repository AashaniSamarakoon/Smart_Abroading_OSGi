package accommodation.cost.tracker;

import common.api.AccommodationCostService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class AccommodationCostActivator implements BundleActivator {
    private ServiceRegistration<?> registration;

    @Override
    public void start(BundleContext context) throws Exception {
        AccommodationCostService service = new AccommodationCostServiceImpl();
        registration = context.registerService(AccommodationCostService.class, service, null);
        System.out.println("Accommodation Cost Tracker Service Started.");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        registration.unregister();
        System.out.println("Accommodation Cost Tracker Service Stopped.");
    }
}
