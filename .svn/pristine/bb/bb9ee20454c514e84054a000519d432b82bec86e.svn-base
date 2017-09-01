package com.iycharge.server.domain.service;

import com.iycharge.server.domain.entity.admin.Menu;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

public interface MenuService {

    public Page<Menu> findAll(Pageable pageable);

    public Menu findById(long id);

    public Menu save(Menu menu);

	List<Menu> findListAll();
	
	void del(Long id);

	Page<Menu> findAllSearch(String[] fields, Menu menu, Pageable pageable);

	List<Menu> findByIdIn(List<Long> ids);

}
