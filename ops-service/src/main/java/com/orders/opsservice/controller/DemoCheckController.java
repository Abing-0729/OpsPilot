package com.orders.opsservice.controller;

import com.orders.opscommon.common.Result;
import com.orders.opsservice.service.DemoCheckService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Map;

@RestController
@RequestMapping("/api/check-incidents")
@RequiredArgsConstructor
public class DemoCheckController {

    private final DemoCheckService demoCheckService;

    @GetMapping("/check-demo")
    public Result<Map<String, Object>> checkDemo() {
        return Result.success(demoCheckService.check());
    }

    /**
     * 故障复现入口：调用 demo-service 慢接口，预期触发 Feign 读超时
     */
    @GetMapping("/check-demo-slow")
    public Result<String> checkDemoSlow() {
        return Result.success(demoCheckService.slowCheck());
    }
}
