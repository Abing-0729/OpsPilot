package com.orders.opsservice.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.orders.opsservice.entity.Incident;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface IncidentMapper extends BaseMapper<Incident> {}
