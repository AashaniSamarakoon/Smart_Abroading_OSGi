package financial_planning_assistant;

import common.api.CurrencyExchangeService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

import java.util.Scanner;

public class Activator implements BundleActivator {
    private ServiceReference<CurrencyExchangeService> serviceReference;
    private static final Scanner scanner = new Scanner(System.in);
    
    // ANSI escape codes for colors
    private static final String BLUE = "\u001B[34m";
    private static final String GREEN = "\u001B[32m";
    private static final String RED = "\u001B[31m";
    private static final String CYAN = "\u001B[36m";
    private static final String RESET = "\u001B[0m"; // Reset color

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println(BLUE + "Financial Planning Assistant!" + RESET);

        // Get reference to CurrencyExchangeService
        serviceReference = context.getServiceReference(CurrencyExchangeService.class);

        if (serviceReference != null) {
            CurrencyExchangeService currencyService = context.getService(serviceReference);

            if (currencyService != null) {
                while (true) { // Allows multiple conversions in a single session
                    System.out.println("\n========================================");
                    System.out.println(CYAN + "Currency Conversion" + RESET);
                    System.out.println("========================================");
                    
                    try {
                        // Get user input
                        System.out.print(GREEN + "Enter amount in USD: " + RESET);
                        double amount = scanner.nextDouble();
                        scanner.nextLine(); // Consume newline

                        System.out.print(GREEN + "Enter target currency (e.g., LKR, GBP, INR): " + RESET);
                        String currency = scanner.nextLine().trim().toUpperCase();

                        // Get exchange rate and convert
                        double exchangeRate = currencyService.getExchangeRate(currency);
                        if (exchangeRate > 0) {
                            double convertedAmount = amount * exchangeRate;

                            System.out.println("\nConversion Successful!");
                            System.out.println("-------------------------------------------------");
                            System.out.printf("| %-10s | %-15s | %-10s |\n", "Amount", "Currency", "Exchange Rate");
                            System.out.println("-------------------------------------------------");
                            System.out.printf("| %-10.2f | %-15s | %-10.4f |\n", amount, currency, exchangeRate);
                            System.out.printf("| %-10s | %-15s | %-10.2f |\n", "Total", currency, convertedAmount);
                            System.out.println("-------------------------------------------------");
                        } else {
                            System.out.println(RED + "Invalid currency or exchange rate not available." + RESET);
                        }

                    } catch (Exception e) {
                        System.out.println(RED + "Invalid input! Please enter a valid number." + RESET);
                        scanner.nextLine(); // Clear buffer
                    }

                    // Ask user if they want another conversion
                    System.out.print(CYAN + "\n Do you want to convert another currency? (yes/no): " + RESET);
                    String choice = scanner.nextLine().trim().toLowerCase();
                    if (!choice.equals("yes")) {
                        break;
                    }
                }
            }
        } else {
            System.out.println(RED + "Currency Exchange Service not available." + RESET);
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println(RED + "Financial Planning Assistant Stopping..." + RESET);
        if (serviceReference != null) {
            context.ungetService(serviceReference);
        }
    }
}
