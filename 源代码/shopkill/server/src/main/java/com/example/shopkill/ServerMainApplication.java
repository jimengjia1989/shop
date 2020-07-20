package com.example.shopkill;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling
public class ServerMainApplication {
    public static void main(String[] args) {
        SpringApplication.run(ServerMainApplication.class,args);
    }
}
