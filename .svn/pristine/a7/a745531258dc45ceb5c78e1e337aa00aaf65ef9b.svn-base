package com.iycharge.server.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.Query;

import com.iycharge.server.domain.entity.dict.DictCategory;
import com.iycharge.server.domain.entity.event.Event;

public interface DictCategoryService {
	public Page<DictCategory> findAll(Pageable pageable);

	public void delById(Long id);
	
	public DictCategory save(DictCategory dictCategory);
	
	public DictCategory findById(Long id);
	
    public List<DictCategory> findAll();
   
    public List<DictCategory> findByIds(long id);
    
    public Page<DictCategory> findAllSearch(final String name, final DictCategory dictCategory, Pageable pageable);
    
    public DictCategory findByName(String name);
}
