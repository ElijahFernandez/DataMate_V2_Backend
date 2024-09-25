package com.capstone.datamate.Service;

import com.capstone.datamate.OpenAI.OpenAIInterface;
import com.capstone.datamate.OpenAI.OpenAIRecords.OpenAIRequest;
import com.capstone.datamate.OpenAI.OpenAIRecords.OpenAIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.capstone.datamate.OpenAI.OpenAIRecords.Message;

import java.util.List;


@Service
public class OpenAIService {
    public static final String MODEL = "gpt-4o-mini";
    private final OpenAIInterface openAIInterface;

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public OpenAIService(OpenAIInterface openAIInterface) {
        this.openAIInterface = openAIInterface;
    }

    // Method to customize your request using preset values
    public OpenAIResponse getCompletion(OpenAIRequest request) {
        return openAIInterface.getCompletion(request);
    }

    public String getCompletion(String text) {

        List<Message> messages = getMessages(text);

        // Preset OpenAIRequest with system and user message
        OpenAIRequest request = new OpenAIRequest(
                MODEL,
                messages,
                0.7,                      // Temperature (preset value)
                100,                      // Max tokens (preset value)
                1,                        // Top P (preset value)
                0,                        // Frequency penalty (preset value)
                0                         // Presence penalty (preset value)
        );

        // Make the API call and get the response
        OpenAIResponse response = getCompletion(request);
        return response.choices().get(0).message().content();
    }

    private static List<Message> getMessages(String text) {
        String systemMessage = "You are a JSON assistant. Given an array of headers, return their associated " +
                "input types. If a header likely represents an ID or primary key, return 'ID'. Examples: " +
                "Input: company_id, username, birthdate, salary, profile_url, hasCompletedTraining. Output: " +
                "ID, text, date, number, url, checkbox.";

        // Messages list includes both system message and user message
        return List.of(
                new Message("system", systemMessage),  // System instruction
                new Message("user", text)  // User input (headers)
        );
    }
}

