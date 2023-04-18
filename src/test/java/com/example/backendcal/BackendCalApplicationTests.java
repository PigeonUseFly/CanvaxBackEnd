package com.example.backendcal;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class })
public class BackendCalApplicationTests {

    public static void main(String[] args) {
        SpringApplication.run(BackendCalApplicationTests.class, args);
    }


    void contextLoads() {

    }

}
