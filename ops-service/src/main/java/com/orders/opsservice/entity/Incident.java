package com.orders.opsservice.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class Incident {
    private Long id;                    // 主键ID
    private String EventNo;             // 故障时间编号
    private String ServiceName;         // 发生故障的服务名称
    private String ServiceVersion;      // 发生故障的服务版本
    private String EventType;           // 故障类型
    private String Severity;            // 故障严重程度
    private String Description;         // 故障描述
    private String Status;              // 故障状态
    private LocalDateTime CreatedAt;    // 创建时间
    private LocalDateTime UpdatedAt;    // 更新时间
}
