package com.orders.opsservice.service;

import com.orders.opsservice.client.DemoServiceClient;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DemoCheckService {
    private final DemoServiceClient demoServiceClient;

    public Map<String, Object> check() {
        return demoServiceClient.health();
    }
}
