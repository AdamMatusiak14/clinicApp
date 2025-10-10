package ad.clinic.service;

import org.springframework.stereotype.Service;

@Service
public class AssistantService {


    private final AIModelService aiService;

    public AssistantService( AIModelService aiService) {
        this.aiService= aiService;
      
    }

   public String  findDiease(String description) {
       
       String prediction = aiService.findDisease(description);
       System.out.print("Prediction w AssistantService: " + prediction);
        return prediction   ;
   }
}
