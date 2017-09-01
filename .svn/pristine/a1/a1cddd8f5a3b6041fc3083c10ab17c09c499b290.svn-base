package com.iycharge.server.domain.service.impl;

import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.admin.Role;
import com.iycharge.server.domain.repository.RoleRepository;
import com.iycharge.server.domain.service.RoleService;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
public class RoleServiceImpl implements RoleService{
	@Autowired
	RoleRepository roleRepository;
	@Override
	public Page<Role> findAll(Pageable pageable) {
		return roleRepository.findAll(pageable);
	}
	
	public List<Role> findByManagers(List<Manager> list) {
		return roleRepository.findByManagers(list);
	}
	
	@Override
	public Role findById(long id) {
		return roleRepository.findOne(id);
	}

	@Override
	public Role save(Role role) {
		return roleRepository.save(role);
	}

	@Override
	public List<Role> findListAll() {
		return roleRepository.findAll();
	}

	@Override
	public void del(Long id) {
		roleRepository.delete(id);
	}

	@Override
	public Page<Role> findAllSearch(String[] fields, Role role, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}


}
