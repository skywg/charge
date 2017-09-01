package com.iycharge.server.domain.entity.event;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.admin.Manager;

/**
 * 反馈问题处理日志
 * 
 * @author bwang
 */
@Entity
@Table(name="event_audit_log")
public class EventAuditLog extends BaseEntity {

    /**
     * 关联告警事件
     */
    @ManyToOne(cascade = CascadeType.REFRESH)
    private Event event;

    /**
     * 处理人
     */
    @ManyToOne(cascade = CascadeType.REFRESH)
    @JoinColumn(name = "loginName")
    private Manager manager;

    /**
     * 状态
     */
    @Enumerated(EnumType.STRING)
    private EventStatus status;

    /**
     * 内容
     */
    private String content;

    public EventAuditLog() {

    }

    public Event getEvent() {
        return event;
    }

    public void setEvent(Event event) {
        this.event = event;
    }

    public Manager getManager() {
        return manager;
    }

    public void setManager(Manager manager) {
        this.manager = manager;
    }

    public EventStatus getStatus() {
        return status;
    }

    public void setStatus(EventStatus status) {
        this.status = status;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
