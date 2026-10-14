package com.orders.demoordersservice.controller;

import com.orders.demoordersservice.service.DemoService;
import com.orders.opscommon.common.Result;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/demo")
public class DemoController {

    private static final Logger log =
            LoggerFactory.getLogger(DemoController.class);

    private final DemoService demoService;

    public DemoController(DemoService demoService) {
        this.demoService = demoService;
    }

    @GetMapping("/orders/{id}")
    public Result<Map<String, Object>> getOrder(
            @PathVariable Long id) {

        log.info("收到订单查询请求，orderId={}", id);

        return demoService.getOrder(id);
    }

    @GetMapping("/orders/slow")
    public Result<String> slowRequest() throws InterruptedException {
        log.info("收到慢请求，开始处理");

        return demoService.slowRequest();
    }

    @GetMapping("/orders/error")
    public Result<String> triggerError() {
        log.warn("收到故障注入请求：内部异常");

        return demoService.triggerError();
    }

    @GetMapping("/orders/db-error")
    public Result<String> triggerDatabaseError() {
        log.warn("收到故障注入请求：模拟数据库故障");

        return demoService.triggerDatabaseError();
    }
}