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

    /**
     * 调用 demo-service 的慢接口，用于复现远程调用超时故障
     */
    public String slowCheck() {
        return demoServiceClient.slowQuery();
    }
}
