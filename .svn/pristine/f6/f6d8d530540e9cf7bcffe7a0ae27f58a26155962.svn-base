package com.iycharge.server.report.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;

/**
 * 告警统计
 * @author bwang
 */
@Entity
@Table(name="r_event_data")
public class EventData extends BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3123183401470504413L;
    
    /**
     * 省
     */
    @JsonView(Summary.class)
    private String province;
    
    /**
     * 市
     */
    @JsonView(Summary.class)
    private String city;
    
    /**
     * 区 
     */
    @JsonView(Summary.class)
    private String district;
    
    /**
     * 电站ID
     */
    @JsonView(Summary.class)
    private long stationId;
    
    /**
     * 电站名称
     */
    @JsonView(Summary.class)
    private String stationName;
       
    /**
     * 事件类型
     */
    @JsonView(Summary.class)
    private String eventType;
    
    /**
     * 根据事件类型统计得到的告警数据
     */
    @JsonView(Summary.class)
    private long eventTypeNum; 
    
    /**
     * 事件级别
     */
    @JsonView(Summary.class)
    private String eventLevel;
    
    /**
     * 根据事件级别统计得到的告警数据
     */
    @JsonView(Summary.class)
    private long eventLevelNum;
    
    /**
     * 统计日期
     */
    @Temporal(TemporalType.DATE)
    private Date day;
    
    
    @Transient
    private String yearstartdate;
    @Transient
    private String yearenddate;
    @Transient
    private String monthstartdate;
    @Transient
    private String monthenddate;
    @Transient
    private String daystartdate;
    @Transient
    private String dayenddate;
    @Transient
    private String datetype;
    @Transient
    private String type;
    public EventData() {
        
    }

    public String getProvince() {
        return province;
    }

    public void setProvince(String province) {
        this.province = province;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public long getStationId() {
        return stationId;
    }

    public void setStationId(long stationId) {
        this.stationId = stationId;
    }

    public String getStationName() {
        return stationName;
    }

    public void setStationName(String stationName) {
        this.stationName = stationName;
    }

    public String getEventType() {
        return eventType;
    }

    public void setEventType(String eventType) {
        this.eventType = eventType;
    }

    public long getEventTypeNum() {
        return eventTypeNum;
    }

    public void setEventTypeNum(long eventTypeNum) {
        this.eventTypeNum = eventTypeNum;
    }

    public String getEventLevel() {
        return eventLevel;
    }

    public void setEventLevel(String eventLevel) {
        this.eventLevel = eventLevel;
    }

    public long getEventLevelNum() {
        return eventLevelNum;
    }

    public void setEventLevelNum(long eventLevelNum) {
        this.eventLevelNum = eventLevelNum;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

	public String getYearstartdate() {
		return yearstartdate;
	}

	public void setYearstartdate(String yearstartdate) {
		this.yearstartdate = yearstartdate;
	}

	public String getYearenddate() {
		return yearenddate;
	}

	public void setYearenddate(String yearenddate) {
		this.yearenddate = yearenddate;
	}

	public String getMonthstartdate() {
		return monthstartdate;
	}

	public void setMonthstartdate(String monthstartdate) {
		this.monthstartdate = monthstartdate;
	}

	public String getMonthenddate() {
		return monthenddate;
	}

	public void setMonthenddate(String monthenddate) {
		this.monthenddate = monthenddate;
	}

	public String getDaystartdate() {
		return daystartdate;
	}

	public void setDaystartdate(String daystartdate) {
		this.daystartdate = daystartdate;
	}

	public String getDayenddate() {
		return dayenddate;
	}

	public void setDayenddate(String dayenddate) {
		this.dayenddate = dayenddate;
	}

	public String getDatetype() {
		return datetype;
	}

	public void setDatetype(String datetype) {
		this.datetype = datetype;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	} 
    
    
}
