package com.iycharge.server.domain.repository;
import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.iycharge.server.domain.entity.price.ParamSetting;
import com.iycharge.server.domain.entity.price.ParamSettingResult;

@Repository
public interface ParamSettingResultRepository extends JpaRepository<ParamSettingResult, Long> {
	public Page<ParamSettingResult> findAll(Specification<ParamSettingResult> spec, Pageable pageable);  //分页按条件查询

	public Page<ParamSettingResult> findByDelStatus(String string, Pageable pageable);
	
	public List<ParamSettingResult> findByDelStatusAndParamSetting(String string,ParamSetting paramSetting);
}
