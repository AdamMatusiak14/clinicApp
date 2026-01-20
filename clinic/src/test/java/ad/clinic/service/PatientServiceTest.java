package ad.clinic.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;

import ad.clinic.DTO.PatientDTO;
import ad.clinic.model.Patient;
import ad.clinic.repository.PatientRepository;

@ExtendWith(MockitoExtension.class)
public class PatientServiceTest {

    @Mock
    private PatientRepository patientRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @InjectMocks
    private PatientService patientService;

    private Patient patient;
    private PatientDTO patientDTO;

    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1L);
        patient.setEmail("patient@example.com");
        patient.setFirstName("Jane");
        patient.setLastName("Doe");
        patient.setPassword("password");
        patient.setRole("PATIENT");
        patient.setInfoPatient("Some info");

        patientDTO = new PatientDTO();
        patientDTO.setId(1L);
        patientDTO.setFirstName("Jane");
        patientDTO.setLastName("Doe");
    }

    @Test
    void testRegisterPatient() {
        when(passwordEncoder.encode("password")).thenReturn("encodedPassword");
        when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        patientService.registerPatient(patient);

        assertEquals("PATIENT", patient.getRole());
        assertEquals("encodedPassword", patient.getPassword());
        verify(passwordEncoder, times(1)).encode("password");
        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    void testVerifyPatient_Found() {
        when(patientRepository.findByFirstNameAndLastName("Jane", "Doe")).thenReturn(Optional.of(patient));

        Patient result = patientService.verifyPatient(patientDTO);

        assertEquals(patient, result);
        verify(patientRepository, times(1)).findByFirstNameAndLastName("Jane", "Doe");
    }

    @Test
    void testVerifyPatient_NotFound() {
        when(patientRepository.findByFirstNameAndLastName("Jane", "Doe")).thenReturn(Optional.empty());

        Patient result = patientService.verifyPatient(patientDTO);

        assertNull(result);
        verify(patientRepository, times(1)).findByFirstNameAndLastName("Jane", "Doe");
    }

    @Test
    void testFindPatient_Found() {
        when(patientRepository.findByFirstNameAndLastName("Jane", "Doe")).thenReturn(Optional.of(patient));

        Patient result = patientService.findPatient(patientDTO);

        assertEquals(patient, result);
        verify(patientRepository, times(1)).findByFirstNameAndLastName("Jane", "Doe");
    }

    @Test
    void testFindPatient_NotFound() {
        when(patientRepository.findByFirstNameAndLastName("Jane", "Doe")).thenReturn(Optional.empty());

        Patient result = patientService.findPatient(patientDTO);

        assertNull(result);
        verify(patientRepository, times(1)).findByFirstNameAndLastName("Jane", "Doe");
    }

    @Test
    void testFindById_Found() {
        when(patientRepository.findById(1L)).thenReturn(Optional.of(patient));

        Patient result = patientService.findById(1L);

        assertEquals(patient, result);
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    void testFindById_NotFound() {
        when(patientRepository.findById(1L)).thenReturn(Optional.empty());

        Patient result = patientService.findById(1L);

        assertNull(result);
        verify(patientRepository, times(1)).findById(1L);
    }

    @Test
    void testSavePatient() {
       when(patientRepository.save(any(Patient.class))).thenReturn(patient);

        patientService.savePatient(patient);

        verify(patientRepository, times(1)).save(patient);
    }

    @Test
    void testFindPatientByfirstNameAndLastName_Found() {
        when(patientRepository.findByFirstNameAndLastName("Jane", "Doe")).thenReturn(Optional.of(patient));

        Patient result = patientService.findPatientByfirstNameAndLastName("Jane", "Doe");

        assertEquals(patient, result);
        verify(patientRepository, times(1)).findByFirstNameAndLastName("Jane", "Doe");
    }

    @Test
    void testFindPatientByfirstNameAndLastName_NotFound() {
        when(patientRepository.findByFirstNameAndLastName("Jane", "Doe")).thenReturn(Optional.empty());

        Patient result = patientService.findPatientByfirstNameAndLastName("Jane", "Doe");

        assertNull(result);
        verify(patientRepository, times(1)).findByFirstNameAndLastName("Jane", "Doe");
    }

    @Test
    void testFindPatientByUsername() {
        when(patientRepository.findPatientByEmail("patient@example.com")).thenReturn(Optional.of(patient));

        Optional<Patient> result = patientService.findPatientByUsername("patient@example.com");

        assertTrue(result.isPresent());
        assertEquals(patient, result.get());
        verify(patientRepository, times(1)).findPatientByEmail("patient@example.com");
    }

    @Test
    void testFindPatientByEmail_Found() {
        when(patientRepository.findPatientByEmail("patient@example.com")).thenReturn(Optional.of(patient));

        Patient result = patientService.findPatientByEmail("patient@example.com");

        assertEquals(patient, result);
        verify(patientRepository, times(1)).findPatientByEmail("patient@example.com");
    }

    @Test
    void testFindPatientByEmail_NotFound() {
        when(patientRepository.findPatientByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        Patient result = patientService.findPatientByEmail("nonexistent@example.com");

        assertNull(result);
        verify(patientRepository, times(1)).findPatientByEmail("nonexistent@example.com");
    }

    @Test
    void testFindByUsername_Success() {
        when(patientRepository.findPatientByEmail("patient@example.com")).thenReturn(Optional.of(patient));

        Patient result = patientService.findByUsername("patient@example.com");

        assertEquals(patient, result);
        verify(patientRepository, times(1)).findPatientByEmail("patient@example.com");
    }

    @Test
    void testFindByUsername_NotFound() {
        when(patientRepository.findPatientByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            patientService.findByUsername("nonexistent@example.com");
        });

        assertEquals("Patient not found with username: nonexistent@example.com", exception.getMessage());
        verify(patientRepository, times(1)).findPatientByEmail("nonexistent@example.com");
    }

    @Test
    void testGetAllPatients() {
        List<Patient> patients = Arrays.asList(patient);
        when(patientRepository.findAll()).thenReturn(patients);

        List<PatientDTO> result = patientService.getAllPatients();

        assertEquals(1, result.size());
        assertEquals("Jane", result.get(0).getFirstName());
        assertEquals("Doe", result.get(0).getLastName());
        verify(patientRepository, times(1)).findAll();
    }
}
