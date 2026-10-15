package com.orders.opsservice.exception;


public class ServiceDegradedException extends RuntimeException {
    public ServiceDegradedException(String message) {
        super(message);
    }
}
