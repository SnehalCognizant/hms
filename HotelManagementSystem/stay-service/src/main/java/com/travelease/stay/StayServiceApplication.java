package com.travelease.stay;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class StayServiceApplication {
    public static void main(String[] args) {
        SpringApplication.run(StayServiceApplication.class, args);
    }
}
