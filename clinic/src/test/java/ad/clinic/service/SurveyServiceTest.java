package ad.clinic.service;

import static org.mockito.Mockito.*;
import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import ad.clinic.model.PatientData;
import ad.clinic.repository.SurveyRepository;
import jakarta.inject.Inject;

@ExtendWith(MockitoExtension.class)
public class SurveyServiceTest {

    @Mock
    private SurveyRepository surveyRepository;

    @InjectMocks
    private SurveyService surveyService;

    private PatientData patientData;

    @BeforeEach
    void setUp() {
        patientData = new PatientData();
        patientData.setId(1L);
        patientData.setAge(30);
        patientData.setSex("Male");
        patientData.setTakingMedication("No");
        patientData.setPastIllnesses("None");
        patientData.setChronicDiseases("None");
        patientData.setVaccinations("Up to date");
        patientData.setAllergies("None");
        patientData.setFamilyHistory("No significant history");
        patientData.setSmoking("No");
        patientData.setAlcohol("Occasionally");
    }

    @Test
    void testSaveSurveyNote() {
        when(surveyRepository.save(patientData)).thenReturn(patientData);

        String result = surveyService.saveSurveyNote(patientData);

        verify(surveyRepository, times(1)).save(patientData);
        assertEquals("Zapisano poprawnie", result);
    } 

    @Test
    void testGetSurveyByID_Success() {
        when(surveyRepository.findById(1L)).thenReturn(java.util.Optional.of(patientData));

        PatientData result = surveyService.getSurveyByID(1L);

        verify(surveyRepository, times(1)).findById(1L);
        assertEquals(patientData, result);
    }

    @Test
    void testGetSurveyByID_NotFound() {
        when(surveyRepository.findById(1L)).thenReturn(java.util.Optional.empty());

        Exception exception = assertThrows(RuntimeException.class, () -> {
            surveyService.getSurveyByID(1L);
        });

        verify(surveyRepository, times(1)).findById(1L);
        assertEquals("Survey not found", exception.getMessage());
    }
}
