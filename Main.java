import java.util.Scanner;

public class Main {
    

    public  void main() {
        Scanner scanner = new Scanner(System.in);

        System.out.println("Enter your job number:");
        System.out.println("1. Patient Information Management");
        System.out.println("2. Medical Report Management");
        System.out.println("3. Patient Appointment Management");
        System.out.println("4. Billing and Payment Processing");
        System.out.println("5. Exit");

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
                case 4:
                billing.Billing();
                break;
                case 5:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;
            default:
                System.out.println("Invalid choice. Please enter a valid option.");
        }
        
        scanner.close();
    }
    
}
