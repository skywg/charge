package com.iycharge.server.domain.entity.content;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import javax.persistence.Transient;

import org.springframework.format.annotation.DateTimeFormat;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.fasterxml.jackson.databind.annotation.JsonSerialize;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.utils.serializer.JSonDateSerializer;


/**
 * 内容管理
 * @author daisi
 *
 */
@Entity
@Table(name = "contents")
public class Content extends BaseEntity{
	 /*
	  * 分类
	  */
	 @JsonView(Summary.class)
	 private String classification ;
	 /*
	  * 内容类型
	  */
	 @JsonView(Summary.class)
	 @Column(name="text_type")
	 private String textType ;
	
	/*
	  * 标题
	  */
	 @JsonView(Summary.class)
	 private String title;
	 /*
	  * 短标题
	  */
	//@JsonView(Summary.class)
	 @JsonIgnore
	 private String slug;
	 /*
	  * 备注
	  */
	 @JsonIgnore
	 @Column(length = 4096)
	 private String remark;
	 /*
	  * 关键字
	  */
	 @JsonIgnore
	 private String keyword;
	 /*
	  * 描述
	  */
	 @JsonIgnore
	 @Column(length = 4096)
	 private String description;
	/*
	 * 封面图片
	 *
	 *
	 */
	  @JsonView(Summary.class)
	  private String image;
	 
	 /*
	  * 内容文本
	  */
	 @JsonView(Summary.class)
	 @Column(length = 10000)
	 private  String text;
	 /*
	  * 链接
	  */
	 @JsonView(Summary.class)
	 private  String url;
	/*
	  * 编辑人
	  */
	 @JsonIgnore
	 private String name;
	 /*
	  * 状态：-1 : 编辑    0 ：提交     1：审核    2：发布  3:下架
	  */
	 private byte status = -1;
	 @Transient 
	 private String transientStatus;
	 /*
	  *  开始时间（用户查询时接收表单参数）
	  */
	 @JsonIgnore
	 @DateTimeFormat(pattern="yyyy-MM-dd")
	 @Transient 
	 private Date startAt;
	    
	 public Date getStartAt() {
		return startAt;
     }
	 /*
	  * 有效时间
	  */
	 @DateTimeFormat(pattern="yyyy-MM-dd")
	 @Column(name = "valid_at")
	 @JsonView(Summary.class)
	 @JsonSerialize(using=JSonDateSerializer.class)
	 private Date validAt;
	 public Date getValidAt() {
		return validAt;
	}
	public void setValidAt(Date validAt) {
		this.validAt = validAt;
	}
	/*
	  * 发布时间
	  */
	 @DateTimeFormat(pattern="yyyy-MM-dd")
	 @Column(name = "released_at")
	 @JsonView(Summary.class)
	 @JsonSerialize(using=JSonDateSerializer.class)
	 private Date releasedAt;
	 
	 @Transient
	 private String sClassification;
	 @Transient
	 private String sContentType;
	 
	public Date getReleasedAt() {
		return releasedAt;
	}
	public void setReleasedAt(Date releasedAt) {
		this.releasedAt = releasedAt;
	}
	public void setStartAt(Date startAt) {
		this.startAt = startAt;
	}

	public String getClassification() {
		return classification;
	}
	public void setClassification(String classification) {
		this.classification = classification;
	}

	public String getTextType() {
		return textType;
	}
	public void setTextType(String textType) {
		this.textType = textType;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getSlug() {
		return slug;
	}
	public void setSlug(String slug) {
		this.slug = slug;
	}
	public String getRemark() {
		return remark;
	}
	public void setRemark(String remark) {
		this.remark = remark;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public byte getStatus() {
		return status;
	}
	public void setStatus(byte status) {
		this.status = status;
	}
	public String getImage() {
		return image;
	}
	public void setImage(String image) {
		this.image = image;
	}
	public String getText() {
		return text;
	}
	public void setText(String text) {
		this.text = text;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}
	public String getsClassification() {
		String tmp = this.getClassification();
		switch (tmp) {
		case "START":return "APP启动广告";
		case "REGISTER":return "APP注册协议";
		case "ACTIVITY":return "APP活动";
		case "QUESTION":return "常见问题";
		default:
			break;
		}
		return tmp;
	}
	public String getsContentType() {
	    String tmp = this.getTextType();
	    switch (tmp) {
		case "IMAGE":return "图片";
		case "TEXT":return "文本";
		case "URL":return "链接";
		default:
			break;
		}
		return tmp;
	}
	public String getTransientStatus() {
		return transientStatus;
	}
	public void setTransientStatus(String transientStatus) {
		this.transientStatus = transientStatus;
	}
	
}
