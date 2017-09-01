package com.iycharge.server.domain.entity.event;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;

/**
 * 告警级别
 * @author bwang
 */
@Entity
@Table(name="event_level")
public class EventLevel {
    
    /*
     * 告警级别
     */
    @Id
    @JsonView(BaseEntity.Summary.class)
    private int level;
    
    /*
     * 级别名称
     */
    @JsonView(BaseEntity.Summary.class)
    @Column(unique = true)
    private String name;
    
    /*
     * 级别描述
     */
    @JsonView(BaseEntity.Summary.class)
    private String description;
    
    /*
     * 是否启用
     */
    @JsonView(BaseEntity.Summary.class)
    @Column(nullable = false, columnDefinition = "BOOLEAN default 1")
    private Boolean active = true; 
    
    @JsonIgnore
    @OneToMany(mappedBy = "eventLevel", cascade = {CascadeType.MERGE, CascadeType.REFRESH}, fetch = FetchType.LAZY)
    private List<EventCode> codeList = new ArrayList<>();
    
    public EventLevel() {
        
    }

    public int getLevel() {
        return level;
    }

    public void setLevel(int level) {
        this.level = level;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return active;
    }

    public void setActive(Boolean active) {
        this.active = active;
    }
}
