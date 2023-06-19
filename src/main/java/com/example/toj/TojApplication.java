package com.example.toj;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
public class TojApplication {

    public static void main(String[] args) {
        SpringApplication.run(TojApplication.class, args);
    }

}
