package com.iycharge.server.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iycharge.server.domain.entity.dict.DictCategory;
import com.iycharge.server.domain.entity.dict.DictData;

public interface DictDataService {
	public Page<DictData> findAll(Pageable pageable);

	public void delById(Long id);
	
	public void del(DictData dictData);
	
	public DictData save(DictData DictData);
	
	public DictData findById(Long id);
	
	public List<DictData> findAll(); 
	
	public Page<DictData> findByDictCategory(Pageable pageable,DictCategory dict);

	public List<DictData> findByDictCategory(DictCategory dict);

	public DictData findByDictKey(String dictKey);
}
