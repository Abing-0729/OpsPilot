package com.orders.opsservice.service.impl;

import com.orders.opsservice.dto.IncidentCreateDTO;
import com.orders.opsservice.entity.Incident;
import com.orders.opsservice.mapper.IncidentMapper;
import com.orders.opsservice.service.IncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Service
@RequiredArgsConstructor
public class IncidentServiceImpl implements IncidentService {

    private final IncidentMapper incidentMapper;

    /**
     * 创建故障事件
     */
    @Override
    public void create(IncidentCreateDTO incidentCreateDTO) {
        // 1. 生成故障事件编号
        String eventNo = "INCIDENT_" + UUID.randomUUID();

        // 2. 获取当前时间
        LocalDateTime now = LocalDateTime.now();

        // 3. 组装故障实体
        Incident incident = Incident.builder()
                .EventNo(eventNo)
                .ServiceName(incidentCreateDTO.getServiceName())
                .ServiceVersion(incidentCreateDTO.getServiceVersion())
                .EventType(incidentCreateDTO.getEventType())
                .Severity(incidentCreateDTO.getSeverity())
                .Description(incidentCreateDTO.getDescription())
                .Status("DETECTED")
                .CreatedAt(now)
                .build();

        // 4. 保存到数据库
        int ok = incidentMapper.insert(incident);
        if (ok != 1) {
            throw new RuntimeException("创建故障事件失败");
        }
    }

    /**
     * 根据ID查询故障事件
     * @param id 故障事件ID
     * @return 故障事件实体
     */
    @Override
    public Incident getById(String id) {
        return incidentMapper.selectById(id);
    }

    /**
     * 查询所有故障事件
     * @return 所有故障事件列表
     */
       @Override
    public List<Incident> list() {
        return incidentMapper.selectList(null);
    }
}
