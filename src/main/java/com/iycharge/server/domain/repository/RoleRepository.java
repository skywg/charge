package com.iycharge.server.domain.repository;

import com.iycharge.server.domain.entity.admin.Manager;
import com.iycharge.server.domain.entity.admin.Role;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface RoleRepository extends JpaRepository<Role, Long> {
	List<Role> findByManagers(List<Manager> list);

}
