package com.iycharge.server.domain.service.impl;

import java.util.List;

import org.elasticsearch.common.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.repository.ManagerRepository;
import com.iycharge.server.domain.service.ManagerService;

/**
 * 系统账户管理业务接口实现类
 * @author bwang
 */
@Service
public class ManagerServiceImpl implements ManagerService{
    
    @Autowired
    ManagerRepository managerRepository;
    
    @Override
    public Manager save(Manager manager) { 
    	
        return managerRepository.saveAndFlush(manager);
    }

    @Override
    public Manager findByLoginName(String loginName) {
        return managerRepository.findByLoginName(loginName);
    }
    @Override
    public List<Manager> findByLoginNameIn(List<String> loginName) {
        return managerRepository.findByLoginNameIn(loginName);
    }
    
    @Override
    public Page<Manager> findAll(Pageable pageable) {
        return managerRepository.findAll(pageable);
    }

	@Override
	public void del(String loginName) {
		managerRepository.delete(loginName);
		
	}


	@Override
	public List<Manager> findAll() {
		return managerRepository.findAll();
	}

	@Override
	public Page<Manager> findByLoginName(String loginName, Pageable pageable) {
		String newname=loginName;
		if(StringUtils.isEmpty(newname)){
			return managerRepository.findAll(pageable);
		}else{
			newname = loginName.trim();
		}
		return managerRepository.findByRealnameContaining(newname, pageable);
	}
}
