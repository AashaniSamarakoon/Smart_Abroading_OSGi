package skill.assessment.system;

import common.api.SkillAssessmentService;
import java.util.HashMap;
import java.util.Map;

public class SkillAssessmentSystem implements SkillAssessmentService {
    private final Map<String, String[]> skillRecommendations = new HashMap<>();

    public SkillAssessmentSystem() {
        skillRecommendations.put("Software Engineer", new String[]{"Java", "Spring Boot", "Docker"});
        skillRecommendations.put("Data Analyst", new String[]{"SQL", "Python", "Power BI"});
        skillRecommendations.put("Cybersecurity Specialist", new String[]{"Ethical Hacking", "Network Security", "Penetration Testing"});
        skillRecommendations.put("Mechanical Engineer", new String[]{"SolidWorks", "AutoCAD", "Thermodynamics"});
        skillRecommendations.put("Marketing Specialist", new String[]{"SEO", "Google Ads", "Social Media Marketing"});
    }

    @Override
    public String[] getSkillRecommendations(String profession) {
        return skillRecommendations.getOrDefault(profession, new String[]{"No recommendations available for this profession."});
    }
}
