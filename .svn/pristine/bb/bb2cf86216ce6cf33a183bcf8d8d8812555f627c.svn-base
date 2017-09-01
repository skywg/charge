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
 * 充电消费数据
 *      集成属性说明： createdAt : 汇总时间
 * @author bwang
 */
@Entity
@Table(name="r_charging_data")
public class ChargingData extends BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7290001226138245643L;
    
    /**
     * 省
     */
    @JsonView(Summary.class)
    private String  province;
    
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
     * 统计时段内通过直流桩充电的消费金额
     */
    @Column(name="money_dc", precision=16, scale=2)
    @JsonView(Summary.class)
    private BigDecimal moneyDC = new BigDecimal(0);
    
    /**
     * 统计时段内通过直流桩充电的电量
     */
    @Column(name="electric_dc", precision=16, scale=2)
    @JsonView(Summary.class)
    private BigDecimal electricDC = new BigDecimal(0);
    
    /**
     * 统计时段内通过直流桩充电的次数
     */
    @Column(name="times_dc")
    @JsonView(Summary.class)
    private long timesDC = 0;
    
    /**
     * 统计时段内通过交流桩充电的消费金额
     */
    @Column(name="money_ac", precision=16, scale=2)
    @JsonView(Summary.class)
    private BigDecimal moneyAC = new BigDecimal(0);
    
    /**
     * 统计时段内通过交流桩充电的电量
     */
    @Column(name="electric_ac", precision=16, scale=2)
    @JsonView(Summary.class)
    private BigDecimal electricAC = new BigDecimal(0);
    
    /**
     * 统计时段内通过交流桩充电的次数
     */
    @JsonView(Summary.class)
    @Column(name="times_ac")
    private long timesAC = 0;
    
    /**
     * 统计时段内通过app充电的消费金额
     */
    @Column(precision=16, scale=2)
    @JsonView(Summary.class)
    private BigDecimal moneyApp = new BigDecimal(0);
    
    /**
     * 统计时段内通过app充电的电量
     */
    @Column(precision=16, scale=2)
    @JsonView(Summary.class)
    private BigDecimal electricApp = new BigDecimal(0);
    
    /**
     * 统计时段内通过app充电的次数
     */
    @JsonView(Summary.class)
    private long timesApp = 0;
    
    /**
     * 统计时段内通过刷卡充电的消费金额
     */
    @Column(precision=16, scale=2)
    @JsonView(Summary.class)
    private BigDecimal moneyCard = new BigDecimal(0);
    
    /**
     * 统计时段内通过刷卡充电的电量
     */
    @Column(precision=16, scale=2)
    @JsonView(Summary.class)
    private BigDecimal electricCard = new BigDecimal(0);
    
    /**
     * 统计时段内通过刷卡充电的次数
     */
    @JsonView(Summary.class)
    private long timesCard = 0;
    
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
    private String counttype;
    @Transient
    private String type;
    @Transient
    private String checkboxstation;
    @Transient
    private String checkboxstationname;
    public ChargingData() {
        
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

    public BigDecimal getMoneyDC() {
        return moneyDC;
    }

    public void setMoneyDC(BigDecimal moneyDC) {
        this.moneyDC = moneyDC;
    }

    public BigDecimal getElectricDC() {
        return electricDC;
    }

    public void setElectricDC(BigDecimal electricDC) {
        this.electricDC = electricDC;
    }

    public long getTimesDC() {
        return timesDC;
    }

    public void setTimesDC(long timesDC) {
        this.timesDC = timesDC;
    }

    public BigDecimal getMoneyAC() {
        return moneyAC;
    }

    public void setMoneyAC(BigDecimal moneyAC) {
        this.moneyAC = moneyAC;
    }

    public BigDecimal getElectricAC() {
        return electricAC;
    }

    public void setElectricAC(BigDecimal electricAC) {
        this.electricAC = electricAC;
    }

    public long getTimesAC() {
        return timesAC;
    }

    public void setTimesAC(long timesAC) {
        this.timesAC = timesAC;
    }

    public BigDecimal getMoneyApp() {
        return moneyApp;
    }

    public void setMoneyApp(BigDecimal moneyApp) {
        this.moneyApp = moneyApp;
    }

    public BigDecimal getElectricApp() {
        return electricApp;
    }

    public void setElectricApp(BigDecimal electricApp) {
        this.electricApp = electricApp;
    }

    public long getTimesApp() {
        return timesApp;
    }

    public void setTimesApp(long timesApp) {
        this.timesApp = timesApp;
    }

    public BigDecimal getMoneyCard() {
        return moneyCard;
    }

    public void setMoneyCard(BigDecimal moneyCard) {
        this.moneyCard = moneyCard;
    }

    public BigDecimal getElectricCard() {
        return electricCard;
    }

    public void setElectricCard(BigDecimal electricCard) {
        this.electricCard = electricCard;
    }

    public long getTimesCard() {
        return timesCard;
    }

    public void setTimesCard(long timesCard) {
        this.timesCard = timesCard;
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

	public String getCounttype() {
		return counttype;
	}

	public void setCounttype(String counttype) {
		this.counttype = counttype;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
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

	public String getCheckboxstation() {
		return checkboxstation;
	}

	public void setCheckboxstation(String checkboxstation) {
		this.checkboxstation = checkboxstation;
	}

	public String getCheckboxstationname() {
		return checkboxstationname;
	}

	public void setCheckboxstationname(String checkboxstationname) {
		this.checkboxstationname = checkboxstationname;
	}
    
}
