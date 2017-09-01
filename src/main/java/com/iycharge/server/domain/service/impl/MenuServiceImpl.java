package com.iycharge.server.domain.service.impl;

import com.iycharge.server.domain.entity.admin.Menu;
import com.iycharge.server.domain.repository.MenuRepository;
import com.iycharge.server.domain.service.MenuService;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
@Service
public class MenuServiceImpl implements MenuService{
	@Autowired
	MenuRepository menuRepository;
	@Override
	public Page<Menu> findAll(Pageable pageable) {
		return menuRepository.findAll(pageable);
	}

	@Override
	public Menu findById(long id) {
		return menuRepository.findOne(id);
	}
	
	@Override
	public List<Menu> findByIdIn(List<Long> ids) {
		return menuRepository.findByIdIn(ids);
	}

	@Override
	public Menu save(Menu menu) {
		return menuRepository.save(menu);
	}

	@Override
	public List<Menu> findListAll() {
		return menuRepository.findAll();
	}

	@Override
	public void del(Long id) {
		menuRepository.delete(id);
	}

	@Override
	public Page<Menu> findAllSearch(String[] fields, Menu menu, Pageable pageable) {
		// TODO Auto-generated method stub
		return null;
	}


}
