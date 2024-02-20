package patientinfo;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

class Patient {
    static int nextPatientId = 4567821; // Auto-incrementing patient ID
    int patientId;
    String name;
    int age;
    String gender;
    String contact;
    String medicalHistory;

    // Constructor
    public Patient(String name, int age, String gender, String contact, String medicalHistory) {
        this.patientId = nextPatientId++;
        this.name = name;
        this.age = age;
        this.gender = gender;
        this.contact = contact;
        this.medicalHistory = medicalHistory;
    }
}

class PatientList {
    Node head; // Head of the doubly linked list

    static class Node {
        Patient patient;
        Node next;
        Node prev;

        // Constructor
        public Node(Patient patient) {
            this.patient = patient;
            this.next = null;
            this.prev = null;
        }
    }

    // Insert a new patient record at the end of the linked list
    public void insertPatient(Patient patient) {
        Node newNode = new Node(patient);
        if (head == null) {
            head = newNode;
        } else {
            Node last = head;
            while (last.next != null) {
                last = last.next;
            }
            last.next = newNode;
            newNode.prev = last;
        }
    }

    // Delete a patient record by name
    public void deletePatientByName(String patientName) {
        Node current = head;
        while (current != null) {
            if (current.patient.name.equals(patientName)) {
                deleteNode(current);
                return; // Found and deleted the patient, exit the method
            }
            current = current.next;
        }
        System.out.println("Patient with name " + patientName + " not found.");
    }

    // Delete a patient record by ID
    public void deletePatientById(int patientId) {
        Node current = head;
        while (current != null) {
            if (current.patient.patientId == patientId) {
                deleteNode(current);
                return; // Found and deleted the patient, exit the method
            }
            current = current.next;
        }
        System.out.println("Patient with ID " + patientId + " not found.");
    }

    // Delete the given node from the linked list
    private void deleteNode(Node node) {
        if (node.prev != null) {
            node.prev.next = node.next;
        } else {
            head = node.next;
        }

        if (node.next != null) {
            node.next.prev = node.prev;
        }
    }

    // Retrieve and display all patient records in the linked list
    public void displayPatients() {
        Node current = head;
        while (current != null) {
            System.out.println("ID: " + current.patient.patientId + ", Name: " + current.patient.name +
                    ", Age: " + current.patient.age +
                    ", Gender: " + current.patient.gender +
                    ", Contact: " + current.patient.contact +
                    ", Medical History: " + current.patient.medicalHistory);
            current = current.next;
        }
    }

    // Search for a patient by name
    public void searchPatientByName(String patientName) {
        List<Patient> foundPatients = new ArrayList<>();
        Node current = head;
        while (current != null) {
            if (current.patient.name.equals(patientName)) {
                foundPatients.add(current.patient);
            }
            current = current.next;
        }
        if (!foundPatients.isEmpty()) {
            System.out.println("Patients with name " + patientName + ":");
            for (Patient patient : foundPatients) {
                System.out.println("ID: " + patient.patientId + ", Name: " + patient.name +
                        ", Age: " + patient.age +
                        ", Gender: " + patient.gender +
                        ", Contact: " + patient.contact +
                        ", Medical History: " + patient.medicalHistory);
            }
        } else {
            System.out.println("No patients found with name " + patientName);
        }
    }
}

public class PatientInfo {
    public static void main(String[] args) {
        PatientList patientList = new PatientList();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Patient");
            System.out.println("2. Delete Patient by Name");
            System.out.println("3. Delete Patient by ID");
            System.out.println("4. Search Patient by Name");
            System.out.println("5. Display All Patients");
            System.out.println("6. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Display next available patient ID
                    System.out.println("Next available patient ID: " + Patient.nextPatientId);

                    // Accepting user input for adding patients
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

                    // Inserting the patient using user-provided data
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
                    // Searching for a patient by name
                    System.out.print("\nEnter the name of the patient to search: ");
                    String nameToSearch = scanner.nextLine();
                    patientList.searchPatientByName(nameToSearch);
                    break;
                case 5:
                    // Displaying all patients
                    patientList.displayPatients();
                    break;
                case 6:
                    // Exiting the program
                    System.out.println("Exiting the program.");
                    System.exit(0);
                    break;
                default:
                    System.out.println("Invalid choice. Please enter a valid option.");
                    scanner.close();
            }
        }
    }
}