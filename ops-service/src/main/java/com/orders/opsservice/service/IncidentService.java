package com.orders.opsservice.service;

import com.orders.opsservice.dto.IncidentCreateDTO;
import com.orders.opsservice.entity.Incident;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface IncidentService {
    /**
     * 创建故障
     * @param incidentCreateDTO 故障创建DTO
     * @return 创建的故障实体
     */
    public void create(IncidentCreateDTO incidentCreateDTO);

    /**
     * 根据ID查询故障事件
     * @param id 故障事件ID
     * @return 故障事件实体
     */
    Incident getById(String id);

    /**
     * 查询所有故障事件
     * @return 所有故障事件列表
     */
    List<Incident> list();
}
