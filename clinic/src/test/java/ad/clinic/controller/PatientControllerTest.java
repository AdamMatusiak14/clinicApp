package ad.clinic.controller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UserDetails;

import ad.clinic.DTO.PatientCardDTO;
import ad.clinic.DTO.PatientDTO;
import ad.clinic.DTO.patientDTO.PatientDoctorCardDTO;
import ad.clinic.model.Patient;
import ad.clinic.model.PatientData;
import ad.clinic.service.PatientService;
import ad.clinic.service.SurveyService;

@ExtendWith(MockitoExtension.class)
public class PatientControllerTest {
    
    @Mock
    private PatientService patientService;

    @Mock
    private SurveyService surveyService;

    @InjectMocks
    private PatientController patientController;

    private Patient testPatient1;
    private Patient testPatient2;
    private PatientDTO patientDTO1;
    private PatientDTO patientDTO2;
    private PatientCardDTO patientCardDTO1;
    private PatientData survey;

    
@BeforeEach
    void setUp() {

        testPatient1 = new Patient();
        testPatient1.setId(1L);
        testPatient1.setEmail("alice@clinic.com");
        testPatient1.setFirstName("Alice");
        testPatient1.setLastName("Johnson");
        testPatient1.setPassword("alice123");
        testPatient1.setRole("ROLE_PATIENT");

        testPatient2 = new Patient();
        testPatient2.setId(2L);
        testPatient2.setEmail("bob@clinic.com");
        testPatient2.setFirstName("Bob");
        testPatient2.setLastName("Williams");
        testPatient2.setPassword("bob123");
        testPatient2.setRole("ROLE_PATIENT");

        patientDTO1 = new PatientDTO();
        patientDTO1.setId(1L);
        patientDTO1.setFirstName("Alice");
        patientDTO1.setLastName("Johnson");

        patientDTO2 = new PatientDTO();
        patientDTO2.setId(2L);
        patientDTO2.setFirstName("Bob");
        patientDTO2.setLastName("Williams");

        patientCardDTO1 = new PatientCardDTO();
        patientCardDTO1.setPatientId(1L);
        patientCardDTO1.setName("Alice");
        patientCardDTO1.setSurname("Johnson");
        patientCardDTO1.setInfoPatient("No known allergies.");

        survey = new PatientData();
        survey.setId(1L);
        survey.setAge(30);
        survey.setSex("female");
        survey.setTakingMedication("no");
        survey.setPastIllnesses("no");
        survey.setChronicDiseases("no");
        survey.setVaccinations("up to date");
        survey.setAllergies("peanuts");
        survey.setFamilyHistory("no");
        survey.setSmoking("yes");
        survey.setAlcohol("yes");
        survey.setPatient(testPatient1);
        testPatient1.setPatientData(survey);
        survey.getPatient().setId(1L);
            }

    // Add test methods here
    @Test
    void resgisterPatient_ShouldRegisterSuccessfully() {

        doNothing().when(patientService).registerPatient(testPatient1);

       ResponseEntity <String> response =  patientController.registerPatient(testPatient1);   
       // Patient registered successfully

       assertEquals(200, response.getStatusCodeValue());
     

        verify(patientService, times(1)).registerPatient(testPatient1);
       
    }   

    @Test
    void registerPatient_ShouldRegisterFailure() {

        doThrow(new RuntimeException("Database error")).when(patientService).registerPatient(testPatient2);

       ResponseEntity <String> response =  patientController.registerPatient(testPatient2);   
       // Patient registered successfully

       assertEquals(500, response.getStatusCodeValue());
       assertEquals("Error registering patient: Database error", response.getBody());

        verify(patientService, times(1)).registerPatient(testPatient2);
       
    }   

    @Test
    void getAllPatients_ShouldReturnPatientList() {

        List<PatientDTO> mockPatientList = Arrays.asList(patientDTO1, patientDTO2);
        when(patientService.getAllPatients()).thenReturn(mockPatientList);

        ResponseEntity<List<PatientDTO>> response = patientController.getAllPatients();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(2, response.getBody().size());
        assertEquals("Alice", response.getBody().get(0).getFirstName());
        assertEquals("Bob", response.getBody().get(1).getFirstName());

        verify(patientService, times(1)).getAllPatients();
    }

    @Test
    void getAllPatients_ShouldReturnEmptyList() {

        when(patientService.getAllPatients()).thenReturn(Arrays.asList());

        ResponseEntity<List<PatientDTO>> response = patientController.getAllPatients();

        assertEquals(200, response.getStatusCodeValue());
        assertEquals(0, response.getBody().size());

        verify(patientService, times(1)).getAllPatients();
    }   

    @Test
    void verifyPatient_ShouldReturnPatient_WhenExists() {

        when(patientService.verifyPatient(patientDTO1)).thenReturn(testPatient1);

        ResponseEntity<Patient> response = patientController.verifyPatient(patientDTO1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Alice", response.getBody().getFirstName());

        verify(patientService, times(1)).verifyPatient(patientDTO1);
    }

    @Test
    void verifyPatient_ShouldReturnNotFound_WhenDoesNotExist() {

        when(patientService.verifyPatient(patientDTO2)).thenReturn(null);

        ResponseEntity<Patient> response = patientController.verifyPatient(patientDTO2);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals(null, response.getBody());

        verify(patientService, times(1)).verifyPatient(patientDTO2);
    }

    @Test
    void findPatient_ShouldReturnPatientDTO_WhenExists() {
        when(patientService.findPatient(patientDTO1)).thenReturn(testPatient1);

        ResponseEntity<PatientDTO> response = patientController.findPatient(patientDTO1);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Alice", response.getBody().getFirstName());

        verify(patientService, times(1)).findPatient(patientDTO1);

}

    @Test
    void findPatient_ShouldReturnNotFound_WhenDoesNotExist() {
        when(patientService.findPatient(patientDTO2)).thenReturn(null);

        ResponseEntity<PatientDTO> response = patientController.findPatient(patientDTO2);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals(null, response.getBody());

        verify(patientService, times(1)).findPatient(patientDTO2);
    }

    @Test
    void getPatientCard_ShouldReturnPatientCardDTO_WhenPatientExists() {
        when(patientService.findById(1L)).thenReturn(testPatient1);

        ResponseEntity<PatientCardDTO> response = patientController.getPatientCard(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Alice", response.getBody().getName());
        assertEquals("Johnson", response.getBody().getSurname());

        verify(patientService, times(1)).findById(1L);
    }

    @Test
    void getPatientCard_ShouldReturnNotFound_WhenPatientDoesNotExist() {
        when(patientService.findById(2L)).thenReturn(null);

        ResponseEntity<PatientCardDTO> response = patientController.getPatientCard(2L);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals(null, response.getBody());

        verify(patientService, times(1)).findById(2L);
    }

    @Test
    void getCurrentPatientCard_ShouldReturnPatientCardDTO_WhenPatientExists() {
        when(patientService.findPatientByEmail("alice@clinic.com")).thenReturn(testPatient1);

        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("alice@clinic.com");
        ResponseEntity<PatientCardDTO> response = patientController.getCurrentPatientCard(userDetails);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Alice", response.getBody().getName());    
        assertEquals("Johnson", response.getBody().getSurname());
        verify(patientService, times(1)).findPatientByEmail("alice@clinic.com");

}

    @Test
    void getCurrentPatientCard_ShouldReturnNotFound_WhenPatientDoesNotExist() {
        when(patientService.findPatientByEmail("alice@clinic.com")).thenReturn(null);
        UserDetails userDetails = mock(UserDetails.class);
        when(userDetails.getUsername()).thenReturn("alice@clinic.com");
        ResponseEntity<PatientCardDTO> response = patientController.getCurrentPatientCard(userDetails);

        assertEquals(404, response.getStatusCodeValue());
        assertEquals(null, response.getBody());
        verify(patientService, times(1)).findPatientByEmail("alice@clinic.com");
    }

    @Test
    void getPatientCardForDoctor_Success() {
        when(patientService.findById(1L)).thenReturn(testPatient1);
        when(surveyService.getSurveyByID(testPatient1.getPatientData().getId())).thenReturn(survey);


        ResponseEntity<PatientDoctorCardDTO> response = patientController.getPatientCardForDoctor(1L);

        assertEquals(200, response.getStatusCodeValue());
        assertEquals("Alice", response.getBody().getFirstName());
        assertEquals("Johnson", response.getBody().getLastName());
        assertEquals(30, response.getBody().getAge());
        assertEquals("female", response.getBody().getSex());


        verify(patientService, times(1)).findById(1L);


}

@Test
void getPatientCardForDoctor_PatientNotFound() {
    when(patientService.findById(2L)).thenReturn(null);


    ResponseEntity<PatientDoctorCardDTO> response = patientController.getPatientCardForDoctor(2L);

    assertEquals(404, response.getStatusCodeValue());
    assertEquals(null, response.getBody());

    verify(patientService, times(1)).findById(2L);
}

}