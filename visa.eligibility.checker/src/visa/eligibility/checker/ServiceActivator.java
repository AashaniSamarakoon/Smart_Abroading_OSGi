package visa.eligibility.checker;

import common.api.JobMarketService;
import common.api.VisaProcessingService;
import org.osgi.framework.*;

public class ServiceActivator implements BundleActivator {
    private ServiceReference<JobMarketService> jobMarketReference;
    private ServiceReference<VisaProcessingService> visaReference;
    private JobMarketService jobMarketService;
    private VisaProcessingService visaService;

    @Override
    public void start(BundleContext context) throws Exception {
        jobMarketReference = context.getServiceReference(JobMarketService.class);
        visaReference = context.getServiceReference(VisaProcessingService.class);

        if (jobMarketReference != null && visaReference != null) {
            jobMarketService = context.getService(jobMarketReference);
            visaService = context.getService(visaReference);

            String country = "Canada";
            String[] jobs = jobMarketService.getAvailableJobs(country);
            String visaStatus = visaService.getVisaStatus("P12345678");

            if (visaStatus.equals("Approved") && jobs.length > 0) {
                System.out.println("Visa Eligibility Checker: You are eligible to work in " + country);
            } else {
                System.out.println("Visa Eligibility Checker: Work visa not approved or no jobs available.");
            }
        } else {
            System.out.println("Visa Eligibility Checker: Required services not available.");
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Visa Eligibility Checker Stopped.");
    }
}
