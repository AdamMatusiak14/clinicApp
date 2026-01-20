package ad.clinic.model;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;

@Entity
@Table(name = "survey")
public class PatientData {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    Long id;
    int age;
    String sex; 
    String takingMedication;
    String pastIllnesses;
    String chronicDiseases;
    String vaccinations;  
    String allergies;
    String familyHistory;
    String smoking;
    String alcohol;
    @OneToOne(mappedBy = "patientData")
    private Patient patient;



   public  PatientData() {
        // Default constructor
    }   

    PatientData(Long id, int age, String sex, String takingMedication, 
                String pastIllnesses, String chronicDiseases, String vaccinations, 
                String allergies, String familyHistory, String smoking, String alcohol, Patient patient) {
        this.id = id;
        this.age = age; 
        this.sex = sex;
        this.takingMedication = takingMedication;   
        this.pastIllnesses = pastIllnesses;
        this.chronicDiseases = chronicDiseases; 
        this.vaccinations = vaccinations;
        this.allergies = allergies; 
        this.familyHistory = familyHistory;
        this.smoking = smoking;
        this.alcohol = alcohol;
        this.patient = patient;
    
}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public String getSex() {
        return  sex;
}

    public void setSex(String sex){
        this.sex = sex;
    }

    public String getTakingMedication() {
        return takingMedication;
    }

    public void setTakingMedication(String takingMedication) {
        this.takingMedication = takingMedication;
    }



    public String getPastIllnesses() {
        return pastIllnesses;
    }



    public void setPastIllnesses(String pastIllnesses) {
        this.pastIllnesses = pastIllnesses;
    }

    public String getChronicDiseases() {
        return chronicDiseases;
    }   

    public void setChronicDiseases(String chronicDiseases) {
        this.chronicDiseases = chronicDiseases;
    }

    public String getVaccinations() {
        return vaccinations;
    }

    public void setVaccinations(String vaccinations) {
        this.vaccinations = vaccinations;
    }

    public String getAllergies() {
        return allergies;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public String getFamilyHistory() {
        return familyHistory;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }

    public String getSmoking() {
        return smoking;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public String getAlcohol() {
        return alcohol;
    }   

    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }

    public Patient getPatient() {
        return patient;
    }

    public void setPatient(Patient patient) {
        this.patient = patient;
}



}

