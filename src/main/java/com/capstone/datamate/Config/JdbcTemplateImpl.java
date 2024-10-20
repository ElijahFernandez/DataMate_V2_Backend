package com.capstone.datamate.Config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

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

    public void executeSQL(String sqlCode) throws DataAccessException {
        System.out.println("Received SQL Code for execution");

        if (sqlCode != null && !sqlCode.isEmpty()) {
            // Split the SQL commands by semicolon and trim any whitespace
            String[] sqlStatements = sqlCode.split(";");
            for (String sql : sqlStatements) {
                sql = sql.trim(); // Remove leading and trailing whitespace
                if (!sql.isEmpty()) { // Ensure we don't execute empty statements
                    System.out.println("Executing SQL: " + sql);
                    jdbc.execute(sql);
                    System.out.println("SQL executed successfully: " + sql);
                }
            }
        } else {
            System.out.println("SQL Code is empty, skipping execution");
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

    public void updateValues(String tableName, List<String> headers, List<String> values, String conditions) {
        if (headers.size() != values.size()) {
            throw new IllegalArgumentException("Number of headers and values must be equal.");
        }

        StringBuilder setClause = new StringBuilder();

        for (int i = 0; i < headers.size(); i++) {
            setClause.append(headers.get(i)).append(" = ?");

            if (i < headers.size() - 1) {
                setClause.append(", ");
            }
        }

        String query = "UPDATE " + tableName + " SET " + setClause + " WHERE " + conditions;
        Object[] valuesArray = values.toArray();

        jdbc.update(query, valuesArray);
    }

    public boolean checkIfIdExists(String tableName, String idColumn, String idValue) {
        String query = "SELECT COUNT(*) FROM " + tableName + " WHERE " + idColumn + " = ?";
        Integer count = jdbc.queryForObject(query, new Object[]{idValue}, Integer.class);
        return count != null && count > 0;
    }

//    public List<String> getAvailableIds(String tableName) {
//        String query = "SELECT id FROM " + tableName;
//        return jdbc.queryForList(query, String.class);
//    }

    public List<String> getAvailableIds(String tableName) {
        String query = "SELECT id FROM " + tableName;
        return jdbc.queryForList(query, String.class);
    }

    public List<String> getAllIds(String idKeyColumn, String tableName) {
        String query = "SELECT " + idKeyColumn + " FROM " + tableName;
        return jdbc.queryForList(query, String.class);
    }

    public Map<String, Object> getRowData(String tableName, String idColumn, String idValue) {
        String query = "SELECT * FROM " + tableName + " WHERE " + idColumn + " = ?";
        try {
            return jdbc.queryForMap(query, idValue);
        } catch (EmptyResultDataAccessException e) {
            return null;
        }
    }

    public boolean deleteRow(String tableName, String idColumn, String idValue) {
        String query = "DELETE FROM " + tableName + " WHERE " + idColumn + " = ?";
        int rowsAffected = jdbc.update(query, idValue);
        return rowsAffected > 0;
    }
}