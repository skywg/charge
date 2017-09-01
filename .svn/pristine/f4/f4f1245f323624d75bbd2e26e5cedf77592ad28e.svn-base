package com.iycharge.server.report.entity;

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;
import javax.persistence.UniqueConstraint;

import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.utils.outputExcel.ExcelResources;

/**
 * 用户账单 
 * @author bwang
 */
@Entity
@Table(name="r_user_bill_data", uniqueConstraints={@UniqueConstraint(columnNames={"account_id", "month"})})
public class UserBillData extends BaseEntity implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 9169197078678101811L;
    
    /**
     * 企业会员
     */
    @ManyToOne(cascade=CascadeType.REFRESH)
    @JoinColumn(name="account_id")
    private Account account;
    
    /**
     * 月度充电电量
     */
    @Column(precision=12, scale=2)
    private BigDecimal totalElectricity = new BigDecimal(0);
    
    /**
     * 月度消费金额
     */
    @Column(precision=12, scale=2)
    private BigDecimal totalMoney = new BigDecimal(0);
    
    /**
     * 期初金额
     */
    @Column(precision=12, scale=2)
    private BigDecimal startBalance = new BigDecimal(0);
    
    /**
     * 期末金额
     */
    @Column(precision=12, scale=2)
    private BigDecimal endBalance = new BigDecimal(0);
    
    /**
     * 月度入账金额
     */
    @Column(precision=12, scale=2)
    private BigDecimal totalRecharge = new BigDecimal(0);
    
    /**
     * 统计月份
     */
    @Temporal(TemporalType.DATE)
    private Date month;
    /**
     * 统计日期
     */
    
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
    public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
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

    public UserBillData() {
        
    }

    public Account getAccount() {
        return account;
    }
    @ExcelResources(title="会员名称",order=1)
	public String getAccountName(){
		if(this.account==null){
			return "";
		}
		return this.account.getRealName();
	}
    @ExcelResources(title="会员电话",order=2)
	public String getAccountPhone(){
		if(this.account==null){
			return "";
		}
		return this.account.getPhone();
	}

    public void setAccount(Account account) {
        this.account = account;
    }
    @ExcelResources(title="充电总量/kwh",order=3)
    public BigDecimal getTotalElectricity() {
        return totalElectricity;
    }

    public void setTotalElectricity(BigDecimal totalElectricity) {
        this.totalElectricity = totalElectricity;
    }
    @ExcelResources(title="充电金额",order=4)
    public BigDecimal getTotalMoney() {
        return totalMoney;
    }

    public void setTotalMoney(BigDecimal totalMoney) {
        this.totalMoney = totalMoney;
    }
    @ExcelResources(title="期初金额",order=5)
    public BigDecimal getStartBalance() {
        return startBalance;
    }

    public void setStartBalance(BigDecimal startBalance) {
        this.startBalance = startBalance;
    }
    @ExcelResources(title="期末余额",order=7)
    public BigDecimal getEndBalance() {
        return endBalance;
    }

    public void setEndBalance(BigDecimal endBalance) {
        this.endBalance = endBalance;
    }
    @ExcelResources(title="本期入账金额",order=6)
    public BigDecimal getTotalRecharge() {
        return totalRecharge;
    }

    public void setTotalRecharge(BigDecimal totalRecharge) {
        this.totalRecharge = totalRecharge;
    }
    @ExcelResources(title="时间",order=0)
    public Date getMonth() {
        return month;
    }

    public void setMonth(Date month) {
        this.month = month;
    }
}
