package com.iycharge.server.domain.entity.event;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity.Summary;

/**
 * 故障代码实体定义类
 * @author bwang
 */
@Entity
@Table(name="event_code")
public class EventCode {
    
    /*
     * 故障编码
     */
    @Id
    @JsonView(Summary.class)
    private int code;
    
    /*
     * 故障描述
     */
    @JsonView(Summary.class)
    private String decription;
    /*
     * 事件级别
     */


    @JsonView(Summary.class)
    private String eventLevel;

    
    /*
     *  事件类型 
     */
    @JsonView(Summary.class)
    private String eventType;
    
    /*
     * 是否可用 
     */
    private boolean isActive;
    
    /*
     * 备注
     */
    private String remark;
    
    @JsonIgnore
    @OneToMany(mappedBy = "eventCode", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Event> eventList = new ArrayList<>();
    
    public EventCode(){
        
    }
    
    public int getCode(){
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getDecription() {
        return decription;
    }

    public void setDecription(String decription) {
        this.decription = decription;
    }

    


    public String getEventLevel() {

		return eventLevel;
	}

	public void setEventLevel(String eventLevel) {
		this.eventLevel = eventLevel;
	}

	public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }

    public List<Event> getEventList() {
        return eventList;
    }

    public void setEventList(List<Event> eventList) {
        this.eventList = eventList;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }
}
