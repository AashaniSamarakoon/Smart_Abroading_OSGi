package university_admission_funding_tracker;

import common.api.ScholarshipService;
import common.api.UniversityAdmissionService;
import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;

public class UniversityAdmissionFundingTracker implements BundleActivator {

    private ServiceReference<ScholarshipService> scholarshipReference;
    private ServiceReference<UniversityAdmissionService> admissionReference;
    private ScholarshipService scholarshipService;
    private UniversityAdmissionService admissionService;

    @Override
    public void start(BundleContext context) {
        System.out.println("*************************************************");
        System.out.println("    University Admission & Scholarship Tracker   ");
        System.out.println("*************************************************");

        // Get Scholarship Service
        scholarshipReference = context.getServiceReference(ScholarshipService.class);
        if (scholarshipReference != null) {
            scholarshipService = (ScholarshipService) context.getService(scholarshipReference);
            System.out.println("Scholarship Service found.");
        } else {
            System.out.println("Scholarship Service not available.");
        }

        // Get University Admission Service
        admissionReference = context.getServiceReference(UniversityAdmissionService.class);
        if (admissionReference != null) {
            admissionService = (UniversityAdmissionService) context.getService(admissionReference);
            System.out.println("University Admission Service found.");
        } else {
            System.out.println("University Admission Service not available.");
        }

        if (scholarshipService != null && admissionService != null) {
            String admissionStatus = admissionService.getAdmissionStatus("S12345");
            String[] scholarships = scholarshipService.getAvailableScholarships("Computer Science");

            System.out.println("\n Admission & Scholarships Overview:");
            System.out.println("-------------------------------------------------");
            System.out.println("Admission Status for Student ID S12345: " + admissionStatus);
            System.out.println("-------------------------------------------------");
            System.out.println("Available Scholarships for Computer Science:");
            if (scholarships.length > 0) {
                for (String scholarship : scholarships) {
                    System.out.println("  ➡️ " + scholarship);
                }
            } else {
                System.out.println("No scholarships available for this field.");
            }
            System.out.println("-------------------------------------------------");
        } else {
            System.out.println("University Admission & Scholarship Tracker: Required services not available.");
        }

        System.out.println("*************************************************");
        System.out.println("    Track your admission & funding journey!   ");
        System.out.println("*************************************************");
    }

    @Override
    public void stop(BundleContext context) {
        if (scholarshipReference != null) context.ungetService(scholarshipReference);
        if (admissionReference != null) context.ungetService(admissionReference);
        System.out.println("University Admission & Scholarship Tracker stopped.");
    }
}
