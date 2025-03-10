package course.recommendation.system;

import common.api.SkillAssessmentService;
import org.osgi.framework.*;

import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;

public class Activator implements BundleActivator {
    private ServiceReference<SkillAssessmentService> skillReference;
    private SkillAssessmentService skillService;
    private volatile boolean running = true;  

    private final Map<String, String[]> courses = new HashMap<>();
    private final Map<String, String[]> universityCourses = new HashMap<>();

    public Activator() {
        courses.put("Java", new String[]{"Java Programming - Udemy", "Advanced Java - Coursera"});
        courses.put("SQL", new String[]{"Database Management - edX", "SQL Fundamentals - Udacity"});
        courses.put("Python", new String[]{"Python for Data Science - DataCamp", "Machine Learning with Python - Coursera"});
        courses.put("SEO", new String[]{"SEO Mastery - Udemy", "Google Digital Garage"});
        courses.put("AutoCAD", new String[]{"AutoCAD Essentials - LinkedIn Learning", "SolidWorks Basics - Coursera"});

        universityCourses.put("Software Engineer", new String[]{"BSc in Software Engineering", "BSc in Computer Science"});
        universityCourses.put("Data Analyst", new String[]{"BSc in Data Science", "BSc in Business Analytics"});
        universityCourses.put("Cybersecurity Specialist", new String[]{"BSc in Cybersecurity", "BSc in Information Security"});
        universityCourses.put("Mechanical Engineer", new String[]{"BSc in Mechanical Engineering", "BSc in Robotics"});
        universityCourses.put("Marketing Specialist", new String[]{"BBA in Marketing", "BSc in Digital Marketing"});
  
    }

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("Course Recommendation System: Attempting to retrieve required services.");

        skillReference = context.getServiceReference(SkillAssessmentService.class);

        if (skillReference != null) {
            skillService = context.getService(skillReference);
            System.out.println("Found Skill Assessment Service.");
        } else {
            System.out.println("Skill Assessment Service not found.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        while (running) {
            System.out.print("\nEnter your profession to get skill recommendations (or type 'exit' to stop): ");
            String profession = scanner.nextLine().trim();
            if (profession.equalsIgnoreCase("exit")) {
                running = false;
                break;
            }

            String[] skills = skillService.getSkillRecommendations(profession);
            System.out.println("Recommended Skills for " + profession + ": " + String.join(", ", skills));

           
            if (universityCourses.containsKey(profession)) {
                System.out.println("\nRecommended University Courses for " + profession + ": " + String.join(", ", universityCourses.get(profession)));
            } else {
                System.out.println("No university courses found for this profession.");
            }
            
        }

        scanner.close();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        running = false;
        if (skillReference != null) {
            context.ungetService(skillReference);
        }
        System.out.println("Course Recommendation System Stopped.");
    }
}
