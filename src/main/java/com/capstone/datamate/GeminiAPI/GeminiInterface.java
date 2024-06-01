package com.capstone.datamate.GeminiAPI;

import com.capstone.datamate.GeminiAPI.GeminiRecords.GeminiCountResponse;
import com.capstone.datamate.GeminiAPI.GeminiRecords.GeminiRequest;
import com.capstone.datamate.GeminiAPI.GeminiRecords.GeminiResponse;
import com.capstone.datamate.GeminiAPI.GeminiRecords.ModelList;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.service.annotation.GetExchange;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;

@HttpExchange("/v1beta/models/")
public interface GeminiInterface {
    @GetExchange
    ModelList getModels();

    @PostExchange("{model}:countTokens")
    GeminiCountResponse countTokens(
            @PathVariable String model,
            @RequestBody GeminiRequest request);

    @PostExchange("{model}:generateContent")
    GeminiResponse getCompletion(
            @PathVariable String model,
            @RequestBody GeminiRequest request);
}
