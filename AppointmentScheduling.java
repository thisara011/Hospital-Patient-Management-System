import java.util.Scanner;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

class Appointment {
    String name;
    String scheduledDate; // Assuming scheduled date is represented as string
    String scheduledTime; // Assuming scheduled time is represented as string

    public Appointment(String name, String scheduledDate, String scheduledTime) {
        this.name = name;
        this.scheduledDate = scheduledDate;
        this.scheduledTime = scheduledTime;
    }
}

class MinHeap {
    private final int MAX_SIZE = 100; // Maximum size of the heap
    public Appointment[] heap;
    private int size;

    public boolean remove(String name) {
        for (int i = 0; i < size; i++) {
            if (heap[i].name.equalsIgnoreCase(name)) {
                heap[i] = heap[size - 1];
                size--;
                minHeapify(i);
                return true;
            }
        }
        return false;
    }

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

    private boolean isEarlier(String date1, String time1, String date2, String time2) {
        if (date1.compareTo(date2) < 0) {
            return true;
        } else if (date1.compareTo(date2) == 0) {
            return time1.compareTo(time2) < 0;
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
}







public class AppointmentScheduling {
    public static void main(String[] args) {
        // Creating a MinHeap instance
        MinHeap minHeap = new MinHeap();
        Scanner scanner = new Scanner(System.in);

        // Scheduling some appointments with date and time
        System.out.println("Enter\n'1' to input a new appointment\n'2' to remove an appointment \n'3' to see upcoming appointment \n'4' to stop:");

        while (true) {
            System.out.print("\nCommand: ");
            String command = scanner.nextLine();

            if (command.equalsIgnoreCase("4")) {
                break;
            } else if (command.equalsIgnoreCase("1")) {
                System.out.print("Name: ");
                String name = scanner.nextLine();

                System.out.print("Scheduled Date (yyyy-MM-dd): ");
                String scheduledDate = scanner.nextLine();

                System.out.print("Scheduled Time (HH:mm): ");
                String scheduledTime = scanner.nextLine();

                minHeap.insert(new Appointment(name, scheduledDate, scheduledTime));
            } else if (command.equalsIgnoreCase("2")) {
                System.out.print("Enter the name of the appointment to delete: ");
                String appointmentName = scanner.nextLine();
                
                // Remove the appointment with the given name
                boolean removed = minHeap.remove(appointmentName);
                if (removed) {
                    System.out.println("Appointment '" + appointmentName + "' deleted successfully.");
                } else {
                    System.out.println("Appointment '" + appointmentName + "' not found.");
                }
            } else if (command.equalsIgnoreCase("3")) {
                // View upcoming appointments
                // Display the next appointment from the current time
                LocalDateTime currentTime = LocalDateTime.now();
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
                String formattedCurrentTime = currentTime.format(formatter);

                // Update the MinHeap before viewing
                minHeap.minHeapify(0);

                boolean upcomingDisplayed = false;

                for (int i = 0; i < minHeap.getSize(); i++) {
                    Appointment nextAppointment = minHeap.heap[i];
                    String scheduledDateTime = nextAppointment.scheduledDate + " " + nextAppointment.scheduledTime;
                    if (scheduledDateTime.compareTo(formattedCurrentTime) > 0) {
                        System.out.println("Next Appointment: " + nextAppointment.name + " at " + scheduledDateTime+"\n");
                        upcomingDisplayed = true;
                        break;
                    }
                }

                if (!upcomingDisplayed) {
                    System.out.println("No upcoming appointments.");
                } else {
                    System.out.println("Upcoming Appointments:");
                    for (int i = 0; i < minHeap.getSize(); i++) {
                        Appointment nextAppointment = minHeap.heap[i];
                        System.out.println(nextAppointment.name + " has appointment at " +
                                nextAppointment.scheduledDate + " " + nextAppointment.scheduledTime);
                    }
                }
            } else {
                System.out.println("Invalid command. Please enter 'add', 'delete', 'view', or 'exit'.");
            }
        }

        scanner.close();
    }
}