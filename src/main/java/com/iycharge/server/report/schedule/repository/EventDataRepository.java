package com.iycharge.server.report.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iycharge.server.report.entity.EventData;

/**
 * 告警统计数据查询DAO接口
 * @author bwang
 */
@Repository
public interface EventDataRepository extends JpaRepository<EventData, Long> {
    
}
