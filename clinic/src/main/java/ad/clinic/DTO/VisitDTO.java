package ad.clinic.DTO;

import java.time.LocalDate;
import java.time.LocalTime;

import org.springframework.cglib.core.Local;

public class VisitDTO {

    Long visitId;
    LocalDate date;
    LocalTime time;
    String info;
    String doctorName;
    String doctorSurname;


    public VisitDTO() {
    }

    public VisitDTO(Long visitId, LocalDate date, LocalTime time, String info, String doctorName, String doctorSurname) {
        this.visitId = visitId;
        this.date = date;
        this.time = time;
        this.info = info;
        this.doctorName = doctorName;
        this.doctorSurname = doctorSurname;
    }

    public Long getVisitId() {
        return visitId;
    }
    public void setVisitId(Long visitId) {
        this.visitId = visitId;
    }
    public LocalDate getDate() {
        return date;
    }
    public void setDate(LocalDate date) {
        this.date = date;
    }
    public LocalTime getTime() {
        return time;
    }
    public void setTime(LocalTime time) {
        this.time = time;
    }
    public String getInfo() {
        return info;
    }
    public void setInfo(String info) {
        this.info = info;
    }
    public String getDoctorName() {
        return doctorName;
    }
    public void setDoctorName(String doctorName) {
        this.doctorName = doctorName;
    }
    public String getDoctorSurname() {
        return doctorSurname;
    }
    public void setDoctorSurname(String doctorSurname) {
        this.doctorSurname = doctorSurname;
    }

    

    
}
