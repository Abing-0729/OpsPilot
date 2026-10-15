package com.orders.opscommon.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.MDC;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

/**
 * 链路追踪 Filter：从请求头取 traceId 放入 MDC，没有则生成。
 * <p>
 * 职责边界：只负责"读取 + 放入 MDC"，不写响应头。
 * 响应头的回写由网关统一负责——它是唯一入口，且调用失败没有下游响应时
 * 依然能保证客户端拿到 traceId。两边都写就会出现重复的响应头。
 * <p>
 * 注意：本类在 ops-common，不在各服务的组件扫描范围内，
 * 必须由各服务用 FilterRegistrationBean 手动注册才会生效。
 */
public class TraceIdFilter extends OncePerRequestFilter {

    public static final String TRACE_ID_HEADER = "X-Trace-Id";
    public static final String MDC_KEY = "traceId";

    public static String newTraceId() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain chain) throws IOException, ServletException {
        String traceId = request.getHeader(TRACE_ID_HEADER);
        MDC.put(MDC_KEY, StringUtils.hasText(traceId) ? traceId : newTraceId());
        try {
            chain.doFilter(request, response);
        } finally {
            MDC.remove(MDC_KEY);
        }
    }
}
