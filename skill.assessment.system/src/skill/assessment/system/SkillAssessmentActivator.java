package skill.assessment.system;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceRegistration;
import common.api.SkillAssessmentService;

public class SkillAssessmentActivator implements BundleActivator {
    private ServiceRegistration<?> registration;

    @Override
    public void start(BundleContext context) throws Exception {
        SkillAssessmentService service = new SkillAssessmentSystem();
        registration = context.registerService(SkillAssessmentService.class.getName(), service, null);
        System.out.println("Skill Assessment System Started.");
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        registration.unregister();
        System.out.println("Skill Assessment System Stopped.");
    }
}
