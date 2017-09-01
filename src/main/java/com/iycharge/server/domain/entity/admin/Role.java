package com.iycharge.server.domain.entity.admin;

import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
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
@Table(name="role")
public class Role{
    
    /**
     * 角色ID
     */
    @JsonView(Summary.class)
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private long roleId;
    
    /**
     * 角色名
     */
    @JsonView(Summary.class)
    private String roleName;
    
    /**
     * 角色描述
     */
    @JsonView(Summary.class)
    private String roleDescr;
    
    /**
     * 
     */
    @ManyToMany(mappedBy="roles",fetch = FetchType.EAGER, cascade=CascadeType.REFRESH)
    private List<Manager> managers;
    
    @JsonIgnore
    @ManyToMany(cascade={CascadeType.ALL})
    @JoinTable(name="role_permission", 
            inverseJoinColumns=@JoinColumn(name="pkey"),
            joinColumns=@JoinColumn(name="role_id"))
    private List<Permission> permissions;
    @Transient
    private String temppermission;
    @Transient
    private String[] formtemppermission;
    @JsonIgnore
    @ManyToMany(cascade={CascadeType.REFRESH})
    @JoinTable(name="role_menu", 
            inverseJoinColumns=@JoinColumn(name="id"),
            joinColumns=@JoinColumn(name="role_id"))
    private List<Menu> menu;
    
    @Transient
    private boolean flag=false;
    
    @Transient
    private String tempMenu;
    @Transient
    private String tempManager;
    public Role() {
        
    }
    
    public String getRoleName() {
        return roleName;
    }

    public void setRoleName(String roleName) {
        this.roleName = roleName;
    }

    public String getRoleDescr() {
        return roleDescr;
    }

    public void setRoleDescr(String roleDescr) {
        this.roleDescr = roleDescr;
    }

	public long getRoleId() {
		return roleId;
	}

	public void setRoleId(long roleId) {
		this.roleId = roleId;
	}

	public List<Manager> getManagers() {
		return managers;
	}

	public void setManagers(List<Manager> managers) {
		this.managers = managers;
	}

	public List<Permission> getPermissions() {
		return permissions;
	}

	public void setPermissions(List<Permission> permissions) {
		this.permissions = permissions;
	}

	public List<Menu> getMenu() {
		return menu;
	}

	public void setMenu(List<Menu> menu) {
		this.menu = menu;
	}

	public boolean isFlag() {
		return flag;
	}

	public void setFlag(boolean flag) {
		this.flag = flag;
	}

	public String getTempMenu() {
		return tempMenu;
	}

	public void setTempMenu(String tempMenu) {
		this.tempMenu = tempMenu;
	}

	public String getTempManager() {
		return tempManager;
	}

	public void setTempManager(String tempManager) {
		this.tempManager = tempManager;
	}

	public String getTemppermission() {
		return temppermission;
	}

	public void setTemppermission(String temppermission) {
		this.temppermission = temppermission;
	}

	public String[] getFormtemppermission() {
		return formtemppermission;
	}

	public void setFormtemppermission(String[] formtemppermission) {
		this.formtemppermission = formtemppermission;
	}
    
}
