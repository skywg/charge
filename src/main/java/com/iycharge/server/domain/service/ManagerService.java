package com.iycharge.server.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.iycharge.server.domain.entity.admin.Manager;

/**
 * 系统账户管理业务接口
 * @author bwang
 */
public interface ManagerService {
    
    /**
     * 保存用户信息
     * @param manager
     * @return
     */
    public Manager save(Manager manager);
    
    /**
     * 根据系统用户登录名查询用户登录信息
     * @param loginName     系统用户登录名
     * @return
     */
    public Manager findByLoginName(String loginName);
    
    public Page<Manager> findByLoginName(String loginName,Pageable pageable);

    /**
     * 查询所有
     * @param pageable
     * @return
     */
	public Page<Manager> findAll(Pageable pageable);
	
	public List<Manager> findAll();
	
    /**
     * 根据
     * @param id
     */
	void del(String loginName);

	List<Manager> findByLoginNameIn(List<String> loginName);
	
}
