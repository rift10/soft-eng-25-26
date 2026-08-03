package projects.tasks;
import java.util.PriorityQueue;

public class Main {

    private static PriorityQueue<Event> runs = new PriorityQueue<>();

    private Resources resources = new Resources(Constants.TOTAL_ROOMS, Constants.TOTAL_NURSES,
            Constants.TOTAL_DOCTORS, Constants.TOTAL_INPATIENT_ROOMS);

    private static int patientID = 0;

    private static int time = 0;

    public static void main(String args[]) {
        new Main().run();
    }

    public void run() {
        try {
            runs.add(new PatientArrival(patientID, runs, time));
            patientID++;
            while (runs.size() > 0) {
                time = runs.peek().getTimestamp();
                System.out.println("time: " + time + ":00");
                while (runs.peek().getTimestamp() == time) {
                    // System.out.println(resources.getNumberOfRooms() + " rooms");
                    // System.out.println(resources.getNumberOfNurses() + " nurses");
                    // System.out.println(resources.getNumberOfDoctors() + " doctors");
                    // System.out.println(resources.getNumberOfInpatientRooms() + " inpatient rooms");
                    runs.poll().run(resources);
                }
                if (Math.random() < Constants.PATIENT_PROBABILITY) {
                    runs.add(new PatientArrival(patientID, runs, time + 1));
                    patientID++;
                }
                System.out.println("---------------------------------------------------");
            }
        } catch (NullPointerException e) {
        } catch (Exception e) {
            System.err.println(e);
        }
        System.out.println("Total patients: " + patientID);
    }
}
