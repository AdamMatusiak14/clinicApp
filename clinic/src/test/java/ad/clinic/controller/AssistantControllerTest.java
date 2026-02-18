package ad.clinic.controller;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.http.ResponseEntity;

import ad.clinic.DTO.Asisstant;
import ad.clinic.service.AssistantService;

@ExtendWith(MockitoExtension.class)
public class AssistantControllerTest {

    @Mock
    private AssistantService assistantService;

    @InjectMocks
    private AssitantController assitantController;

    private Asisstant assistant;

    @BeforeEach
    void setUp() {
        assistant = new Asisstant();
        assistant.setDescription("I have a headache and fever.");
        assistant.setResponse("flu");
    }   



    @Test
    public void testGetResponse() {
        // Given
        String description = assistant.getDescription();
        String expectedResponse = assistant.getResponse();
        
        when(assistantService.findDiease(description)).thenReturn(expectedResponse);

        // When
        ResponseEntity<String> response = assitantController.getResponse(new Asisstant(description, expectedResponse));

        // Then
        assert(response.getStatusCode().is2xxSuccessful());
        assert(response.getBody().equals(expectedResponse));
    }



    
}
