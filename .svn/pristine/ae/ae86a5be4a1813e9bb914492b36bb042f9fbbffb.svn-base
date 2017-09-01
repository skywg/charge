package com.iycharge.server.domain.service.impl;

import com.iycharge.server.domain.entity.admin.Menu;
import com.iycharge.server.domain.entity.admin.Permission;
import com.iycharge.server.domain.entity.admin.Role;
import com.iycharge.server.domain.repository.PermissionRepository;
import com.iycharge.server.domain.service.PermissionService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
public class PermissionServiceImpl implements PermissionService{
	@Autowired
	PermissionRepository permissionRepository;
	@Override
	public Page<Permission> findAll(Pageable pageable) {
		return permissionRepository.findAll(pageable);
	}

	@Override
	public Permission findById(long id) {
		return permissionRepository.findOne(id);
	}
	
	@Override
	public Permission findByPkey(String pkey) {
		return permissionRepository.findByPkey(pkey);
	}

	@Override
	public Permission save(Permission permission) {
		return permissionRepository.save(permission);
	}

	@Override
	public List<Permission> findListAll() {
		return permissionRepository.findAll();
	}
	@Override
	public List<Permission> findByMenu(Menu menu) {
		return permissionRepository.findByMenuAndClickIdIsNotNull(menu);
	}
	
	@Override
	public List<Permission> findByRoles(List<Role> lists) {
		return permissionRepository.findByRoles(lists);
	}
	
	@Override
	public void del(Long id) {
		permissionRepository.delete(id);
	}

	@Override
	public Page<Permission> findAllSearch(String[] fields, Permission permission, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public List<Permission> findByPkeyIn(List<String> ids) {
		return permissionRepository.findByPkeyIn(ids);
	}
	@Override
	public List<Permission> findByClickIdIsNull() {
		return permissionRepository.findByClickIdIsNull();
	}
}
