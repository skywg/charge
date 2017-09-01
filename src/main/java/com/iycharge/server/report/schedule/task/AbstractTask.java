package com.iycharge.server.report.schedule.task;


import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import javax.annotation.Resource;

import com.iycharge.server.report.schedule.CheckType;
import com.iycharge.server.report.schedule.ScheduleTask;
import com.iycharge.server.report.schedule.service.ScheduleTaskService;

/**
 * 抽象工作任务
 * @author bwang
 */
public abstract class AbstractTask implements Runnable {  
    
    @Resource 
    private ScheduleTaskService scheduleTaskService;
    
    /**
     * 需要执行的调度任务
     */
    private ScheduleTask task;
    
    public AbstractTask() {
        
    }
    
    public AbstractTask(ScheduleTask task) {
        this.task = task;
    }

    public ScheduleTask getTask() {
        return task;
    }

    public void setTask(ScheduleTask task) {
        this.task = task;
    }
    
    /**
     * 执行统计的工作
     */
    public abstract void doWork();
    
    /**
     * 更新任务状态
     */
    public void updateTask() {
        if(this.getTask().isFlag()) {
            this.getTask().setLastTime(getEndTime());
            this.getTask().setLastDesr("汇总成功！");
        } else {
            this.getTask().setLastDesr("汇总失败！统计时段：[" + format(getStartTime()) + "~" + format(getEndTime()) + "]" );
        } 
        this.scheduleTaskService.update(task); 
    }
    
    
    /**
     * 返回汇总统计开始时间
     * @return
     */
    public Date getStartTime() {
        return this.task.getLastTime();
    }
    
    /**
     * 返回汇总统计结束时间
     * @return
     */
    public Date getEndTime() {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(this.getStartTime());
        if(this.task.getCheckType() == CheckType.DAY) {
            calendar.add(Calendar.DAY_OF_MONTH, 1);
        } else if (this.task.getCheckType() == CheckType.WEEK) {
            calendar.add(Calendar.DAY_OF_MONTH, 7);
        } else if (this.task.getCheckType() == CheckType.MONTH) {
            calendar.add(Calendar.MONTH, 1);
        }
        return calendar.getTime();
    }
    
    /**
     * 检测当前任务是否立即执行
     * @return
     */
    public boolean check() {
        return System.currentTimeMillis() > this.getEndTime().getTime();
    }
    
    private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
    
    /**
     * 格式化日期
     * @param date
     * @return
     */
    public static String format(Date date) {
        if(date == null) {
            return null;
        }      
        return sdf.format(date);
    }
}
