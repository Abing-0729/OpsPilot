package com.orders.opsservice.dto;

import jakarta.validation.constraints.Pattern;
import lombok.Data;

/**
 * 故障事件更新 DTO
 * <p>
 * 与创建 DTO 分开：更新只允许改"允许被修改"的字段，且全部可选（null 表示不改）。
 * serviceName/eventNo 不在此列——事件归属和编号不允许通过更新接口篡改。
 */
@Data
public class IncidentUpdateDTO {

    @Pattern(regexp = "LOW|MEDIUM|HIGH|CRITICAL",
            message = "severity 只允许 LOW/MEDIUM/HIGH/CRITICAL")
    private String severity;

    @Pattern(regexp = "DETECTED|PROCESSING|RESOLVED|CLOSED",
            message = "status 只允许 DETECTED/PROCESSING/RESOLVED/CLOSED")
    private String status;

    private String serviceVersion;

    private String description;
}
