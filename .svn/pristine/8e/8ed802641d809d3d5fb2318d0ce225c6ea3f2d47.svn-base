package com.iycharge.server.domain.entity.admin;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Transient;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
import com.iycharge.server.domain.entity.BaseEntity.Summary;

/**
 *
 * @author bwang
 */
@Entity
@Table(name="permission")
public class Permission{
    
    /**
     * 权限key，用于判断是否具有某权限的关键字符串
     */
    @Id
    @JsonView(Summary.class)
    private String pkey;
    
    /**
     * 权限名称
     */
    @JsonView(Summary.class)
    private String pName;
    
    /**
     * 权限描述
     */
    @JsonView(Summary.class)
    private String pDescr;
    
    /**
     * 父权限节点
     */
    @JsonView(Summary.class)
    private String parentKey;
    
    /**
     * 访问权限url
     */
    @JsonView(Summary.class)
    private String uri;
    
    /**
     * 按钮或者URl的访问ID
     */
    @JsonView(Summary.class)
    private String clickId;
    
    @JsonView(Summary.class)
    @ManyToMany(fetch = FetchType.LAZY,cascade={CascadeType.ALL}, mappedBy="permissions")
    private List<Role> roles;
    /*
     * 所属菜单
     */
    //@JsonView(Detail.class)
    @JsonIgnore
    @ManyToOne(fetch = FetchType.EAGER, cascade = {CascadeType.MERGE, CascadeType.REFRESH})
    private Menu menu = new Menu();
    @Transient
    private String[] role;
    
    public Permission() {
        
    }

    public String getPkey() {
        return pkey;
    }

    public void setPkey(String pkey) {
        this.pkey = pkey;
    }

    public String getpName() {
        return pName;
    }

    public void setpName(String pName) {
        this.pName = pName;
    }

    public String getpDescr() {
        return pDescr;
    }

    public void setpDescr(String pDescr) {
        this.pDescr = pDescr;
    }

    public String getParentKey() {
        return parentKey;
    }

    public void setParentKey(String parentKey) {
        this.parentKey = parentKey;
    }

	public String getUri() {
		return uri;
	}

	public void setUri(String uri) {
		this.uri = uri;
	}

	public String getClickId() {
		return clickId;
	}

	public void setClickId(String clickId) {
		this.clickId = clickId;
	}

	public List<Role> getRoles() {
		return roles;
	}

	public void setRoles(List<Role> roles) {
		this.roles = roles;
	}

	public String[] getRole() {
		return role;
	}

	public void setRole(String[] role) {
		this.role = role;
	}

	public Menu getMenu() {
		return menu;
	}

	public void setMenu(Menu menu) {
		this.menu = menu;
	}

	
}
