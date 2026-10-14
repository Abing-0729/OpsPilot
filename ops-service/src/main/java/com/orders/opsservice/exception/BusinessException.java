package com.orders.opsservice.exception;

/**
 * 业务异常：携带面向调用方的可读信息。
 * <p>
 * 练习 3 的全局异常处理器会把它统一转换成 Result 响应；
 * 在那之前它会以 500 冒出去，属于已知中间状态。
 */
public class BusinessException extends RuntimeException {

    public BusinessException(String message) {
        super(message);
    }
}
