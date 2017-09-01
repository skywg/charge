package com.iycharge.server.domain.entity.operator;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;

import javax.persistence.*;

import org.springframework.web.multipart.MultipartFile;

/*
 * 运营商实体类定义
 */
@Entity
@Table(name = "operators")
public class Operator extends BaseEntity {
    /*
     * 运营商编码
     */
    //@Column(unique = true)
    @JsonView(Summary.class)
    private String code;
    /*
     * 运营商名称
     */
    //@Column(unique = true)
    @JsonView(Summary.class)
    private String name;

    /*
     * 运营商类型
     */
    @JsonView(Summary.class)
    private String type;

    /*
     * 运营商状态
     */
    @JsonView(Summary.class)
    @Enumerated(EnumType.STRING)
    private OperatorStatus status = OperatorStatus.NORMAL;

    /*
     * 运营商地址
     */
    @JsonIgnore
    private String address;

    
    /*
     * 运营商电话
     */
    @JsonView(Summary.class)
    private String telephone;
    
    @JsonView(Summary.class)
    private String contact;
    /*
     * 运营商描述
     */
    @JsonView(Summary.class)
    private String description;
    
   /* 
     * 上传图片名称
     
    @JsonView(Summary.class)
    private String fileName;*/

    @Transient
    private String codeAndName;
    /*
     * 删除状态
     */
    @JsonView(Summary.class)
    private String delStatus="normal";
   /* *//**
     * 图片
     *//*
    @Transient
    private MultipartFile file;*/
    @Transient
    private String flag="2";
    public Operator() {
    }


	public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public OperatorStatus getStatus() {
        return status;
    }

    public void setStatus(OperatorStatus status) {
        this.status = status;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }
    


	public String getType() {
		return type;
	}


	public void setType(String type) {
		this.type = type;
	}


	public String getDelStatus() {
		return delStatus;
	}

	public void setDelStatus(String delStatus) {
		this.delStatus = delStatus;
	}


	public String getCodeAndName() {
		return codeAndName;
	}


	public void setCodeAndName(String codeAndName) {
		this.codeAndName = codeAndName;
	}

	public String getContact() {
		return contact;
	}


	public void setContact(String contact) {
		this.contact = contact;
	}


	public String getAddress() {
		return address;
	}


	public void setAddress(String address) {
		this.address = address;
	}


	public String getFlag() {
		return flag;
	}


	public void setFlag(String flag) {
		this.flag = flag;
	}
	
}
