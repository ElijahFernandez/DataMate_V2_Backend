package com.capstone.datamate.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;
import java.util.stream.Collectors;

@Setter
@Getter
@AllArgsConstructor
@Service
public class APICallService {
    private final GeminiService service;

    public String callAPI(String filepath) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(filepath));

        String file = new String(bytes);
        String question = "Given this SQL file, return the table headers in a user readable manner AND in a comma separated manner.";
        String prompt = file + "\n" + question;

        return service.getCompletion(prompt);
    }

//    public String callAPIWithHeaders(List<String> headers) throws IOException {
//        String headersString = String.join(", ", headers);
//        String question = "These are SQL Headers:\n\n"
//                + headersString +
//                ":\n\nCreate an HTML Form with these as labels " +
//                "(Turn them into questions such as 'What is your vendor name?') " +
//                "Use appropriate input types as well. Don't explain. Just print the HTML form.";
//
//        return service.getCompletion(question);
//    }
    public String callAPIWithHeaders(List<String> headers) {
        String headersString = String.join(", ", headers);
        String question = String.format(
                "These are SQL Headers: %s. " +
                "Create an HTML Form with these as labels " +
                "(Turn them into questions such as 'What is your vendor name?'). " +
                "Strictly use the headers only." +
                "Use appropriate input types as well. " +
                "Don't explain. Just print the HTML form." +
                "Exclude other tags except the <form> tag" +
                "", headersString);
        return service.getCompletion(question);
    }
}
