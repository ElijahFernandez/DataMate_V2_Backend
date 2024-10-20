package com.capstone.datamate.Entity;

public class SqlRequestEntity {
    private String sqlCode;

    // Default constructor
    public SqlRequestEntity() {
    }

    // Constructor with sqlCode parameter
    public SqlRequestEntity(String sqlCode) {
        this.sqlCode = sqlCode;
    }

    // Getter and Setter
    public String getSqlCode() {
        return sqlCode;
    }

    public void setSqlCode(String sqlCode) {
        this.sqlCode = sqlCode;
    }
}
