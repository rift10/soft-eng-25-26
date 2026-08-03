package projects.tasks;
import java.util.HashMap;
import java.util.Map;

public class Resources {

    private int numberOfRooms;
    private int numberOfNurses;
    private int numberOfDoctors;
    private int numberOfInpatientRooms;

    private static Map<Integer, Integer> severityMap = new HashMap<>();

    public Resources(int rooms, int nurses, int doctors, int inpatientRooms) {
        numberOfRooms = rooms;
        numberOfNurses = nurses;
        numberOfDoctors = doctors;
        numberOfInpatientRooms = inpatientRooms;
    }

    // ---------- Setters ----------

    public void useRoom() {
        numberOfRooms--;
    }

    public void useInpatientRoom() {
        numberOfInpatientRooms--;
    }

    public void useNurse() {
        numberOfNurses--;
    }

    public void useDoctor() {
        numberOfDoctors--;
    }

    public void dischargePatient() {
        numberOfRooms++;
    }

    public void dischargePatientFromFurtherCare() {
        numberOfInpatientRooms++;
    }

    public void freeNurse() {
        numberOfNurses++;
    }

    public void freeDoctor() {
        numberOfDoctors++;
    }

    // ---------- Getters ----------

    // 1 = non-critical (can be let go immediately)
    // 2 = medium (only needs doctor consultation)
    // 3 = critical (needs to go to further care)
    public static int getSeverity(int patientID) {
        if (!severityMap.containsKey(patientID)) severityMap.put(patientID, (int) (Math.random() * 3) + 1);
        return severityMap.get(patientID);
    }

    public int getNumberOfRooms() {
        return numberOfRooms;
    }

    public int getNumberOfInpatientRooms() {
        return numberOfInpatientRooms;
    }

    public int getNumberOfNurses() {
        return numberOfNurses;
    }

    public int getNumberOfDoctors() {
        return numberOfDoctors;
    }
}
