import java.util.Scanner;
import patientinfo.PatientInfo;
import medicalrecord.MRM;
import appointmentsch.Appointmentsch;

public class Main {
    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your job number:");
        System.out.println("1. Patient Information Management");
        System.out.println("2. Medical Report Management");
        System.out.println("3. Patient Appointment Management");

        int choice = scanner.nextInt();

        switch (choice) {
            case 1:

                PatientInfo.PatientInfoManagement();
                break;
            case 2:
                MRM.MedicalReportManagement();
                break;
            case 3:
                Appointmentsch.PatientAppoimentManagment();
                break;
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
        }
        
        scanner.close();
    }
    
}
