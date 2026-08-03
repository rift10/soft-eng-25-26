package projects.tasks;
public class Free implements Event {

    private ResourceType type;
    private int time;
    private int patientID;

    public Free(ResourceType type, int patientID, int time) {
        this.time = time;
        this.type = type;
        this.patientID = patientID;
    }

    @Override
    public void run(Resources resources) {
        switch (type) {
            case ROOM:
                resources.dischargePatient();
                System.out.println("Patient " + patientID + " discharged");
                break;
            case INPATIENT_ROOM:
                resources.dischargePatientFromFurtherCare();
                System.out.println("Patient " + patientID + " discharged from further care");
                break;
            case NURSE:
                resources.freeNurse();
                break;
            case DOCTOR:
                resources.freeDoctor();
                break;
        }
    }

    @Override
    public int getTimestamp() {
        return time;
    }

}
