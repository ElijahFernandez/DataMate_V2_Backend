package com.capstone.datamate.Service;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;
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

    public String processHeaders(List<String> headers) {
        String headersString = String.join(", ", headers);
        // Simply pass the headers as user input, no need to add any additional question
        return service.getCompletion(headersString);
    }

//    public String generateAggregateTitle(String addPrompt) {
//        // Define a prompt to ask GPT for a suitable aggregate title
//        String gptPrompt = String.format(
//                "Given the following user instruction: \"%s\", " +
//                        "generate a concise and user-friendly title for the type of aggregation requested. " +
//                        "The title should be appropriate for use in a report and reflect the operation described (e.g., Total, Average, Count, etc.). " +
//                        "If no specific aggregation is mentioned, return 'Aggregate'." +
//                        "Don't include anything else in your response, just the appropriate title.",
//                addPrompt != null ? addPrompt : "No prompt provided"
//        );
//
//        // Call GPT to generate the title
//        return service.getCompletion(gptPrompt);
//    }

//    public String callAPIforSQLReport(List<String> headers, String dbName, String tblName, String addPrompt) {
//        String headersString = String.join(",", headers);
////        String prompt = String.format(
////                "%s" +
////                        "Given the headers above, " +
////                        "and the table name: %s, " +
////                        "and this additional prompt from the user: %s." +
////                        "First, generate a SELECT statement that retrieves data based on the provided headers and the additional prompt." +
////                        "Next, analyze the headers to determine if any of them are numerical in nature." +
////                        "If any header is numerical, move on to the next step; if none are numerical, the SQL generation should return only the original SELECT statement without any aggregation functions or total rows." +
////                        "If any header is numerical, treat the data as numerical and use the REPLACE function to remove money signs and commas for proper aggregation." +
////                        "The only aggregation functions that should be used are TOTAL and SUM." +
////                        "Then, generate another SELECT statement that creates a 'Total' row, which includes the aggregation of the relevant numerical columns, starting with the row header 'Total'." +
////                        "Finally, combine the two SELECT statements using UNION ALL." +
////                        "The SQL query must be written in a single line, with no unnecessary spaces or line breaks." +
////                        "Do not include anything else in your response. Just the SQL code. Do not use ``` tags around the code.",
////                headersString, tblName, addPrompt);
//        // Define aggregation-related keywords
//        List<String> aggregationKeywords = Arrays.asList("SUM", "TOTAL", "AVERAGE", "COUNT", "MIN", "MAX");
//
//// Check if the user prompt contains any of the aggregation keywords
//        boolean hasAggregation = addPrompt != null && aggregationKeywords.stream()
//                .anyMatch(keyword -> addPrompt.toUpperCase().contains(keyword));
//        String sqlQuery = " ";
//        String title = generateAggregateTitle(addPrompt);
//
//        int columnCount = headers.size(); // Assuming headers are parsed into an array.
//
//        if (hasAggregation) {
//            String placeholderPattern;
//            if (columnCount == 2) {
//                // Only one NULL followed by the aggregate
//                placeholderPattern = "NULL";
//            } else {
//                // Dynamically generate NULLs for all but the last column
//                placeholderPattern = String.join(", ", Collections.nCopies(columnCount - 1, "NULL"));
//            }
//
//            if (addPrompt.toUpperCase().contains("SUM") || addPrompt.toUpperCase().contains("TOTAL")) {
//                sqlQuery = String.format(
//                        "%s" +
//                                "Given the headers above, " +
//                                "and the table name: %s, " +
//                                "and this additional prompt from the user: %s. " +
//                                "First, generate a SELECT statement that retrieves data based on ALL provided headers and the additional prompt. " +
//                                "Analyze the user's additional prompt to identify any aggregation instructions (e.g., SUM). " +
//                                "For the second SELECT statement, ensure that the values containing any monetary or numerical columns are cleaned (e.g., remove currency symbols, commas) and cast into a numeric type (e.g., DECIMAL) for proper aggregation." +
//                                "Include %s for non-aggregated columns and add the aggregated column as the last column. " +
//                                "Combine the two SELECT statements using UNION ALL, ensuring the data types and number of columns align. " +
//                                "The SQL query must be written in a single line, with no unnecessary spaces or line breaks. " +
//                                "The response you provide must NOT include anything else other than the SQL code void of ``` tags.",
//                        headersString, tblName, addPrompt, placeholderPattern + ", aggregate");
//            } else if (addPrompt.toUpperCase().contains("COUNT")) {
//                sqlQuery = String.format(
//                        "%s" +
//                                " Given the headers above, " +
//                                "and the table name: %s, " +
//                                "and this additional prompt from the user: %s. " +
//                                "First, generate a SIMPLE SELECT FROM statement that retrieves data based on ALL provided headers exactly as they are in the string of headers (NO modifications, NO 'AS' clauses, NO additional columns, NO casting) and the additional prompt. " +
//                                "Next, generate a second SELECT statement with aggregated columns. Use the appropriate aggregation function (e.g., COUNT) as specified in the additional prompt. " +
//                                "Include %s for non-aggregated columns and add the aggregated column as the last column. " +"Combine the two SELECT statements using UNION ALL, ensuring the data types and number of columns align across both SELECT statements. " +
//                                "The SQL query must be written in a single line, with no unnecessary spaces or line breaks. " +
//                                "The response you provide must NOT include anything else other than the SQL code void of ``` tags.",
//                        headersString, tblName, addPrompt, placeholderPattern);
//            } else if (addPrompt.toUpperCase().contains("MIN") || addPrompt.toUpperCase().contains("MAX")) {
//                String aggregationType = addPrompt.toUpperCase().contains("MIN") ? "MIN" : "MAX";
//                sqlQuery = String.format(
//                        "%s" +
//                                " Given the headers above, " +
//                                "and the table name: %s, " +
//                                "and this additional prompt from the user: %s. " +
//                                "First, generate a SIMPLE SELECT statement that retrieves data based on ALL provided headers exactly as they are (no more, no less) and the additional prompt. " +
//                                "Next, generate a second SELECT statement with aggregated columns using the %s function. " +
//                                "For the second SELECT statement, ensure that the values containing any monetary or numerical columns are cleaned (e.g., remove currency symbols, commas) and cast into a numeric type (e.g., DECIMAL) for proper aggregation. " +
//                                "For each of the columns that require aggregation, apply the %s function after cleaning the values. " +
//                                "Include %s for non-aggregated columns and add the aggregated column as the last column." +
//                                "Combine the two SELECT statements using UNION ALL, ensuring that the data types and number of columns align across both SELECT statements. " +
//                                "Ensure that the aggregation function (e.g., %s) is applied only to the appropriate numerical columns. " +
//                                "The SQL query must be written in a single line, with no unnecessary spaces or line breaks. " +
//                                "The response you provide must NOT include anything else other than the SQL code void of ``` tags.",
//                        headersString, tblName, addPrompt, aggregationType, aggregationType, placeholderPattern, aggregationType);
//            } else if (addPrompt.toUpperCase().contains("AVG") || addPrompt.toUpperCase().contains("AVERAGE")) {
//                sqlQuery = String.format(
//                        "%s" +
//                                "Given the headers above, " +
//                                "and the table name: %s, " +
//                                "and this additional prompt from the user: %s. " +
//                                "First, generate a SELECT statement that retrieves data based on ALL provided headers and the additional prompt. " +
//                                "For the second SELECT statement, ensure that the values containing any monetary or numerical columns are cleaned (e.g., remove currency symbols, commas) and cast into a numeric type (e.g., DECIMAL) for proper aggregation." +
//                                "Include %s for non-aggregated columns and add the aggregated column as the last column. " +
//                                "Combine the two SELECT statements using UNION ALL, ensuring the data types and number of columns align. " +
//                                "The SQL query must be written in a single line, with no unnecessary spaces or line breaks. " +
//                                "The response you provide must NOT include anything else other than the SQL code void of ``` tags.",
//                        headersString, tblName, addPrompt, placeholderPattern);
//            }
//        }
//        else {
//            sqlQuery = String.format(
//                    "%s" +
//                            "Given the headers above, " +
//                            "and the table name: %s, " +
//                            "and this additional prompt from the user: %s. " +
//                            "Generate an SQL query that SELECTS the data from the headers and the additional prompt." +
//                            "The SQL query must be written in a single line, with no unnecessary spaces or line breaks. " +
//                            "The response you provide must NOT include anything else other than the SQL code void of ``` tags.",
//                    headersString, tblName, addPrompt);
//        }
//        String SQLCode = service.getCompletion(sqlQuery);
//        System.out.println(SQLCode);
//        return SQLCode.replaceFirst("NULL", "'" + title + "'");
//    }

    public String headerExtractor (String headersString, String addPrompt) {
        //Get header
        String prompt = String.format(
                "You are analyzing a user request and a list of database column headers. " +
                        "The column headers are: %s. " +
                        "The user's request is: '%s'. " +
                        "Determine which column the user is referring to in their request. " +
                        "Return only the exact column name if it matches one from the list. ",
                headersString, addPrompt
        );

        String header = service.getCompletion(prompt);
        return header.trim();
    }

    public String nullCounter(int columns) {
        // If columns are less than or equal to 2, return a blank string
        if (columns <= 2) {
            return "";
        }

        // Remove the trailing space and comma for cleaner output
        return "NULL, ".repeat(columns - 2).trim();
    }

    public String callAPIforSQLReport(List<String> headers, String dbName, String tblName, String addPrompt) {
        String headersString = String.join(",", headers);
        int columnCount = headers.size();

        String sqlQuery = String.format(
                "%s" +
                        "Given the headers above, " +
                        "and the table name: %s, " +
                        "and this additional prompt from the user: %s. " +
                        "Generate an SQL query that SELECTS the data from the headers and the additional prompt." +
                        "The SQL query must be written in a single line, with no unnecessary spaces or line breaks. " +
                        "The response you provide must NOT include anything else other than the SQL code void of tags.",
                headersString, tblName, addPrompt);

        List<String> aggregationKeywords = Arrays.asList("SUM", "TOTAL", "AVERAGE", "COUNT", "MIN", "MAX", "AVG");

        String sqlCode = "";

        boolean hasAggregation = !addPrompt.isEmpty() && aggregationKeywords.stream().anyMatch(keyword -> addPrompt.toUpperCase().contains(keyword));

        if (hasAggregation) {
            String queryWithoutPrompt = String.format(
                    "%s" +
                            "Given the headers above, " +
                            "and the table name: %s, " +
                            "Generate an SQL query that SELECTS the data from the headers." +
                            "The SQL query must be written in a single line, with no unnecessary spaces or line breaks. " +
                            "The response you provide must NOT include anything else other than the SQL code void of tags.",
                    headersString, tblName);
            sqlCode = service.getCompletion(queryWithoutPrompt);
            sqlCode = sqlCode.replace(";", "");

            if(addPrompt.toUpperCase().contains("SUM") || addPrompt.toUpperCase().contains("TOTAL")) {
                sqlCode = sqlCode + "  UNION ALL SELECT 'Total'," + nullCounter(columnCount) + "SUM(CAST(REPLACE(REPLACE(" + headerExtractor(headersString, addPrompt) + ",'$',''),',','') AS DECIMAL)) FROM " + tblName + ";";
            }
            else if(addPrompt.toUpperCase().contains("COUNT")) {
                sqlCode = sqlCode + " UNION ALL SELECT 'Count'," + nullCounter(columnCount) + "COUNT(*) FROM " + tblName + ";";
            }
            else if(addPrompt.toUpperCase().contains("MIN")) {
                sqlCode = sqlCode + " UNION ALL SELECT 'Minimum'," + nullCounter(columnCount) + "MIN(CAST(REPLACE(REPLACE(" + headerExtractor(headersString, addPrompt) + ",'$',''),',','') AS DECIMAL)) FROM " + tblName + ";";
            }
            else if(addPrompt.toUpperCase().contains("MAX")) {
                sqlCode = sqlCode + " UNION ALL SELECT 'Maximum'," + nullCounter(columnCount) + "MAX(CAST(REPLACE(REPLACE(" + headerExtractor(headersString, addPrompt) + ",'$',''),',','') AS DECIMAL)) FROM " + tblName + ";";
            }
            else if(addPrompt.toUpperCase().contains("AVG") || addPrompt.toUpperCase().contains("AVERAGE")) {
                sqlCode = sqlCode + "  UNION ALL SELECT 'Average'," + nullCounter(columnCount) + "AVG(CAST(REPLACE(REPLACE(" + headerExtractor(headersString, addPrompt) + ",'$',''),',','') AS DECIMAL)) FROM " + tblName + ";";
            }

            return sqlCode;
        }

        sqlCode = service.getCompletion(sqlQuery);
        return sqlCode;
    }
}