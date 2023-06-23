package com.example.shelterbot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ShelterBotApplication {

    public static void main(String[] args) {
        SpringApplication.run(ShelterBotApplication.class, args);
    }

}
