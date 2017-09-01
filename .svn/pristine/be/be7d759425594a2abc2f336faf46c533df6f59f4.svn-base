package com.iycharge.server.report.schedule;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

import com.iycharge.server.domain.entity.BaseEntity;

/**
 * 调度任务
 * @author bwang
 */
@Entity
public class ScheduleTask extends BaseEntity implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = 794605747022275742L;
    
    /**
     * 数据名称
     */
    private String dataName;
    
    /**
     * 工作名称：执行具体汇总任务类全名
     */
    private String workName;
    
    /**
     * 汇总粒度(day, week, month)
     */
    @Enumerated(EnumType.STRING)
    private CheckType checkType;
    
    /**
     * 汇总进度
     */
    private Date lastTime;
    
    /**
     * 汇总结果： true 成功   false 失败
     */
    private boolean flag;
    
    /**
     * 上次任务汇总描述(汇总成功or汇总失败：统计时段：2016-11-01~2016-12-01)
     */
    private String lastDesr;
    
    public ScheduleTask() {
        
    }

    public String getDataName() {
        return dataName;
    }

    public void setDataName(String dataName) {
        this.dataName = dataName;
    }

    public String getWorkName() {
        return workName;
    }

    public void setWorkName(String workName) {
        this.workName = workName;
    }

    public CheckType getCheckType() {
        return checkType;
    }

    public void setCheckType(CheckType checkType) {
        this.checkType = checkType;
    }

    public Date getLastTime() {
        return lastTime;
    }

    public void setLastTime(Date lastTime) {
        this.lastTime = lastTime;
    }

    public boolean isFlag() {
        return flag;
    }

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    public String getLastDesr() {
        return lastDesr;
    }

    public void setLastDesr(String lastDesr) {
        this.lastDesr = lastDesr;
    }  
}
