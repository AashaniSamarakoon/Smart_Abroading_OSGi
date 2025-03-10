package study.plan.manager;

import org.osgi.framework.BundleActivator;
import org.osgi.framework.BundleContext;
import org.osgi.framework.ServiceReference;
import common.api.UniversityAdmissionService;
import common.api.ScholarshipService;
import java.util.Scanner;

public class ServiceActivator implements BundleActivator {
    private ServiceReference<UniversityAdmissionService> admissionReference;
    private ServiceReference<ScholarshipService> scholarshipReference;
    private UniversityAdmissionService admissionService;
    private ScholarshipService scholarshipService;
    private volatile boolean running = true; 

    @Override
    public void start(BundleContext context) throws Exception {
        System.out.println("\nStudy Plan Manager: Attempting to retrieve required services.");

        admissionReference = context.getServiceReference(UniversityAdmissionService.class);
        scholarshipReference = context.getServiceReference(ScholarshipService.class);

        if (admissionReference != null) {
            admissionService = context.getService(admissionReference);
            System.out.println("\nFound University Admission Service.");
        } else {
            System.out.println("University Admission Service not found.");
        }

        if (scholarshipReference != null) {
            scholarshipService = context.getService(scholarshipReference);
            System.out.println("Found Scholarship Service.");
        } else {
            System.out.println("Scholarship Service not found.");
        }

        if (admissionService == null) {
            System.out.println("Required services not available.");
            return;
        }

        Scanner scanner = new Scanner(System.in);

        while (running) {
            System.out.print("\nEnter your student ID to check admission status (or type 'exit' to stop): ");
            String studentId = scanner.nextLine().trim();
            if (studentId.equalsIgnoreCase("exit")) {
                running = false;
                break;
            }

            String status = admissionService.getAdmissionStatus(studentId);
            System.out.println("Admission Status for " + studentId + ": " + status);

            if ("Invalid Student ID".equals(status)) {
                System.out.println("Please enter a valid student ID.");
                continue;
            }

            if (!"Accepted".equals(status)) {
                System.out.println("Application not accepted. Study plan cannot be created.");
                continue;
            }

            System.out.print("\nEnter your field of study to check available scholarships (or type 'exit' to stop): ");
            String field = scanner.nextLine().trim();
            if (field.equalsIgnoreCase("exit")) {
                running = false;
                break;
            }

            if (scholarshipService != null) {
                String[] scholarships = scholarshipService.getAvailableScholarships(field);
                System.out.println("Available Scholarships for " + field + ": " + String.join(", ", scholarships));
            } else {
                System.out.println("Scholarship Service is not available.");
            }
        }

        scanner.close();
    }

    @Override
    public void stop(BundleContext context) throws Exception {
        running = false; 
        if (admissionReference != null) {
            context.ungetService(admissionReference);
        }
        if (scholarshipReference != null) {
            context.ungetService(scholarshipReference);
        }
        System.out.println("Study Plan Manager Stopped.");
    }
}
