package com.iycharge.server.report.schedule.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iycharge.server.report.schedule.ScheduleTask;
import com.iycharge.server.report.schedule.repository.ScheduleTaskRepository;
import com.iycharge.server.report.schedule.service.ScheduleTaskService;

/**
 *
 * @author bwang
 */
@Service
public class ScheduleTaskServiceImpl implements ScheduleTaskService {
    
    @Resource
    private ScheduleTaskRepository scheduleTaskRepository;
    
    @Override
    public List<ScheduleTask> findAll() {
        // TODO Auto-generated method stub
        return scheduleTaskRepository.findAll();
    }

    
    @Transactional(readOnly=false)
    @Override
    public ScheduleTask update(ScheduleTask task) {
        ScheduleTask persist = scheduleTaskRepository.findOne(task.getId());
        persist.setFlag(task.isFlag());
        persist.setLastTime(task.getLastTime());
        persist.setLastDesr(task.getLastDesr());
        return scheduleTaskRepository.save(persist);
    }

}
