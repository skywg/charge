package com.iycharge.server.domain.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.web.bind.annotation.ResponseBody;

import com.iycharge.server.domain.entity.price.ParamTemplate;
import com.iycharge.server.domain.entity.price.ParamTemplateAttr;

@ResponseBody
public interface ParamTemplateAttrRepository extends JpaRepository<ParamTemplateAttr, Long>{
	public List<ParamTemplateAttr> findByTemplate(ParamTemplate template);
}
