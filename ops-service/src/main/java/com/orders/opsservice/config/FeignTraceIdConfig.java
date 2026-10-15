package com.orders.opsservice.config;

import com.orders.opscommon.config.TraceIdFilter;
import feign.RequestInterceptor;
import org.slf4j.MDC;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.StringUtils;

/**
 * Feign 出站调用拦截器：把当前线程 MDC 里的 traceId 写进请求头，
 * 让 ops-service 发往 demo-service 的请求继续带着同一个 traceId。
 * <p>
 * 注意这是 feign.RequestInterceptor，不是 Spring MVC 的 HandlerInterceptor：
 * 前者作用于"发出去的 HTTP 调用"，后者作用于"进来的 HTTP 请求"。
 */
@Configuration
public class FeignTraceIdConfig {

    @Bean
    public RequestInterceptor feignTraceIdInterceptor() {
        return template -> {
            String traceId = MDC.get(TraceIdFilter.MDC_KEY);
            if (StringUtils.hasText(traceId)) {
                template.header(TraceIdFilter.TRACE_ID_HEADER, traceId);
            }
        };
    }
}
