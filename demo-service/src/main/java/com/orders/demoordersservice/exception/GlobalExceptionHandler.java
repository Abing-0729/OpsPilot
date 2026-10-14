package com.orders.demoordersservice.exception;

import com.orders.demoordersservice.service.DemoService.DatabaseUnavailableException;
import com.orders.opscommon.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 数据库故障：503 + Result.error(503, ...)
     */
    @ExceptionHandler(DatabaseUnavailableException.class)
    public ResponseEntity<Result<Void>> handleDatabaseError(
            DatabaseUnavailableException ex) {
        log.error("数据库故障：{}", ex.getMessage(), ex);
        return ResponseEntity.status(503)
                .body(Result.error(503, "数据库暂时不可用"));
    }

    /**
     * 兜底：500。堆栈只进日志，不外泄
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<Void>> handleException(
            Exception ex) {
        log.error("订单服务处理异常", ex);
        return ResponseEntity.status(500)
                .body(Result.error(500, "服务内部异常"));
    }
}
