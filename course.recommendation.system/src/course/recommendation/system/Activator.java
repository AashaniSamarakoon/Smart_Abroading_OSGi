package course.recommendation.system;

import common.api.SkillAssessmentService;
import org.osgi.framework.*;

import java.util.HashMap;
import java.util.Map;

public class Activator implements BundleActivator {
    private ServiceReference<SkillAssessmentService> skillReference;
    private SkillAssessmentService skillService;

    private final Map<String, String[]> courses = new HashMap<>();

    public Activator() {
        courses.put("Java", new String[]{"Java Programming - Udemy", "Advanced Java - Coursera"});
        courses.put("SQL", new String[]{"Database Management - edX", "SQL Fundamentals - Udacity"});
        courses.put("Python", new String[]{"Python for Data Science - DataCamp", "Machine Learning with Python - Coursera"});
    }

    @Override
    public void start(BundleContext context) throws Exception {
        skillReference = context.getServiceReference(SkillAssessmentService.class);
        if (skillReference != null) {
            skillService = context.getService(skillReference);

            String profession = "Software Engineer"; // Example profession
            String[] skills = skillService.getSkillRecommendations(profession);

            System.out.println("Course Recommendation System:");
            for (String skill : skills) {
                System.out.println("Skill: " + skill);
                if (courses.containsKey(skill)) {
                    System.out.println("Recommended Courses: " + String.join(", ", courses.get(skill)));
                } else {
                    System.out.println("No courses found for this skill.");
                }
            }
        } else {
            System.out.println("Course Recommendation System: Skill assessment service not available.");
        }
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        if (skillReference != null) {
            context.ungetService(skillReference);
        }
        System.out.println("Course Recommendation System Stopped.");
    }
}