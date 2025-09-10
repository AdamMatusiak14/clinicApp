package ad.clinic.model;

import java.time.LocalDate;
import java.time.LocalTime;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;


@Entity
@Table(name = "visit")
public class Visit {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private LocalTime time;
    private LocalDate date;
    private String info; // ewentualna inforamacja od przychodni np. co zabrać na wizyte
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private Doctor doctor; // lekarz do którego jest wizyta
    @ManyToOne
    @JoinColumn(name = "patient_id")
    private Patient patient; // pacjent, który ma wizytę

    public Visit() {
        // Default constructor
    }

    public Visit(Long id, LocalTime time, LocalDate date, String info, Doctor doctor, Patient patient) {
        this.id = id;
        this.time = time;
        this.date = date;
        this.info = info;
        this.doctor = doctor;
        this.patient = patient;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public String getInfo() {
        return info;
    }

    public void setInfo(String info) {
        this.info = info;
    }

    public Doctor getDoctor() {
        return doctor;
    }

    public void setDoctor(Doctor doctor) { 
        this.doctor = doctor;
    }


    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
    }

    




    
}
