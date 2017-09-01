package com.iycharge.server.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import com.iycharge.server.domain.entity.price.ParamTemplate;
import com.iycharge.server.domain.entity.price.ParamTemplateQueryParam;
import com.iycharge.server.domain.entity.price.PriceTemplate;

public interface ParamTemplateService {
	public Page<ParamTemplate> findAll(Pageable pageable);
	public Page<ParamTemplate> findByDelStatus(String delStatus,Pageable pageable);
	Page<ParamTemplate> find(ParamTemplateQueryParam queryParam, Pageable pageable);
	public ParamTemplate findById(long id);
	void del(ParamTemplate paramTemplate);
	public ParamTemplate save(ParamTemplate template);
	List<ParamTemplate> findByType(String type);
	/**
     * 查询指定电桩当前的电价
     * @param chargerId
     * @return
     */
	ParamTemplate findChargerPrice(Long chargerId);
	ParamTemplate findChargerParam(Long chargerId);
}
