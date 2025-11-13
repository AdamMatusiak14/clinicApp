package ad.clinic.DTO.doctorDTO;

import org.springframework.stereotype.Service;

import ad.clinic.model.Doctor;

@Service
public class DoctorCredentialMapperDTO {

    public static DoctorCredentialDTO map (Doctor doctor) {
        DoctorCredentialDTO dto = new DoctorCredentialDTO();
        dto.setUsername(doctor.getFirstName());
        dto.setPassword(doctor.getLastName());
        dto.setRole(doctor.getRole());
        return dto;
    }
    
}
