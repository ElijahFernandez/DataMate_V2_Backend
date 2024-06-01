package com.capstone.datamate.Config;

import javax.sql.DataSource;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.stereotype.Component;

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
    
}