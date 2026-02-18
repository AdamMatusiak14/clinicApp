package ad.clinic.controller;

import ad.clinic.DTO.DoctorDTO;
import ad.clinic.DTO.DoctorNameDTO;
import ad.clinic.model.Doctor;
import ad.clinic.service.DoctorService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.DisplayName;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.security.Principal;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


@ExtendWith(MockitoExtension.class)
@DisplayName("DoctorController Tests")
class DoctorControllerTest {

    @Mock
    private DoctorService doctorService;

    @InjectMocks
    private DoctorController doctorController;

    private Doctor testDoctor1;
    private Doctor testDoctor2;

    @BeforeEach
    void setUp() {
        //MockitoAnnotations.openMocks(this);

        // Initialize test data
        testDoctor1 = new Doctor();
        testDoctor1.setId(1L);
        testDoctor1.setFirstName("John");
        testDoctor1.setLastName("Smith");
        testDoctor1.setPassword("password123");
        testDoctor1.setRole("ROLE_DOCTOR");
        testDoctor1.setAge(35);
        testDoctor1.setSpecialist("Cardiology");
        testDoctor1.setExperience("10 years");
        testDoctor1.setPhotoPath("/photos/doctor1.jpg");

        testDoctor2 = new Doctor();
        testDoctor2.setId(2L);
        testDoctor2.setFirstName("Jane");
        testDoctor2.setLastName("Doe");
        testDoctor2.setPassword("password456");
        testDoctor2.setRole("ROLE_DOCTOR");
        testDoctor2.setAge(28);
        testDoctor2.setSpecialist("Neurology");
        testDoctor2.setExperience("5 years");
        testDoctor2.setPhotoPath("/photos/doctor2.jpg");
    }

   

    @Test
    @DisplayName("Should retrieve all doctors successfully")
    void testGetAllDoctors_Success() {
        // Arrange
        List<Doctor> doctorList = Arrays.asList(testDoctor1, testDoctor2);
        when(doctorService.getAllDoctors()).thenReturn(doctorList);

        // Act
        ResponseEntity<List<DoctorDTO>> response = doctorController.getAllDoctors();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());
        
        DoctorDTO firstDoctor = response.getBody().get(0);
        assertEquals("John", firstDoctor.getFirstName());
        assertEquals("Smith", firstDoctor.getLastName());
        assertEquals("Cardiology", firstDoctor.getSpecialist());
        
        DoctorDTO secondDoctor = response.getBody().get(1);
        assertEquals("Jane", secondDoctor.getFirstName());
        assertEquals("Doe", secondDoctor.getLastName());

        verify(doctorService, times(1)).getAllDoctors();
    }

    @Test
    @DisplayName("Should return empty list when no doctors exist")
    void testGetAllDoctors_EmptyList() {
        // Arrange
        when(doctorService.getAllDoctors()).thenReturn(Arrays.asList());

        // Act
        ResponseEntity<List<DoctorDTO>> response = doctorController.getAllDoctors();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
        verify(doctorService, times(1)).getAllDoctors();
    }
    @Test
    @DisplayName("Should map Doctor to DoctorDTO correctly")
    void testGetAllDoctors_DoctorToDTOMappingCorrect() {
        // Arrange
        List<Doctor> doctorList = Arrays.asList(testDoctor1);
        when(doctorService.getAllDoctors()).thenReturn(doctorList);

        // Act
        ResponseEntity<List<DoctorDTO>> response = doctorController.getAllDoctors();
        DoctorDTO doctorDTO = response.getBody().get(0);

        // Assert
        assertEquals(testDoctor1.getFirstName(), doctorDTO.getFirstName());
        assertEquals(testDoctor1.getLastName(), doctorDTO.getLastName());
        assertEquals(testDoctor1.getPassword(), doctorDTO.getPassword());
        assertEquals(testDoctor1.getRole(), doctorDTO.getRole());
        assertEquals(testDoctor1.getAge(), doctorDTO.getAge());
        assertEquals(testDoctor1.getSpecialist(), doctorDTO.getSpecialist());
        assertEquals(testDoctor1.getExperience(), doctorDTO.getExperience());
        assertEquals(testDoctor1.getPhotoPath(), doctorDTO.getPhotoPath());
    }

    @Test
    @DisplayName("Should retrieve all doctor names successfully")
    void testGetAllDoctorNames_Success() {
        // Arrange
        List<Doctor> doctorList = Arrays.asList(testDoctor1, testDoctor2);
        when(doctorService.getAllDoctors()).thenReturn(doctorList);

        // Act
        ResponseEntity<List<DoctorNameDTO>> response = doctorController.getAllDoctorNames();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(2, response.getBody().size());

        DoctorNameDTO firstDoctor = response.getBody().get(0);
        assertEquals(1L, firstDoctor.getId());
        assertEquals("John", firstDoctor.getFirstName());
        assertEquals("Smith", firstDoctor.getLastName());

        DoctorNameDTO secondDoctor = response.getBody().get(1);
        assertEquals(2L, secondDoctor.getId());
        assertEquals("Jane", secondDoctor.getFirstName());
        assertEquals("Doe", secondDoctor.getLastName());

        verify(doctorService, times(1)).getAllDoctors();
    }

    @Test
    @DisplayName("Should return empty list of doctor names when no doctors exist")
    void testGetAllDoctorNames_EmptyList() {
        // Arrange
        when(doctorService.getAllDoctors()).thenReturn(Arrays.asList());

        // Act
        ResponseEntity<List<DoctorNameDTO>> response = doctorController.getAllDoctorNames();

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertTrue(response.getBody().isEmpty());
        verify(doctorService, times(1)).getAllDoctors();
    }

    @Test
    @DisplayName("Should map Doctor to DoctorNameDTO correctly")
    void testGetAllDoctorNames_DoctorToNameDTOMappingCorrect() {
        // Arrange
        List<Doctor> doctorList = Arrays.asList(testDoctor1);
        when(doctorService.getAllDoctors()).thenReturn(doctorList);

        // Act
        ResponseEntity<List<DoctorNameDTO>> response = doctorController.getAllDoctorNames();
        DoctorNameDTO doctorNameDTO = response.getBody().get(0);

        // Assert
        assertEquals(testDoctor1.getId(), doctorNameDTO.getId());
        assertEquals(testDoctor1.getFirstName(), doctorNameDTO.getFirstName());
        assertEquals(testDoctor1.getLastName(), doctorNameDTO.getLastName());
    }


    // DOCTOR PANEL 6 TESTÓW
    @Test
    @DisplayName("Should get doctor panel with authenticated principal")
    void testGetDoctorPanel_Success() {
        // Arrange
        Principal principal = mock(Principal.class);
        when(principal.getName()).thenReturn("doctor@example.com");

        // Act
        ResponseEntity<?> response = doctorController.getDoctorPanel(principal);

        // Assert
        assertNotNull(response);
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Welcome to the Doctor's Panel!", response.getBody());
        verify(principal, times(1)).getName();
    }

    

    @Test
    @DisplayName("Should handle different principal usernames")
    void testGetDoctorPanel_DifferentPrincipals() {
        // Arrange
        Principal principal1 = mock(Principal.class);
        when(principal1.getName()).thenReturn("doctor1@clinic.com");
        
        Principal principal2 = mock(Principal.class);
        when(principal2.getName()).thenReturn("doctor2@clinic.com");

        // Act
        ResponseEntity<?> response1 = doctorController.getDoctorPanel(principal1);
        ResponseEntity<?> response2 = doctorController.getDoctorPanel(principal2);

        // Assert
        assertEquals(HttpStatus.OK, response1.getStatusCode());
        assertEquals(HttpStatus.OK, response2.getStatusCode());
        assertEquals(response1.getBody(), response2.getBody());
    }

}
