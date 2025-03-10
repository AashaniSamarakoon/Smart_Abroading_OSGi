package job.recommendation.engine;

import common.api.JobMarketService;
import common.api.SkillAssessmentService;
import org.osgi.framework.*;
import java.util.Scanner;

public class ServiceActivator implements BundleActivator {
    private ServiceReference<JobMarketService> jobMarketReference;
    private ServiceReference<SkillAssessmentService> skillReference;
    private JobMarketService jobMarketService;
    private SkillAssessmentService skillService;
    
    private volatile boolean running = true;  

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Job Recommendation Engine: Attempting to retrieve required services...\n");

        jobMarketReference = context.getServiceReference(JobMarketService.class);
        skillReference = context.getServiceReference(SkillAssessmentService.class);

        if (jobMarketReference != null) {
            jobMarketService = context.getService(jobMarketReference);
            System.out.println("Found Job Market Service.");
        } else {
            System.out.println("Job Market Service not found!");
        }

        if (skillReference != null) {
            skillService = context.getService(skillReference);
            System.out.println("Found Skill Assessment Service.\n");
        } else {
            System.out.println("Skill Assessment Service not found!");
        }

        if (jobMarketService == null || skillService == null) {
            System.out.println("Job Recommendation Engine: Required services not available.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        while (running) {
            System.out.print("\nEnter a country to find jobs (or type 'exit' to stop): ");
            String country = scanner.nextLine().trim();
            if (country.equalsIgnoreCase("exit")) {
                running = false;
                break;
            }

            String[] jobs = jobMarketService.getAvailableJobs(country);
            System.out.println("Matching Jobs in " + country + ": " + String.join(", ", jobs));
            
            System.out.print("\nEnter a profession for skill recommendations (or type 'exit' to stop): ");
            String profession = scanner.nextLine().trim();
            if (profession.equalsIgnoreCase("exit")) {
                running = false;
                break;
            }
            
            String[] skills = skillService.getSkillRecommendations(profession);
            System.out.println("Recommended Skills for " + profession + ": " + String.join(", ", skills));
        }

        scanner.close();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        running = false; 
        if (jobMarketReference != null) {
            context.ungetService(jobMarketReference);
        }
        if (skillReference != null) {
            context.ungetService(skillReference);
        }
        System.out.println("Job Recommendation Engine Stopped.");
    }
}
