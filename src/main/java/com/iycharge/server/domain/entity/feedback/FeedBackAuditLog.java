package com.iycharge.server.domain.entity.feedback;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.BaseEntity.Summary;
import com.iycharge.server.domain.entity.admin.Manager;

/**
 * 反馈问题处理日志
 * 
 * @author bwang
 */
@Entity
@Table(name="feedback_audit_log")
public class FeedBackAuditLog extends BaseEntity {

    /**
     * 关联反馈记录
     */
    @ManyToOne(cascade = CascadeType.REFRESH)
    private FeedBack feedback;

    /**
     * 处理人
     */
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "loginName")
    private Manager manager;

    /**
     * 状态
     */
    @JsonView(Summary.class)
    @Enumerated(EnumType.STRING)
    private FeedBackStatus status;

    /**
     * 内容
     */
    @JsonView(Summary.class)
    private String content;

    public FeedBackAuditLog() {

    }

    public FeedBack getFeedback() {
        return feedback;
    }

    public void setFeedback(FeedBack feedback) {
        this.feedback = feedback;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public FeedBackStatus getStatus() {
        return status;
    }

    public void setStatus(FeedBackStatus status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
