package com.iycharge.server.domain.service;

import java.util.List;

import com.iycharge.server.domain.entity.price.ParamTemplate;
import com.iycharge.server.domain.entity.price.ParamTemplateAttr;

public interface ParamTemplateAttrService {
	
	public List<ParamTemplateAttr> findByTemplate(ParamTemplate template);

	public void delete(ParamTemplateAttr paramTemplateAttr);

	public ParamTemplateAttr save(ParamTemplateAttr template);
	
	void save(List<ParamTemplateAttr> paramTemplateAttrs);
	
	void delete(List<ParamTemplateAttr> paramTemplateAttrs);
	
}
