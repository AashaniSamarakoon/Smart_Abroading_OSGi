package currency_exchange_monitor;

import java.util.HashMap;
import java.util.Map;

import common.api.CurrencyExchangeService;

public class CurrencyExchangeMonitorPublisher implements CurrencyExchangeService {
	private final Map<String, Double> exchangeRates = new HashMap<>();

    public CurrencyExchangeMonitorPublisher() {
        exchangeRates.put("USD", 1.0);
        exchangeRates.put("EUR", 0.92);
        exchangeRates.put("CAD", 1.36);
    }

	@Override
	public double getExchangeRate(String currency) {
		return exchangeRates.getOrDefault(currency, 0.0);
	}
}
