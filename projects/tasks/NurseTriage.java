package projects.tasks;
import java.util.PriorityQueue;

public class NurseTriage implements Event {

    private PriorityQueue<Event> queue;
    private int duration;
    private int patientID;
    private int time;

    public NurseTriage(int patientID, PriorityQueue<Event> queue, int time) {
        this.patientID = patientID;
        this.queue = queue;
        this.time = time;
        duration = Resources.getSeverity(patientID);
    }

    @Override
    public void run(Resources resources) {
        if (resources.getNumberOfNurses() > 0) {
            System.out.println("Nurse arrived for patient " + patientID + ", took " + duration + " hours");
            resources.useNurse();
            if (duration == 1) { // aka lowest severity
                System.out.println("Patient " + patientID + " is non-critical, scheduling discharge");
                schedule(queue, new Free(ResourceType.ROOM, patientID, time + duration));
            } else if (resources.getNumberOfDoctors() > 0) {
                schedule(queue, new DoctorConsultation(patientID, queue, time + duration));
            }
            schedule(queue, new Free(ResourceType.NURSE, patientID, time + duration));
        } else {
            System.out.println("No free nurses, rescheduling nurse triage for patient " + patientID);
            schedule(queue, new NurseTriage(patientID, queue, time + duration));
        }
    }

    @Override
    public int getTimestamp() {
        return time;
    }

}
