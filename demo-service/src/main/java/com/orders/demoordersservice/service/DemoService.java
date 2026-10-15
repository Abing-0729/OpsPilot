package com.orders.demoordersservice.service;

import com.orders.demoordersservice.config.DemoSleepConfig;
import com.orders.opscommon.common.Result;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class DemoService {

    private final DemoSleepConfig sleepConfig;

    public DemoService(DemoSleepConfig sleepConfig) {
        this.sleepConfig = sleepConfig;
    }

    public Result<Map<String, Object>> getOrder(Long orderId) {
        return Result.success(Map.of(
                "orderId", orderId,
                "status", "CREATED",
                "message", "订单查询成功"
        ));
    }

    public Result<String> slowRequest() throws InterruptedException {
        Thread.sleep(sleepConfig.getSleepMillis());
        return Result.success("请求处理完成，耗时约 " + sleepConfig.getSleepMillis() + " 毫秒");
    }

    public Result<String> triggerError() {
        return Result.error("模拟订单服务内部异常");
    }

    public Result<String> triggerDatabaseError() {
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