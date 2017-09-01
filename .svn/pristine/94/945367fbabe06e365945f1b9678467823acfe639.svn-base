package com.iycharge.server.domain.service;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import com.iycharge.server.domain.entity.price.ParamSetting;
import com.iycharge.server.domain.entity.price.ParamSettingResult;

public interface ParamSettingResultService {

	Page<ParamSettingResult> findAll(Pageable pageable);
	
	List<ParamSettingResult> findByParamSetting(ParamSetting paramSetting);

	ParamSettingResult save(ParamSettingResult paramSettingResult);

	ParamSettingResult findById(long id);
	
	Page<ParamSettingResult> findAllSearch(String[] fields, ParamSettingResult paramSettingResult, Pageable pageable);
	
	/**
	 * 查找电桩某类型参数最近一次的设置结果
	 * @param chargerCode      电桩编号
	 * @param paramType        参数类型
	 * @return
	 */
	ParamSettingResult findLatestResult(String chargerCode, String paramType); 
}
