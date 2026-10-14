package com.orders.opsservice.controller;

import com.orders.opscommon.common.Result;
import com.orders.opsservice.dto.IncidentCreateDTO;
import com.orders.opsservice.dto.IncidentUpdateDTO;
import com.orders.opsservice.entity.Incident;
import com.orders.opsservice.service.IncidentService;
import jakarta.validation.Valid;
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
    public Result<Incident> createIncident(@Valid @RequestBody IncidentCreateDTO incidentCreateDTO) {
        return Result.success(incidentService.create(incidentCreateDTO));
    }

    @PutMapping("/{id}")
    public Result<Incident> updateIncident(@PathVariable Long id,
                                           @Valid @RequestBody IncidentUpdateDTO incidentUpdateDTO) {
        return Result.success(incidentService.update(id, incidentUpdateDTO));
    }

    @GetMapping("/{id}")
    public Result<Incident> getIncident(@PathVariable Long id) {
        return Result.success(incidentService.getById(id));
    }

    @GetMapping
    public Result<List<Incident>> getAllIncidents() {
        return Result.success(incidentService.list());
    }
}
