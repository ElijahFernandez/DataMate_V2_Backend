package com.capstone.datamate.Service;


import com.capstone.datamate.GeminiAPI.GeminiInterface;
import com.capstone.datamate.GeminiAPI.GeminiRecords;
import com.capstone.datamate.GeminiAPI.GeminiRecords.*;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class GeminiService {
    public static final String GEMINI_PRO = "gemini-pro";
    public static final String GEMINI_1_5_PRO = "gemini-1.5-pro-latest";
    public static final String GEMINI_PRO_VISION = "gemini-pro-vision";

    private final GeminiInterface geminiInterface;

    public GeminiService(GeminiInterface geminiInterface) {
        this.geminiInterface = geminiInterface;
    }

    public GeminiRecords.ModelList getModels() {
        return geminiInterface.getModels();
    }

    public GeminiCountResponse countTokens(String model, GeminiRequest request) {
        return geminiInterface.countTokens(model, request);
    }

    public int countTokens(String text) {
        GeminiCountResponse response = countTokens(GEMINI_1_5_PRO, new GeminiRequest(
                List.of(new Content(List.of(new TextPart(text))))));
        return response.totalTokens();
    }

    public GeminiResponse getCompletion(GeminiRequest request) {
        return geminiInterface.getCompletion(GEMINI_1_5_PRO, request);
    }

    public GeminiResponse getCompletionWithModel(String model, GeminiRequest request) {
        return geminiInterface.getCompletion(model, request);
    }

    public String getCompletion(String text) {
        GeminiResponse response = getCompletion(new GeminiRequest(
                List.of(new Content(List.of(new TextPart(text))))));
        return response.candidates().get(0).content().parts().get(0).text();
    }
}
