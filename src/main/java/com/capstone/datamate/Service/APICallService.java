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
                        "Given the headers above, " +
                        "and the table name: %s, " +
                        "and this additional prompt from the user: %s." +
                        "First, generate a SELECT statement that retrieves data based on the provided headers and the additional prompt." +
                        "Next, analyze the headers to determine if any of them are numerical in nature." +
                        "If any header is numerical, move on to the next step; if none are numerical, the SQL generation should return only the original SELECT statement without any aggregation functions or total rows." +
                        "If any header is numerical, treat the data as numerical and use the REPLACE function to remove money signs and commas for proper aggregation." +
                        "The only aggregation functions that should be used are TOTAL and SUM." +
                        "Then, generate another SELECT statement that creates a 'Total' row, which includes the aggregation of the relevant numerical columns, starting with the row header 'Total'." +
                        "Finally, combine the two SELECT statements using UNION ALL." +
                        "The SQL query must be written in a single line, with no unnecessary spaces or line breaks." +
                        "Do not include anything else in your response. Just the SQL code. Do not use ``` tags around the code.",
                headersString, tblName, addPrompt);

        return service.getCompletion(prompt);
    }




}
