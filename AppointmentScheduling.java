import java.util.PriorityQueue;

class Appointment {
    String patientName;
    String scheduledTime;

    // Constructor
    public Appointment(String patientName, String scheduledTime) {
        this.patientName = patientName;
        this.scheduledTime = scheduledTime;
    }
}

class AppointmentScheduler {
    PriorityQueue<Appointment> minHeap;

    // Constructor
    public AppointmentScheduler() {
        minHeap = new PriorityQueue<>((a1, a2) -> a1.scheduledTime.compareTo(a2.scheduledTime));
    }

    // Schedule a new appointment
    public void scheduleAppointment(String patientName, String scheduledTime) {
        Appointment newAppointment = new Appointment(patientName, scheduledTime);
        minHeap.offer(newAppointment);
    }

    // Cancel an appointment
    // Cancel an appointment
    public void cancelAppointment(String patientName, String scheduledTime) {
        Appointment canceledAppointment = new Appointment(patientName, scheduledTime);
        minHeap.removeIf(appointment -> appointment.patientName.equals(patientName) && appointment.scheduledTime.equals(scheduledTime));
    }


    // Retrieve and display the next appointment
    public void getNextAppointment() {
        if (!minHeap.isEmpty()) {
            Appointment nextAppointment = minHeap.peek();
            System.out.println("Next Appointment:");
            System.out.println("Patient: " + nextAppointment.patientName);
            System.out.println("Scheduled Time: " + nextAppointment.scheduledTime);
        } else {
            System.out.println("No upcoming appointments.");
        }
    }
}

public class AppointmentScheduling {
    public static void main(String[] args) {
        AppointmentScheduler scheduler = new AppointmentScheduler();

        // Schedule appointments
        scheduler.scheduleAppointment("John Doe", "2024-02-20 10:00 AM");
        scheduler.scheduleAppointment("Jane Smith", "2024-02-20 11:30 AM");

        // Display the next appointment
        scheduler.getNextAppointment();

        // Cancel an appointment
        scheduler.cancelAppointment("John Doe", "2024-02-20 10:00 AM");

        // Display the next appointment after cancellation
        scheduler.getNextAppointment();
    }
}