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

    public String callAPIforSQLReport(List<String> headers, String dbName, String tblName) {
        String headersString = String.join(",", headers);
        String prompt = String.format(
                "%s" +
                "Given the headers above" +
                "And the table name: %s" +
                "Give me SQL code for retrieving the data from those headers." +
                "Do not include anything else in your response. Just the SQL code. Don't cover the code in ``` tags.", headersString, tblName);

        return service.getCompletion(prompt);
    }
}
