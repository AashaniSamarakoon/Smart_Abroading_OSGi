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
	            String status = service.getAdmissionStatus("S12345");

	            if ("Accepted".equals(status)) {
	                System.out.println("Study Plan Manager: Admission accepted! Preparing study plan...");
	            } else if ("Pending".equals(status)) {
	                System.out.println("Study Plan Manager: Application still pending.");
	            } else {
	                System.out.println("Study Plan Manager: Application rejected. Consider alternative options.");
	            }
	        } else {
	            System.out.println("Study Plan Manager: No UniversityAdmissionService available.");
	        }
	    }

	    @Override
	    public void stop(BundleContext context) throws Exception {
	        System.out.println("Study Plan Manager is shutting down...");
	        
	        if (reference != null) {
	            context.ungetService(reference);
	            System.out.println("Study Plan Manager Stopped.");

	        } else {
	            System.out.println("Study Plan Manager: No service reference to unregister.");
	        }
	    }


}
