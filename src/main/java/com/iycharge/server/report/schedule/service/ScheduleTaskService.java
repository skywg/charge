package com.iycharge.server.report.schedule.service;

import java.util.List;

import com.iycharge.server.report.schedule.ScheduleTask;

/**
 *
 * @author bwang
 */
public interface ScheduleTaskService {
    
    /**
     * 查询所有任务
     * @return
     */
    List<ScheduleTask> findAll();
    
    /**
     * 更新任务
     * @param task
     * @return
     */
    ScheduleTask update(ScheduleTask task);
       
}
