package billing;
import java.util.Scanner;

class BillingInfo {
    int billID;
    String patientName;
    double amount;
    boolean isPaid;

    BillingInfo next;
    BillingInfo prev;

    // Constructor and other methods go here
}

class BillingLinkedList {
    BillingInfo head;
    BillingInfo tail;

    public void generateBill(String patientName, double amount) {
        BillingInfo newBill = new BillingInfo();
        newBill.billID = generateUniqueBillID();
        newBill.patientName = patientName;
        newBill.amount = amount;
        newBill.isPaid = false;

        addToEnd(newBill);
        System.out.println("Bill generated successfully. Bill ID: " + newBill.billID);
    }

    public void trackPayment(int billID) {
        BillingInfo current = findBillByID(billID);

        if (current != null) {
            current.isPaid = true;
            System.out.println("Payment for Bill ID " + billID + " has been tracked.");
        } else {
            System.out.println("Bill ID " + billID + " not found.");
        }
    }

    public void updateBillingInfo(int billID, double newAmount) {
        BillingInfo current = findBillByID(billID);

        if (current != null) {
            current.amount = newAmount;
            System.out.println("Billing information for Bill ID " + billID + " has been updated.");
        } else {
            System.out.println("Bill ID " + billID + " not found.");
        }
    }

    public void displayBillingInfo() {
        BillingInfo current = head;
        while (current != null) {
            System.out.println("Bill ID: " + current.billID);
            System.out.println("Patient: " + current.patientName);
            System.out.println("Amount: $" + current.amount);
            System.out.println("Paid: " + (current.isPaid ? "Yes" : "No"));
            System.out.println("----------------------");
            current = current.next;
        }
    }

    private void addToEnd(BillingInfo newBill) {
        if (head == null) {
            head = newBill;
            tail = newBill;
        } else {
            tail.next = newBill;
            newBill.prev = tail;
            tail = newBill;
        }
    }

    private BillingInfo findBillByID(int billID) {
        BillingInfo current = head;
        while (current != null && current.billID != billID) {
            current = current.next;
        }
        return current;
    }

    private int generateUniqueBillID() {
        return (int) (System.currentTimeMillis() % Integer.MAX_VALUE);
    }
}

public class billing {
    public static void Billing() {
        BillingLinkedList billingList = new BillingLinkedList();
        Scanner scanner = new Scanner(System.in);

        // Get user inputs for generating bills
        System.out.print("Enter patient name for the bill: ");
        String patientName = scanner.nextLine();

        System.out.print("Enter bill amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        billingList.generateBill(patientName, amount);

        // Display initial billing information
        System.out.println("Initial Billing Information:");
        billingList.displayBillingInfo();

        // Get user input for tracking payment
        System.out.print("Do you want to track payment for a bill? (YES/NO): ");
        String trackPaymentOption = scanner.nextLine().toUpperCase();

        if ("YES".equals(trackPaymentOption)) {
            System.out.print("Enter the Bill ID to track payment: ");
            int paymentBillID = scanner.nextInt();
            billingList.trackPayment(paymentBillID);
        }

        // Get user input for updating billing information
        System.out.print("Do you want to update billing information? (YES/NO): ");
        String updateBillingOption = scanner.next().toUpperCase();

        if ("YES".equals(updateBillingOption)) {
            System.out.print("Enter the Bill ID to update billing information: ");
            int updateBillID = scanner.nextInt();

            System.out.print("Enter the new amount: ");
            double newAmount = scanner.nextDouble();
            scanner.nextLine(); // Consume the newline character

            billingList.updateBillingInfo(updateBillID, newAmount);
        }

        // Display updated billing information
        System.out.println("Updated Billing Information:");
        billingList.displayBillingInfo();

        // Close the scanner
        scanner.close();
    }
}
