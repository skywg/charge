package com.iycharge.server.domain.entity.account;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.OneToOne;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.BaseEntity.Private;

/**
 * app用户偏好设置
 * @author bwang
 */
@Entity
public class Preference extends BaseEntity{
    
    /**
     * 汽车品牌
     */
	@JsonView(Summary.class)
    private String carBrand;
    
    /**
     * 家庭住址
     */
	@JsonView(Summary.class)
    private String homeAddress;
    
    /**
     * 公司地址
     */
	@JsonView(Summary.class)
    private String compnayAddress;
    
    /**
     * 关联会员
     */
	@JsonIgnore
    @OneToOne(cascade=CascadeType.REFRESH)
    private Account account;
    
    public Preference() {
        
    }

    public String getCarBrand() {
        return carBrand;
    }

    public void setCarBrand(String carBrand) {
        this.carBrand = carBrand;
    }

    public String getHomeAddress() {
        return homeAddress;
    }

    public void setHomeAddress(String homeAddress) {
        this.homeAddress = homeAddress;
    }

    public String getCompnayAddress() {
        return compnayAddress;
    }

    public void setCompnayAddress(String compnayAddress) {
        this.compnayAddress = compnayAddress;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
