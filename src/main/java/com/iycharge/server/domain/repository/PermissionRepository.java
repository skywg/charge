package com.iycharge.server.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iycharge.server.domain.entity.admin.Menu;
import com.iycharge.server.domain.entity.admin.Permission;
import com.iycharge.server.domain.entity.admin.Role;

@Repository
public interface PermissionRepository extends JpaRepository<Permission, Long> {

	Permission findByPkey(String pkey);
	
	List<Permission> findByMenu(Menu menu);
	
	List<Permission> findByPkeyIn(List<String> ids);
	
	List<Permission> findByRoles(List<Role> lists);

	List<Permission> findByRolesIn(List<Role> lists);

	List<Permission> findByClickIdIsNull();

	List<Permission> findByMenuAndClickIdIsNotNull(Menu menu);
}
