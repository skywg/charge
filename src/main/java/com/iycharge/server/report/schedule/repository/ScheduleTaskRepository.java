package com.iycharge.server.report.schedule.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iycharge.server.report.schedule.ScheduleTask;

/**
 *
 * @author bwang
 */
@Repository
public interface ScheduleTaskRepository extends JpaRepository<ScheduleTask, Long>{
    
}
