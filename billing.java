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

    public void performBillingOperations(Scanner scanner) {
        // Display initial billing information
        System.out.println("Initial Billing Information:");
        displayBillingInfo();

        // Ask user for operations
        System.out.println("Choose an option:");
        System.out.println("1. Add new bill");
        System.out.println("2. Track payment");
        System.out.println("3. Update billing information");
        System.out.println("4. Main Menu");
        System.out.print("Enter the option number: ");
        int option = scanner.nextInt();
        scanner.nextLine(); // Consume the newline character

        switch (option) {
            case 1:
                addNewBill(scanner);
                break;
            case 2:
                trackPayment(scanner);
                break;
            case 3:
                updateBillingInfo(scanner);
                break;
            case 4:
            Main main = new Main();
            main.main();    
            break;

            default:
                System.out.println("Invalid option");
        }

        // Display updated billing information
        System.out.println("Updated Billing Information:");
        displayBillingInfo();
    }

    private void addNewBill(Scanner scanner) {
        // Get user inputs for generating bills
        System.out.print("Enter patient name for the bill: ");
        String patientName = scanner.nextLine();

        System.out.print("Enter bill amount: ");
        double amount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        generateBill(patientName, amount);
        System.out.println("Bill added successfully.");

        // Ask user if they want to perform another operation
        System.out.print("Do you want to perform another operation? (YES/NO): ");
        String continueOption = scanner.nextLine().toUpperCase();

        if ("YES".equals(continueOption)) {
            performBillingOperations(scanner); // Navigate to another operation
        }
        else if("NO".equals(continueOption)){
            Main main = new Main();
            main.main();  
        }
    }

    private void trackPayment(Scanner scanner) {
        System.out.print("Enter the Bill ID to track payment: ");
        int paymentBillID = scanner.nextInt();
        trackPayment(paymentBillID);
        System.out.println("Payment for Bill ID " + paymentBillID + " has been tracked.");
        Main main = new Main();
            main.main();
    }

    private void updateBillingInfo(Scanner scanner) {
        System.out.print("Enter the Bill ID to update billing information: ");
        int updateBillID = scanner.nextInt();

        System.out.print("Enter the new amount: ");
        double newAmount = scanner.nextDouble();
        scanner.nextLine(); // Consume the newline character

        updateBillingInfo(updateBillID, newAmount);
        System.out.println("Billing information for Bill ID " + updateBillID + " has been updated.");
    }
}

public class billing {
    public static void Billing() {
        BillingLinkedList billingList = new BillingLinkedList();
        Scanner scanner = new Scanner(System.in);

        billingList.performBillingOperations(scanner);

        // Close the scanner
        scanner.close();
    }
}
