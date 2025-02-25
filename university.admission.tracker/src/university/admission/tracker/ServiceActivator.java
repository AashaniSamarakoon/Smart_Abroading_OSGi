package university.admission.tracker;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

import common.api.UniversityAdmissionService;

public class ServiceActivator implements BundleActivator {

    private ServiceRegistration<?> registration;


	public void start(BundleContext context) throws Exception {
        System.out.println("University Admission Tracker Started.");

		 UniversityAdmissionService service = new UniversityAdmissionTracker();
	        registration = context.registerService(UniversityAdmissionService.class.getName(), service, null);
	}

	public void stop(BundleContext context) throws Exception {
        System.out.println("University Admission Tracker Stop.");
        registration.unregister();

	}

}
