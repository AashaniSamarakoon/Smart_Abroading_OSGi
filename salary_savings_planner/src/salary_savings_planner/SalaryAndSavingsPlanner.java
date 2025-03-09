package salary_savings_planner;

import common.api.CurrencyExchangeService;
import common.api.JobMarketService;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class SalaryAndSavingsPlanner implements BundleActivator {

	private ServiceReference<CurrencyExchangeService> currencyReference;
    private ServiceReference<JobMarketService> jobMarketReference;
    private CurrencyExchangeService currencyService;
    private JobMarketService jobMarketService;

    @Override
    public void start(BundleContext context) {
        currencyReference = context.getServiceReference(CurrencyExchangeService.class);
        if (currencyReference != null) {
            currencyService = (CurrencyExchangeService) context.getService(currencyReference);
        }

        jobMarketReference = context.getServiceReference(JobMarketService.class);
        if (jobMarketReference != null) {
            jobMarketService = (JobMarketService) context.getService(jobMarketReference);
        }

        if (currencyService != null && jobMarketService != null) {
            String[] jobs = jobMarketService.getAvailableJobs("Canada");
            double exchangeRate = currencyService.getExchangeRate("CAD");

            System.out.println("Salary & Savings Planner: Jobs in Canada - " + String.join(", ", jobs));
            System.out.println("Exchange Rate for CAD: " + exchangeRate);
        } else {
            System.out.println("Salary & Savings Planner: Required services not available.");
        }
    }

    @Override
    public void stop(BundleContext context) {
        if (currencyReference != null) context.ungetService(currencyReference);
        if (jobMarketReference != null) context.ungetService(jobMarketReference);
    }

}
