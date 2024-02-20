import java.util.Scanner;
//import java.util.ArrayList;
//import java.util.List;

class MedicalRecord {
    String timestamp;
    String patientName;
    String doctorName;
    String diagnosis;
    String prescription;

    // Constructor
    public MedicalRecord(String timestamp, String patientName, String doctorName, String diagnosis, String prescription) {
        this.timestamp = timestamp;
        this.patientName = patientName;
        this.doctorName = doctorName;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
    }

    // Getter for patientName
    public String getPatientName() {
        return patientName;
    }
}

class MedicalRecordBST {
    Node root;

    static class Node {
        MedicalRecord record;
        Node left, right;

        // Constructor
        public Node(MedicalRecord record) {
            this.record = record;
            this.left = this.right = null;
        }
    }

    // Insert a new medical record into the BST
    public void insertRecord(MedicalRecord record) {
        root = insertRec(root, record);
    }

    private Node insertRec(Node root, MedicalRecord record) {
        if (root == null) {
            return new Node(record);
        }

        // Compare patient names to decide the direction of insertion
        if (record.getPatientName().compareTo(root.record.getPatientName()) < 0) {
            root.left = insertRec(root.left, record);
        } else if (record.getPatientName().compareTo(root.record.getPatientName()) > 0) {
            root.right = insertRec(root.right, record);
        } else {
            // Handle duplicate patient names (if needed)
            // This example considers patient names to be unique
        }

        return root;
    }

    // Update an existing medical record by patient name
    public void updateRecord(String patientName, String newDoctorName, String newDiagnosis, String newPrescription) {
        root = updateRec(root, patientName, newDoctorName, newDiagnosis, newPrescription);
    }

    private Node updateRec(Node root, String patientName, String newDoctorName, String newDiagnosis, String newPrescription) {
        if (root == null) {
            return null; // Record not found
        }

        int compareResult = patientName.compareTo(root.record.getPatientName());

        if (compareResult < 0) {
            root.left = updateRec(root.left, patientName, newDoctorName, newDiagnosis, newPrescription);
        } else if (compareResult > 0) {
            root.right = updateRec(root.right, patientName, newDoctorName, newDiagnosis, newPrescription);
        } else {
            // Found the record, update it
            root.record.doctorName = newDoctorName;
            root.record.diagnosis = newDiagnosis;
            root.record.prescription = newPrescription;
        }

        return root;
    }

    // Retrieve and display medical records based on patient names
    public void retrieveRecords() {
        inorderTraversal(root);
    }

    private void inorderTraversal(Node root) {
        if (root != null) {
            inorderTraversal(root.left);
            System.out.println("Timestamp: " + root.record.timestamp);
            System.out.println("Patient: " + root.record.patientName);
            System.out.println("Doctor: " + root.record.doctorName);
            System.out.println("Diagnosis: " + root.record.diagnosis);
            System.out.println("Prescription: " + root.record.prescription);
            System.out.println();
            inorderTraversal(root.right);
        }
    }

    // Search for a medical record by patient name
    public MedicalRecord searchRecord(String patientName) {
        return searchRec(root, patientName);
    }

    private MedicalRecord searchRec(Node root, String patientName) {
        if (root == null) {
            return null; // Record not found
        }

        int compareResult = patientName.compareTo(root.record.getPatientName());

        if (compareResult < 0) {
            return searchRec(root.left, patientName);
        } else if (compareResult > 0) {
            return searchRec(root.right, patientName);
        } else {
            return root.record; // Found the record
        }
    }

    // Delete a medical record by patient name
    public void deleteRecord(String patientName) {
        root = deleteRec(root, patientName);
    }

    private Node deleteRec(Node root, String patientName) {
        if (root == null) {
            return null;
        }

        int compareResult = patientName.compareTo(root.record.getPatientName());

        if (compareResult < 0) {
            root.left = deleteRec(root.left, patientName);
        } else if (compareResult > 0) {
            root.right = deleteRec(root.right, patientName);
        } else {
            // Node with only one child or no child
            if (root.left == null) {
                return root.right;
            } else if (root.right == null) {
                return root.left;
            }

            // Node with two children
            root.record = minValue(root.right);

            // Delete the in-order successor
            root.right = deleteRec(root.right, root.record.getPatientName());
        }

        return root;
    }

    private MedicalRecord minValue(Node root) {
        MedicalRecord minValue = root.record;
        while (root.left != null) {
            minValue = root.left.record;
            root = root.left;
        }
        return minValue;
    }
}

public class MRM {
    public static void main(String[] args) {
        MedicalRecordBST recordBST = new MedicalRecordBST();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nMenu:");
            System.out.println("1. Add Medical Record");
            System.out.println("2. Update Medical Record by Patient Name");
            System.out.println("3. Search Medical Record by Timestamp");
            System.out.println("4. Search Medical Record by Patient Name");
            System.out.println("5. Display All Medical Records");
            System.out.println("6. Delete Medical Record by Patient Name");
            System.out.println("7. Exit");
            System.out.print("Enter your choice: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    // Accepting user input for adding medical records
                    System.out.println("\nEnter Medical Record details (Timestamp, Patient, Doctor, Diagnosis, Prescription):");
                    System.out.print("Timestamp: ");
                    String timestamp = scanner.nextLine();
                    System.out.print("Patient: ");
                    String patient = scanner.nextLine();
                    System.out.print("Doctor: ");
                    String doctor = scanner.nextLine();
                    System.out.print("Diagnosis: ");
                    String diagnosis = scanner.nextLine();
                    System.out.print("Prescription: ");
                    String prescription = scanner.nextLine();

                    // Inserting the medical record using user-provided data
                    recordBST.insertRecord(new MedicalRecord(timestamp, patient, doctor, diagnosis, prescription));
                    System.out.println("Medical Record added successfully.");
                    break;

                case 2:
                    // Updating a medical record by patient name
                    System.out.print("\nEnter the patient name of the medical record to update: ");
                    String patientNameToUpdate = scanner.nextLine();
                    System.out.print("Enter the new Doctor's Name: ");
                    String newDoctorName = scanner.nextLine();
                    System.out.print("Enter the new Diagnosis: ");
                    String newDiagnosis = scanner.nextLine();
                    System.out.print("Enter the new Prescription: ");
                    String newPrescription = scanner.nextLine();

                    // Updating the medical record using user-provided data
                    recordBST.updateRecord(patientNameToUpdate, newDoctorName, newDiagnosis, newPrescription);
                    System.out.println("Medical Record updated successfully.");
                    break;

                case 3:
                    // Searching for a medical record by timestamp
                    System.out.print("\nEnter the timestamp of the medical record to search: ");
                    String timestampToSearch = scanner.nextLine();
                    MedicalRecord foundRecordByTimestamp = recordBST.searchRecord(timestampToSearch);
                    if (foundRecordByTimestamp != null) {
                        System.out.println("Medical Record found:");
                        System.out.println("Timestamp: " + foundRecordByTimestamp.timestamp);
                        System.out.println("Patient: " + foundRecordByTimestamp.patientName);
                        System.out.println("Doctor: " + foundRecordByTimestamp.doctorName);
                        System.out.println("Diagnosis: " + foundRecordByTimestamp.diagnosis);
                        System.out.println("Prescription: " + foundRecordByTimestamp.prescription);
                    } else {
                        System.out.println("Medical Record not found.");
                    }
                    break;

                case 4:
                    // Searching for medical records by patient name
                    System.out.print("\nEnter the patient name to search for medical records: ");
                    String patientNameToSearch = scanner.nextLine();
                    MedicalRecord foundRecordByPatientName = recordBST.searchRecord(patientNameToSearch);
                    if (foundRecordByPatientName != null) {
                        System.out.println("Medical Record found:");
                        System.out.println("Timestamp: " + foundRecordByPatientName.timestamp);
                        System.out.println("Patient: " + foundRecordByPatientName.patientName);
                        System.out.println("Doctor: " + foundRecordByPatientName.doctorName);
                        System.out.println("Diagnosis: " + foundRecordByPatientName.diagnosis);
                        System.out.println("Prescription: " + foundRecordByPatientName.prescription);
                    } else {
                        System.out.println("Medical Record not found.");
                    }
                    break;

                case 5:
                    // Displaying all medical records
                    recordBST.retrieveRecords();
                    break;

                case 6:
                    // Deleting a medical record by patient name
                    System.out.print("\nEnter the patient name of the medical record to delete: ");
                    String patientNameToDelete = scanner.nextLine();
                    recordBST.deleteRecord(patientNameToDelete);
                    System.out.println("Medical Record deleted successfully.");
                    break;

                case 7:
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
