package com.orders.opsservice.client;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;

import java.util.Map;

@FeignClient(name = "demo-service")
public interface DemoServiceClient {

    @GetMapping("actuator/health")
    Map<String, Object> health();
}
