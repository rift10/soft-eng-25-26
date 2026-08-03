package projects.tasks;
import java.util.PriorityQueue;

public class Inpatient implements Event {

    private PriorityQueue<Event> queue;
    private int duration;
    private int patientID;
    private int time;

    public Inpatient(int patientID, PriorityQueue<Event> queue, int time) {
        this.patientID = patientID;
        this.queue = queue;
        this.time = time;
        duration = Resources.getSeverity(patientID) * Constants.INPATIENT_MULTIPLIER;
    }

    @Override
    public void run(Resources resources) {
        if (resources.getNumberOfInpatientRooms() > 0) {
            resources.useInpatientRoom();
            schedule(queue, new Free(ResourceType.INPATIENT_ROOM, patientID, time + duration));
        } else {
            System.out.println("No free inpatient room, rescheduling further care for patient " + patientID);
            schedule(queue, new Inpatient(patientID, queue, time + duration));
        }
    }

    @Override
    public int getTimestamp() {
        return time;
    }

}
