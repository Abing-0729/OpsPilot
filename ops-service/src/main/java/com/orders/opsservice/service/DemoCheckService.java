package com.orders.opsservice.service;

import com.orders.opsservice.client.DemoServiceClient;
import com.orders.opsservice.resilience.RemoteCallRecorder;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class DemoCheckService {

    private static final String DEMO_SERVICE = "demo-service";

    private final DemoServiceClient demoServiceClient;
    private final RemoteCallRecorder remoteCallRecorder;

    public Map<String, Object> check() {
        return remoteCallRecorder.call(DEMO_SERVICE, demoServiceClient::health);
    }

    /**
     * 调用 demo-service 的慢接口，用于复现远程调用超时故障
     */
    public String slowCheck() {
        return remoteCallRecorder.call(DEMO_SERVICE, demoServiceClient::slowQuery);
    }
}
