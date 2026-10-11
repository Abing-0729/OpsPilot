package com.orders.opsservice.controller;

import com.orders.opscommon.common.Result;
import com.orders.opsservice.dto.IncidentCreateDTO;
import com.orders.opsservice.entity.Incident;
import com.orders.opsservice.service.IncidentService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/incidents")
@RequiredArgsConstructor
public class IncidentController {

    private final IncidentService incidentService;

    /**
     * 创建故障事件
     * @param incidentCreateDTO 故障创建DTO
     * @return 故障事件
     */
    @PostMapping
    public Result<Void> CreateIncident(@RequestBody IncidentCreateDTO incidentCreateDTO) {
        incidentService.create(incidentCreateDTO);
        return Result.success();
    }

    @GetMapping("/{id}")
    public Result<Incident> getIncident(@PathVariable String id) {
        return Result.success(incidentService.getById(id));
    }

    @GetMapping
    public Result<List<Incident>> getAllIncidents() {
        return Result.success(incidentService.list());
    }

//    @GetMapping("/check-service")
//    public Result<String> checkService() {
//
//    }
}
