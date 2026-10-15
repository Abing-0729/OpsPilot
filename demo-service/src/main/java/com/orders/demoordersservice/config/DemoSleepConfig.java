package com.orders.demoordersservice.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.stereotype.Component;

/**
 * 故障注入参数：慢接口的睡眠时长。
 * <p>
 * 放在 Nacos 共享配置里（dataId=OpsPilot-starter, group=application）：
 * demo:
 *   sleep-millis: 3000
 * <p>
 * @RefreshScope 的作用：Nacos 里改了值并发布后，这个 Bean 会重建，
 * 不用重启服务就能实时改变故障注入强度——这也是"恢复验证"的手段：
 * 把睡眠改成 0，熔断器的半开探测就能成功，熔断自动关闭。
 */
@Component
@RefreshScope
public class DemoSleepConfig {

    @Value("${demo.sleep-millis:3000}")
    private long sleepMillis;

    public long getSleepMillis() {
        return sleepMillis;
    }
}
