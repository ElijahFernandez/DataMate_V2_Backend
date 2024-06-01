package com.capstone.datamate.Config;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;

@Configuration
@ComponentScan("com.capstone.datamate")
public class AppConfig {

    @Bean
    public DataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName("com.mysql.jdbc.Driver");
        dataSource.setUrl("jdbc:mysql://localhost:3306/datamate"); // was datamate initially
        dataSource.setUsername("root");
        dataSource.setPassword(""); // was root initially

        return dataSource;
    }

    @Bean
    public JdbcTemplate getjdbctemplate() {
    return new JdbcTemplate(dataSource());
    }
}
