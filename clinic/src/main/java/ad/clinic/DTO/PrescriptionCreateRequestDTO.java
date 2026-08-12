package ad.clinic.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;

public class PrescriptionCreateRequestDTO {
    private Long patientId;

    @NotBlank(message = "Medicine cannot be blank")
    @Size(min = 2, max=100, message = "Medicine must be between 2 and 100 characters")
    private String medicine;

    // Getters and setters
    public Long getPatientId() {
        return patientId;
    }

    public void setPatientId(Long patientId) {
        this.patientId = patientId;
    }

    public String getMedicine() {
        return medicine;
    }

    public void setMedicine(String medicine) {
        this.medicine = medicine;
    }
}