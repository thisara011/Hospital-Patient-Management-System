
import java.util.Scanner;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.LocalDateTime;


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

    //Method to find the index of parent
    private int parent(int pos) {
        return (pos - 1) / 2;
    }

    //Method to find the index of left child
    private int leftChild(int pos) {
        return (2 * pos) + 1;
    }

    //Method to find the index of right child
    private int rightChild(int pos) {
        return (2 * pos) + 2;
    }

    //Method to swap apppointments to maintain the min heap
    private void swap(int fpos, int spos) {
        Appointment tmp;
        tmp = heap[fpos];
        heap[fpos] = heap[spos];
        heap[spos] = tmp;
    }

    //Method to check the newly inserted appointment is earlier to parent appointment
    private boolean isEarlier(String date1, int time1, String date2, int time2) {
        if (date1.compareTo(date2) < 0) {
            return true;
        } else if (date1.compareTo(date2) == 0) {
            return time1 < time2;
        }
        return false;
    }


    //Method to facilitate to maintain of min heap factor 
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

    //Method to insert a new appointment
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

    //Method to get min value
    public Appointment getMin() {
        if (size == 0)
            return null;
        return heap[0];
    }

    //Method to remove min value
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

    //Method to check heap tree is empty
    public boolean isEmpty() {
        return size == 0;
    }


    public Appointment peekMin() {
        if (size == 0)
            return null;
        return heap[0];
    }

    //Method to upcoming view appointments
    public Appointment viewAppointments() {
        MinHeap tempHeap = new MinHeap();
        tempHeap.size = size;
        tempHeap.heap = new Appointment[MAX_SIZE];
        for (int i = 0; i < size; i++) {
            tempHeap.heap[i] = heap[i];
        }
    
        LocalDate currentDate = LocalDate.now();
        int currentTime = Integer.parseInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmm")));
    
        System.out.println("Upcoming Appointments:");
        Appointment nextAppointment = null;
        while (!tempHeap.isEmpty()) {
            nextAppointment = tempHeap.getMin();
            LocalDate appointmentDate = LocalDate.parse(nextAppointment.scheduledDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
            int appointmentTime = nextAppointment.scheduledTime;
    
            // Check if the appointment is in the future
            if (appointmentDate.isAfter(currentDate) || 
                (appointmentDate.equals(currentDate) && appointmentTime >= currentTime)) {
                System.out.println(nextAppointment.name + " has appointment at " +
                                   nextAppointment.scheduledDate + " " + nextAppointment.scheduledTime);
            }
            
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
            System.out.println("4. main menu");
            System.out.println("5. exit");

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

                    minHeap.insert(new Appointment(name, scheduledDateStr, scheduledTime)); //calling insertion function
                    break;

                case 2:  
                    minHeap.viewAppointments(); //calling view appointments function
                    Appointment firstAppointment = minHeap.getMin();
                    if (firstAppointment != null) {
                        // Get the current date and time
                        LocalDate currentDate = LocalDate.now();
                        int currentTime = Integer.parseInt(LocalDateTime.now().format(DateTimeFormatter.ofPattern("HHmm")));

                        int minTimeDifference = Integer.MAX_VALUE; // Initialize to a large value
                        Appointment nextAppointment = null;

                        // Iterate through the heap to find the next appointment after the current time
                        for (int i = 0; i < minHeap.getSize(); i++) {
                            Appointment currentAppointment = minHeap.heap[i];
                            LocalDate appointmentDate = LocalDate.parse(currentAppointment.scheduledDate, DateTimeFormatter.ofPattern("yyyy-MM-dd"));
                            int appointmentTime = currentAppointment.scheduledTime;

                            // Calculate the time difference between the current appointment and the current time
                            int timeDifference;
                            if (appointmentDate.isAfter(currentDate) || (appointmentDate.equals(currentDate) && appointmentTime >= currentTime)) {
                                timeDifference = (appointmentDate.getDayOfMonth() - currentDate.getDayOfMonth()) * 24 * 60 + (appointmentTime / 100 * 60 + appointmentTime % 100) - (currentTime / 100 * 60 + currentTime % 100);

                                if (timeDifference < minTimeDifference) {
                                    minTimeDifference = timeDifference;
                                    nextAppointment = currentAppointment;
                                }
                            }
                        }

                        if (nextAppointment != null) {
                            System.out.println("Next Appointment is to " + nextAppointment.name + " at " + nextAppointment.scheduledDate + " " + nextAppointment.scheduledTime);
                        } else {
                            System.out.println("No upcoming appointments.");
                        }
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
                            minHeap.heap[i] = minHeap.heap[minHeap.getSize() - 1];//the removed appointment is replaced by last appointment 
                            minHeap.size--;
                            minHeap.minHeapify(i);//call this to maintain min heap
                            removed = true;
                            break;
                        }
                    }
                    if (removed) {
                        System.out.println("Appointment '" + nameToRemove + "' removed successfully.");
                        Appointment firstAppointmentAfterRemoval = minHeap.getMin();
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
                    
                    Main main = new Main();
                    main.main();    
                    break;

                    case 5:
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
