package study.plan.manager;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import common.api.UniversityAdmissionService;

public class ServiceActivator implements BundleActivator {
	 private ServiceReference<UniversityAdmissionService> reference;
	    private UniversityAdmissionService service;

	
	    @Override
	    public void start(BundleContext context) throws Exception {
	        reference = context.getServiceReference(UniversityAdmissionService.class);
	        if (reference != null) {
	            service = context.getService(reference);
	            if (service != null) {
	                String status = service.getAdmissionStatus("S12345");
	                System.out.println("Study Plan Manager: Admission status is " + status + ". Preparing study plan...");
	            } else {
	                System.out.println("Study Plan Manager: Service is unavailable. Retrying...");
	            }
	        } else {
	            System.out.println("Study Plan Manager: No UniversityAdmissionService available.");
	        }
	    }

	    @Override
	    public void stop(BundleContext context) throws Exception {
	        System.out.println("Study Plan Manager is shutting down...");
	        
	        if (reference != null) {
	            System.out.println("Study Plan Manager Stopped.");

	            context.ungetService(reference);
	        } else {
	            System.out.println("Study Plan Manager: No service reference to unregister.");
	        }
	    }


}
