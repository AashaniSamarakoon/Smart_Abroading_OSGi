package currency_exchange_monitor;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import common.api.CurrencyExchangeService;

public class CurrencyExchangeMonitorPublisher implements CurrencyExchangeService {
	private static final String API_URL = "https://openexchangerates.org/api/latest.json?app_id=a5e629e3b3204af3a042b2fd046b1433";
	
	@Override
    public double getExchangeRate(String currency) {
        try {
        	URL url = URI.create(API_URL).toURL();
            HttpURLConnection conn = (HttpURLConnection) url.openConnection();
            conn.setRequestMethod("GET");

            BufferedReader reader = new BufferedReader(new InputStreamReader(conn.getInputStream()));
            StringBuilder response = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                response.append(line);
            }
            reader.close();


            String jsonResponse = response.toString();


            String searchKey = "\"" + currency + "\":";
            int startIndex = jsonResponse.indexOf(searchKey);
            if (startIndex == -1) {
                System.out.println("Currency not found: " + currency);
                return 0.0;
            }


            startIndex += searchKey.length();
            int endIndex = jsonResponse.indexOf(",", startIndex);
            if (endIndex == -1) {
                endIndex = jsonResponse.indexOf("}", startIndex);
            }

            String exchangeRateStr = jsonResponse.substring(startIndex, endIndex).trim();
            return Double.parseDouble(exchangeRateStr);

        } catch (Exception e) {
            System.out.println("Error fetching exchange rate: " + e.getMessage());
            return 0.0;
        }
    }
}
