package ad.clinic.service;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import jakarta.inject.Inject;

@ExtendWith(MockitoExtension.class)
public class AssistanceServiceTest {

    @Mock
    private AIModelService aiModelService;

    @InjectMocks
    private AssistantService assistantService;


 
    @Test
    void testFindDisease() {
        String description = "Patient has a sore throat and cough.";
        String expectedPrediction = "Predicted disease: Common Cold";

        when(aiModelService.findDisease(description)).thenReturn(expectedPrediction);

        String actualPrediction = assistantService.findDiease(description);

        assertEquals(expectedPrediction, actualPrediction);
        verify(aiModelService, times(1)).findDisease(description);
    }
    
}
