package com.orders.opsservice.service;

import com.orders.opsservice.client.DemoServiceClient;
import com.orders.opsservice.exception.ServiceDegradedException;
import com.orders.opsservice.resilience.RemoteCallRecorder;
import io.github.resilience4j.circuitbreaker.annotation.CircuitBreaker;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * demo-service 健康检查调用。
 * <p>
 * 熔断加在这一层（Spring Bean 方法），不加在 Feign 接口上：
 * 1. 熔断边界要包住"调用 + 故障记录"整个业务动作，接口层够不着 recorder；
 * 2. 降级方法需要有地方放——接口没有实现体，Service 方法才有；
 * 3. 不同业务方法可以配不同的熔断实例名和降级策略。
 */
@Service
@RequiredArgsConstructor
public class DemoCheckService {

    private static final String DEMO_SERVICE = "demo-service";
    private static final String CIRCUIT_BREAKER_NAME = "demoService";

    private final DemoServiceClient demoServiceClient;
    private final RemoteCallRecorder remoteCallRecorder;

    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "checkFallback")
    public Map<String, Object> check() {
        return remoteCallRecorder.call(DEMO_SERVICE, demoServiceClient::health);
    }

    /**
     * 调用 demo-service 的慢接口，用于复现远程调用超时故障
     */
    @CircuitBreaker(name = CIRCUIT_BREAKER_NAME, fallbackMethod = "slowCheckFallback")
    public String slowCheck() {
        return remoteCallRecorder.call(DEMO_SERVICE, demoServiceClient::slowQuery);
    }

    /**
     * 熔断打开时的降级：记一条 CIRCUIT_OPEN 事件，转 503 给调用方。
     * 注意：开门期间原方法根本不会执行，所以这里补上记录。
     */
    private Map<String, Object> checkFallback(Throwable t) {
        return degrade(t);
    }

    private String slowCheckFallback(Throwable t) {
        return degrade(t);
    }

    private <T> T degrade(Throwable t) {
        remoteCallRecorder.recordCircuitOpen(DEMO_SERVICE);
        throw new ServiceDegradedException(
                DEMO_SERVICE + " 服务降级中（熔断器已开启）：" + t.getMessage());
    }
}
