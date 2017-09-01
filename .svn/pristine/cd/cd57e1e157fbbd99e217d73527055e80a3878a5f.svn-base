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
import com.iycharge.server.domain.entity.BaseEntity.Private;
import com.iycharge.server.domain.entity.BaseEntity.Summary;

/**
 * 系统管理员实体类
 * @author bwang
 */
@Entity
@Table(name="manager")
public class Manager {
    
    /**
     * 系统登录名
     */
    @Id
    @JsonView(Summary.class)
    private String loginName;
    
    /**
     * 登录密码
     */
    @JsonView(Private.class)
    private String password;
    
    /**
     * 真实姓名
     */
    @JsonView(Summary.class)
    private String realname;
    
    /**
     * 用户邮箱地址
     */
    @JsonView(Summary.class)
    private String email;
    
    
    /**
     * 用户联系电话
     */
    @JsonView(Summary.class)
    private String telephone;
    
    /**
     * 用户状态, 0 : 禁用， 1 : 启用  
     */
    @JsonView(Summary.class)
    @Column(nullable = false, columnDefinition = "BOOLEAN default 1")
    private Boolean status = true;
    
    public String getDis() {
		 if(status==true){
		    return "正常";
		}else{
			return "违规";
		}
	}

	public void setDis(String dis) {
		this.dis = dis;
	}

	/**
     * 临时变量显示状态
     */
    @Transient
    @JsonView(Summary.class)
    private String dis;
    
    /**
     * 注册时间
     */
    @JsonView(Summary.class)
    private Date registerTime;
    
    /**
     * 最后一次登录时间
     */
    @JsonView(Summary.class)
    private Date lastLoginTime;
    
    /**
     * 最后一次登录IP
     */
    @JsonView(Summary.class)
    private String lastLoginIP;
    
    /**
     *  用户拥有的角色
     */
    @JsonIgnore
    @ManyToMany(cascade={CascadeType.REFRESH})
    @JoinTable(name="manager_role", 
            inverseJoinColumns=@JoinColumn(name="role_id"),
            joinColumns=@JoinColumn(name="login_name"))
    private List<Role> roles;
    @Transient
    private String[] role;
    @Transient
    private List<RoleBean> roleBeanList;
    @Transient
    private String flag;
    public Manager() {
        
        
    }

    public String getLoginName() {
        return loginName;
    }

    public void setLoginName(String loginName) {
        this.loginName = loginName;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getRealname() {
        return realname;
    }

    public void setRealname(String realname) {
        this.realname = realname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getTelephone() {
        return telephone;
    }

    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }

    public Boolean getStatus() {
        return status;
    }

    public void setStatus(Boolean status) {
        this.status = status;
    }

    public Date getRegisterTime() {
        return registerTime;
    }

    public void setRegisterTime(Date registerTime) {
        this.registerTime = registerTime;
    }

    public Date getLastLoginTime() {
        return lastLoginTime;
    }

    public void setLastLoginTime(Date lastLoginTime) {
        this.lastLoginTime = lastLoginTime;
    }

    public String getLastLoginIP() {
        return lastLoginIP;
    }

    public void setLastLoginIP(String lastLoginIP) {
        this.lastLoginIP = lastLoginIP;
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

	public List<RoleBean> getRoleBeanList() {
		return roleBeanList;
	}

	public void setRoleBeanList(List<RoleBean> roleBeanList) {
		this.roleBeanList = roleBeanList;
	}

	public String getFlag() {
		return flag;
	}

	public void setFlag(String flag) {
		this.flag = flag;
	}

}
