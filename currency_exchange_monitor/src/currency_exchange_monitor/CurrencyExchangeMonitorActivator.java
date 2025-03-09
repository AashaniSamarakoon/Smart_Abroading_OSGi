package currency_exchange_monitor;

import common.api.CurrencyExchangeService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;

public class CurrencyExchangeMonitorActivator implements BundleActivator {

	private ServiceRegistration<?> registration;

	public void start(BundleContext context) throws Exception {
		CurrencyExchangeService service = new CurrencyExchangeMonitorPublisher();
        registration = context.registerService(CurrencyExchangeService.class.getName(), service, null);
        System.out.println("Currency Exchange Monitor Started with Real-Time Rates!");
	}

	public void stop(BundleContext context) {
        registration.unregister();
        System.out.println("Currency Exchange Monitor Stopped.");
    }

}
