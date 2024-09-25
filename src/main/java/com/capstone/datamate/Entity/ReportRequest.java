package com.capstone.datamate.Entity;

import java.util.List;

public class ReportRequest {
    private List<String> headers;
    private String dbName;
    private String tblName;

    // Getters and setters
    public List<String> getHeaders() {
        return headers;
    }

    public void setHeaders(List<String> headers) {
        this.headers = headers;
    }

    public String getDbName() {
        return dbName;
    }

    public void setDbName(String dbName) {
        this.dbName = dbName;
    }

    public String getTblName() {
        return tblName;
    }

    public void setTblName(String tblName) {
        this.tblName = tblName;
    }
}
