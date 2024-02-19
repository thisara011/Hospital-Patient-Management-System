class MedicalRecord {
    String timestamp;
    String patientName;
    String diagnosis;
    String prescription;

    // Constructor
    public MedicalRecord(String timestamp, String patientName, String diagnosis, String prescription) {
        this.timestamp = timestamp;
        this.patientName = patientName;
        this.diagnosis = diagnosis;
        this.prescription = prescription;
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

        // Compare timestamps to decide the direction of insertion
        if (record.timestamp.compareTo(root.record.timestamp) < 0) {
            root.left = insertRec(root.left, record);
        } else if (record.timestamp.compareTo(root.record.timestamp) > 0) {
            root.right = insertRec(root.right, record);
        } else {
            // Handle duplicate timestamps (if needed)
            // This example considers timestamps to be unique
        }

        return root;
    }

    // Update an existing medical record
    public void updateRecord(String timestamp, String newDiagnosis, String newPrescription) {
        root = updateRec(root, timestamp, newDiagnosis, newPrescription);
    }

    private Node updateRec(Node root, String timestamp, String newDiagnosis, String newPrescription) {
        if (root == null) {
            return null; // Record not found
        }

        int compareResult = timestamp.compareTo(root.record.timestamp);

        if (compareResult < 0) {
            root.left = updateRec(root.left, timestamp, newDiagnosis, newPrescription);
        } else if (compareResult > 0) {
            root.right = updateRec(root.right, timestamp, newDiagnosis, newPrescription);
        } else {
            // Found the record, update it
            root.record.diagnosis = newDiagnosis;
            root.record.prescription = newPrescription;
        }

        return root;
    }

    // Retrieve and display medical records based on timestamps
    public void retrieveRecords() {
        inorderTraversal(root);
    }

    private void inorderTraversal(Node root) {
        if (root != null) {
            inorderTraversal(root.left);
            System.out.println("Timestamp: " + root.record.timestamp +
                    ", Patient: " + root.record.patientName +
                    ", Diagnosis: " + root.record.diagnosis +
                    ", Prescription: " + root.record.prescription);
            inorderTraversal(root.right);
        }
    }
}

public class MRM {
    public static void main(String[] args) {
        MedicalRecordBST recordBST = new MedicalRecordBST();

        // Insert medical records
        recordBST.insertRecord(new MedicalRecord("2024-02-20 10:15 AM", "John Doe", "Fever", "Medicine A"));
        recordBST.insertRecord(new MedicalRecord("2024-02-20 11:30 AM", "Jane Smith", "Allergies", "Medicine B"));

        // Display medical records
        System.out.println("Medical Records:");
        recordBST.retrieveRecords();

        // Update a medical record
        recordBST.updateRecord("2024-02-20 10:15 AM", "Flu", "Medicine C");

        // Display medical records after update
        System.out.println("\nMedical Records after Update:");
        recordBST.retrieveRecords();
    }
}