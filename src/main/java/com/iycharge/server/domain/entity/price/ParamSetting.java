package com.iycharge.server.domain.entity.price;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.charger.Charger;


/**
 * 参数设置
 * 继承属性说明：
 *      createdAt : 首次添加的时间 
 *      updatedAt : 再次编辑的时间
 * @author bwang
 */
@Entity
@Table(name="param_setting")
public class ParamSetting extends BaseEntity{
    
    /*
     * 参数下发任务名称
     */
    private String name;
    
    /*
     * 类型
     */
    @Column(name="param_type")
    private String paramType;
    
    /*
     * 生效时间
     */
    @DateTimeFormat(pattern="yyyy-MM-dd HH:mm:ss") 
    @JsonView(Summary.class)
    @Column(name="effective_time")
    private Date effectiveTime;
    /**
     * 下发电桩
     */
    @JsonView(Summary.class)
    @Column(name="chargerIds")
    private String chargerIds;

    /*
     * 使用模板
     */
    @ManyToOne(cascade={CascadeType.MERGE, CascadeType.REFRESH})
    @JoinColumn(name="param_template_id")
    private ParamTemplate paramTemplate = new ParamTemplate();
    
    /*
     * 备注信息
     */
    private String remark;
    
    /*
     * 下发人所属运营商
     */
    @Column(name="operator_name")
    private String operatorName;
    
    /*
     * 下发人姓名
     */
    @Column(name="user_name")
    private String sender;
    
    /*
     * 下发时间
     */
    @Column(name="send_time")
    private Date sendTime;
    
    /*
     * 参数下发结果
     */
    @OneToMany(mappedBy="paramSetting", cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    private List<ParamSettingResult> settingResult = new ArrayList<>();
    
    /*
     * 下发标记
     *      false : 未下发
     *      true  ： 已下发
     */
    @Column(nullable=false)
    private String sendFlag;
    
    /*
     * 逻辑删除状态
     */
    @JsonView(Summary.class)
    private String delStatus="normal";
    
    @Transient
    private String actionType;
    //未下发charger
    @Transient
    private Charger charger =new Charger();
    //已下发charger
    @Transient
    private Charger chargerY =new Charger();
    @Transient
    private String hiddenchargerNocheck;
    @Transient
    private String hiddenchargerCheck;
    @Transient
    private List<Charger> chargers = new ArrayList<Charger>();
    @Transient
    private List<Charger> chargersY = new ArrayList<Charger>();
    @Transient
    private String chargersName;
    @Transient
    private String flag="2";
    
    public Charger getChargerY() {
		return chargerY;
	}

	public void setChargerY(Charger chargerY) {
		this.chargerY = chargerY;
	}

	public String getHiddenchargerNocheck() {
		return hiddenchargerNocheck;
	}

	public void setHiddenchargerNocheck(String hiddenchargerNocheck) {
		this.hiddenchargerNocheck = hiddenchargerNocheck;
	}

	public String getHiddenchargerCheck() {
		return hiddenchargerCheck;
	}

	public void setHiddenchargerCheck(String hiddenchargerCheck) {
		this.hiddenchargerCheck = hiddenchargerCheck;
	}

	public String getActionType() {
		return actionType;
	}

	public void setActionType(String actionType) {
		this.actionType = actionType;
	}

	public String getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}

	/*
     * 默认构造函数
     */
    public ParamSetting() {
        
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getParamType() {
		return paramType;
	}

	public void setParamType(String paramType) {
		this.paramType = paramType;
	}

	public Date getEffectiveTime() {
        return effectiveTime;
    }

    public void setEffectiveTime(Date effectiveTime) {
        this.effectiveTime = effectiveTime;
    }

    public String getRemark() {
        return remark;
    }

    public ParamTemplate getParamTemplate() {
		return paramTemplate;
	}

	public void setParamTemplate(ParamTemplate paramTemplate) {
		this.paramTemplate = paramTemplate;
	}

	public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getOperatorName() {
        return operatorName;
    }

    public void setOperatorName(String operatorName) {
        this.operatorName = operatorName;
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String sender) {
        this.sender = sender;
    }

    public Date getSendTime() {
        return sendTime;
    }

    public void setSendTime(Date sendTime) {
        this.sendTime = sendTime;
    }

    public List<ParamSettingResult> getSettingResult() {
        return settingResult;
    }

    public void setSettingResult(List<ParamSettingResult> settingResult) {
        this.settingResult = settingResult;
    }

	

	public String getSendFlag() {
		return sendFlag;
	}

	public void setSendFlag(String sendFlag) {
		this.sendFlag = sendFlag;
	}

	public String getChargerIds() {
		return chargerIds;
	}

	public void setChargerIds(String chargerIds) {
		this.chargerIds = chargerIds;
	}

	public Charger getCharger() {
		return charger;
	}

	public void setCharger(Charger charger) {
		this.charger = charger;
	}

	public List<Charger> getChargers() {
		return chargers;
	}

	public void setChargers(List<Charger> chargers) {
		this.chargers = chargers;
	}

	public List<Charger> getChargersY() {
		return chargersY;
	}

	public void setChargersY(List<Charger> chargersY) {
		this.chargersY = chargersY;
	}

	public String getChargersName() {
		return chargersName;
	}

	public void setChargersName(String chargersName) {
		this.chargersName = chargersName;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

	
}
