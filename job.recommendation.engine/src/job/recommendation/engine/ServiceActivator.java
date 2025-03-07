package job.recommendation.engine;

import common.api.JobMarketService;
import common.api.SkillAssessmentService;
import org.osgi.framework.*;

public class ServiceActivator implements BundleActivator {
    private ServiceReference<JobMarketService> jobMarketReference;
    private ServiceReference<SkillAssessmentService> skillReference;
    private JobMarketService jobMarketService;
    private SkillAssessmentService skillService;

    @Override
    public void start(BundleContext context) throws Exception {
        jobMarketReference = context.getServiceReference(JobMarketService.class);
        skillReference = context.getServiceReference(SkillAssessmentService.class);

        if (jobMarketReference != null && skillReference != null) {
            jobMarketService = context.getService(jobMarketReference);
            skillService = context.getService(skillReference);

            String profession = "Software Engineer";
            String[] skills = skillService.getSkillRecommendations(profession);
            String[] jobs = jobMarketService.getAvailableJobs("Canada");

            System.out.println("Job Recommendation Engine:");
            System.out.println("Recommended Skills: " + String.join(", ", skills));
            System.out.println("Matching Jobs: " + String.join(", ", jobs));
        } else {
            System.out.println("Job Recommendation Engine: Required services not available.");
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        System.out.println("Job Recommendation Engine Stopped.");
    }
}
