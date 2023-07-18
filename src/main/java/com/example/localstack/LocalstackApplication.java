package com.example.localstack;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class LocalstackApplication {

    public static void main(String[] args) {
        SpringApplication.run(LocalstackApplication.class, args);
    }
}
