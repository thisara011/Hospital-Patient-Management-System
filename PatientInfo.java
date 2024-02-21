
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;

// Class representing a patient
class Patient {
    static int nextPatientId = 4501; // Auto-incrementing patient ID
    int patientId;
    String patientName;
    int patientAge;
    String patientGender;
    String patientContact;
    String patientMedicalHistory;

    // Constructor
    public Patient(String patientName, int patientAge, String patientGender, String patientContact, String patientMedicalHistory) {
        this.patientId = nextPatientId++; // Assigning the next available patient ID and then incrementing it
        this.patientName = patientName;
        this.patientAge = patientAge;
        this.patientGender = patientGender;
        this.patientContact = patientContact;
        this.patientMedicalHistory = patientMedicalHistory;
    }
}

// Class representing a list of patients
class PatientList {
    Node head; // Head of the doubly linked list

    // Node class representing an element in the linked list
    static class Node {
        Patient patient; // Patient data
        Node next; // Reference to the next node
        Node prev; // Reference to the previous node

        // Constructor
        public Node(Patient patient) {
            this.patient = patient;
            this.next = null;
            this.prev = null;
        }
    }

    // Method to insert a new patient record at the end of the linked list
    public void insertPatient(Patient patient) {
        Node newNode = new Node(patient);
        if (head == null) {
            head = newNode; // If the list is empty, make the new node the head
        } else {
            Node last = head;
            while (last.next != null) {
                last = last.next; // Traverse to the last node
            }
            last.next = newNode; // Add the new node after the last node
            newNode.prev = last; // Set the previous reference of the new node
        }
    }

    // Method to delete a patient record by name
    public void deletePatientByName(String patientName) {
        Node current = head;
        while (current != null) {
            if (current.patient.patientName.equals(patientName)) {
                deleteNode(current); // Delete the node containing the patient
                System.out.println("Patient with name " + patientName + " deleted successfully.");
                return; // Exit the method after deleting the patient
            }
            current = current.next;
        }
        System.out.println("Patient with name " + patientName + " not found.");
    }

    // Method to delete a patient record by ID
    public void deletePatientById(int patientId) {
        Node current = head;
        while (current != null) {
            if (current.patient.patientId == patientId) {
                deleteNode(current); // Delete the node containing the patient
                System.out.println("Patient with id" + patientId + " deleted successfully.");
                return; // Exit the method after deleting the patient
            }
            current = current.next;
        }
        System.out.println("Patient with ID " + patientId + " not found.");
    }

    // Method to update patient details by name
    public void updatePatientByName(String patientName, String newPatientName, int newAge, String newGender, String newContact) {
        Node current = head;
        boolean found = false;
        while (current != null) {
            if (current.patient.patientName.equals(patientName)) {
                current.patient.patientName = newPatientName;
                current.patient.patientAge = newAge;
                current.patient.patientGender = newGender;
                current.patient.patientContact = newContact;
                found = true;
                System.out.println("Patient details updated successfully.");
                return;
            }
            current = current.next;
        }
        if (!found) {
            System.out.println("Patient with name " + patientName + " not found.");
        }
    }

    // Method to update patient medical history by name
    public void updateMedicalHistoryByName(String patientName, String newMedicalHistory) {
        Node current = head;
        boolean found = false;
        while (current != null) {
            if (current.patient.patientName.equals(patientName)) {
                current.patient.patientMedicalHistory = newMedicalHistory;
                found = true;
                System.out.println("Medical history updated successfully.");
                return;
            }
            current = current.next;
        }
        if (!found) {
            System.out.println("Patient with name " + patientName + " not found.");
        }
    }

    // Method to delete the given node from the linked list
    private void deleteNode(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next; // If the node to be deleted is the head, update the head
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        }
    }

    // Method to retrieve and display all patient records in the linked list
    public void displayPatients() {
        Node current = head;
        while (current != null) {
            System.out.println("ID: " + current.patient.patientId + ", Name: " + current.patient.patientName +
                    ", Age: " + current.patient.patientAge +
                    ", Gender: " + current.patient.patientGender +
                    ", Contact: " + current.patient.patientContact +
                    ", Medical History: " + current.patient.patientMedicalHistory);
            current = current.next; // Move to the next node
        }
        System.out.println("No Patients");
    }

    // Method to search for a patient by contact
    public void searchPatientByContact(String contact) {
        List<Patient> foundPatients = new ArrayList<>();
        Node current = head;
        while (current != null) {
            if (current.patient.patientContact.equals(contact)) {
                foundPatients.add(current.patient); // Add the patient to the list of found patients
            }
            current = current.next;
        }
        if (!foundPatients.isEmpty()) {
            System.out.println("Patients with contact " + contact + ":");
            for (Patient patient : foundPatients) {
                System.out.println("ID: " + patient.patientId + ", Name: " + patient.patientName +
                        ", Age: " + patient.patientAge +
                        ", Gender: " + patient.patientGender +
                        ", Contact: " + patient.patientContact +
                        ", Medical History: " + patient.patientMedicalHistory);
            }
        } else {
            System.out.println("No patients found with contact " + contact);
        }
    }

    // Method to search for a patient by Name
    public void searchPatientByName(String patientName) {
        Node current = head;
        boolean found = false;
        while (current != null) {
            if (current.patient.patientName.equals(patientName)) {
                System.out.println("Patient found:");
                System.out.println("ID: " + current.patient.patientId + ", Name: " + current.patient.patientName +
                        ", Age: " + current.patient.patientAge +
                        ", Gender: " + current.patient.patientGender +
                        ", Contact: " + current.patient.patientContact +
                        ", Medical History: " + current.patient.patientMedicalHistory);
                found = true;
                break;
            }
            current = current.next;
        }
        if (!found) {
            System.out.println("Patient with name " + patientName + " not found.");
        }
    }

}

// Main class for running the patient information management system
public class PatientInfo {
    public static void PatientInfoManagement() {
        PatientList patientList = new PatientList(); // Create a new patient list
        Scanner scanner = new Scanner(System.in); // Scanner for user input

        while (true) {
            try {
                // Displaying the main menu options
                System.out.println("\nMenu:");
                System.out.println("1. Add Patient");
                System.out.println("2. Delete Patient by Name");
                System.out.println("3. Delete Patient by ID");
                System.out.println("4. Update Patient Name, Age, Gender, Contact by Name");
                System.out.println("5. Update Patient Medical History by Name");
                System.out.println("6. Search Patient by Name");
                System.out.println("7. Search Patient by Contact");
                System.out.println("8. Display All Patients");
                System.out.println("9. main menu");
                System.out.println("10. exit");
                System.out.print("Enter your choice: ");
                int choice = scanner.nextInt();
                scanner.nextLine(); // Consume newline

                switch (choice) {
                    case 1:
                        // Adding a new patient
                        System.out.println("Next available patient ID: " + Patient.nextPatientId);
                        System.out.println("\nEnter patient details (Name, Age, Gender, Contact, Medical History):");
                        System.out.print("Name: ");
                        String name = scanner.nextLine();
                        System.out.print("Age: ");
                        int age = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Gender: ");
                        String gender = scanner.nextLine();
                        System.out.print("Contact: ");
                        String contact = scanner.nextLine();
                        System.out.print("Medical History: ");
                        String medicalHistory = scanner.nextLine();
                        patientList.insertPatient(new Patient(name, age, gender, contact, medicalHistory));
                        System.out.println("Patient added successfully.");
                        break;

                    case 2:
                        // Deleting a patient by name
                        System.out.print("\nEnter the name of the patient to delete: ");
                        String nameToDelete = scanner.nextLine();
                        patientList.deletePatientByName(nameToDelete);
                        break;

                    case 3:
                        // Deleting a patient by ID
                        System.out.print("\nEnter the ID of the patient to delete: ");
                        int idToDelete = scanner.nextInt();
                        patientList.deletePatientById(idToDelete);
                        break;

                    case 4:
                        // Updating patient details by name
                        System.out.print("\nEnter the name of the patient to update: ");
                        String nameToUpdate = scanner.nextLine();
                        System.out.print("Enter new name: ");
                        String newName = scanner.nextLine();
                        System.out.print("Enter new age: ");
                        int newAge = scanner.nextInt();
                        scanner.nextLine(); // Consume newline
                        System.out.print("Enter new gender: ");
                        String newGender = scanner.nextLine();
                        System.out.print("Enter new contact: ");
                        String newContact = scanner.nextLine();
                        patientList.updatePatientByName(nameToUpdate, newName, newAge, newGender, newContact);
                        break;

                    case 5:
                        // Updating patient medical history by name
                        System.out.print("\nEnter the name of the patient to update medical history: ");
                        String nameToSearch = scanner.nextLine();
                        System.out.print("Enter new medical history: ");
                        String newMedicalHistory = scanner.nextLine();
                        patientList.updateMedicalHistoryByName(nameToSearch, newMedicalHistory);
                        break;

                    case 6:
                        // Searching for a patient by name
                        System.out.print("\nEnter the name of the patient to search: ");
                        String nameToSearchs = scanner.nextLine();
                        patientList.searchPatientByName(nameToSearchs);
                        break;

                    case 7:
                        // Searching for a patient by contact
                        System.out.print("\nEnter the contact of the patient to search: ");
                        String contactToSearch = scanner.nextLine();
                        patientList.searchPatientByContact(contactToSearch);
                        break;

                    case 8:
                        // Displaying all patients
                        patientList.displayPatients();
                        break;

                    case 9:
                        // enter main menu
                        Main main = new Main();
                        main.main();
                        break;
                        
                    case 10:
                        // Exiting the program
                        System.out.println("Exiting the program.");
                        scanner.close();
                        System.exit(0);
                        break;

                    default:
                        System.out.println("Invalid choice. Please enter a valid option.");
                }
            } catch (InputMismatchException e) {
                System.out.println("Input mismatch. Please enter a valid choice.");
                scanner.nextLine(); // Clear the input buffer
            }
        }
    }
}
