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

import ad.clinic.model.Doctor;
import ad.clinic.repository.DoctorRepository;

@ExtendWith(MockitoExtension.class)
public class DoctorServiceTest {

    @Mock
    private DoctorRepository doctorRepository;

    @InjectMocks
    private DoctorService doctorService;

    private Doctor doctor;

    @BeforeEach
    void setUp() {
        doctor = new Doctor();
        doctor.setId(1L);
        doctor.setEmail("doctor@example.com");
        doctor.setFirstName("John");
        doctor.setLastName("Doe");
        doctor.setPassword("password");
        doctor.setRole("DOCTOR");
        doctor.setAge(30);
        doctor.setSpecialist("Cardiology");
        doctor.setExperience("5 years");
        doctor.setPhotoPath("/path/to/photo");
    }

    @Test
    void testGetAllDoctors() {
        List<Doctor> doctors = Arrays.asList(doctor);
        when(doctorRepository.findAll()).thenReturn(doctors);

        List<Doctor> result = doctorService.getAllDoctors();

        assertEquals(doctors, result);
        verify(doctorRepository, times(1)).findAll();
    }

    @Test
    void testGetDoctorById_Success() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.of(doctor));

        Doctor result = doctorService.getDoctorById(1L);

        assertEquals(doctor, result);
        verify(doctorRepository, times(1)).findById(1L);
    }

    @Test
    void testGetDoctorById_NotFound() {
        when(doctorRepository.findById(1L)).thenReturn(Optional.empty());

        RuntimeException exception = assertThrows(RuntimeException.class, () -> {
            doctorService.getDoctorById(1L);
        });

        assertEquals("Doctor not found with id: 1", exception.getMessage());
        verify(doctorRepository, times(1)).findById(1L);
    }

    @Test
    void testSaveDoctor() {
        when(doctorRepository.save(doctor)).thenReturn(doctor);

        Doctor result = doctorService.saveDoctor(doctor);

        assertEquals(doctor, result);
        verify(doctorRepository, times(1)).save(doctor);
    }

    @Test
    void testDeleteDoctor() {
        doNothing().when(doctorRepository).deleteById(1L);

        doctorService.deleteDoctor(1L);

        verify(doctorRepository, times(1)).deleteById(1L);
    }

    @Test
    void testFindDoctorByUsername() {
        when(doctorRepository.findDoctorByEmail("doctor@example.com")).thenReturn(Optional.of(doctor));

        Optional<Doctor> result = doctorService.findDoctorByUsername("doctor@example.com");

        assertTrue(result.isPresent());
        assertEquals(doctor, result.get());
        verify(doctorRepository, times(1)).findDoctorByEmail("doctor@example.com");
    }

    @Test
    void testFindDoctorByEmail_Success() {
        when(doctorRepository.findDoctorByEmail("doctor@example.com")).thenReturn(Optional.of(doctor));

        Doctor result = doctorService.findDoctorByEmail("doctor@example.com");

        assertEquals(doctor, result);
        verify(doctorRepository, times(1)).findDoctorByEmail("doctor@example.com");
    }

    @Test
    void testFindDoctorByEmail_NotFound() {
        when(doctorRepository.findDoctorByEmail("nonexistent@example.com")).thenReturn(Optional.empty());

        UsernameNotFoundException exception = assertThrows(UsernameNotFoundException.class, () -> {
            doctorService.findDoctorByEmail("nonexistent@example.com");
        });

        assertEquals("Doctor not found with email: nonexistent@example.com", exception.getMessage());
        verify(doctorRepository, times(1)).findDoctorByEmail("nonexistent@example.com");
    }
}
