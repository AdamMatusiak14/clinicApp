package ad.clinic.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

public class VisitFrontDTO {

    String firstName;
    String lastName;
    Long doctor;
    String date;
    String time;


    public VisitFrontDTO() {
    }

    public VisitFrontDTO(String firstName, String lastName, Long doctor, String date, String time) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.doctor = doctor;
        this.date = date;
        this.time = time;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public Long getDoctor() {
        return doctor;
    }

    public void setDoctor(Long doctor_id) {
        this.doctor = doctor_id;
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
