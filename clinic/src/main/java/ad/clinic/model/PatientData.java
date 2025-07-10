package ad.clinic.model;

public class PatientData {

    Long id;
    int age;
    String sex; 
    String takingMedication;
    String pastIllnesses;
    String chronicDiseases;
    String vacations;
    String allergies;
    String familyHistory;
    String smoking;
    String alcohol;



    PatientData() {
        // Default constructor
    }   

    PatientData(Long id, int age, String sex, String takingMedication, 
                String pastIllnesses, String chronicDiseases, String vacations, 
                String allergies, String familyHistory, String smoking, String alcohol) {
        this.id = id;
        this.age = age; 
        this.sex = sex;
        this.takingMedication = takingMedication;   
        this.pastIllnesses = pastIllnesses;
        this.chronicDiseases = chronicDiseases; 
        this.vacations = vacations;
        this.allergies = allergies; 
        this.familyHistory = familyHistory;
        this.smoking = smoking;
        this.alcohol = alcohol;
    
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

    public void setSex(){
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

    public String getVacations() {
        return vacations;
    }

    public void setVacations(String vacations) {
        this.vacations = vacations;
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
}