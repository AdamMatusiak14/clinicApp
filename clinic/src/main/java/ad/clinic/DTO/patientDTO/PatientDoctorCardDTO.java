package ad.clinic.DTO.patientDTO;

public class PatientDoctorCardDTO {
    
    private Long id;
    private String firstName;
    private String lastName;
    private String infoPatient;
    private int age;
    private String sex;
    private String takingMedication;
    private String pastIllnesses;
    private String chronicDiseases;
    private String vaccinations;
    private String allergies;
    private String familyHistory;
    private String smoking;
    private String alcohol;

    public PatientDoctorCardDTO() {
    }

    

    public PatientDoctorCardDTO(Long id, String firstName, String lastName, String infoPatient, int age, String sex,
            String takingMedication, String pastIllnesses, String chronicDiseases, String vaccinations,
            String allergies, String familyHistory, String smoking, String alcohol) {
        this.id = id;
        this.firstName = firstName;
        this.lastName = lastName;
        this.infoPatient = infoPatient;
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
    }



    public void setId(Long id) {
        this.id = id;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public void setInfoPatient(String infoPatient) {
        this.infoPatient = infoPatient;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public void setSex(String sex) {
        this.sex = sex;
    }

    public void setTakingMedication(String takingMedication) {
        this.takingMedication = takingMedication;
    }

    public void setPastIllnesses(String pastIllnesses) {
        this.pastIllnesses = pastIllnesses;
    }

    public void setChronicDiseases(String chronicDiseases) {
        this.chronicDiseases = chronicDiseases;
    }

    public void setVaccinations(String vaccinations) {
        this.vaccinations = vaccinations;
    }

    public void setAllergies(String allergies) {
        this.allergies = allergies;
    }

    public void setFamilyHistory(String familyHistory) {
        this.familyHistory = familyHistory;
    }

    public void setSmoking(String smoking) {
        this.smoking = smoking;
    }

    public void setAlcohol(String alcohol) {
        this.alcohol = alcohol;
    }



    public Long getId() {
        return id;
    }



    public String getFirstName() {
        return firstName;
    }



    public String getLastName() {
        return lastName;
    }



    public String getInfoPatient() {
        return infoPatient;
    }



    public int getAge() {
        return age;
    }



    public String getSex() {
        return sex;
    }



    public String getTakingMedication() {
        return takingMedication;
    }



    public String getPastIllnesses() {
        return pastIllnesses;
    }



    public String getChronicDiseases() {
        return chronicDiseases;
    }



    public String getVaccinations() {
        return vaccinations;
    }



    public String getAllergies() {
        return allergies;
    }



    public String getFamilyHistory() {
        return familyHistory;
    }



    public String getSmoking() {
        return smoking;
    }



    public String getAlcohol() {
        return alcohol;
    }
    

    
    
    
}
