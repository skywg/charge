package com.iycharge.server.domain.service.impl;

import java.util.List;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Path;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iycharge.server.domain.entity.dict.DictCategory;
import com.iycharge.server.domain.entity.event.Event;
import com.iycharge.server.domain.repository.DictCategoryRepository;
import com.iycharge.server.domain.service.DictCategoryService;

@Service
public class DictCategoryServiceImpl implements DictCategoryService {

	@Autowired
	private DictCategoryRepository dictCategoryRepository;
	@Override
	public Page<DictCategory> findAll(Pageable pageable) {
		
		return dictCategoryRepository.findAll(pageable);
	}
	@Override
	@Transactional
	public void delById(Long id) {
		dictCategoryRepository.deleteById(id);
	}
	@Override
	public DictCategory save(DictCategory dictCategory) {
		return dictCategoryRepository.save(dictCategory);
	}
	@Override
	public DictCategory findById(Long id) {
		DictCategory dictCategory = dictCategoryRepository.findOne(id);
		System.out.println(dictCategory.getDictCategoryList());
		System.out.println(dictCategory.getDictDataList());
		return dictCategoryRepository.findOne(id);
	}
	@Override
	public List<DictCategory> findAll() {
		return dictCategoryRepository.findAll();
	}
	@Override
	public List<DictCategory> findByIds(long id) {
		// TODO Auto-generated method stub
		return dictCategoryRepository.findByIds(id);
	}
	@Override
	public Page<DictCategory> findAllSearch(final String name, DictCategory dictCategory, Pageable pageable) {		
		Specification<DictCategory> spec = new Specification<DictCategory>() {
			@Override
			public Predicate toPredicate(Root<DictCategory> root, CriteriaQuery<?> query, CriteriaBuilder cb) {
				Predicate ps = null;
				Path<String> nick = root.get("descr");
				ps = cb.like(nick, "%"+name+"%");
				if(ps != null){
					query.where(ps);
				}
				return null;
			}
		};
		
		return dictCategoryRepository.findAll(spec,pageable);
	}
	@Override
	public DictCategory findByName(String name) {
		// TODO Auto-generated method stub
		return dictCategoryRepository.findByName(name);
	}
	
	

}
