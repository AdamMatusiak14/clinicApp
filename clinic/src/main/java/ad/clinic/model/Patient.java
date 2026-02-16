package ad.clinic.model;

import java.util.ArrayList;
import java.util.List;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "patient")
public class Patient {
    
        @Id
        @GeneratedValue(strategy = GenerationType.IDENTITY)
        Long id;
        String email;
        String firstName;
        String lastName;
        String password;
        String role; 
        @Column(name = "info_patient")
        String infoPatient;
        
        @OneToOne(cascade = CascadeType.ALL)
        @JoinColumn(name = "patient_data_id", referencedColumnName = "id")
        private PatientData patientData;

        @OneToMany(mappedBy = "patient")
        private List<Visit> visits = new ArrayList<>(); // Assuming a Visit class exists for patient visits
        @OneToMany (mappedBy = "patient")
        private List<Prescription> prescriptions = new ArrayList<>(); // Assuming a Prescription class exists for patient prescriptions
       


    //String note; 

    public Patient() {
    }

    public Patient(Long id, String email, String firstName, String lastName, String password, String role, PatientData patientData, List<Visit> visits, List<Prescription> prescriptions, String infoPatient) {
        this.id = id;
        this.email = email;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.role = role;
        this.patientData = patientData;
        this.visits = visits;
        this.prescriptions = prescriptions;  
        this.infoPatient = infoPatient;
    
    }

    public Patient(Long id, String firstName, String lastName) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
    }

    public Long getId() {
        return id;
    }
    public void setId(Long id) {
        this.id = id;
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
    public String getPassword() {
        return password;
    }
    public void setPassword(String password) {
        this.password = password;
    }
    public String getRole() {
        return role;
    }
    public void setRole(String role) {
        this.role = role;
    }

    public PatientData getPatientData() {
        return patientData;
    }
    public void setPatientData(PatientData patientData) {
        this.patientData = patientData;
    }

    public List<Visit> getVisits() {
        return visits;
    }

    public void setVisits(List<Visit> visits) {
        this.visits = visits;
    }
    public List<Prescription> getPrescriptions() {
        return prescriptions;
    }
    public void setPrescriptions(List<Prescription> prescriptions) {
        this.prescriptions = prescriptions;
    }

 

    public String getInfoPatient() {
        return infoPatient;
    }
    public void setInfoPatient(String infoPatient) {
        this.infoPatient = infoPatient;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

   

}
