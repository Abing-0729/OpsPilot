package com.orders.demoordersservice;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class DemoOrdersServiceApplication {

    public static void main(String[] args) {
        SpringApplication.run(DemoOrdersServiceApplication.class, args);
    }

}
