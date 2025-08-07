package ad.clinic.DTO;


public class PatientCardDTO {


    
    private String name;
    private String surname;
    private Long patientId;
    private String InfoPatient;

       
    public PatientCardDTO(String name, String surname, Long patientId, String InfoPatient) {
        this.name = name;
        this.surname = surname;
        this.patientId = patientId;
        this.InfoPatient = InfoPatient;
    }

    public PatientCardDTO() {
        // Default constructor
    }
    
    public String getName() {
        return name;
    }
 
    public void setName(String name) {
        this.name = name;
    }
    public String getSurname() {
        return surname;
    }
    public void setSurname(String surname) {
        this.surname = surname;
    }
    public Long getPatientId() {
        return patientId;
    }
    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }
    public String getInfoPatient() {
        return InfoPatient;
    }
    public void setInfoPatient(String infoPatient) {
        InfoPatient = infoPatient;
    }



}
