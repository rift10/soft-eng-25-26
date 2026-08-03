package projects.tasks;
import java.util.PriorityQueue;

public class DoctorConsultation implements Event {

    private PriorityQueue<Event> queue;
    private int duration;
    private int patientID;
    private int time;

    public DoctorConsultation(int patientID, PriorityQueue<Event> queue, int time) {
        this.patientID = patientID;
        this.queue = queue;
        duration = Resources.getSeverity(patientID);
        this.time = time;
    }

    @Override
    public void run(Resources resources) {
        if (resources.getNumberOfDoctors() > 0) {
            resources.useDoctor();
            System.out.println("Doctor arrived for patient " + patientID + ", took " + duration + " hours");
            if (duration == 3) { // aka highest severity
                System.out.println("Patient " + patientID + " is critical, admitting to further care");
                schedule(queue, new Inpatient(patientID, queue, time + duration));
            }
            schedule(queue, new Free(ResourceType.ROOM, patientID, time + duration));
            schedule(queue, new Free(ResourceType.DOCTOR, patientID, time + duration));
        } else {
            System.out.println("No free doctors, rescheduling doctor consultation for patient " + patientID);
            schedule(queue, new DoctorConsultation(patientID, queue, time + duration));
        }
    }

    @Override
    public int getTimestamp() {
        return time;
    }

}
