package ad.clinic.service;

import java.util.List;
import java.util.Optional;

import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import ad.clinic.DTO.doctorDTO.DoctorCredentialDTO;
import ad.clinic.DTO.doctorDTO.DoctorCredentialMapperDTO;
import ad.clinic.model.Doctor;
import ad.clinic.repository.DoctorRepository;

@Service
public class DoctorService {

    private final DoctorRepository doctorRepository;

    public DoctorService(DoctorRepository doctorRepository) {
        this.doctorRepository = doctorRepository;
    }

    public List<Doctor> getAllDoctors() {
        return doctorRepository.findAll();
    }

    public Doctor getDoctorById(Long id) {   // albo Optional
        return doctorRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Doctor not found with id: " + id));
                
    }

    public Doctor saveDoctor(Doctor doctor) {
        return doctorRepository.save(doctor);
    }

    public void deleteDoctor(Long id) {
        doctorRepository.deleteById(id);
    }   

    public Optional <Doctor> findDoctorByUsername(String username) {
       return doctorRepository.findDoctorByEmail(username);  

        }
       
    
    
}
