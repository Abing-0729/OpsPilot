package com.orders.opsservice.exception;

import com.orders.opscommon.common.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.method.annotation.MethodArgumentTypeMismatchException;
import org.springframework.web.servlet.resource.NoResourceFoundException;

import java.util.stream.Collectors;

@RestControllerAdvice
@Slf4j
public class GlobalExceptionHandler {

    /**
     * 处理业务异常（如故障事件不存在）
     */
    @ExceptionHandler(BusinessException.class)
    public ResponseEntity<Result<Void>> handleBusiness(BusinessException e) {
        log.warn("业务异常：{}", e.getMessage());
        return ResponseEntity.status(404).body(Result.error(404, e.getMessage()));
    }

    /**
     * 处理参数校验异常：把字段名和校验信息拼给调用方
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Result<Void>> handleMethodArgumentNotValid(MethodArgumentNotValidException e) {
        String message = e.getBindingResult().getFieldErrors().stream()
                .map(error -> error.getField() + ": " + error.getDefaultMessage())
                .collect(Collectors.joining("; "));
        log.warn("参数校验失败：{}", message);
        return ResponseEntity.status(400).body(Result.error(400, message));
    }

    /**
     * 处理唯一键冲突（event_no）
     */
    @ExceptionHandler(DuplicateKeyException.class)
    public ResponseEntity<Result<Void>> handleDuplicateKeyException(DuplicateKeyException e) {
        log.warn("唯一键冲突：{}", e.getMessage());
        return ResponseEntity.status(409).body(Result.error(409, "eventNo 冲突，故障事件创建失败"));
    }

    /**
     * 静态资源不存在（典型场景：浏览器自动请求 /favicon.ico）。
     * 这不是服务故障，按 404 静默处理，不打 error 日志污染日志。
     */
    @ExceptionHandler(NoResourceFoundException.class)
    public ResponseEntity<Result<Void>> handleNoResource(NoResourceFoundException e) {
        log.debug("静态资源不存在：{}", e.getResourcePath());
        return ResponseEntity.status(404).body(Result.error(404, "资源不存在"));
    }

    /**
     * 处理参数类型不匹配异常
     */
    @ExceptionHandler(MethodArgumentTypeMismatchException.class)
    public ResponseEntity<Result<Void>> handleMethodArgumentTypeMismatch(MethodArgumentTypeMismatchException e) {
        log.warn("参数类型不匹配：{}", e.getMessage());
        return ResponseEntity.status(400).body(Result.error(400, "参数类型不匹配"));
    }

    /**
     * 处理请求方法不支持异常
     */
    @ExceptionHandler(HttpRequestMethodNotSupportedException.class)
    public ResponseEntity<Result<Void>> handleHttpRequestRequestMethodNotSupported(HttpRequestMethodNotSupportedException e) {
        log.warn("请求方法不支持：{}", e.getMessage());
        return ResponseEntity.status(405).body(Result.error(405, "请求方法不支持"));
    }

    /**
     * 兜底：500。堆栈只进日志，不外泄给调用方
     */
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Result<Void>> handleException(Exception e) {
        log.error("服务内部异常", e);
        return ResponseEntity.status(500).body(Result.error(500, "服务内部异常"));
    }
}
