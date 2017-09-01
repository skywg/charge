package com.iycharge.server.report.schedule;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import javax.annotation.Resource;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import com.iycharge.server.ccu.util.Utility;
import com.iycharge.server.report.schedule.service.ScheduleTaskService;
import com.iycharge.server.report.schedule.task.AbstractTask;

/**
 * 任务调度中心
 * @author bwang
 */
@Component
public class ScheduleCenter {
    
    private Logger logger = LoggerFactory.getLogger(ScheduleCenter.class);
    
    @Resource
    private ScheduleTaskService scheduleTaskService;
    
    private static final int MAX_THREAD_NUM = 4;
    
    private ExecutorService executorService = Executors.newFixedThreadPool(MAX_THREAD_NUM);
    
    @Scheduled(cron="${quartz.cron}")
    public void execute() {   
        logger.info("ScheduleCenter execute() start");
        try{
            List<ScheduleTask> taskList = scheduleTaskService.findAll();
            if(taskList != null && taskList.size() > 0) {
                for(ScheduleTask task : taskList) {  
                    AbstractTask work = (AbstractTask)Utility.getBean(Class.forName(task.getWorkName()));
                    work.setTask(task);
                    executorService.execute(work); 
                }
            } 
        } catch (Exception e) {
            e.printStackTrace();
            logger.error("ScheduleCenter execute() ", e);
        }
        logger.info("ScheduleCenter execute() finished");
    }      
}
