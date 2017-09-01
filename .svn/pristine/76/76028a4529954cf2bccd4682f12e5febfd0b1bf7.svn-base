package com.iycharge.server.report.entity;

import java.io.Serializable;
import java.math.BigDecimal;
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
 * 充值数据
 *      集成属性说明： createdAt : 汇总时间
 * @author zw
 */
@Entity
@Table(name="r_record_data")
public class RecordData extends BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7290001226138245643L;
    
    
    /**
     * 个人充值记录（当天）
     */
    @Column(name="person_record", precision=16, scale=2)
    @JsonView(Summary.class)
    private BigDecimal personRecord = new BigDecimal(0);
    
    /**
     * 个人消费记录（当天）
     */
    @Column(name="person_cost", precision=16, scale=2)
    @JsonView(Summary.class)
    private BigDecimal personCost = new BigDecimal(0);
    
    /**
     * 企业充值记录（当天）
     */
    @Column(name="company_record", precision=16, scale=2)
    @JsonView(Summary.class)
    private BigDecimal companyRecord = new BigDecimal(0);
    
    /**
     * 企业消费记录（当天）
     */
    @Column(name="company_cost", precision=16, scale=2)
    @JsonView(Summary.class)
    private BigDecimal companyCost = new BigDecimal(0);
    
    
    /**
     * 充值总数
     */
    @Column(name="record_total",precision=16, scale=2)
    @JsonView(Summary.class)
    private BigDecimal recordTotal = new BigDecimal(0);
    
    /**
     * 消费总数
     */
    @Column(name="cost_total",precision=16, scale=2)
    @JsonView(Summary.class)
    private BigDecimal costTotal = new BigDecimal(0);
    
    
    /**
     * 统计日期
     */
    @Temporal(TemporalType.DATE)
    private Date day;
    @Transient
    private String time;
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
    public RecordData() {
        
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

	public BigDecimal getPersonRecord() {
		return personRecord;
	}

	public void setPersonRecord(BigDecimal personRecord) {
		this.personRecord = personRecord;
	}

	public BigDecimal getPersonCost() {
		return personCost;
	}

	public void setPersonCost(BigDecimal personCost) {
		this.personCost = personCost;
	}

	public BigDecimal getCompanyRecord() {
		return companyRecord;
	}

	public void setCompanyRecord(BigDecimal companyRecord) {
		this.companyRecord = companyRecord;
	}

	public BigDecimal getCompanyCost() {
		return companyCost;
	}

	public void setCompanyCost(BigDecimal companyCost) {
		this.companyCost = companyCost;
	}

	public BigDecimal getRecordTotal() {
		return recordTotal;
	}

	public void setRecordTotal(BigDecimal recordTotal) {
		this.recordTotal = recordTotal;
	}

	public BigDecimal getCostTotal() {
		return costTotal;
	}

	public void setCostTotal(BigDecimal costTotal) {
		this.costTotal = costTotal;
	}

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}
    
}
