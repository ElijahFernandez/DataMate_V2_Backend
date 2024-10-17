package com.capstone.datamate.Entity;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

public class ReportRequest {
    // Getters and setters
    @Setter
    @Getter
    private List<String> headers;
    @Setter
    @Getter
    private String dbName;
    @Setter
    @Getter
    private String tblName;
    @Setter
    @Getter
    private String addPrompt;
}
