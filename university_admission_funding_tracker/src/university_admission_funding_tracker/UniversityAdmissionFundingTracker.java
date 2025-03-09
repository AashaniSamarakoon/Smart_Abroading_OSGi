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
        // Get Scholarship Service
        scholarshipReference = context.getServiceReference(ScholarshipService.class);
        if (scholarshipReference != null) {
            scholarshipService = (ScholarshipService) context.getService(scholarshipReference);
        }

        // Get University Admission Service
        admissionReference = context.getServiceReference(UniversityAdmissionService.class);
        if (admissionReference != null) {
            admissionService = (UniversityAdmissionService) context.getService(admissionReference);
        }

        if (scholarshipService != null && admissionService != null) {
            String admissionStatus = admissionService.getAdmissionStatus("S12345");
            String[] scholarships = scholarshipService.getAvailableScholarships("Computer Science");

            System.out.println("University Admission Tracker: Admission Status - " + admissionStatus);
            System.out.println("Available Scholarships: " + String.join(", ", scholarships));
        } else {
            System.out.println("University Admission Tracker: Required services not available.");
        }
    }

    @Override
    public void stop(BundleContext context) {
        if (scholarshipReference != null) context.ungetService(scholarshipReference);
        if (admissionReference != null) context.ungetService(admissionReference);
    }

}
