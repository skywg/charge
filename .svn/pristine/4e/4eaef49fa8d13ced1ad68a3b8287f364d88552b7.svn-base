package com.iycharge.server.domain.entity.account;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.persistence.*;

import org.springframework.format.annotation.DateTimeFormat;

/**
 * 用户认证车辆信息
 * @author bwang
 */
@Entity
@Table(name = "my_car")
public class Car extends BaseEntity {
    /*
     * 品牌
     */
    @Column(name = "car_brand")
    @JsonView(Summary.class)
    private String carBrand;
    
    /*
     * 型号
     */
    @Column(name="car_type")
    @JsonView(Summary.class)
    private String carType;
    
    /*
     * 车架号
     */
    @Column(name = "frame_number")
    @JsonView(Summary.class)
    private String frameNumber;
    
    /*
     * 发动机号
     */
    @Column(name = "engine_number")
    @JsonView(Summary.class)
    private String engineNumber;
    
    /*
    * 车牌号
    */
    @Column(name = "plate_number")
    @JsonView(Summary.class)
    private String plateNumber;
    
    /*
     * 车辆VIN码
     */
    @Column(name = "vin_number")
    @JsonView(Summary.class)
    private String vinNumber;
    
    /*
    * 行驶证照片路径
    */
    
    @Column(name = "driving_license_photo")
    @JsonView(Summary.class)
    private String drivingLicensePhoto;

    /*
     * 验证状态
     */
    @JsonView(Summary.class)
    @Enumerated(EnumType.STRING)
    @Column(name="car_identify_status")
    private CarIdentifyStatus carIdentifyStatus;
    
    /*
     * 关联审核日志
     */
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY)
    @JoinColumn(name="car_id")
    private List<CarAuditLog> carAuditLogList = new ArrayList<>();
    
    /*
     * 关联注册会员
     */
    @OneToOne(cascade=CascadeType.REFRESH)
    private Account  account = new Account();
    
    /**
     *  开始时间（用户查询时接收表单参数）
     */
    @JsonIgnore
    @DateTimeFormat(pattern="yyyy-MM-dd")
    @Transient 
    private Date startAt;
    @Transient 
    private String formstartAt;
    @Transient 
    private String formupdateAt;
    @Transient 
    private String flag="2";
    public Car() {
        
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getCarType() {
        return carType;
    }

    public void setCarType(String carType) {
        this.carType = carType;
    }

    public String getFrameNumber() {
        return frameNumber;
    }

    public void setFrameNumber(String frameNumber) {
        this.frameNumber = frameNumber;
    }

    public String getEngineNumber() {
        return engineNumber;
    }

    public void setEngineNumber(String engineNumber) {
        this.engineNumber = engineNumber;
    }

    public String getPlateNumber() {
        return plateNumber;
    }

    public void setPlateNumber(String plateNumber) {
        this.plateNumber = plateNumber;
    }

    public String getVinNumber() {
        return vinNumber;
    }

    public void setVinNumber(String vinNumber) {
        this.vinNumber = vinNumber;
    }

	public String getDrivingLicensePhoto() {
        return drivingLicensePhoto;
    }

    public void setDrivingLicensePhoto(String drivingLicensePhoto) {
        this.drivingLicensePhoto = drivingLicensePhoto;
    }

    public CarIdentifyStatus getCarIdentifyStatus() {
		return carIdentifyStatus;
	}

	public void setCarIdentifyStatus(CarIdentifyStatus carIdentifyStatus) {
		this.carIdentifyStatus = carIdentifyStatus;
	}

    public List<CarAuditLog> getCarAuditLogList() {
        return carAuditLogList;
    }

    public void setCarAuditLogList(List<CarAuditLog> carAuditLogList) {
        this.carAuditLogList = carAuditLogList;
    }

	public Account getAccount() {
		return account;
	}

	public void setAccount(Account account) {
		this.account = account;
	}

	public Date getStartAt() {
		return startAt;
	}

	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}

	public String getFormstartAt() {
		return formstartAt;
	}

	public void setFormstartAt(String formstartAt) {
		this.formstartAt = formstartAt;
	}

	public String getFormupdateAt() {
		return formupdateAt;
	}

	public void setFormupdateAt(String formupdateAt) {
		this.formupdateAt = formupdateAt;
	}
	
	@JsonView(Summary.class)
	public String getStatus() {
	    return this.carIdentifyStatus.getTitle();
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
