package com.iycharge.server.domain.repository;

import com.iycharge.server.domain.entity.admin.Menu;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface MenuRepository extends JpaRepository<Menu, Long> {

	List<Menu> findByIdIn(List<Long> ids);
	
	
}
