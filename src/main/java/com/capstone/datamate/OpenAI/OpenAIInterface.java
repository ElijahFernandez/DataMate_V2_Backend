package com.capstone.datamate.OpenAI;

import com.capstone.datamate.OpenAI.OpenAIRecords.OpenAIRequest;
import com.capstone.datamate.OpenAI.OpenAIRecords.OpenAIResponse;
import org.springframework.web.service.annotation.HttpExchange;
import org.springframework.web.service.annotation.PostExchange;
import org.springframework.web.bind.annotation.RequestBody;

@HttpExchange("/v1/chat/completions")
public interface OpenAIInterface {
    @PostExchange
    OpenAIResponse getCompletion(@RequestBody OpenAIRequest request);
}