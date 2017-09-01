package com.iycharge.server.domain.entity.station;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.Image;
import com.iycharge.server.domain.entity.account.Account;
import com.iycharge.server.domain.entity.charger.Charger;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.notification.Notification;
import com.iycharge.server.domain.entity.operator.Operator;
import com.iycharge.server.domain.entity.review.Rating;
import com.iycharge.server.domain.entity.review.Review;
import com.iycharge.server.domain.entity.utils.outputExcel.ExcelResources;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.*;

/**
 * 充电站实体定义
 * @author bwang
 */
@Entity
@Table(name = "stations")
public class Station extends BaseEntity {
    /*
     * 充电站编码
     */
    @Column(unique = true)
    @JsonView(Summary.class)
    private String code;

    /*
     * 充电站名称
     */
    @Column(nullable=false)
    @JsonView(Summary.class)    
    private String name;
    
    /*
     * 充电站位置之省份
     */
    @Column(nullable=false)
    private String province;

    /*
     * 充电站位置之城市
     */
    @Column(nullable=false)
    private String city;

    /*
     * 充电站位置之行政区
    */
    @Column(nullable=false)
    private String district;

    /*
     * 充电站位置之详细地址
     */
    @JsonIgnore
    private String address;
    
    /*
     * 充电站经度
     */
    @JsonView(Summary.class)
    @Column(precision=19, scale=6)
    private BigDecimal longitude = BigDecimal.ZERO;

    /*
     * 充电站纬度
     */
    @JsonView(Summary.class)
    @Column(precision=19, scale=6)
    private BigDecimal latitude = BigDecimal.ZERO;
    
    /*
     * 充电站包含的电桩类型
     */
    @JsonView(Summary.class)
    private String chargerType;
    
    /*
     * 充电站对外开放类型
     */
    @JsonView(Summary.class)
    private String stationType;
    
    /*
     * 充电站状态, 根据其下充电桩的状态实时计算所得.
     */
    @JsonView(Summary.class)
    @Enumerated(EnumType.STRING)
    private StationStatus status = StationStatus.OFFLINE;
    
    /*
     * 充电站计费方式
     */
    @JsonView(Summary.class)
    private String paymentMethod;
    
    /*
     * 充电站功能区域
     */
    @JsonView(Summary.class)
    private String area ;
    
    /*
     * 充电站电力单位价值, 单位为 元/千瓦时
     */
    @JsonView(Summary.class)
    @Column(precision=10, scale=2)
    private BigDecimal price = BigDecimal.ZERO;
    
    /*
     * 电站营业状态
     */
    @JsonView(Summary.class)
    @Enumerated(EnumType.STRING)
    private OperatingStatus operatingStatus;
    
    /*
     * 停车费
     */
    @JsonView(Summary.class)
    @Column(precision=10, scale=2)
    private BigDecimal fee = BigDecimal.ZERO;
    
    /*
     * 营业时间
     */
    @JsonView(Summary.class)
    private String openTime;
    
    /*
     * 充电站在搜索引擎中的id
    */
    @JsonIgnore
    private String searchId;
    
    @JsonIgnore
    @ManyToMany(fetch = FetchType.LAZY,cascade={CascadeType.REFRESH}, mappedBy="stations")
    private List<Account> accounts;
    
    /*
     * 充电站描述
     */
    @Column(length = 4096)
    @JsonView(Summary.class)
    private String description;
    
    /*
     * 充电站所属运营商
     */
    @JsonView(Summary.class)
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.ALL})
    private Operator operator = new Operator();
    
    /*
     * 充电站图片
     */
    @JsonView(Detail.class)
    @OrderBy("position asc, id asc")
    @ManyToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL})
    private List<Image> images = new ArrayList<>();

    /*
     * 充电站评分
     */
    @Embedded
    @JsonView(Detail.class)
    private Rating rating = new Rating();

    /*
     * 充电站评价
     */
    @JsonView(Detail.class)
    @OneToMany(fetch = FetchType.LAZY, cascade = {CascadeType.ALL}, mappedBy="station")
    private Set<Review> reviews = new LinkedHashSet<>();
    
    /*
     * 充电站里的充电桩
     */
    @JsonView(Detail.class)
    @OneToMany(mappedBy = "station", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private Set<Charger> chargers = new HashSet<>();

    
    @Transient
    private String countAC="0";
    
    @Transient
	private String countDC="0";
    
    /*
     * 删除状态del删除，其他未删除
     */
    @JsonIgnore
    private String delStatus="normal";
    
    /*
     * 电桩总数 
     */
    @JsonView(Summary.class)
    @Transient
    private int totalCount;
    
    /*
     * 空闲电桩数
     */
    @JsonView(Summary.class)
    @Transient
    private int idleCount;
    /*
     * 电站总枪数
     */
    @JsonView(Summary.class)
    @Transient
    private int totalGuns;
    /*
     * 空闲枪数
     */
    @JsonView(Summary.class)
    @Transient
    private int totalIdelCung;
    
    @Transient
    private String codeAndName;
    
    
    /**
     * 电站被用户收藏的标记， Y ： 已收藏       N ： 未收藏
     */
    @JsonView(Summary.class)
    @Transient
    private String collected = "N";
    
    @Transient
    private String checked;
    @Transient
    private String flag="2";
    public Station() {
    }
    @ExcelResources(title="充电站编号",order=0)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    @ExcelResources(title="充电站名称",order=1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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
    @ExcelResources(title="所属行政区",order=3)
    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public BigDecimal getLongitude() {
        return longitude;
    }

    public void setLongitude(BigDecimal longitude) {
        this.longitude = longitude;
    }

    public BigDecimal getLatitude() {
        return latitude;
    }

    public void setLatitude(BigDecimal latitude) {
        this.latitude = latitude;
    }

 
  

    public StationStatus getStatus() {
        return status;
    }

    public void setStatus(StationStatus status) {
        this.status = status;
    }

  

    public String getPaymentMethod() {
		return paymentMethod;
	}

	public void setPaymentMethod(String paymentMethod) {
		this.paymentMethod = paymentMethod;
	}

	public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public OperatingStatus getOperatingStatus() {
        return operatingStatus;
    }

    public void setOperatingStatus(OperatingStatus operatingStatus) {
        this.operatingStatus = operatingStatus;
    }

    public BigDecimal getFee() {
        return fee;
    }

    public void setFee(BigDecimal fee) {
        this.fee = fee;
    }

    public String getOpenTime() {
        return openTime;
    }

    public void setOpenTime(String openTime) {
        this.openTime = openTime;
    }

    public String getSearchId() {
        return searchId;
    }

    public void setSearchId(String searchId) {
        this.searchId = searchId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Set<Review> getReviews() {
        return reviews;
    }

    public void setReviews(Set<Review> reviews) {
        this.reviews = reviews;
    }

    public Set<Charger> getChargers() {
        return chargers;
    }

    public void setChargers(Set<Charger> chargers) {
        this.chargers = chargers;
    }
    
    @JsonView(Summary.class)
    public String getLocation() {
        return province + city + district + address;
    }
  
    public void addReview(Review review) {
        rating.updateTotal(review.getRating().getTotal());
        rating.updateDevice(review.getRating().getDevice());
        rating.updateSpeed(review.getRating().getSpeed());

        reviews.add(review);
    }  
    @ExcelResources(title="交流桩数量",order=5)
	public String getCountAC() {
		return countAC;
	}

	public void setCountAC(String countAC) {
		this.countAC = countAC;
	}
	@ExcelResources(title="直流桩数量",order=6)
	public String getCountDC() {
		return countDC;
	}

	public void setCountDC(String countDC) {
		this.countDC = countDC;
	}

	public String getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}

    public int getTotalCount() {
        return totalCount;
    }

    public void setTotalCount(int totalCount) {
        this.totalCount = totalCount;
    }

    public int getIdleCount() {
        return idleCount;
    }

    public void setIdleCount(int idleCount) {
        this.idleCount = idleCount;
    }

	public String getCodeAndName() {
		return codeAndName;
	}

	public void setCodeAndName(String codeAndName) {
		this.codeAndName = codeAndName;
	}
    
    public String getChargerType() {
		return chargerType;
	}

	public void setChargerType(String chargerType) {
		this.chargerType = chargerType;
	}

	public String getStationType() {
		return stationType;
	}

	public void setStationType(String stationType) {
		this.stationType = stationType;
	}

	@JsonView(Summary.class)
    public String getSStatus() {
        return this.getStatus().getTitle();
    }
    
/*    @JsonView(Summary.class)
    public String getAType() {
        return this.getArea().getTitle();
    }*/
    
    @JsonView(Summary.class)
    public String getOStatus() {
        return this.getOperatingStatus().getTitle();
    }

    public String getCollected() {
        return collected;
    }

    public void setCollected(String collected) {
        this.collected = collected;
    }
    
    @JsonView(Summary.class)
	public String getStype() {
		return EntityUtil.getDictTile(CategoryConstant.STATION_TYPE, this.stationType);
	}

    
    @JsonView(Summary.class)
	public String getCtype() {
		return EntityUtil.getDictTile(CategoryConstant.CHAEGER_TYPE, this.chargerType);
	}
    
    @JsonView(Summary.class)
	public String getPayType() {
		return EntityUtil.getDictTile(CategoryConstant.PAYMENT_TYPE, this.paymentMethod);
	}


	@ExcelResources(title="所属运营商",order=4)
	public String getOper(){
		return this.operator.getName();
	}
	@ExcelResources(title="充电站类型",order=2)
	public String getst(){
		return EntityUtil.getDictTile(CategoryConstant.STATION_TYPE, this.getStationType());
	}
	
	@ExcelResources(title="状态",order=7)
    public String getStationST() {
        return this.getOperatingStatus().getTitle();
    }
	public String getChecked() {
		return checked;
	}
	public void setChecked(String checked) {
		this.checked = checked;
	}
	public String getFlag() {
		return flag;
	}
	public void setFlag(String flag) {
		this.flag = flag;
	}
	public List<Account> getAccounts() {
		return accounts;
	}
	public void setAccounts(List<Account> accounts) {
		this.accounts = accounts;
	}
	public int getTotalGuns() {
		return totalGuns;
	}
	public void setTotalGuns(int totalGuns) {
		this.totalGuns = totalGuns;
	}
	public int getTotalIdelCung() {
		return totalIdelCung;
	}
	public void setTotalIdelCung(int totalIdelCung) {
		this.totalIdelCung = totalIdelCung;
	}
	
}
