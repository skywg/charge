package com.iycharge.server.domain.entity.notification;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;

/**
 * 系统消息
 * @author bwang
 */
@Entity
public class SystemMessage extends BaseEntity {
    
    /**
     * 标题
     */
    
    @JsonView(Summary.class)
    private String title;
    
    /**
     * 摘要
     */
    @JsonView(Summary.class)
    private String summary;
    
    /**
     * 内容
     */
    @JsonView(Summary.class)
    private String content;
    
    /**
     * 备注
     */
    @JsonIgnore
    private String remark;
    
    /**
     * 发布人
     */
    @JsonIgnore
    private String publisher;
    
    /**
     * 消息是否已被阅读
     */
    @Transient
    private boolean isRead;
    
    @OneToMany(cascade=CascadeType.ALL, fetch=FetchType.LAZY, mappedBy="systemMessage")
    private List<SystemMessageReader> readerList;
    
    public SystemMessage() {
        
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getRemark() {
        return remark;
    }

    public void setRemark(String remark) {
        this.remark = remark;
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher;
    }

    public boolean isRead() {
        return isRead;
    }

    public void setRead(boolean isRead) {
        this.isRead = isRead;
    }
}
