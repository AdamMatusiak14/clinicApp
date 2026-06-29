package ad.clinic.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

@Service
public class AssistantService {

    private static final Logger logger = LoggerFactory.getLogger(AssistantService.class);
    private final AIModelService aiService;

    public AssistantService( AIModelService aiService) {
        this.aiService= aiService;
      
    }

   public String  findDiease(String description) {
       
       String prediction = aiService.findDisease(description);
       logger.debug("Prediction in AssistantService: {}", prediction);
       return prediction;
   }
}
