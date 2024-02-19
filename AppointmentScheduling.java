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
    private Appointment[] heap;
    private int size;

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

    private void minHeapify(int pos) {
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

        // Scheduling some appointments with date and time
        minHeap.insert(new Appointment("Sarath Kumara", "2024-02-21", "10:00"));
        minHeap.insert(new Appointment("Ravindu perera", "2024-02-10", "15:30"));
        minHeap.insert(new Appointment("Sakith ransana", "2024-02-21", "08:45"));
        minHeap.insert(new Appointment("Akila Nirmal", "2024-02-22", "12:15"));
        
        minHeap.removeMin();
        // Retrieving and displaying the upcoming appointments
        System.out.println("Next Appointment: " + minHeap.getMin().name + " at " +
                minHeap.getMin().scheduledDate + " " + minHeap.getMin().scheduledTime+"\n");

        

        System.out.println("Upcoming Appointments:");
        while (!minHeap.isEmpty()) {
            Appointment nextAppointment = minHeap.getMin();
            System.out.println(nextAppointment.name + " has appointment at " +
                    nextAppointment.scheduledDate + " " + nextAppointment.scheduledTime);
            minHeap.removeMin();
        }
    }
}

