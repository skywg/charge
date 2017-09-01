package com.iycharge.server.domain.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.iycharge.server.domain.entity.price.ParamTemplate;
import com.iycharge.server.domain.entity.price.ParamTemplateAttr;
import com.iycharge.server.domain.repository.ParamTemplateAttrRepository;
import com.iycharge.server.domain.service.ParamTemplateAttrService;

@Transactional(readOnly=true)
@Service
public class ParamTemplateAttrServiceImpl implements ParamTemplateAttrService {
	@Autowired
	private ParamTemplateAttrRepository paramTemplateAttrRepository;

	@Override
	public List<ParamTemplateAttr> findByTemplate(ParamTemplate template) {

		return paramTemplateAttrRepository.findByTemplate(template);
	}
	
	@Transactional(readOnly=false)
	@Override
	public void delete(ParamTemplateAttr paramTemplateAttr) {
		paramTemplateAttrRepository.delete(paramTemplateAttr);

	}
	
	@Transactional(readOnly=false)
	@Override
	public ParamTemplateAttr save(ParamTemplateAttr template) {
		return paramTemplateAttrRepository.save(template);
	}

	@Override
	public void save(List<ParamTemplateAttr> paramTemplateAttrs) {
		// TODO Auto-generated method stub
		paramTemplateAttrRepository.save(paramTemplateAttrs);
	}
	
	@Transactional(readOnly=false)
	@Override
	public void delete(List<ParamTemplateAttr> paramTemplateAttrs) {
		// TODO Auto-generated method stub
		paramTemplateAttrRepository.delete(paramTemplateAttrs);
	}
}
