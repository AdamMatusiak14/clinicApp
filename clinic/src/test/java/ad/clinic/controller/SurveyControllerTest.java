package ad.clinic.controller;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import java.security.Principal;

import org.aspectj.lang.annotation.Before;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import ad.clinic.model.Patient;
import ad.clinic.model.PatientData;
import ad.clinic.service.PatientService;
import ad.clinic.service.SurveyService;
import jakarta.inject.Inject;

@ExtendWith(MockitoExtension.class)
public class SurveyControllerTest {
    
    @Mock
    private SurveyService surveyService;

    @Mock
    private Principal principal;

    @Mock
    private PatientService patientService;

    @InjectMocks
    private SurveyController surveyController;

    private Patient patient;
    private PatientData newPatientData;
    private PatientData existingPatientData;


    @BeforeEach
    void setUp() {
        patient = new Patient();
        patient.setId(1L);
        patient.setEmail("patient@example.com");
        patient.setFirstName("John");
        patient.setLastName("Doe");
        patient.setPassword("password");
        patient.setRole("PATIENT");
        patient.setInfoPatient("Some info");

        newPatientData = new PatientData();
        newPatientData.setId(1L);
        newPatientData.setAge(30);
        newPatientData.setSex("Male");
        newPatientData.setTakingMedication("No");
        newPatientData.setPastIllnesses("None");
        newPatientData.setChronicDiseases("None");
        newPatientData.setVaccinations("Up to date");
        newPatientData.setAllergies("Peanuts");
        newPatientData.setFamilyHistory("No significant history");
        newPatientData.setSmoking("No");
        newPatientData.setAlcohol("Occasionally");
        newPatientData.setPatient(patient);

        existingPatientData = new PatientData();
        existingPatientData.setId(2L);
        existingPatientData.setAge(35);
        existingPatientData.setSex("Female");
        existingPatientData.setTakingMedication("Yes");
        existingPatientData.setPastIllnesses("Flu");
        existingPatientData.setChronicDiseases("Asthma");
        existingPatientData.setVaccinations("Partially");
        existingPatientData.setAllergies("None");
        existingPatientData.setFamilyHistory("Diabetes");
        existingPatientData.setSmoking("Yes");
        existingPatientData.setAlcohol("Never");
        existingPatientData.setPatient(patient);

        when(principal.getName()).thenReturn("John");
        when(patientService.findByUsername("John")).thenReturn(patient);
    }

    @Test
    void testSaveSurveyNote_WhenPatientDataIsNull() {
        // Arrange
        patient.setPatientData(null);
        //when(patient.getPatientData()).thenReturn(null);

        // Act
        ResponseEntity<String> response = surveyController.saveSurveyNote(newPatientData, principal);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        
        // Verify service methods were called with correct parameters
        verify(patientService, times(1)).findByUsername("John");
        verify(surveyService, times(1)).saveSurveyNote(newPatientData);
        verify(patientService, times(1)).savePatient(patient);
        
        // Verify that patient's patientData was set
       // verify(patient, times(1)).setPatientData(newPatientData);
    }

    @Test
    void testSaveSurveyNote_WhenPatientDataExists() {
        // Arrange
        patient.setPatientData(existingPatientData);
      //  when(patient.getPatientData()).thenReturn(existingPatientData);

        PatientData updatedData = new PatientData();
        updatedData.setAge(40);
        updatedData.setSex("Male");
        updatedData.setTakingMedication("Yes");
        updatedData.setPastIllnesses("None");
        updatedData.setChronicDiseases("Diabetes");
        updatedData.setVaccinations("Up to date");
        updatedData.setAllergies("Lactose");
        updatedData.setFamilyHistory("Heart disease");
        updatedData.setSmoking("No");
        updatedData.setAlcohol("Occasionally");

        // Act
        ResponseEntity<String> response = surveyController.saveSurveyNote(updatedData, principal);

        // Assert
        assertEquals(200, response.getStatusCodeValue());
        
        // Verify service methods were called
        verify(patientService, times(1)).findByUsername("John");
        verify(surveyService, times(1)).saveSurveyNote(existingPatientData);
        verify(patientService, times(1)).savePatient(patient);
        
        // Verify that existing patientData fields were updated
        assertEquals(40, existingPatientData.getAge());
        assertEquals("Male", existingPatientData.getSex());
        assertEquals("Yes", existingPatientData.getTakingMedication());
        assertEquals("Diabetes", existingPatientData.getChronicDiseases());
        assertEquals("Lactose", existingPatientData.getAllergies());
    }

}
