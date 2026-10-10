package com.orders.opsservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class OpsServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(OpsServiceApplication.class, args);
    }

}
