package com.orders.opsservice.config;

import com.orders.opscommon.config.TraceIdFilter;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;

/**
 * 注册 ops-common 里的 TraceIdFilter。
 * <p>
 * 为什么用 FilterRegistrationBean 而不是在 TraceIdFilter 上加 @Component：
 * TraceIdFilter 在 ops-common 包下，不在本服务启动类的扫描范围内，
 * @Component 永远不会生效；FilterRegistrationBean 还能控制 URL 匹配和顺序。
 */
@Configuration
public class TraceIdFilterConfig {

    @Bean
    public FilterRegistrationBean<TraceIdFilter> traceIdFilterRegistration() {
        FilterRegistrationBean<TraceIdFilter> registration =
                new FilterRegistrationBean<>(new TraceIdFilter());
        registration.addUrlPatterns("/*");
        registration.setOrder(Ordered.HIGHEST_PRECEDENCE);
        return registration;
    }
}
