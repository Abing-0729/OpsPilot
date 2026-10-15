package com.orders.opsservice.resilience;

import com.orders.opsservice.dto.IncidentCreateDTO;
import com.orders.opsservice.service.IncidentService;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;

import java.net.SocketTimeoutException;
import java.util.concurrent.TimeoutException;
import java.util.function.Supplier;

/**
 * 远程调用守门员：包住每一次 Feign 调用，
 * 失败时自动记录一条故障事件（ops_incident），然后把异常原样抛出去。
 * <p>
 * 设计要点：
 * 1. 只记录、不吞异常 —— 响应用什么格式返回是 GlobalExceptionHandler 的职责，
 *    这里管"故障留痕"，两边职责分离。
 * 2. 记录动作自带 try-catch —— 记库失败绝不能影响主流程。
 * 3. description 里带 traceId —— 数据库记录和日志从此能互相定位，
 *    这是"故障可追踪"闭环的关键一笔。
 */
@Component
@Slf4j
public class RemoteCallRecorder {

    public static final String EVENT_TYPE_TIMEOUT = "REMOTE_CALL_TIMEOUT";
    public static final String EVENT_TYPE_ERROR = "REMOTE_CALL_ERROR";

    private final IncidentService incidentService;

    public RemoteCallRecorder(IncidentService incidentService) {
        this.incidentService = incidentService;
    }

    /**
     * 执行一次远程调用，失败则记录故障事件后原样抛出
     *
     * @param targetService 被调用方服务名
     * @param action        远程调用动作
     */
    public <T> T call(String targetService, Supplier<T> action) {
        try {
            return action.get();
        } catch (Exception e) {
            recordFailure(targetService, e);
            throw e;
        }
    }

    private void recordFailure(String targetService, Exception e) {
        try {
            String eventType = isTimeout(e) ? EVENT_TYPE_TIMEOUT : EVENT_TYPE_ERROR;
            String traceId = MDC.get("traceId");

            IncidentCreateDTO dto = new IncidentCreateDTO();
            dto.setServiceName(targetService);
            dto.setEventType(eventType);
            dto.setSeverity("HIGH");
            dto.setDescription(String.format(
                    "%s 远程调用失败：%s。已按 500 返回调用方，traceId=%s",
                    targetService, e.getMessage(), traceId));

            incidentService.create(dto);
            log.warn("故障已记录 service={} type={} traceId={}", targetService, eventType, traceId);
        } catch (Exception ex) {
            log.error("故障事件记录失败，不影响主流程", ex);
        }
    }

    /**
     * 判断是否为超时类故障：顺着 cause 链找 SocketTimeoutException / TimeoutException
     */
    private boolean isTimeout(Throwable t) {
        Throwable current = t;
        while (current != null) {
            if (current instanceof SocketTimeoutException
                    || current instanceof TimeoutException) {
                return true;
            }
            current = current.getCause();
        }
        return false;
    }
}
