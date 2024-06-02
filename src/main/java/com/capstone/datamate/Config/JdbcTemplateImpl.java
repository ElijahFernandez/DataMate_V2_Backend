package com.capstone.datamate.Config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class JdbcTemplateImpl{
    
    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbc;

    public JdbcTemplateImpl(){}
    
    public JdbcTemplateImpl(DataSource dataSource, JdbcTemplate jdbc) {
        this.dataSource = dataSource;
        this.jdbc = jdbc;
    }
    public JdbcTemplate getJdbc() {
        return jdbc;
    }
    public void setJdbc(JdbcTemplate jdbc) {
        this.jdbc = new JdbcTemplate(dataSource);
    }

    public void executeSQL(String tblName, String strValues, int op) throws DataAccessException{
        String sqlStatement = "";
        //op = 1 for CREATE
        //op = 2 for INSERT
        if(op == 1){
            sqlStatement = "CREATE TABLE IF NOT EXISTS " + tblName + " " + strValues;
        }else if(op == 2){
            sqlStatement = "INSERT INTO " + tblName + " " + strValues;
        }
        if(!sqlStatement.equals("")){
            jdbc.execute(sqlStatement);
        }
    }
    public List<String> getColumnHeaders(String tableName) {
        String query = "SELECT COLUMN_NAME FROM INFORMATION_SCHEMA.COLUMNS WHERE TABLE_NAME = ?";
        return jdbc.queryForList(query, new Object[]{tableName}, String.class);
    }

    public void insertValues(String tableName, List<String> headers, List<String> values) {
        if (headers.size() != values.size()) {
            throw new IllegalArgumentException("Number of headers and values must be equal.");
        }

        StringBuilder columnNames = new StringBuilder();
        StringBuilder placeholders = new StringBuilder();

        for (int i = 0; i < headers.size(); i++) {
            columnNames.append(headers.get(i));
            placeholders.append("?");

            if (i < headers.size() - 1) {
                columnNames.append(", ");
                placeholders.append(", ");
            }
        }

        String query = "INSERT INTO " + tableName + " (" + columnNames + ") VALUES (" + placeholders + ")";
        Object[] valuesArray = values.toArray();

        jdbc.update(query, valuesArray);
    }
}