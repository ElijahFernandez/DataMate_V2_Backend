package com.capstone.datamate.OpenAI;

import java.util.List;

public class OpenAIRecords {
    public record OpenAIRequest(
            String model,                  // Model name
            List<Message> messages,        // User message list
            double temperature,            // Controls randomness
            int max_tokens,                // Limits the response length
            double top_p,                  // Nucleus sampling
            double frequency_penalty,      // Penalize new tokens based on frequency
            double presence_penalty        // Penalize new tokens based on presence
    ) {}

    public record Message(String role, String content) {}

    public record OpenAIResponse(List<Choice> choices) {
        public record Choice(Message message) {}
    }
}