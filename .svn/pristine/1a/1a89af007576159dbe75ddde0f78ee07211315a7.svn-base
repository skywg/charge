package com.iycharge.server.domain.service;

import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.admin.Role;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface RoleService {

    public Page<Role> findAll(Pageable pageable);

    public Role findById(long id);

    public Role save(Role role);

	List<Role> findListAll();
	
	public List<Role> findByManagers(List<Manager> list);
	
	void del(Long id);

	Page<Role> findAllSearch(String[] fields, Role role, Pageable pageable);

}
