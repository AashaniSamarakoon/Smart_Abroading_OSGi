package travel_advisory_system;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import java.util.HashMap;
import java.util.Map;

public class TravelAdvisoryServiceImpl implements BundleActivator, TravelAdvisoryService {
    private ServiceRegistration<?> registration;
    private Map<String, String> advisories = new HashMap<>();

    @Override
    public void start(BundleContext context) throws Exception {
        advisories.put("USA", "Low risk: Exercise normal precautions.");
        advisories.put("France", "Medium risk: Protests and strikes expected.");
        advisories.put("Japan", "Low risk: Safe to travel.");
        advisories.put("Afghanistan", "High risk: Avoid all travel.");

        registration = context.registerService(TravelAdvisoryService.class, this, null);
        System.out.println("TravelAdvisorySystem started...");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        registration.unregister();
        System.out.println("TravelAdvisorySystem stopped...");
    }

    @Override
    public String getTravelAdvisory(String country) {
        return advisories.getOrDefault(country, "No advisory available.");
    }
}