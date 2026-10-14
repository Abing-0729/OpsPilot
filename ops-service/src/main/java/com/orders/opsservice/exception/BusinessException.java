package com.orders.opsservice.exception;

/**
 * 业务异常：携带面向调用方的可读信息。
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
