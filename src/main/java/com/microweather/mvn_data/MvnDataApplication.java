package com.microweather.mvn_data;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class MvnDataApplication {

    public static void main(String[] args) {
        SpringApplication.run(MvnDataApplication.class, args);
    }

}
