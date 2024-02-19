class Patient {
    String name;
    int age;
    String gender;
    String contact;
    String medicalHistory;

    // Constructor
    public Patient(String name, int age, String gender, String contact, String medicalHistory) {
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
    public void deletePatient(String patientName) {
        Node current = head;
        while (current != null) {
            if (current.patient.name.equals(patientName)) {
                if (current.prev != null) {
                    current.prev.next = current.next;
                } else {
                    head = current.next;
                }

                if (current.next != null) {
                    current.next.prev = current.prev;
                }

                return; // Found and deleted the patient, exit the method
            }

            current = current.next;
        }

        System.out.println("Patient with name " + patientName + " not found.");
    }

    // Retrieve and display all patient records in the linked list
    public void displayPatients() {
        Node current = head;
        while (current != null) {
            System.out.println("Name: " + current.patient.name +
                               ", Age: " + current.patient.age +
                               ", Gender: " + current.patient.gender +
                               ", Contact: " + current.patient.contact +
                               ", Medical History: " + current.patient.medicalHistory);
            current = current.next;
        }
    }
}

public class PatientInfo {
    public static void main(String[] args) {
        PatientList patientList = new PatientList();

        // Inserting patients
        patientList.insertPatient(new Patient("John Doe", 30, "Male", "123456789", "None"));
        patientList.insertPatient(new Patient("Jane Smith", 25, "Female", "987654321", "Allergies"));

        // Displaying patients
        System.out.println("Patients before deletion:");
        patientList.displayPatients();

        // Deleting a patient
        patientList.deletePatient("John Doe");

        // Displaying patients after deletion
        System.out.println("\nPatients after deletion:");
        patientList.displayPatients();
    }
}