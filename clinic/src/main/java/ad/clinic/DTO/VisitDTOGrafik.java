package ad.clinic.DTO;

public class VisitDTOGrafik {


    private String patientName;
    private String patientSurname;
    private String date;
    private String time;

    public VisitDTOGrafik() {
    }

    public VisitDTOGrafik( String patientName, String patientSurname, String date, String time) {
    
        this.patientName = patientName;
        this.patientSurname = patientSurname;
        this.date = date;
        this.time = time;
    }

    public String getPatientName() {
        return patientName;
    }
    public void setPatientName(String patientName) {
        this.patientName = patientName;
    }
    public String getPatientSurname() {
        return patientSurname;
    }
    public void setPatientSurname(String patientSurname) {
        this.patientSurname = patientSurname;
    }
    public String getDate() {
        return date;
    }
    public void setDate(String date) {
        this.date = date;
    }
    public String getTime() {
        return time;
    }
    public void setTime(String time) {
        this.time = time;
    }

    
}
