package com.capstone.datamate.Service;

import com.capstone.datamate.Entity.DatabaseEntity;
import com.capstone.datamate.OpenAI.OpenAIInterface;
import com.capstone.datamate.OpenAI.OpenAIRecords;
import com.capstone.datamate.OpenAI.OpenAIRecords.OpenAIRequest;
import com.capstone.datamate.OpenAI.OpenAIRecords.OpenAIResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Service;
import com.capstone.datamate.OpenAI.OpenAIRecords.Message;
import com.capstone.datamate.OpenAI.OpenAIRecords;
import org.springframework.web.client.RestTemplate;
import org.json.JSONObject;

import java.util.List;
import java.util.Map;

import static org.springframework.http.MediaType.*;

@Service
public class OpenAIService {
    public static final String GPT_3_5_TURBO = "gpt-3.5-turbo";
    public static final String GPT_4o_MINI = "gpt-4o-mini";
    private final OpenAIInterface openAIInterface;

    @Value("${openai.api.key}")
    private String openaiApiKey;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public OpenAIService(OpenAIInterface openAIInterface) {
        this.openAIInterface = openAIInterface;
    }

    public OpenAIResponse getCompletion(OpenAIRequest request) {
        return openAIInterface.getCompletion(request);
    }

    public String getCompletion(String text) {
        List<OpenAIRecords.Message> messages = List.of(new OpenAIRecords.Message("user", text));
        OpenAIRequest request = new OpenAIRequest(GPT_4o_MINI, messages);
        OpenAIResponse response = getCompletion(request);
        return response.choices().get(0).message().content();
    }

}