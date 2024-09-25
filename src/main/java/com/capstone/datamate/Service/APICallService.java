package com.capstone.datamate.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

@Setter
@Getter
@AllArgsConstructor
@Service
public class APICallService {
    private final OpenAIService service;
    public String callAPI(String filepath) throws IOException {
        byte[] bytes = Files.readAllBytes(Paths.get(filepath));

        String file = new String(bytes);
        String question = "Given this SQL file, return the table headers in a user readable manner AND in a comma separated manner.";
        String prompt = file + "\n" + question;

        return service.getCompletion(prompt);
    }
    public String processHeaders (List<String> headers) {
        String headersString = String.join(", ", headers);

        // Simply pass the headers as user input, no need to add any additional question
        return service.getCompletion(headersString);
    }

//    public String callAPIWithHeaders(List<String> headers) {
//        String headersString = String.join(", ", headers);
//        String question = String.format(
//                "These are SQL Headers: %s. " +
//                "Create an HTML Form with these as labels " +
//                "Add a ':' on the headers" +
//                "Turn them into questions such as 'What is your vendor name?'. " +
//                "Strictly use the headers only." +
//                "Use appropriate input types as well. " +
//                "Don't explain. Just print the HTML form." +
//                "Exclude other tags except the <form> tag" +
//                        "Remove other tags such as '''" +
//                "Design the forms minimally using inline CSS." +
//                        "Arrange the CSS (Label on the top, input at the bottom). Do not align to center" +
//                        "<form style=\"display=flex, flex-direction=column\">" +
//                        "5px padding on all labels and input and button" +
//                        "Add a submit button as well. Design it with the color green and with full functionality", headersString);
//        return service.getCompletion(question);
//    }
}
