package com.capstone.datamate;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

//@SpringBootApplication
//public class DatamateApplication{
//	
//	public static void main(String[] args){
//		SpringApplication.run(DatamateApplication.class, args);
//	}
//
//}
@SpringBootApplication
@ComponentScan(basePackages = "com.capstone.datamate")
public class DatamateApplication {
    public static void main(String[] args) {
        SpringApplication.run(DatamateApplication.class, args);
    }
}
