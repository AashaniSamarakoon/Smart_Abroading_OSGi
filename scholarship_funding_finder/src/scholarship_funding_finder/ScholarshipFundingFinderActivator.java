package scholarship_funding_finder;

import common.api.ScholarshipService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class ScholarshipFundingFinderActivator implements BundleActivator {
	private ServiceRegistration<?> registration;

	public void start(BundleContext context) throws Exception {
		ScholarshipService service = new ScholarshipFundingFinderPublisher();
        registration = context.registerService(ScholarshipService.class.getName(), service, null);
        System.out.println("Scholarship Funding Finder Started.");
	}

	public void stop(BundleContext context) throws Exception {
		registration.unregister();
        System.out.println("Scholarship Funding Finder Stopped.");
	}

}
