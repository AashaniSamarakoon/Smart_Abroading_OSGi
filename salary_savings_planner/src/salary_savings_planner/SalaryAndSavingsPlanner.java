package salary_savings_planner;

import common.api.CurrencyExchangeService;
import common.api.JobMarketService;
import org.osgi.framework.*;

public class SalaryAndSavingsPlanner implements BundleActivator {
    private ServiceReference<CurrencyExchangeService> currencyReference;
    private ServiceReference<JobMarketService> jobMarketReference;
    private CurrencyExchangeService currencyService;
    private JobMarketService jobMarketService;

    @Override
    public void start(BundleContext context) {
        System.out.println("*************************************************");
        System.out.println("    Starting Salary & Savings Planner   ");
        System.out.println("*************************************************");

        currencyReference = context.getServiceReference(CurrencyExchangeService.class);
        if (currencyReference != null) {
            currencyService = context.getService(currencyReference);
            System.out.println("CurrencyExchangeService found.");
        } else {
            System.out.println("CurrencyExchangeService not available.");
        }

        jobMarketReference = context.getServiceReference(JobMarketService.class);
        if (jobMarketReference != null) {
            jobMarketService = context.getService(jobMarketReference);
            System.out.println("JobMarketService found.");
        } else {
            System.out.println("JobMarketService not available.");
        }

        if (currencyService != null && jobMarketService != null) {

            String country = "Canada";
            String currency = "CAD";

            String[] jobs = jobMarketService.getAvailableJobs(country);
            if (jobs.length == 0 || (jobs.length == 1 && jobs[0].equals("No job listings available"))) {
                System.out.println("No jobs available in " + country);
                return;
            }

            double exchangeRate = currencyService.getExchangeRate(currency);
            if (exchangeRate == 0.0) {
                System.out.println("Exchange rate for " + currency + " not available.");
                return;
            }

            double averageSalary = 5000.0;
            double convertedSalary = averageSalary * exchangeRate;

            System.out.println("\n Salary & Savings Planner Overview:");
            System.out.println("-------------------------------------------------");
            System.out.println("Jobs in " + country + ": ");
            System.out.println("   ➡️ " + String.join("\n   ➡️ ", jobs));
            System.out.println("-------------------------------------------------");
            System.out.println("Exchange Rate: 1 " + currency + " = " + exchangeRate + " USD");
            System.out.println("-------------------------------------------------");
            System.out.println("Estimated Salary: " + convertedSalary + " USD (Converted)");
            System.out.println("-------------------------------------------------");
        } else {
            System.out.println("Salary & Savings Planner: Required services not available.");
        }

        System.out.println("*************************************************");
        System.out.println("     Track your salary & savings journey!   ");
        System.out.println("*************************************************");
    }

    @Override
    public void stop(BundleContext context) {
        if (currencyReference != null) {
            context.ungetService(currencyReference);
        }
        if (jobMarketReference != null) {
            context.ungetService(jobMarketReference);
        }
        System.out.println("Salary & Savings Planner stopped.");
    }
}
