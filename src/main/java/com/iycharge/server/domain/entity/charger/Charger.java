package com.iycharge.server.domain.entity.charger;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

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
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iycharge.server.admin.cache.EntityUtil;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.BaseEntity.Private;
import com.iycharge.server.domain.entity.account.Card;
import com.iycharge.server.domain.entity.dict.CategoryConstant;
import com.iycharge.server.domain.entity.event.Event;
import com.iycharge.server.domain.entity.operator.Operator;
import com.iycharge.server.domain.entity.price.ParamNameConstant;
import com.iycharge.server.domain.entity.price.ParamTemplate;
import com.iycharge.server.domain.entity.price.ParamTemplateAttr;
import com.iycharge.server.domain.entity.station.Station;
import com.iycharge.server.domain.entity.utils.outputExcel.ExcelResources;
import com.iycharge.server.domain.entity.utils.serializer.JsonStationSerializer;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

/**
 * 充电桩 实体定义
 * @author bwang
 */
@Entity
@Table(name = "chargers")
public class Charger extends BaseEntity {
    
    /*
     * 充电桩编码
     */
    @Column(unique = true)
    @JsonView(Summary.class)
    private String code;
    
    /*
     * 充电桩名称
     */
    @JsonView(Summary.class)
    private String name;
    
    /*
     * 充电桩状态.
     */
    @JsonView(Summary.class)
    @Enumerated(EnumType.STRING)
    private ChargerStatus status;
    
    /*
     * 车位号
     */
    @JsonView(Summary.class)
    private String parkNo;
    
    /*
     * 充电桩生产厂商
     */
    @JsonView(Detail.class)
    private String manufacturer;
    
    /*
     * 充电桩类型
     */
    @JsonView(Summary.class)
    private String type;
    
    /*
     * 联网类型
     */
    @JsonView(Summary.class)
    @Column(name="neting_work_model")
    private String netingWorkModel;
    
    /*
     * 充电类型
     */
    @JsonView(Summary.class)
    @Column(name="charger_model")
    private String chargerModel;
    
    /*
     * 充电接口 
     */
    @JsonView(Summary.class)
    private String chargeIf;
    
    /*
     * 电压
     */
    @JsonView(Summary.class)
    private String voltage;
    
    /*
     * 电流
     */
    @JsonView(Summary.class)
    private String electricity;
    
    /*
     * 功率
     */
    @JsonView(Summary.class)
    private String power;
    
    /* 
     * 支持电动汽车品牌 
     */
    @JsonView(Summary.class)
    private String supportCars;
    
    /*
     * 是否认证
     */
    @JsonView(Summary.class)
    @Column(nullable = false, columnDefinition = "BOOLEAN default 0")
    public Boolean certified = false;
    
    /*
     * 是否联网
    */
    @JsonView(Summary.class)
    @Column(nullable = false, columnDefinition = "BOOLEAN default 0")
    public Boolean onlined = false;
    
    /*
     * 是否支持预约
     */
    @JsonView(Summary.class)
    @Column(nullable = false, columnDefinition = "BOOLEAN default 0")
    public Boolean onorder = false;
    /*
     * 充电桩电力单位价值, 单位为 0/kWh
     */
    @JsonView(Summary.class)
    @Column(precision=10, scale=2)
    private BigDecimal price = BigDecimal.ZERO;
    
    /*
     * 充电桩描述
     */
    @JsonView(Summary.class)
    @Column(length = 10000)
    private String description;
    /*
     * 二维码
     */
    @JsonView(Summary.class)
    @Column(length = 255)
    private String  qrcode;
    
    

	public String getQrcode() {
		return qrcode;
	}

	public void setQrcode(String qrcode) {
		this.qrcode = qrcode;
	}

	/*
     * 充电桩所属的充电站
     */
    @JsonView(Detail.class)
    @JsonSerialize(using=JsonStationSerializer.class)
    @ManyToOne(fetch = FetchType.LAZY, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Station station = new Station();
    
    /*
     * 所属运营商
     */
    @JsonIgnore
    //@JsonView(Detail.class)
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    private Operator operator = new Operator();
    
    @JsonIgnore
    @OneToMany(mappedBy="charger", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Event> event;
    /*
     * 包含充电枪
     */
    @JsonView(Detail.class)
    @OneToMany(mappedBy="charger", cascade = CascadeType.ALL,fetch = FetchType.EAGER)
    private List<ChargerGun> guns = new ArrayList<>();
    
    /*
     * 电价计费模板
     */
    @Transient
    private ParamTemplate priceTemplate;
    /*
     * 参数模板
     */
    @Transient
    private ParamTemplate paramTemplate;
    @Transient
    private String flag = "2";
    @Transient
    private String stationcheckboxs;
    @Transient
    private List<Station> lstation = new ArrayList<Station>();
    public ParamTemplate getParamTemplate() {
		return paramTemplate;
	}

	public void setParamTemplate(ParamTemplate paramTemplate) {
		this.paramTemplate = paramTemplate;
	}

	/*
     * 生效时间
     */
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name="effective_time")
    private Date  effectiveTime;
    
    /*
     * 电价是否被下发，0: 没有下发或下发失败，1 ：下发成功
     */
    @JsonIgnore
    @JoinColumn(name="price_setting_flag", columnDefinition = "BOOLEAN default 0")
    private boolean priceSettingFlag;
    
    /*
     * 删除状态del删除，其他未删除
     */
    @JsonIgnore
    private String delStatus="normal";
    
    @Transient
    private String codeAndName;
    
    @Transient
    private String checked;
    /*
     * Default Constructor
     */
    public Charger() {
        
    }
    @ExcelResources(title="充电桩编号",order=0)
    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
    @ExcelResources(title="充电桩名称",order=1)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    public ChargerStatus getStatus() {
        return status == null ? ChargerStatus.OFFLINE : status;
    }

    public void setStatus(ChargerStatus status) {
        this.status = status;
    }

    public String getParkNo() {
        return parkNo;
    }

    public void setParkNo(String parkNo) {
        this.parkNo = parkNo;
    }
    @ExcelResources(title="充电桩厂家",order=5)
    public String getManufacturer() {
        return manufacturer;
    }
    
    public void setManufacturer(String manufacturer) {
        this.manufacturer = manufacturer;
    }

    public String getVoltage() {
        return voltage;
    }

    public void setVoltage(String voltage) {
        this.voltage = voltage;
    }
   
    public String getElectricity() {
        return electricity;
    }

    public void setElectricity(String electricity) {
        this.electricity = electricity;
    }

    public String getPower() {
        return power;
    }

    public void setPower(String power) {
        this.power = power;
    }

	public Boolean getCertified() {
        return certified;
    }

    public void setCertified(Boolean certified) {
        this.certified = certified;
    }

    public Boolean getOnlined() {
        return onlined;
    }

    public void setOnlined(Boolean onlined) {
        this.onlined = onlined;
    }

    public BigDecimal getPrice() {
        return price;
    }

    public void setPrice(BigDecimal price) {
        this.price = price;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Station getStation() {
        return station;
    }

    public void setStation(Station station) {
        this.station = station;
    }

    public Operator getOperator() {
        return operator;
    }

    public void setOperator(Operator operator) {
        this.operator = operator;
    }
	public String getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}

	public Boolean getOnorder() {
		return onorder;
	}

	public void setOnorder(Boolean onorder) {
		this.onorder = onorder;
	}

	public String getSupportCars() {
		return supportCars;
	}

	public void setSupportCars(String supportCars) {
		this.supportCars = supportCars;
	}
          
    @JsonView(Summary.class)
    public String getCStatus(){
        return this.getStatus() == null ? ChargerStatus.OFFLINE.getTitle() : this.getStatus().getTitle();
    }
    
    @JsonView(Detail.class)
    public JSONArray getPriceTemplate() {
        JSONArray priceItems = new JSONArray();
        if(priceTemplate != null) {
            List<ParamTemplateAttr> attrs = priceTemplate.getParamList();
            Map<String, String> temp = new HashMap<>();
            for(ParamTemplateAttr attr : attrs) {
                temp.put(attr.getAttrName(), attr.getAttrVal());
            }
            int periods = attrs.size() / 5;
            for(int i=0; i<periods; i++) {
                JSONObject item = new JSONObject();
                item.put(ParamNameConstant.PRICE_START_TIME, temp.get(ParamNameConstant.PRICE_START_TIME + i));
                item.put(ParamNameConstant.PRICE_END_TIME, temp.get(ParamNameConstant.PRICE_END_TIME + i));
                item.put(ParamNameConstant.PRICE_PRICE, temp.get(ParamNameConstant.PRICE_PRICE + i));
                item.put(ParamNameConstant.PRICE_FEE, temp.get(ParamNameConstant.PRICE_FEE + i));
                item.put(ParamNameConstant.PRICE_REMARK, temp.get(ParamNameConstant.PRICE_REMARK + i));
                
                priceItems.add(item);
            }
        }
		return priceItems;
	}

	public void setPriceTemplate(ParamTemplate priceTemplate) {
		this.priceTemplate = priceTemplate;
	}

	public boolean isPriceSettingFlag() {
        return priceSettingFlag;
    }

    public void setPriceSettingFlag(boolean priceSettingFlag) {
        this.priceSettingFlag = priceSettingFlag;
    }

    public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

	public String getCodeAndName() {
		return codeAndName;
	}

	public void setCodeAndName(String codeAndName) {
		this.codeAndName = codeAndName;
	}

	public String getType() {
		return type;
	}

	public void setType(String type) {
		this.type = type;
	}

	public String getNetingWorkModel() {
		return netingWorkModel;
	}

	public void setNetingWorkModel(String netingWorkModel) {
		this.netingWorkModel = netingWorkModel;
	}

	public String getChargerModel() {
		return chargerModel;
	}

	public void setChargerModel(String chargerModel) {
		this.chargerModel = chargerModel;
	}

	public String getChargeIf() {
		return chargeIf;
	}

	public void setChargeIf(String chargeIf) {
		this.chargeIf = chargeIf;
	}

	public List<Event> getEvent() {
		return event;
	}

	public void setEvent(List<Event> event) {
		this.event = event;
	}
	
	@JsonView(Summary.class)
	public String getCif() {
		return EntityUtil.getDictTile(CategoryConstant.CONNECTOR_TYPE, this.getChargeIf());
	}

	
	
	@JsonView(Summary.class)
	public String getCtype() {
		return EntityUtil.getDictTile(CategoryConstant.EQUIPMENT_TYPE, this.getType());
	}

	
	@ExcelResources(title="所属运营商",order=2)
	public String getopname(){
		return this.operator.getName();
	}
	@ExcelResources(title="	所属充电站",order=3)
	public String getstname(){
		return this.station.getName();
	}
	@ExcelResources(title="充电桩类型",order=4)
	public String getchType(){
		return EntityUtil.getDictTile(CategoryConstant.EQUIPMENT_TYPE, this.type);
	}
/*	@ExcelResources(title="接口类型",order=6)
	public String getifType(){
		return EntityUtil.getDictTile(CategoryConstant.CONNECTOR_TYPE, this.type);
	}*/
	@ExcelResources(title="状态",order=7)
	public String getst(){
		return this.status.getTitle();
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

	public String getStationcheckboxs() {
		return stationcheckboxs;
	}

	public void setStationcheckboxs(String stationcheckboxs) {
		this.stationcheckboxs = stationcheckboxs;
	}

	public List<Station> getLstation() {
		return lstation;
	}

	public void setLstation(List<Station> lstation) {
		this.lstation = lstation;
	}

	public List<ChargerGun> getGuns() {
		return guns;
	}

	public void setGuns(List<ChargerGun> guns) {
		this.guns = guns;
	}
	
	

}

