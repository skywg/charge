package com.iycharge.server.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iycharge.server.domain.entity.dict.DictCategory;
import com.iycharge.server.domain.entity.dict.DictData;
import com.iycharge.server.domain.repository.DictDataRepository;
import com.iycharge.server.domain.service.DictDataService;

@Service
public class DictDataServiceImpl implements DictDataService {

	@Autowired
	private DictDataRepository dictDataRepository;
	@Override
	public Page<DictData> findAll(Pageable pageable) {
		return dictDataRepository.findAll(pageable);
	}
	@Override
	@Transactional
	public void delById(Long id) {
		dictDataRepository.deleteDictDataById(id);
	}
	@Override
	public DictData save(DictData dictData) {
		return dictDataRepository.save(dictData);
	}
	@Override
	public DictData findById(Long id) {
		DictData dictData = dictDataRepository.findOne(id);
		System.out.println(dictData.getDictDataList());
		return dictData;
	}
	@Override
	public List<DictData> findAll() {
		return dictDataRepository.findAll();
	}
	@Override
	public Page<DictData> findByDictCategory(Pageable pageable, DictCategory dict) {
		
		return dictDataRepository.findByDictCategory(pageable, dict);
	}
	@Override
	public List<DictData> findByDictCategory(DictCategory dict) {
		
		return dictDataRepository.findByDictCategory(dict);
	}
	@Override
	public void del(DictData dictData) {
		dictDataRepository.delete(dictData);
		
	}
	@Override
	public DictData findByDictKey(String dictKey) {
		// TODO Auto-generated method stub
		return dictDataRepository.findByDictKey(dictKey);
	}

}
