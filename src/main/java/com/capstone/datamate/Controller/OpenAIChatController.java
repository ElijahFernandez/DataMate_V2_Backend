package com.capstone.datamate.Controller;

import com.capstone.datamate.Service.OpenAIService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.Map;

@RestController
public class OpenAIChatController {
    @Autowired
    private OpenAIService openAIService;
    @Value("${openai.api.key}")
    private String openaiApiKey;

    @PostMapping("/api/openai/chat")
    public ResponseEntity<String> chatWithOpenAI(@RequestBody Map<String, String> request) {
        String userMessage = request.get("message");

        // Define the payload for OpenAI API request
        String openaiApiUrl = "https://api.openai.com/v1/chat/completions";
        String payload = "{\n" +
                "  \"model\": \"gpt-3.5-turbo\",\n" +
                "  \"messages\": [\n" +
                "    {\"role\": \"system\", \"content\": \"You are a helpful assistant.\"},\n" +
                "    {\"role\": \"user\", \"content\": \"" + userMessage + "\"}\n" +
                "  ]\n" +
                "}";

        RestTemplate restTemplate = new RestTemplate();

        // Set up headers with your OpenAI API key
        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON);
        headers.setBearerAuth(openaiApiKey);

        HttpEntity<String> entity = new HttpEntity<>(payload, headers);

        try {
            // Make the API request
            ResponseEntity<String> response = restTemplate.postForEntity(openaiApiUrl, entity, String.class);
            return ResponseEntity.ok(response.getBody());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to communicate with OpenAI");
        }
    }
}
