package com.orders.demoordersservice.service;

import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DemoService {

    public Map<String, Object> getOrder(Long orderId) {
        return Map.of(
                "orderId", orderId,
                "status", "CREATED",
                "message", "订单查询成功"
        );
    }

    public String slowRequest() throws InterruptedException {
        Thread.sleep(3000);
        return "请求处理完成，耗时约 3 秒";
    }

    public String triggerError() {
        throw new IllegalStateException("模拟订单服务内部异常");
    }

    public String triggerDatabaseError() {
        throw new DatabaseUnavailableException(
                "模拟数据库连接失败"
        );
    }

    public static class DatabaseUnavailableException
            extends RuntimeException {

        public DatabaseUnavailableException(String message) {
            super(message);
        }
    }
}