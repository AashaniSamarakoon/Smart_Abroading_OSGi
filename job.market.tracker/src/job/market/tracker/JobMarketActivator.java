package job.market.tracker;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import common.api.JobMarketService;

public class JobMarketActivator implements BundleActivator {
    private ServiceRegistration<?> registration;

    @Override
    public void start(BundleContext context) throws Exception {
        JobMarketService service = new JobMarketTracker();
        registration = context.registerService(JobMarketService.class.getName(), service, null);
        System.out.println("Job Market Tracker Started.");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        registration.unregister();
        System.out.println("Job Market Tracker Stopped.");
    }
}
