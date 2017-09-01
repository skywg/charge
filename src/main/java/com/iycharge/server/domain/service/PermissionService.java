package com.iycharge.server.domain.service;


import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iycharge.server.domain.entity.admin.Menu;
import com.iycharge.server.domain.entity.admin.Permission;
import com.iycharge.server.domain.entity.admin.Role;

public interface PermissionService {

    public Page<Permission> findAll(Pageable pageable);

    public Permission findById(long id);

    public Permission save(Permission permission);

	List<Permission> findListAll();
	
	List<Permission> findByMenu(Menu menu);
	
	void del(Long id);
	
	Page<Permission> findAllSearch(String[] fields, Permission permission, Pageable pageable);

	Permission findByPkey(String pkey);
	
	List<Permission> findByPkeyIn(List<String> ids);

	List<Permission> findByClickIdIsNull();

	List<Permission> findByRoles(List<Role> lists);
}
