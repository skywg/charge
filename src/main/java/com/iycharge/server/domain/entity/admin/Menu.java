package com.iycharge.server.domain.entity.admin;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Set;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity;
import com.iycharge.server.domain.entity.BaseEntity.Summary;

/**
 * 菜单实体类
 * @author zw
 */
@Entity
@Table(name="menu")
public class Menu extends BaseEntity {
    
    /**
     * 菜单名称
     */
    @JsonView(Summary.class)
    private String menuName;
    
    /**
     * 链接访问地址
     */
    @JsonView(Private.class)
    private String href;
    
    /**
     * 描述
     */
    @JsonView(Summary.class)
    private String description;
    
    /**
     * 父Id
     */
    private long parentId;
    
    
    @JsonView(Summary.class)
    @ManyToMany(fetch = FetchType.EAGER,cascade={CascadeType.REFRESH}, mappedBy="menu")
    private List<Role> roles;

    @Transient
    private List<Menu> children = new ArrayList<Menu>();
    
	public String getMenuName() {
		return menuName;
	}


	public void setMenuName(String menuName) {
		this.menuName = menuName;
	}


	public String getHref() {
		return href;
	}


	public void setHref(String href) {
		this.href = href;
	}


	public String getDescription() {
		return description;
	}


	public void setDescription(String description) {
		this.description = description;
	}


	public long getParentId() {
		return parentId;
	}


	public void setParentId(long parentId) {
		this.parentId = parentId;
	}


	public List<Role> getRoles() {
		return roles;
	}


	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}


	public List<Menu> getChildren() {
		return children;
	}


	public void setChildren(List<Menu> children) {
		this.children = children;
	}
    
    
}
