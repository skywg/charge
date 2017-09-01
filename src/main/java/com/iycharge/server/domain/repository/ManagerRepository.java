package com.iycharge.server.domain.repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import com.iycharge.server.domain.entity.admin.Manager;

/**
 * 系统管理DAO
 * @author bwang
 */
public interface ManagerRepository extends JpaRepository<Manager, String>{
    
    /**
     * 根据系统用户登录名查找用户信息
     * @param loginName 系统用户登录名
     * @return
     */
    public Manager findByLoginName(String loginName);
    
    public List<Manager> findByLoginNameIn(List<String> loginNames);
    
    public Page<Manager> findByRealnameContaining(String loginName,Pageable pageable);
    
    @Override
	public Page<Manager> findAll(Pageable pageable);
    
    @Override
	void delete(String loginName);
    
    @Override
	public List<Manager> findAll();
	
    @Override
	public Manager save(Manager manager);
}
