package ad.clinic.DTO.patientDTO;

import ad.clinic.model.Patient;

public class PatientCredentailMapperDTO {
    public static PatientCredentialDTO map (Patient patient) {
        PatientCredentialDTO dto = new PatientCredentialDTO();
        dto.setUsername(patient.getFirstName());
        dto.setPassword(patient.getLastName());
        dto.setRole(patient.getRole());
        return dto;
    }
    
}
