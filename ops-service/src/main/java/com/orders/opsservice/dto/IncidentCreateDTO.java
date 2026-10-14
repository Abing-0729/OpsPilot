package com.orders.opsservice.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

/**
 * 故障事件创建 DTO
 * <p>
 * 只暴露"创建时由调用方提供"的字段：
 * eventNo 由服务端生成，status 固定为 DETECTED，时间戳由数据库默认值填充。
 */
@Data
public class IncidentCreateDTO {

    @NotBlank(message = "serviceName 不能为空")
    private String serviceName;      // 发生故障的服务名称

    private String serviceVersion;   // 发生故障的服务版本

    @NotBlank(message = "eventType 不能为空")
    private String eventType;        // 故障类型

    private String severity;         // 故障严重程度，缺省 MEDIUM

    private String description;      // 故障描述
}
