package com.iycharge.server.report.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;

/**
 * 用户统计
 *      集成属性说明： createdAt : 汇总时间
 * @author bwang
 */
@Entity
@Table(name="r_user_data")
public class UserData extends BaseEntity implements Serializable {
    
    /**
     * 
     */
    private static final long serialVersionUID = -1552745784201274021L;

    /**
     * 统计时段内新增个人会员数
     */
    @Column(name="new_pnum")
    @JsonView(Summary.class)
    private int newPNum;
    
    /**
     * 统计时段内新增企业会员数
     */
    @Column(name="new_cnum")
    @JsonView(Summary.class)
    private int newCNum;
    
    /**
     * 截止到当前统计日期，个人会员的总人数
     */
    @Column(name="total_pnum")
    @JsonView(Summary.class)
    private int totalPNum;
    
    /**
     * 截止到当前统计日期，企业会员的总人数
     */
    @Column(name="total_cnum")
    @JsonView(Summary.class)
    private int totalCNum;
    
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
    public UserData() {
        
    }

    public int getNewPNum() {
        return newPNum;
    }

    public void setNewPNum(int newPNum) {
        this.newPNum = newPNum;
    }

    public int getNewCNum() {
        return newCNum;
    }

    public void setNewCNum(int newCNum) {
        this.newCNum = newCNum;
    }

    public int getTotalPNum() {
        return totalPNum;
    }

    public void setTotalPNum(int totalPNum) {
        this.totalPNum = totalPNum;
    }

    public int getTotalCNum() {
        return totalCNum;
    }

    public void setTotalCNum(int totalCNum) {
        this.totalCNum = totalCNum;
    }

    public Date getDay() {
        return day;
    }

    public void setDay(Date day) {
        this.day = day;
    }

	public String getDatetype() {
		return datetype;
	}

	public void setDatetype(String datetype) {
		this.datetype = datetype;
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
    
}
