package com.iycharge.server.domain.entity.event;

import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.utils.outputExcel.ExcelResources;


/**
 * 充电桩告警定义
 * @author bwang
 */
@Entity
@Table(name="event")
public class Event extends BaseEntity{
    
    /**
     * 产生告警的电桩
     */
    @JsonView(Summary.class)
    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.REFRESH})
    private Charger charger;
    
    /**
     * 所属站点名称
     */
    @JsonView(Summary.class)   
    private String station;
    
    /**
     *  所属运营商名称
     */
    @JsonView(Summary.class)
    private String operation;
    
    /**
     * 故障时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @JsonView(Summary.class)
    private Date eventTime;
    
	/**
     * 产生故障
     */
    @JsonView(Summary.class)
    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="event_code")
    private EventCode eventCode;
    
    /**
     * 故障处理标记
     */
    @JsonView(Summary.class)
    @Enumerated(EnumType.STRING)
    @Column(name="event_status")
    private EventStatus eventStatus = EventStatus.SUSPENDING;
    
    /**
     * 处理过程中的日志记录
     */
    @JsonView(Summary.class)
    @OneToMany(cascade=CascadeType.ALL, mappedBy="event", fetch=FetchType.LAZY)
    private List<EventAuditLog> eventAuditLogs;
    
    /**
     * 处理人
     */
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "loginName")
    private Manager manager;
    
    public Manager getManager() {
		return manager;
	}

	public void setManager(Manager manager) {
		this.manager = manager;
	}

	public List<EventAuditLog> getEventAuditLogs() {
		return eventAuditLogs;
	}

	public void setEventAuditLogs(List<EventAuditLog> eventAuditLogs) {
		this.eventAuditLogs = eventAuditLogs;
	}

    public Event() {
        
    }

    public Charger getCharger() {
        return charger;
    }

    public void setCharger(Charger charger) {
        this.charger = charger;
    }
    @ExcelResources(title="充电站名称",order=1)
    public String getStation() {
        return station;
    }

    public void setStation(String station) {
        this.station = station;
    }

    public String getOperation() {
        return operation;
    }

    public void setOperation(String operation) {
        this.operation = operation;
    }

    public EventCode getEventCode() {
        return eventCode;
    }

    public void setEventCode(EventCode eventCode) {
        this.eventCode = eventCode;
    }

    public EventStatus getEventStatus() {
        return eventStatus;
    }

    public void setEventStatus(EventStatus eventStatus) {
        this.eventStatus = eventStatus;
    }

    public Date getEventTime() {
        return eventTime;
    }

    public void setEventTime(Date eventTime) {
        this.eventTime = eventTime;
    }
    @ExcelResources(title="告警发生时间",order=0)
    public Date getHappenTime(){
    	return super.getCreatedAt();
    }
    
    @ExcelResources(title="充电桩名称",order=2)
    public String getCname(){
    	return this.charger.getName();
    }
    @ExcelResources(title="告警级别",order=3)
    public String getLevel(){
    	return EntityUtil.getDictTile(CategoryConstant.EVENT_LEVEL, this.eventCode.getEventLevel());
    }
    @ExcelResources(title="告警类型",order=4)
    public String getType(){
    	return EntityUtil.getDictTile(CategoryConstant.EVENT_TYPE, this.eventCode.getEventType());
    }
    @ExcelResources(title="告警内容",order=5)
    public String getContent(){
    	return this.eventCode.getDecription();
    }
    @ExcelResources(title="状态",order=6)
    public String getsstatus(){
    	return this.eventStatus.getTitle();
    }
    
    @ExcelResources(title="清除时间",order=7)
    public Date getDealTime(){
    	return super.getUpdatedAt();
    }
}
