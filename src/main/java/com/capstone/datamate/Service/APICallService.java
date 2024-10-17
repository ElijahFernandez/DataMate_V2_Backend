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

    public String callAPIforSQLReport(List<String> headers, String dbName, String tblName, String addPrompt) {
        String headersString = String.join(",", headers);
        String prompt = String.format(
                "%s" +
                        "Given the headers above" +
                        "And the table name: %s" +
                        "Generate SQL code for retrieving the data from those headers as well as fulfilling the user's request from this additional prompt: %s." +
                        "If any header contains monetary symbols (like $, ¥, or ₱), treat the column as numerical only in the aggregation step. " +
                        "Use the REPLACE function to remove money signs and commas when aggregating, but keep the original format in the retrieved data." +
                        "Analyze the headers to determine which columns can be aggregated and add a summary row at the bottom showing the total aggregation for relevant numerical columns." +
                        "Ensure the original data is displayed once, and the totals row is appended without duplicating data. Avoid using explicit UNION ALL." +
                        "Provide fully executable MySQL code without additional formatting like ``` tags.",
                headersString, tblName, addPrompt);

        return service.getCompletion(prompt);
    }




}
