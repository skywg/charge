package com.iycharge.server.domain.entity.notification;

import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;

import javax.persistence.Column;
import javax.persistence.Embeddable;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;

@Embeddable
public class NotificationTarget {

    @Column(name = "target_type")
    @Enumerated(EnumType.STRING)
    @JsonView(BaseEntity.Summary.class)
    private NotificationTargetType type;

    @Column(name = "target_title")
    @JsonView(BaseEntity.Summary.class)
    private String title;

    @Column(name = "target_data")
    @JsonView(BaseEntity.Summary.class)
    private String data;

    public NotificationTarget(NotificationTargetType type, String title, String data) {
        this.type = type;
        this.title = title;
        this.data = data;
    }

    public NotificationTarget() {
    }

    public NotificationTargetType getType() {
        return type;
    }

    public void setType(NotificationTargetType type) {
        this.type = type;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
