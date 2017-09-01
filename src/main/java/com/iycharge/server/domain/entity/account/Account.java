package com.iycharge.server.domain.entity.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.domain.entity.Authorization;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.BaseEntity.Summary;
import com.iycharge.server.domain.entity.account.favorite.Favorite;
import com.iycharge.server.domain.entity.admin.Role;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.notification.Notification;
import com.iycharge.server.domain.entity.order.Order;
import com.iycharge.server.domain.entity.order.RechargeRecord;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.entity.utils.Gender;
import com.iycharge.server.domain.entity.utils.outputExcel.ExcelResources;
import com.iycharge.server.domain.entity.utils.outputExcel.ExcelTemplate;

import javax.persistence.*;
import org.springframework.format.annotation.DateTimeFormat;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 * 会员实体类定义
 *
 * @author bwang
 */
@Entity
@Table(name = "accounts")
public class Account extends BaseEntity {
    
    /*
     * 会员类型：目前只有[个人会员和企业会员]
     */
    @Column
   
    private String accountType ;
    
    /*
     * 真实姓名
     */
    @JsonView(Summary.class)
    
    private String realName;
    
    /*
     * 性别
     */
    @JsonView(Summary.class)
    @Enumerated(EnumType.STRING)
    private Gender gender = Gender.MALE;
    
    /*
     * 出生日期
     */
    @Temporal(TemporalType.DATE)
    @DateTimeFormat(pattern="yyyy-MM-dd") 
    @JsonView(Summary.class)
    private Date birth;
    
    /*
     * 昵称
     */
    @JsonView(Summary.class)
    private String nickname;
    
    /*
     * 头像
     */
    @JsonView(Summary.class)
    private String avatar;
    
    /*
     * 手机号码
     */
    @JsonView(Private.class)
    @Column(unique = true)
    private String phone;
    
    /*
     * 密码
     */
    @JsonIgnore
    private String password;
    /*
     * 省
     */
    @Column
    @JsonView(Summary.class)
    private String province;
    /*
     * 城市
     */
    @Column
    @JsonView(Summary.class)
    private String city;
    /*
     * 区域
     */
    @Column
    @JsonView(Summary.class)
    private String area;
    /*
     * 详细地址
     */
    @Column
    @JsonView(Summary.class)
    private String detailAddress;
    
    /*
     * 注册会员状态
     */
    @JsonView(Summary.class)
    @Enumerated(EnumType.STRING)
    private AccountStatus status = AccountStatus.NORMAL;
    @JsonIgnore
    private String delstatus = "normal";
    
    @Transient
    private String transientStatus;
    
    /*
     * 级别
     */
    @JsonView(Private.class)
    private String level;
    
    /*
     * 电子邮箱
     */
    @JsonView(Private.class)
    private String email;
    
   /* @JsonView(Summary.class)
    @Column(unique = true)
    private EmailAddress email;
    */
    
    /*
     * 钱包余额
     */
    @JsonView(Private.class)
    @Column(precision=13, scale=2)
    private BigDecimal money = BigDecimal.ZERO;
    
    /*
     * 积分
     */
    @JsonView(Private.class)
    private long credit = 0L; 

    /*
     * 移动设备号
     */
    @JsonView(Private.class)
    private String deviceToken;

    /*
     * 备注
     */
    @JsonIgnore
    @Column(length = 500)
    private String note;
    
    /*
     * 绑定卡片
     */
    @JsonView(Private.class)
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy="account")
    private List<Card> cards = new ArrayList<>();
    
    /*
     * 我的收藏
     */
    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Favorite> favorites;

    /*
     * 我的汽车
     */
    @JsonView(Private.class)
    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY, mappedBy="account")
    private Car car;
    
    /*
     * 我的订单
     */
    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Order> orders = new LinkedList<Order>();
    
    /**
     * 会员只能在以下充电站下面充电
     */
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    @JoinTable(name = "account_station",
            joinColumns = {@JoinColumn(name = "account_id")},
            inverseJoinColumns = {@JoinColumn(name = "station_id")})
    private List<Station> stations;
    /*
     * 我的充值记录
     */
    @JsonIgnore
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<RechargeRecord> rechargeRecords;

    //@JsonIgnore
    @JsonView(Summary.class)
    @OneToMany(mappedBy = "account", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Authorization> authorizations = new LinkedList<Authorization>();
    
   // @JsonView(Summary.class)
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,cascade={CascadeType.REFRESH}, mappedBy="senders")
    private List<Notification> notifications;
    
    /*
     *  个人偏好
     */
    @JsonView(Private.class)
    @OneToOne(cascade=CascadeType.ALL, mappedBy="account", fetch=FetchType.LAZY)
    private Preference preference; 
    
    @Transient
    private String tempauthorization;
    
    @JsonIgnore
    @Column(nullable = false, columnDefinition = "BOOLEAN default 0")
    private boolean blocked = false;

    @JsonIgnore
    private String userAgent = "";
    
    @Transient
    private String transientDate;
    
    @Transient
    private String operator;
    
    @Transient
    private String payType;
    @Transient
    private String src;
    @Transient
    private BigDecimal payMoney = BigDecimal.ZERO;
    @Transient
    private String remark;
    @Transient
    private String flag="2";
    
    public String getAccountType() {
		return accountType;
	}
    @ExcelResources(title="类型",order=6)
    public String getStype(){
    	return EntityUtil.getDictTile(CategoryConstant.ACCOUNT_TYPE, this.accountType);
    }

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public String getTempauthorization() {
		return tempauthorization;
	}

	public void setTempauthorization(String tempauthorization) {
		this.tempauthorization = tempauthorization;
	}

	@ExcelResources(title="昵称",order=3)
    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }
    @ExcelResources(title="邮箱",order=4)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
    @ExcelResources(title="手机号",order=2)
    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }
    @ExcelResources(title="余额(元)",order=10)
    public BigDecimal getMoney() {
        return money;
    }
    
    public void setMoney(BigDecimal money) {
        this.money = money;
    }

    public List<Card> getCards() {
        return cards;
    }

    public void setCards(List<Card> cards) {
        this.cards = cards;
    }

    public String getAvatar() {
        return avatar;
    }

    public void setAvatar(String avatar) {
        this.avatar = avatar;
    }

    public String getDeviceToken() {
        return deviceToken;
    }

    public void setDeviceToken(String deviceToken) {
        this.deviceToken = deviceToken;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public List<Favorite> getFavorites() {
        return favorites;
    }

    public void setFavorites(List<Favorite> favorites) {
        this.favorites = favorites;
    }

    public List<Authorization> getAuthorizations() {
        return authorizations;
    }

    public void setAuthorizations(List<Authorization> authorizations) {
        this.authorizations = authorizations;
    }

    public boolean isBlocked() {
        return blocked;
    }

    public void setBlocked(boolean blocked) {
        this.blocked = blocked;
    }

    public String getUserAgent() {
        return userAgent;
    }

    public void setUserAgent(String userAgent) {
        this.userAgent = userAgent;
    }
    @ExcelResources(title="性别",order=5)
    public Gender getGender() {
        return gender;
    }
    
    public void setGender(Gender gender) {
        this.gender = gender;
    }
    
    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public List<Order> getOrders() {
        return orders;
    }

    public void setOrders(List<Order> orders) {
        this.orders = orders;
    }
    public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}
	@ExcelResources(title="姓名",order=1)
    public String getRealName() {
        return realName;
    }
    
    public void setRealName(String realName) {
        this.realName = realName;
    }
    @ExcelResources(title="生日",order=7)
    public Date getBirth() {
        return birth;
    }

    public void setBirth(Date birth) {
        this.birth = birth;
    }
    @ExcelResources(title="积分",order=9)
    public long getCredit() {
        return credit;
    }

    public void setCredit(long credit) {
        this.credit = credit;
    }
    @ExcelResources(title="会员级别",order=8)
	public String getLevel() {
		return level;
	}

	public void setLevel(String level) {
		this.level = level;
	}
	
	public AccountStatus getStatus() {
		return status;
	}
	@ExcelResources(title="状态",order=12)
	public String getsstuts(){
		return this.status.getTitle();
	}
	
	public void setStatus(AccountStatus status) {
		this.status = status;
	}

    public List<RechargeRecord> getRechargeRecords() {
        return rechargeRecords;
    }

    public void setRechargeRecords(List<RechargeRecord> rechargeRecords) {
        this.rechargeRecords = rechargeRecords;
    }
    
    public String getTransientDate() {
		return transientDate;
	}

	public void setTransientDate(String transientDate) {
		this.transientDate = transientDate;
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
	public String getArea() {
		return area;
	}
	public void setArea(String area) {
		this.area = area;
	}
	public String getDetailAddress() {
		return detailAddress;
	}
	public void setDetailAddress(String detailAddress) {
		this.detailAddress = detailAddress;
	}
	public String getTransientStatus() {
		return transientStatus;
	}

	public void setTransientStatus(String transientStatus) {
		this.transientStatus = transientStatus;
	}

	public String getOperator() {
		return operator;
	}

	public void setOperator(String operator) {
		this.operator = operator;
	}

	public String getPayType() {
		return payType;
	}

	public void setPayType(String payType) {
		this.payType = payType;
	}

	public BigDecimal getPayMoney() {
		return payMoney;
	}

	public void setPayMoney(BigDecimal payMoney) {
		this.payMoney = payMoney;
	}

	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}

	public List<Notification> getNotifications() {
		return notifications;
	}

	public void setNotifications(List<Notification> notifications) {
		this.notifications = notifications;
	}

    public Preference getPreference() {
        return preference;
    }

    public void setPreference(Preference preference) {
        this.preference = preference;
    }
    @ExcelResources(title="注册时间",order=11)
    public Date getCreatedAtexc() {
        return super.getCreatedAt();
    }
/*    @ExcelResources(title="序号",order=13)
    public String getIndex(){
    	return ExcelTemplate.getCurRowIndex()+"";
    }*/
	public String getDelstatus() {
		return delstatus;
	}
	public void setDelstatus(String delstatus) {
		this.delstatus = delstatus;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public List<Station> getStations() {
		return stations;
	}
	public void setStations(List<Station> stations) {
		this.stations = stations;
	}
    
}
