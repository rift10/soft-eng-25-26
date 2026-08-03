package projects.tasks;
import java.util.PriorityQueue;

public class PatientArrival implements Event {

    private PriorityQueue<Event> queue;
    private int patientID;
    private int time;

    public PatientArrival(int patientID, PriorityQueue<Event> queue, int time) {
        this.patientID = patientID;
        this.queue = queue;
        this.time = time;
    }

    @Override
    public void run(Resources resources) {
        if (resources.getNumberOfRooms() > 0) {
            resources.useRoom();
            System.out.println("Patient " + patientID + " arrived, moving to nurse triage");
            schedule(queue, new NurseTriage(patientID, queue, time + 1));
        } else {
            System.out.println("Patient " + patientID + " arrived");
            System.out.println("No available rooms, patient " + patientID + " waiting another hour");
            schedule(queue, new PatientArrival(patientID, queue, time + 1));
        }
    }

    @Override
    public int getTimestamp() {
        return time;
    }

}
