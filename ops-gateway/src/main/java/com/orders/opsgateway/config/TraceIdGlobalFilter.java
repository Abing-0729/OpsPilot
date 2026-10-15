package com.orders.opsgateway.config;

import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Mono;

import java.util.UUID;

/**
 * 网关全局过滤器：对所有路由统一生效。
 * 职责只有两个：请求没有 traceId 就生成一个，写入请求头往下游传；
 * 同时把 traceId 放到响应头，方便调用方对账。
 * 网关不碰 MDC —— WebFlux 是响应式线程模型，与 Servlet 的 ThreadLocal 不是一回事。
 */
@Component
public class TraceIdGlobalFilter implements GlobalFilter, Ordered {

    public static final String TRACE_ID_HEADER = "X-Trace-Id";
    private static final String TRACE_ID_ATTRIBUTE = "traceId";

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        String traceId = exchange.getRequest().getHeaders().getFirst(TRACE_ID_HEADER);
        if (traceId == null || traceId.isBlank()) {
            traceId = UUID.randomUUID().toString().replace("-", "");
        }

        exchange.getAttributes().put(TRACE_ID_ATTRIBUTE, traceId);
        exchange.getResponse().getHeaders().add(TRACE_ID_HEADER, traceId);

        ServerWebExchange mutated = exchange.mutate()
                .request(exchange.getRequest().mutate()
                        .header(TRACE_ID_HEADER, traceId)
                        .build())
                .build();
        return chain.filter(mutated);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
