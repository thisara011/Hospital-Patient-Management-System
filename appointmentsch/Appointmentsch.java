package appointmentsch;
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

class Appointment {
    String name;
    String scheduledDate; // Assuming scheduled date is represented as string
    int scheduledTime; // Assuming scheduled time is represented as int

    public Appointment(String name, String scheduledDate, int scheduledTime) {
        this.name = name;
        this.scheduledDate = scheduledDate;
        this.scheduledTime = scheduledTime;
    }
}

class MinHeap {
    public final int MAX_SIZE = 100; // Maximum size of the heap
    public Appointment[] heap;
    public int size;

    public MinHeap() {
        heap = new Appointment[MAX_SIZE];
        size = 0;
    }

    private int parent(int pos) {
        return (pos - 1) / 2;
    }

    private int leftChild(int pos) {
        return (2 * pos) + 1;
    }

    private int rightChild(int pos) {
        return (2 * pos) + 2;
    }

    private void swap(int fpos, int spos) {
        Appointment tmp;
        tmp = heap[fpos];
        heap[fpos] = heap[spos];
        heap[spos] = tmp;
    }

    private boolean isEarlier(String date1, int time1, String date2, int time2) {
        if (date1.compareTo(date2) < 0) {
            return true;
        } else if (date1.compareTo(date2) == 0) {
            return time1 < time2;
        }
        return false;
    }

    public void minHeapify(int pos) {
        int left = leftChild(pos);
        int right = rightChild(pos);
        int smallest = pos;

        if (left < size && isEarlier(heap[left].scheduledDate, heap[left].scheduledTime, heap[pos].scheduledDate, heap[pos].scheduledTime))
            smallest = left;

        if (right < size && isEarlier(heap[right].scheduledDate, heap[right].scheduledTime, heap[smallest].scheduledDate, heap[smallest].scheduledTime))
            smallest = right;

        if (smallest != pos) {
            swap(pos, smallest);
            minHeapify(smallest);
        }
    }

    public void insert(Appointment newAppointment) {
        if (size >= MAX_SIZE) {
            System.out.println("Heap is full. Cannot insert more elements.");
            return;
        }

        size++;
        int current = size - 1;
        heap[current] = newAppointment;

        while (current != 0 && isEarlier(heap[current].scheduledDate, heap[current].scheduledTime, heap[parent(current)].scheduledDate, heap[parent(current)].scheduledTime)) {
            swap(current, parent(current));
            current = parent(current);
        }
    }

    public Appointment getMin() {
        if (size == 0)
            return null;
        return heap[0];
    }

    public void removeMin() {
        if (size == 0)
            return;

        heap[0] = heap[size - 1];
        size--;
        minHeapify(0);
    }

    public int getSize() {
        return size;
    }

    public boolean isEmpty() {
        return size == 0;
    }

    public Appointment peekMin() {
        if (size == 0)
            return null;
        return heap[0];
    }

    public Appointment viewAppointments() {
        MinHeap tempHeap = new MinHeap();
        tempHeap.size = size;
        tempHeap.heap = new Appointment[MAX_SIZE];
        for (int i = 0; i < size; i++) {
            tempHeap.heap[i] = heap[i];
        }
    
        System.out.println("Upcoming Appointments:");
        Appointment nextAppointment = null;
        while (!tempHeap.isEmpty()) {
            nextAppointment = tempHeap.getMin();
            System.out.println(nextAppointment.name + " has appointment at " +
                    nextAppointment.scheduledDate + " " + nextAppointment.scheduledTime);
            tempHeap.removeMin();
        }
        return heap[0];
    }
    
    
}

public class Appointmentsch {
    public static void PatientAppoimentManagment() {
        MinHeap minHeap = new MinHeap();
        Scanner scanner = new Scanner(System.in);

        while (true) {
            System.out.println("\nEnter your choice:");
            System.out.println("1. Add Appointment");
            System.out.println("2. View Appointments");
            System.out.println("3. Remove Appointment");
            System.out.println("4. Exit");

            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    System.out.print("Enter Name: ");
                    String name = scanner.nextLine();

                    System.out.print("Enter Scheduled Date (yyyy-MM-dd): ");
                    String scheduledDateStr = scanner.nextLine();

                    // Validate the scheduled date
                    LocalDate scheduledDate = LocalDate.parse(scheduledDateStr, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                    if (scheduledDate.isBefore(LocalDate.now())) {
                        System.out.println("Error: Scheduled date cannot be in the past.");
                        break;
                    }

                    System.out.print("Enter Scheduled Time (HHmm): ");
                    int scheduledTime = scanner.nextInt();
                    scanner.nextLine(); // Consume newline

                    minHeap.insert(new Appointment(name, scheduledDateStr, scheduledTime));
                    break;

                case 2:  
                    Appointment firstAppointment = minHeap.viewAppointments();
                    if (firstAppointment != null) {
                        System.out.println("Next Appointment is to "+firstAppointment.name+" at "+firstAppointment.scheduledDate+" "+firstAppointment.scheduledTime);
                    } else {
                        System.out.println("No upcoming appointments.");
                    }
                    break;

                case 3:
                    System.out.print("Enter Name of Appointment to Remove: ");
                    String nameToRemove = scanner.nextLine();
                    boolean removed = false;
                    for (int i = 0; i < minHeap.getSize(); i++) {
                        if (minHeap.heap[i].name.equals(nameToRemove)) {
                            minHeap.heap[i] = minHeap.heap[minHeap.getSize() - 1];
                            minHeap.size--;
                            minHeap.minHeapify(i);
                            removed = true;
                            break;
                        }
                    }
                    if (removed) {
                        System.out.println("Appointment '" + nameToRemove + "' removed successfully.");
                        Appointment firstAppointmentAfterRemoval = minHeap.viewAppointments();
                        if (firstAppointmentAfterRemoval != null) {
                            System.out.println("First Upcoming Appointment: " + firstAppointmentAfterRemoval.name + " has appointment at " +
                                    firstAppointmentAfterRemoval.scheduledDate + " " + firstAppointmentAfterRemoval.scheduledTime);
                        } else {
                            System.out.println("No upcoming appointments.");
                        }
                    } else {
                        System.out.println("Appointment '" + nameToRemove + "' not found.");
                    }
                    break;

                case 4:
                    System.out.println("Exiting...");
                    scanner.close();
                    System.exit(0);
                    break;

                default:
                    System.out.println("Invalid choice. Please enter a number between 1 and 4.");
            }
        }
    }
}
