package com.capstone.datamate.OpenAI;

import java.util.List;

public class OpenAIRecords {
    public record OpenAIRequest(String model, List<Message> messages) {}

    public record Message(String role, String content) {}

    public record OpenAIResponse(List<Choice> choices) {
        public record Choice(Message message) {}
    }
}